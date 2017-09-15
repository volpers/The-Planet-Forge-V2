package org.opengl.stuff.engine.graph;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class TextureObject {

    private static final int BYTES_PER_PIXEL = 4;
    private static final int BYTES_PER_COLOR_COMPONENT = 1;

    private int vboId;

    public TextureObject(String fileName) throws IOException {
        readTexture(fileName);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, vboId);
    }

    private void readTexture(String path) throws IOException {
        PNGDecoder decoder = new PNGDecoder(org.lwjgl.openvr.Texture.class.getResourceAsStream(path));
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(decoder.getHeight() * decoder.getWidth() * BYTES_PER_PIXEL);
        decoder.decode(byteBuffer, decoder.getWidth() * BYTES_PER_PIXEL, PNGDecoder.Format.RGBA);
        byteBuffer.flip();

        vboId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, vboId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, BYTES_PER_COLOR_COMPONENT);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public void cleanUp() {
        glDeleteTextures(vboId);
    }

    public int getVboId() {
        return vboId;
    }
}
