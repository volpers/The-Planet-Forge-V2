package org.opengl.stuff.engine.graph;

import org.joml.Vector3f;

public class GameObject {

    private final Mesh mesh;
    private final Vector3f position;
    private final Vector3f rotation;
    private float scale;

    public GameObject(Mesh mesh) {
        this.mesh = mesh;
        this.position = new Vector3f(0, 0, 0);
        this.scale = 1f;
        this.rotation = new Vector3f(0, 0, 0);
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void render() {
        mesh.render();
    }
}
