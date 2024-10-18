
package net.opencraft.renderer.entity;

import java.util.Random;
import net.opencraft.EnumArt;
import net.opencraft.entity.EntityPainting;
import net.opencraft.renderer.Tessellator;
import net.opencraft.util.Mth;
import org.lwjgl.opengl.GL11;

public class RenderPainting extends Render<EntityPainting> {

	private Random rand;

	public RenderPainting() {
		this.rand = new Random();
	}

	public void doRender(final EntityPainting entityLiving, final double xCoord, final double sqrt_double,
			final double yCoord, final float nya1, final float nya2) {
		this.rand.setSeed(187L);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) xCoord, (float) sqrt_double, (float) yCoord);
		GL11.glRotatef(nya1, 0.0f, 1.0f, 0.0f);
		GL11.glEnable(32826);
		this.loadTexture("/assets/art/kz.png");
		final EnumArt art = entityLiving.art;
		final float n = 0.0625f;
		GL11.glScalef(n, n, n);
		this.func_159_a(entityLiving, art.sizeX, art.sizeY, art.offsetX, art.offsetY);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}

	private void func_159_a(final EntityPainting cy, final int integer2, final int integer3, final int integer4,
			final int integer5) {
		final float n = -integer2 / 2.0f;
		final float n2 = -integer3 / 2.0f;
		final float z1 = -0.5f;
		final float z2 = 0.5f;
		for (int i = 0; i < integer2 / 16; ++i) {
			for (int j = 0; j < integer3 / 16; ++j) {
				final float x1 = n + (i + 1) * 16;
				final float x2 = n + i * 16;
				final float y2 = n2 + (j + 1) * 16;
				final float y1 = n2 + j * 16;
				this.func_160_a(cy, (x1 + x2) / 2.0f, (y2 + y1) / 2.0f);
				final float u2 = (integer4 + integer2 - i * 16) / 256.0f;
				final float u1 = (integer4 + integer2 - (i + 1) * 16) / 256.0f;
				final float v1 = (integer5 + integer3 - j * 16) / 256.0f;
				final float n12 = (integer5 + integer3 - (j + 1) * 16) / 256.0f;
				// TODO: can make an array
				final float u3 = 0.75f;
				final float u4 = 0.8125f;
				final float v2 = 0.0f;
				final float v3 = 0.0625f;
				final float u5 = 0.75f;
				final float u6 = 0.8125f;
				final float v4 = 0.001953125f;
				final float v5 = 0.001953125f;
				final float u8 = 0.7519531f;
				final float u7 = 0.7519531f;
				final float v6 = 0.0f;
				final float v7 = 0.0625f;
				final Tessellator t = Tessellator.instance;
				t.beginQuads();
				{
					t.normal(0.0f, 0.0f, -1.0f);
					t.vertexUV(x1, y1, z1, u1, v1);
					t.vertexUV(x2, y1, z1, u2, v1);
					t.vertexUV(x2, y2, z1, u2, n12);
					t.vertexUV(x1, y2, z1, u1, n12);
					
					t.normal(0.0f, 0.0f, 1.0f);
					t.vertexUV(x1, y2, z2, u3, v2);
					t.vertexUV(x2, y2, z2, u4, v2);
					t.vertexUV(x2, y1, z2, u4, v3);
					t.vertexUV(x1, y1, z2, u3, v3);
					
					t.normal(0.0f, -1.0f, 0.0f);
					t.vertexUV(x1, y2, z1, u5, v4);
					t.vertexUV(x2, y2, z1, u6, v4);
					t.vertexUV(x2, y2, z2, u6, v5);
					t.vertexUV(x1, y2, z2, u5, v5);
					
					t.normal(0.0f, 1.0f, 0.0f);
					t.vertexUV(x1, y1, z2, u5, v4);
					t.vertexUV(x2, y1, z2, u6, v4);
					t.vertexUV(x2, y1, z1, u6, v5);
					t.vertexUV(x1, y1, z1, u5, v5);
					
					t.normal(-1.0f, 0.0f, 0.0f);
					t.vertexUV(x1, y2, z2, u7, v6);
					t.vertexUV(x1, y1, z2, u7, v7);
					t.vertexUV(x1, y1, z1, u8, v7);
					t.vertexUV(x1, y2, z1, u8, v6);
					
					t.normal(1.0f, 0.0f, 0.0f);
					t.vertexUV(x2, y2, z1, u7, v6);
					t.vertexUV(x2, y1, z1, u7, v7);
					t.vertexUV(x2, y1, z2, u8, v7);
					t.vertexUV(x2, y2, z2, u8, v6);
				}
				t.render();
			}
		}
	}

	private void func_160_a(final EntityPainting cy, final float float2, final float float3) {
		int nya1 = Mth.floor_double(cy.x);
		final int floor_double = Mth.floor_double(cy.y + float3 / 16.0f);
		int nya2 = Mth.floor_double(cy.z);
		if (cy.direction == 0) {
			nya1 = Mth.floor_double(cy.x + float2 / 16.0f);
		}
		if (cy.direction == 1) {
			nya2 = Mth.floor_double(cy.z - float2 / 16.0f);
		}
		if (cy.direction == 2) {
			nya1 = Mth.floor_double(cy.x - float2 / 16.0f);
		}
		if (cy.direction == 3) {
			nya2 = Mth.floor_double(cy.z + float2 / 16.0f);
		}
		final float lightBrightness = this.renderManager.worldObj.getLightBrightness(nya1, floor_double, nya2);
		GL11.glColor3f(lightBrightness, lightBrightness, lightBrightness);
	}

}
