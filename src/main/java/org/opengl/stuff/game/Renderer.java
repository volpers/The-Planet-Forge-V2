package org.opengl.stuff.game;

import org.joml.Matrix4f;
import org.opengl.stuff.engine.Utils;
import org.opengl.stuff.engine.Window;
import org.opengl.stuff.engine.graph.GameObject;
import org.opengl.stuff.engine.graph.MatrixTransformations;
import org.opengl.stuff.engine.graph.Mesh;
import org.opengl.stuff.engine.graph.ShaderProgram;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;
    private ShaderProgram shaderProgram;
    private Window window;
    private Matrix4f projectionMatrix;
    private MatrixTransformations matrixTransformations;

    private List<GameObject> gameObjectList = new ArrayList<>();


    public Renderer() {
        matrixTransformations = new MatrixTransformations();
    }

    public void init(Window window) throws Exception {
        this.window = window;
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/fragment.fs"));
        shaderProgram.link();
        //initMatrizes(window);
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("worldMatrix");
    }

    private void initMatrizes(Window window) {
        float aspectRatio = (float) window.getWidth() / window.getHeight();
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);

    }

    public void addGameObject(GameObject gameObject) {
        gameObjectList.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjectList.remove(gameObject);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, Mesh mesh) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgram.bind();

        projectionMatrix = matrixTransformations.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);

        for (GameObject gameObject : gameObjectList) {
            Matrix4f worldMatrix = matrixTransformations.getWorldMatrix(gameObject.getPosition(), gameObject.getScale(), gameObject.getRotation());
            shaderProgram.setUniform("worldMatrix", worldMatrix);
            gameObject.render();
        }

        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
}
