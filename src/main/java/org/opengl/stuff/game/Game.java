package org.opengl.stuff.game;

import org.opengl.stuff.engine.IGameLogic;
import org.opengl.stuff.engine.Window;
import org.opengl.stuff.engine.graph.GameObject;
import org.opengl.stuff.engine.graph.Mesh;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

public class Game implements IGameLogic {

    private final Renderer renderer;
    private int direction = 0;
    private float color = 0.0f;
    private Mesh mesh;


    private Window window;
    private GameObject gameobject;

    public Game() {
        renderer = new Renderer();
    }

    @Override
    public void init(Window window) throws Exception {
        this.window = window;
        renderer.init(window);
        float[] positions = new float[]{
                // VO
                -0.5f, 0.5f, 0.5f,
                // V1
                -0.5f, -0.5f, 0.5f,
                // V2
                0.5f, -0.5f, 0.5f,
                // V3
                0.5f, 0.5f, 0.5f,
                // V4
                -0.5f, 0.5f, -0.5f,
                // V5
                0.5f, 0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,
        };
        float[] colors = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };
        int[] indices = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                4, 0, 3, 5, 4, 3,
                // Right face
                3, 2, 7, 5, 3, 7,
                // Left face
                6, 1, 0, 6, 0, 4,
                // Bottom face
                2, 1, 6, 2, 6, 7,
                // Back face
                7, 6, 4, 7, 4, 5,
        };
        Mesh mesh = new Mesh(positions, colors, indices);
        gameobject = new GameObject(mesh);
        gameobject.setPosition(0, 0, -2);
        renderer.addGameObject(gameobject);
    }

    @Override
    public void input(Window window) {
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            direction = 1;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            direction = -1;
        } else {
            direction = 0;
        }
    }

    @Override
    public void update(float interval) {
        color += direction * 0.01f;
        if (color > 1) {
            color = 1.0f;
        } else if (color < 0) {
            color = 0.0f;
        }

        float rotation = gameobject.getRotation().x + 1.5f;
        if (rotation > 360) {
            rotation = 0;
        }
        gameobject.setRotation(rotation, rotation, rotation);
    }

    @Override
    public void render(Window window) {
        window.setClearColor(color, color, color, 0.0f);
        renderer.render(window, mesh);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        gameobject.getMesh().cleanUp();
    }

}
