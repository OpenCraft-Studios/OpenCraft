
package net.opencraft.renderer.gui;

import net.opencraft.renderer.Tessellator;
import net.opencraft.renderer.font.FontRenderer;

import org.lwjgl.opengl.GL11;

public class GuiElement {

	protected float zLevel;

	public GuiElement() {
		this.zLevel = 0.0f;
	}

	protected void drawGradientRect(final int integer1, final int integer2, final int integer3, final int integer4, final int integer5, final int integer6) {
		final float float4 = (integer5 >> 24 & 0xFF) / 255.0f;
		final float float5 = (integer5 >> 16 & 0xFF) / 255.0f;
		final float float6 = (integer5 >> 8 & 0xFF) / 255.0f;
		final float float7 = (integer5 & 0xFF) / 255.0f;
		final float float8 = (integer6 >> 24 & 0xFF) / 255.0f;
		final float float9 = (integer6 >> 16 & 0xFF) / 255.0f;
		final float float10 = (integer6 >> 8 & 0xFF) / 255.0f;
		final float float11 = (integer6 & 0xFF) / 255.0f;
		GL11.glDisable(3553);
		GL11.glEnable(3042);
		GL11.glDisable(3008);
		GL11.glBlendFunc(770, 771);
		final Tessellator instance = Tessellator.instance;
		instance.beginQuads();
		instance.color(float5, float6, float7, float4);
		instance.vertex(integer3, integer2, 0.0);
		instance.vertex(integer1, integer2, 0.0);
		instance.color(float9, float10, float11, float8);
		instance.vertex(integer1, integer4, 0.0);
		instance.vertex(integer3, integer4, 0.0);
		instance.draw();
		GL11.glDisable(3042);
		GL11.glEnable(3008);
		GL11.glEnable(3553);
	}

	public void drawCenteredString(final FontRenderer ej, final String string, final int integer3, final int integer4, final int integer5) {
		ej.drawShadow(string, integer3 - ej.width(string) / 2, integer4, integer5);
	}

	public void drawString(final FontRenderer fontRenderer, final String string, final int xCoord, final int yCoord, final int combinedColor) {
		fontRenderer.drawShadow(string, xCoord, yCoord, combinedColor);
	}

	public void drawTexturedModalRect(final int x, final int y, final int u, final int v, final int width, final int height) {
		final float texelSize = 0.00390625f;
		final Tessellator tessellator = Tessellator.instance;
		tessellator.beginQuads();
		tessellator.vertexUV(x, y + height, this.zLevel, (u + 0) * texelSize, (v + height) * texelSize);
		tessellator.vertexUV(x + width, y + height, this.zLevel, (u + width) * texelSize, (v + height) * texelSize);
		tessellator.vertexUV(x + width, y + 0, this.zLevel, (u + width) * texelSize, (v + 0) * texelSize);
		tessellator.vertexUV(x + 0, y + 0, this.zLevel, (u + 0) * texelSize, (v + 0) * texelSize);
		tessellator.draw();
	}

}
