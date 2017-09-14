package org.opengl.stuff.engine.graph;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MatrixTransformations {
    private final Matrix4f projectionMatrix;
    private final Matrix4f worldMatrix;

    public MatrixTransformations() {

        projectionMatrix = new Matrix4f();
        worldMatrix = new Matrix4f();
    }

    public Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        float aspectRatio = width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
        return projectionMatrix;
    }

    public Matrix4f getWorldMatrix(Vector3f offset, float scale, Vector3f rotation) {
        worldMatrix.identity().translate(offset).
                rotateX((float) Math.toRadians(rotation.x)).
                rotateY((float) Math.toRadians(rotation.y)).
                rotateZ((float) Math.toRadians(rotation.z)).
                scale(scale);
        return worldMatrix;
    }
}
