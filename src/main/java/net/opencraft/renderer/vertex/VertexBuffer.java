package net.opencraft.renderer.vertex;

import java.nio.ByteBuffer;

import net.opencraft.renderer.GlStateManager;
import net.opencraft.renderer.OpenGlHelper;

public class VertexBuffer {

	private int glBufferId;
	private final VertexFormat vertexFormat;
	private int count;

	public VertexBuffer(VertexFormat vertexFormatIn) {
		this.vertexFormat = vertexFormatIn;
		this.glBufferId = OpenGlHelper.glGenBuffers();
	}

	public void bindBuffer() {
		OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, this.glBufferId);
	}

	public void bufferData(ByteBuffer data) {
		this.bindBuffer();
		OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, data, 35044);
		this.unbindBuffer();
		this.count = data.limit() / this.vertexFormat.getSize();
	}

	public void drawArrays(int mode) {
		GlStateManager.drawArrays(mode, 0, this.count);
	}

	public void unbindBuffer() {
		OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
	}

	public void deleteGlBuffers() {
		if (this.glBufferId >= 0) {
			OpenGlHelper.glDeleteBuffers(this.glBufferId);
			this.glBufferId = -1;
		}

	}

}
