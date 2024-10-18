package net.opencraft.item;

import static net.opencraft.OpenCraft.*;
import static net.opencraft.entity.EntityRenderer.*;
import static org.joml.Math.*;
import static org.lwjgl.opengl.GL11.*;

import net.opencraft.blocks.Block;
import net.opencraft.blocks.material.Material;
import net.opencraft.entity.EntityPlayerSP;
import net.opencraft.renderer.Tessellator;
import net.opencraft.renderer.entity.*;
import net.opencraft.util.Mth;

public class ItemRenderer {

	private ItemStack b;
	private float c, d;
	private boolean f;
	private int e;

	private RenderBlocks renderBlocks;

	public ItemRenderer() {
		this.b = null;
		this.c = 0.0f;
		this.d = 0.0f;
		this.e = 0;
		this.f = false;
		this.renderBlocks = new RenderBlocks();
	}

	public void renderItemInFP(final float float1) {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(70.0F, (float) oc.width / (float) oc.height, 0.05F, 10.0F);
		glMatrixMode(GL_MODELVIEW);

		final float n = this.d + (this.c - this.d) * float1;
		final EntityPlayerSP thePlayer = oc.player;
		glPushMatrix();
		glRotatef(thePlayer.prevRotationPitch + (thePlayer.xRot - thePlayer.prevRotationPitch) * float1, 1.0f, 0.0f,
				0.0f);
		glRotatef(thePlayer.prevRotationYaw + (thePlayer.yRot - thePlayer.prevRotationYaw) * float1, 0.0f, 1.0f, 0.0f);
		RenderHelper.enableStandardItemLighting();
		glPopMatrix();
		final float lightBrightness = oc.world.getLightBrightness(Mth.floor_double(thePlayer.x),
				Mth.floor_double(thePlayer.y), Mth.floor_double(thePlayer.z));
		glColor4f(lightBrightness, lightBrightness, lightBrightness, 1.0f);
		if (this.b != null) {
			glPushMatrix();
			final float n2 = 0.8f;
			if (this.f) {
				final float n3 = (this.e + float1) / 8.0f;
				final float n4 = sin(n3 * PI_f);
				final float n5 = sin(sqrt(n3) * PI_f);
				glTranslatef(-n5 * 0.4f, sin(sqrt(n3) * PI_TIMES_2_f) * 0.2f, -n4 * 0.2f);
			}
			glTranslatef(0.7f * n2, -0.65f * n2 - (1.0f - n) * 0.6f, -0.9f * n2);
			glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
			glEnable(32826);
			if (this.f) {
				final float n3 = (this.e + float1) / 8.0f;
				final float n4 = sin(n3 * n3 * PI_f);
				final float n5 = sin(sqrt(n3) * PI_f);
				glRotatef(-n4 * 20.0f, 0.0f, 1.0f, 0.0f);
				glRotatef(-n5 * 20.0f, 0.0f, 0.0f, 1.0f);
				glRotatef(-n5 * 80.0f, 1.0f, 0.0f, 0.0f);
			}
			final float n3 = 0.4f;
			glScalef(n3, n3, n3);
			if (this.b.itemID < 256 && Block.BLOCKS[this.b.itemID].getRenderType() == 0) {
				oc.renderer.bindTexture("/assets/terrain.png");
				this.renderBlocks.renderBlockOnInventory(Block.BLOCKS[this.b.itemID]);
			} else {
				oc.renderer.bindTexture(b.itemID < 256 ? "/assets/terrain.png" : "/assets/gui/items.png");
				final Tessellator t = Tessellator.instance;
				final float u1 = (this.b.getIconIndex() % 16 * 16 + 0) / 256.0f;
				final float u0 = (this.b.getIconIndex() % 16 * 16 + 16) / 256.0f;
				final float v1 = (this.b.getIconIndex() / 16 * 16 + 0) / 256.0f;
				final float v0 = (this.b.getIconIndex() / 16 * 16 + 16) / 256.0f;
				final float n9 = 1.0f;
				final float n10 = 0.0f;
				final float n11 = 0.3f;
				glEnable(32826);
				glTranslatef(-n10, -n11, 0.0f);
				final float n12 = 1.5f;
				glScalef(n12, n12, n12);
				glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
				glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
				glTranslatef(-0.9375f, -0.0625f, 0.0f);
				final float n13 = 0.0625f;

				// Quad 1
				t.beginQuads();
				{
					t.normal(0.0f, 0.0f, 1.0f);

					t.vertexUV(0.0, 0.0, 0.0, u0, v0);
					t.vertexUV(n9, 0.0, 0.0, u1, v0);
					t.vertexUV(n9, 1.0, 0.0, u1, v1);
					t.vertexUV(0.0, 1.0, 0.0, u0, v1);
				}
				t.render();

				// Quad 2
				t.beginQuads();
				{
					t.normal(0.0f, 0.0f, -1.0f);

					t.vertexUV(0.0, 1.0, 0.0f - n13, u0, v1);
					t.vertexUV(n9, 1.0, 0.0f - n13, u1, v1);
					t.vertexUV(n9, 0.0, 0.0f - n13, u1, v0);
					t.vertexUV(0.0, 0.0, 0.0f - n13, u0, v0);
				}
				t.render();

				// Quad 2
				t.beginQuads();
				{
					t.normal(-1.0f, 0.0f, 0.0f);

					for (int i = 0; i < 16; ++i) {
						final float n14 = i / 16.0f;
						final float n15 = u0 + (u1 - u0) * n14 - 0.001953125f;
						final float n16 = n9 * n14;
						t.vertexUV(n16, 0.0, 0.0f - n13, n15, v0);
						t.vertexUV(n16, 0.0, 0.0, n15, v0);
						t.vertexUV(n16, 1.0, 0.0, n15, v1);
						t.vertexUV(n16, 1.0, 0.0f - n13, n15, v1);
					}
				}
				t.render();

				// Quad 3
				t.beginQuads();
				t.normal(1.0f, 0.0f, 0.0f);
				for (int i = 0; i < 16; ++i) {
					final float n14 = i / 16.0f;
					final float n15 = u0 + (u1 - u0) * n14 - 0.001953125f;
					final float n16 = n9 * n14 + 0.0625f;
					t.vertexUV(n16, 1.0, 0.0f - n13, n15, v1);
					t.vertexUV(n16, 1.0, 0.0, n15, v1);
					t.vertexUV(n16, 0.0, 0.0, n15, v0);
					t.vertexUV(n16, 0.0, 0.0f - n13, n15, v0);
				}
				t.render();
				t.beginQuads();
				t.normal(0.0f, 1.0f, 0.0f);
				for (int i = 0; i < 16; ++i) {
					final float n14 = i / 16.0f;
					final float n15 = v0 + (v1 - v0) * n14 - 0.001953125f;
					final float n16 = n9 * n14 + 0.0625f;
					t.vertexUV(0.0, n16, 0.0, u0, n15);
					t.vertexUV(n9, n16, 0.0, u1, n15);
					t.vertexUV(n9, n16, 0.0f - n13, u1, n15);
					t.vertexUV(0.0, n16, 0.0f - n13, u0, n15);
				}
				t.render();
				t.beginQuads();
				t.normal(0.0f, -1.0f, 0.0f);
				for (int i = 0; i < 16; ++i) {
					final float n14 = i / 16.0f;
					final float n15 = v0 + (v1 - v0) * n14 - 0.001953125f;
					final float n16 = n9 * n14;
					t.vertexUV(n9, n16, 0.0, u1, n15);
					t.vertexUV(0.0, n16, 0.0, u0, n15);
					t.vertexUV(0.0, n16, 0.0f - n13, u0, n15);
					t.vertexUV(n9, n16, 0.0f - n13, u1, n15);
				}
				t.render();
				glDisable(32826);
			}
			glPopMatrix();
		} else {
			glPushMatrix();
			final float n2 = 0.8f;
			if (this.f) {
				final float n3 = (this.e + float1) / 8.0f;
				final float n4 = sin(n3 * PI_f);
				final float n5 = sin(sqrt(n3) * PI_f);
				glTranslatef(-n5 * 0.3f, sin(sqrt(n3) * PI_TIMES_2_f) * 0.4f, -n4 * 0.4f);
			}
			glTranslatef(0.8f * n2, -0.75f * n2 - (1.0f - n) * 0.6f, -0.9f * n2);
			glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
			glEnable(32826);
			if (this.f) {
				final float n3 = (this.e + float1) / 8.0f;
				final float n4 = sin(n3 * n3 * PI_f);
				final float n5 = sin(sqrt(n3) * PI_f);
				glRotatef(n5 * 70.0f, 0.0f, 1.0f, 0.0f);
				glRotatef(-n4 * 20.0f, 0.0f, 0.0f, 1.0f);
			}
			glBindTexture(GL_TEXTURE_2D,
					oc.renderer.loadAndBindTexture(oc.player.skinUrl, oc.player.addToPlayerScore()));
			glTranslatef(-1.0f, 3.6f, 3.5f);
			glRotatef(120.0f, 0.0f, 0.0f, 1.0f);
			glRotatef(200.0f, 1.0f, 0.0f, 0.0f);
			glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
			glScalef(1.0f, 1.0f, 1.0f);
			glTranslatef(5.6f, 0.0f, 0.0f);
			final RenderPlayer renderPlayer = (RenderPlayer) RenderManager.instance.getEntityRenderObject(oc.player);
			final float n5 = 1.0f;
			glScalef(n5, n5, n5);
			renderPlayer.drawFirstPersonHand();
			glPopMatrix();
		}
		glDisable(32826);
		RenderHelper.disableStandardItemLighting();
	}

