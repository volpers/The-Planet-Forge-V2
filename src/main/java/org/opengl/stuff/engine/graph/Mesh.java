package org.opengl.stuff.engine.graph;

import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {

    private final int vaoId;

    private final int vertexCount;

    private List<Integer> vboIdList = new ArrayList<>();
    private TextureObject texture;

    public Mesh(float[] positions, float[] textureCoords, int[] indices, TextureObject texture) {
        this.texture = texture;
        FloatBuffer posBuffer = null;
        IntBuffer indicesBuffer = null;
        FloatBuffer textureCoordsBuffer = null;
        try {
            vertexCount = indices.length;

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            // Position VBO
            int posVboId = glGenBuffers();
            posBuffer = MemoryUtil.memAllocFloat(positions.length);
            posBuffer.put(positions).flip();
            glBindBuffer(GL_ARRAY_BUFFER, posVboId);
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            vboIdList.add(posVboId);

            // Color VBO
//            int colorVboId = glGenBuffers();
//            colorBuffer = MemoryUtil.memAllocFloat(colors.length);
//            colorBuffer.put(colors).flip();
//            glBindBuffer(GL_ARRAY_BUFFER, colorVboId);
//            glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
//            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
//            vboIdList.add(colorVboId);

            //TextureCoords VBO
            int textureCoordsVboId = glGenBuffers();
            textureCoordsBuffer = MemoryUtil.memAllocFloat(textureCoords.length);
            textureCoordsBuffer.put(textureCoords).flip();
            glBindBuffer(GL_ARRAY_BUFFER, textureCoordsVboId);
            glBufferData(GL_ARRAY_BUFFER, textureCoordsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
            vboIdList.add(textureCoordsVboId);
            // Index VBO
            int idxVboId = glGenBuffers();
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
            vboIdList.add(idxVboId);

        } finally {
            if (posBuffer != null) {
                MemoryUtil.memFree(posBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }

            if (textureCoordsBuffer != null) {
                MemoryUtil.memFree(textureCoordsBuffer);
            }
        }
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void cleanUp() {
        glDisableVertexAttribArray(0);

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        vboIdList.forEach(GL15::glDeleteBuffers);
        texture.cleanUp();

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    public void render() {
        glActiveTexture(GL_TEXTURE0);
        texture.bind();

        // Draw the mesh
        glBindVertexArray(getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }
}
