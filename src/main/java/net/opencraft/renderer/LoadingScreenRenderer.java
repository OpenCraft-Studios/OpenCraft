
package net.opencraft.renderer;

import static net.opencraft.OpenCraft.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import net.opencraft.OpenCraftError;
import net.opencraft.ScaledResolution;
import net.opencraft.renderer.gui.IProgressListener;

public class LoadingScreenRenderer implements IProgressListener {

	private static final long INTERVAL = 20L;
	private String field_1004_a;
	private String currentlyDisplayedText;
	private long begin;
	private boolean field_1005_e;

	public LoadingScreenRenderer() {
		this.field_1004_a = "";
		this.currentlyDisplayedText = "";
		this.begin = System.currentTimeMillis();
		this.field_1005_e = false;
	}

	public void printText(final String string) {
		this.field_1005_e = false;
		this.setLoadingProgress(string);
	}

	public void setStage(final String string) {
		this.field_1005_e = true;
		this.setLoadingProgress(this.currentlyDisplayedText);
	}

	public void setLoadingProgress(final String string) {
		if (!oc.running)
			return;
		this.currentlyDisplayedText = string;
		ScaledResolution scaledResolution = new ScaledResolution(oc.width, oc.height);
		int scaledWidth = scaledResolution.getScaledWidth();
		int scaledHeight = scaledResolution.getScaledHeight();
		
		glClear(256);
		glMatrixMode(5889);
		glLoadIdentity();
		glOrtho(0.0, (double) scaledWidth, (double) scaledHeight, 0.0, 100.0, 300.0);
		glMatrixMode(5888);
		glLoadIdentity();
		glTranslatef(0.0f, 0.0f, -200.0f);
	}

	public void setLoadingMessage(final String string) {
		if (oc.running) {
			this.begin = 0L;
			this.field_1004_a = string;
			this.setProgress(-1);
			this.begin = 0L;
			return;
		}
		if (this.field_1005_e) {
			return;
		}
		throw new OpenCraftError();
	}

	public void setProgress(final int integer) {
		if (!oc.running)
			return;

		long now = System.currentTimeMillis();
		if (now - begin < INTERVAL)
			return;

		this.begin = now;
		final ScaledResolution scaledResolution = new ScaledResolution(oc.width, oc.height);
		final int scaledWidth = scaledResolution.getScaledWidth();
		final int scaledHeight = scaledResolution.getScaledHeight();
		glClear(256);
		glMatrixMode(5889);
		glLoadIdentity();
		glOrtho(0.0, (double) scaledWidth, (double) scaledHeight, 0.0, 100.0, 300.0);
		glMatrixMode(5888);
		glLoadIdentity();
		glTranslatef(0.0f, 0.0f, -200.0f);
		glClear(16640);
		final Tessellator t = Tessellator.instance;
		glBindTexture(3553, oc.renderer.loadTexture("/assets/dirt.png"));
		final float n = 32.0f;
		t.beginQuads();
		t.color(4210752);
		t.vertexUV(0.0, scaledHeight, 0.0, 0.0, scaledHeight / n);
		t.vertexUV(scaledWidth, scaledHeight, 0.0, scaledWidth / n, scaledHeight / n);
		t.vertexUV(scaledWidth, 0.0, 0.0, scaledWidth / n, 0.0);
		t.vertexUV(0.0, 0.0, 0.0, 0.0, 0.0);
		t.render();
		if (integer >= 0) {
			final int n2 = 100;
			final int n3 = 2;
			final int n4 = scaledWidth / 2 - n2 / 2;
			final int n5 = scaledHeight / 2 + 16;
			glDisable(3553);
			t.beginQuads();
			t.color(8421504);
			t.vertex(n4, n5, 0.0);
			t.vertex(n4, n5 + n3, 0.0);
			t.vertex(n4 + n2, n5 + n3, 0.0);
			t.vertex(n4 + n2, n5, 0.0);
			t.color(8454016);
			t.vertex(n4, n5, 0.0);
			t.vertex(n4, n5 + n3, 0.0);
			t.vertex(n4 + integer, n5 + n3, 0.0);
			t.vertex(n4 + integer, n5, 0.0);
			t.render();
			glEnable(3553);
		}
		oc.font.drawShadow(this.currentlyDisplayedText, (scaledWidth - oc.font.width(this.currentlyDisplayedText)) / 2,
				scaledHeight / 2 - 4 - 16, 16777215);
		oc.font.drawShadow(this.field_1004_a, (scaledWidth - oc.font.width(this.field_1004_a)) / 2,
				scaledHeight / 2 - 4 + 8, 16777215);
		glfwSwapBuffers(oc.window);
		try {
			Thread.yield();
		} catch (Exception ex) {
		}

	}

}