	public void renderOverlays(final float float1) {
		glDisable(3008);
		if (oc.player.fire > 0) {
			int texId = oc.renderer.loadTexture("/assets/terrain.png");
			glBindTexture(GL_TEXTURE_2D, texId);
			d(float1);
		}

		if (oc.world.player.isEntityInsideOpaqueBlock()) {
			final int xCoord = Mth.floor_double(oc.player.x);
			final int floor_double = Mth.floor_double(oc.player.y);
			final int floor_double2 = Mth.floor_double(oc.player.z);
			glBindTexture(3553, oc.renderer.loadTexture("/assets/terrain.png"));
			final int blockId = oc.world.getBlockId(xCoord, floor_double, floor_double2);
			if (Block.BLOCKS[blockId] != null) {
				this.a(float1, Block.BLOCKS[blockId].getBlockTextureFromSide(2));
			}
		}
		if (oc.player.isInsideOfMaterial(Material.WATER)) {
			final int xCoord = oc.renderer.loadTexture("/assets/water.png");
			glBindTexture(3553, xCoord);
			this.c(float1);
		}
		glEnable(3008);
	}

	private void a(final float float1, final int integer) {
		final Tessellator t = Tessellator.instance;
		float entityBrightness = oc.player.getEntityBrightness(float1);
		entityBrightness = 0.1f;
		glColor4f(entityBrightness, entityBrightness, entityBrightness, 0.5f);
		glPushMatrix();
		final float n = -1.0f;
		final float n2 = 1.0f;
		final float n3 = -1.0f;
		final float n4 = 1.0f;
		final float n5 = -0.5f;
		final float n6 = 0.0078125f;
		final float n7 = integer % 16 / 256.0f - n6;
		final float n8 = (integer % 16 + 15.99f) / 256.0f + n6;
		final float n9 = integer / 16 / 256.0f - n6;
		final float n10 = (integer / 16 + 15.99f) / 256.0f + n6;
		t.beginQuads();
		{
			t.vertexUV(n, n3, n5, n8, n10);
			t.vertexUV(n2, n3, n5, n7, n10);
			t.vertexUV(n2, n4, n5, n7, n9);
			t.vertexUV(n, n4, n5, n8, n9);
		}
		t.render();
		glPopMatrix();
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	private void c(final float float1) {
		final Tessellator t = Tessellator.instance;
		final float entityBrightness = oc.player.getEntityBrightness(float1);
		glColor4f(entityBrightness, entityBrightness, entityBrightness, 0.5f);
		glEnable(3042);
		glBlendFunc(770, 771);
		glPushMatrix();
		final float n = 4.0f;
		final float n2 = -1.0f;
		final float n3 = 1.0f;
		final float n4 = -1.0f;
		final float n5 = 1.0f;
		final float n6 = -0.5f;
		final float n7 = -oc.player.yRot / 64.0f;
		final float n8 = oc.player.xRot / 64.0f;
		t.beginQuads();
		t.vertexUV(n2, n4, n6, n + n7, n + n8);
		t.vertexUV(n3, n4, n6, 0.0f + n7, n + n8);
		t.vertexUV(n3, n5, n6, 0.0f + n7, 0.0f + n8);
		t.vertexUV(n2, n5, n6, n + n7, 0.0f + n8);
		t.render();
		glPopMatrix();
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		glDisable(3042);
	}

	private void d(final float float1) {
		final Tessellator t = Tessellator.instance;
		glColor4f(1.0f, 1.0f, 1.0f, 0.9f);
		glEnable(3042);
		glBlendFunc(770, 771);
		final float n = 1.0f;
		for (int i = 0; i < 2; ++i) {
			glPushMatrix();
			final int n2 = Block.fire.blockIndexInTexture + i * 16;
			final int n3 = (n2 & 0xF) << 4;
			final int n4 = n2 & 0xF0;
			final float n5 = n3 / 256.0f;
			final float n6 = (n3 + 15.99f) / 256.0f;
			final float n7 = n4 / 256.0f;
			final float n8 = (n4 + 15.99f) / 256.0f;
			final float n9 = (0.0f - n) / 2.0f;
			final float n10 = n9 + n;
			final float n11 = 0.0f - n / 2.0f;
			final float n12 = n11 + n;
			final float n13 = -0.5f;
			glTranslatef(-(i * 2 - 1) * 0.24f, -0.3f, 0.0f);
			glRotatef((i * 2 - 1) * 10.0f, 0.0f, 1.0f, 0.0f);
			t.beginQuads();
			{
				t.vertexUV(n9, n11, n13, n6, n8);
				t.vertexUV(n10, n11, n13, n5, n8);
				t.vertexUV(n10, n12, n13, n5, n7);
				t.vertexUV(n9, n12, n13, n6, n7);
			}
			t.render();

			glPopMatrix();
		}
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		glDisable(3042);
	}

	public void updateEquippedItem() {
		this.d = this.c;
		if (f) {
			if (++e == 8) {
				e = 0;
				f = false;
			}
		}
		final ItemStack currentItem = oc.player.inventory.getCurrentItem();
		final float n = 0.4f;
		float n2 = ((currentItem == this.b) ? 1.0f : 0.0f) - this.c;
		if (n2 < -n) {
			n2 = -n;
		}
		if (n2 > n) {
			n2 = n;
		}
		this.c += n2;
		if (this.c < 0.1f) {
			this.b = currentItem;
		}
	}

	public void b() {
		this.c = 0.0f;
	}

	public void resetEquippedProgress() {
		this.e = -1;
		this.f = true;
	}

	public void d() {
		this.c = 0.0f;
	}

}
