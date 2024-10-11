package net.opencraft;

import static org.lwjgl.opengl.GL11.*;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

public class DisplayListRenderer {

	private int x, y, z;
	private float xOffset, yOffset, zOffset;
	private IntBuffer cmdBuffer;
	private boolean ready;
	private boolean bufferFlipped;

	public DisplayListRenderer() {
		this.cmdBuffer = BufferUtils.createIntBuffer(0x10000);
		this.ready = false;
		this.bufferFlipped = false;
	}

	public void initialize(int x, int y, int z, double xOffset, double yOffset, double zOffset) {
		this.ready = true;
		this.cmdBuffer.clear();
		this.x = x;
		this.y = y;
		this.z = z;
		this.xOffset = (float) xOffset;
		this.yOffset = (float) yOffset;
		this.zOffset = (float) zOffset;
	}

	public boolean positionsMatch(int x, int y, int z) {
		if(!ready)
			return false;

		return this.x == x && this.y == y && this.z == z;
	}

	public void addCommand(int cmd) {
		cmdBuffer.put(cmd);
		if(!cmdBuffer.hasRemaining())
			render();
	}

	public void render() {
		if(!ready)
			return;

		if(!bufferFlipped) {
			cmdBuffer.flip();
			bufferFlipped = true;
		}

		if(this.cmdBuffer.hasRemaining()) {
			glPushMatrix();
			{
				glTranslatef(this.x - this.xOffset, this.y - this.yOffset, this.z - this.zOffset);
				glCallLists(this.cmdBuffer);
			}
			glPopMatrix();
		}
	}

	public void reset() {
		ready = false;
		bufferFlipped = false;
	}

}
