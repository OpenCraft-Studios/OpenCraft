
package net.opencraft.item;

import net.opencraft.OpenCraft;
import net.opencraft.block.Block;
import net.opencraft.block.material.Material;
import net.opencraft.client.renderer.Tessellator;
import net.opencraft.client.renderer.entity.RenderBlocks;
import net.opencraft.client.renderer.entity.RenderHelper;
import net.opencraft.client.renderer.entity.RenderManager;
import net.opencraft.client.renderer.entity.RenderPlayer;
import net.opencraft.entity.EntityPlayerSP;
import net.opencraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ItemRenderer {

    private OpenCraft a;
    private ItemStack b;
    private float c;
    private float d;
    private int e;
    private boolean f;
    private RenderBlocks g;

    public ItemRenderer(final OpenCraft aw) {
        this.b = null;
        this.c = 0.0f;
        this.d = 0.0f;
        this.e = 0;
        this.f = false;
        this.g = new RenderBlocks();
        this.a = aw;
    }

    public void renderItemInFirstPerson(final float float1) {
        final float n = this.d + (this.c - this.d) * float1;
        final EntityPlayerSP thePlayer = this.a.thePlayer;
        GL11.glPushMatrix();
        GL11.glRotatef(thePlayer.prevRotationPitch + (thePlayer.rotationPitch - thePlayer.prevRotationPitch) * float1, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(thePlayer.prevRotationYaw + (thePlayer.rotationYaw - thePlayer.prevRotationYaw) * float1, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        final float lightBrightness = this.a.theWorld.getLightBrightness(MathHelper.floor_double(thePlayer.posX), MathHelper.floor_double(thePlayer.posY), MathHelper.floor_double(thePlayer.posZ));
        GL11.glColor4f(lightBrightness, lightBrightness, lightBrightness, 1.0f);
        if (this.b != null) {
            GL11.glPushMatrix();
            final float n2 = 0.8f;
            if (this.f) {
                final float n3 = (this.e + float1) / 8.0f;
                final float n4 = MathHelper.sin(n3 * 3.1415927f);
                final float n5 = MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f);
                GL11.glTranslatef(-n5 * 0.4f, MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f * 2.0f) * 0.2f, -n4 * 0.2f);
            }
            GL11.glTranslatef(0.7f * n2, -0.65f * n2 - (1.0f - n) * 0.6f, -0.9f * n2);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glEnable(32826);
            if (this.f) {
                final float n3 = (this.e + float1) / 8.0f;
                final float n4 = MathHelper.sin(n3 * n3 * 3.1415927f);
                final float n5 = MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f);
                GL11.glRotatef(-n4 * 20.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(-n5 * 20.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-n5 * 80.0f, 1.0f, 0.0f, 0.0f);
            }
            final float n3 = 0.4f;
            GL11.glScalef(n3, n3, n3);
            if (this.b.itemID < 256 && Block.blocksList[this.b.itemID].getRenderType() == 0) {
                GL11.glBindTexture(3553, this.a.renderEngine.getTexture("/assets/terrain.png"));
                this.g.renderBlockOnInventory(Block.blocksList[this.b.itemID]);
            } else {
                if (this.b.itemID < 256) {
                    GL11.glBindTexture(3553, this.a.renderEngine.getTexture("/assets/terrain.png"));
                } else {
                    GL11.glBindTexture(3553, this.a.renderEngine.getTexture("/assets/gui/items.png"));
                }
                final Tessellator instance = Tessellator.instance;
                final float n5 = (this.b.getIconIndex() % 16 * 16 + 0) / 256.0f;
                final float n6 = (this.b.getIconIndex() % 16 * 16 + 16) / 256.0f;
                final float n7 = (this.b.getIconIndex() / 16 * 16 + 0) / 256.0f;
                final float n8 = (this.b.getIconIndex() / 16 * 16 + 16) / 256.0f;
                final float n9 = 1.0f;
                final float n10 = 0.0f;
                final float n11 = 0.3f;
                GL11.glEnable(32826);
                GL11.glTranslatef(-n10, -n11, 0.0f);
                final float n12 = 1.5f;
                GL11.glScalef(n12, n12, n12);
                GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
                GL11.glTranslatef(-0.9375f, -0.0625f, 0.0f);
                final float n13 = 0.0625f;
                instance.startDrawingQuads();
                instance.setNormal(0.0f, 0.0f, 1.0f);
                instance.addVertexWithUV(0.0, 0.0, 0.0, n6, n8);
                instance.addVertexWithUV(n9, 0.0, 0.0, n5, n8);
                instance.addVertexWithUV(n9, 1.0, 0.0, n5, n7);
                instance.addVertexWithUV(0.0, 1.0, 0.0, n6, n7);
                instance.draw();
                instance.startDrawingQuads();
                instance.setNormal(0.0f, 0.0f, -1.0f);
                instance.addVertexWithUV(0.0, 1.0, 0.0f - n13, n6, n7);
                instance.addVertexWithUV(n9, 1.0, 0.0f - n13, n5, n7);
                instance.addVertexWithUV(n9, 0.0, 0.0f - n13, n5, n8);
                instance.addVertexWithUV(0.0, 0.0, 0.0f - n13, n6, n8);
                instance.draw();
                instance.startDrawingQuads();
                instance.setNormal(-1.0f, 0.0f, 0.0f);
                for (int i = 0; i < 16; ++i) {
                    final float n14 = i / 16.0f;
                    final float n15 = n6 + (n5 - n6) * n14 - 0.001953125f;
                    final float n16 = n9 * n14;
                    instance.addVertexWithUV(n16, 0.0, 0.0f - n13, n15, n8);
                    instance.addVertexWithUV(n16, 0.0, 0.0, n15, n8);
                    instance.addVertexWithUV(n16, 1.0, 0.0, n15, n7);
                    instance.addVertexWithUV(n16, 1.0, 0.0f - n13, n15, n7);
                }
                instance.draw();
                instance.startDrawingQuads();
                instance.setNormal(1.0f, 0.0f, 0.0f);
                for (int i = 0; i < 16; ++i) {
                    final float n14 = i / 16.0f;
                    final float n15 = n6 + (n5 - n6) * n14 - 0.001953125f;
                    final float n16 = n9 * n14 + 0.0625f;
                    instance.addVertexWithUV(n16, 1.0, 0.0f - n13, n15, n7);
                    instance.addVertexWithUV(n16, 1.0, 0.0, n15, n7);
                    instance.addVertexWithUV(n16, 0.0, 0.0, n15, n8);
                    instance.addVertexWithUV(n16, 0.0, 0.0f - n13, n15, n8);
                }
                instance.draw();
                instance.startDrawingQuads();
                instance.setNormal(0.0f, 1.0f, 0.0f);
                for (int i = 0; i < 16; ++i) {
                    final float n14 = i / 16.0f;
                    final float n15 = n8 + (n7 - n8) * n14 - 0.001953125f;
                    final float n16 = n9 * n14 + 0.0625f;
                    instance.addVertexWithUV(0.0, n16, 0.0, n6, n15);
                    instance.addVertexWithUV(n9, n16, 0.0, n5, n15);
                    instance.addVertexWithUV(n9, n16, 0.0f - n13, n5, n15);
                    instance.addVertexWithUV(0.0, n16, 0.0f - n13, n6, n15);
                }
                instance.draw();
                instance.startDrawingQuads();
                instance.setNormal(0.0f, -1.0f, 0.0f);
                for (int i = 0; i < 16; ++i) {
                    final float n14 = i / 16.0f;
                    final float n15 = n8 + (n7 - n8) * n14 - 0.001953125f;
                    final float n16 = n9 * n14;
                    instance.addVertexWithUV(n9, n16, 0.0, n5, n15);
                    instance.addVertexWithUV(0.0, n16, 0.0, n6, n15);
                    instance.addVertexWithUV(0.0, n16, 0.0f - n13, n6, n15);
                    instance.addVertexWithUV(n9, n16, 0.0f - n13, n5, n15);
                }
                instance.draw();
                GL11.glDisable(32826);
            }
            GL11.glPopMatrix();
        } else {
            GL11.glPushMatrix();
            final float n2 = 0.8f;
            if (this.f) {
                final float n3 = (this.e + float1) / 8.0f;
                final float n4 = MathHelper.sin(n3 * 3.1415927f);
                final float n5 = MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f);
                GL11.glTranslatef(-n5 * 0.3f, MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f * 2.0f) * 0.4f, -n4 * 0.4f);
            }
            GL11.glTranslatef(0.8f * n2, -0.75f * n2 - (1.0f - n) * 0.6f, -0.9f * n2);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glEnable(32826);
            if (this.f) {
                final float n3 = (this.e + float1) / 8.0f;
                final float n4 = MathHelper.sin(n3 * n3 * 3.1415927f);
                final float n5 = MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f);
                GL11.glRotatef(n5 * 70.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(-n4 * 20.0f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBindTexture(3553, this.a.renderEngine.a(this.a.thePlayer.skinUrl, this.a.thePlayer.addToPlayerScore()));
            GL11.glTranslatef(-1.0f, 3.6f, 3.5f);
            GL11.glRotatef(120.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(200.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glTranslatef(5.6f, 0.0f, 0.0f);
            final RenderPlayer renderPlayer = (RenderPlayer) RenderManager.instance.getEntityRenderObject(this.a.thePlayer);
            final float n5 = 1.0f;
            GL11.glScalef(n5, n5, n5);
            renderPlayer.drawFirstPersonHand();
            GL11.glPopMatrix();
        }
        GL11.glDisable(32826);
        RenderHelper.disableStandardItemLighting();
    }

    public void renderOverlays(final float float1) {
        GL11.glDisable(3008);
        if (this.a.thePlayer.fire > 0) {
            final int xCoord = this.a.renderEngine.getTexture("/assets/terrain.png");
            GL11.glBindTexture(3553, xCoord);
            this.d(float1);
        }
        if (this.a.theWorld.player.isEntityInsideOpaqueBlock()) {
            final int xCoord = MathHelper.floor_double(this.a.thePlayer.posX);
            final int floor_double = MathHelper.floor_double(this.a.thePlayer.posY);
            final int floor_double2 = MathHelper.floor_double(this.a.thePlayer.posZ);
            GL11.glBindTexture(3553, this.a.renderEngine.getTexture("/assets/terrain.png"));
            final int blockId = this.a.theWorld.getBlockId(xCoord, floor_double, floor_double2);
            if (Block.blocksList[blockId] != null) {
                this.a(float1, Block.blocksList[blockId].getBlockTextureFromSide(2));
            }
        }
        if (this.a.thePlayer.isInsideOfMaterial(Material.WATER)) {
            final int xCoord = this.a.renderEngine.getTexture("/assets/water.png");
            GL11.glBindTexture(3553, xCoord);
            this.c(float1);
        }
        GL11.glEnable(3008);
    }

    private void a(final float float1, final int integer) {
        final Tessellator instance = Tessellator.instance;
        float entityBrightness = this.a.thePlayer.getEntityBrightness(float1);
        entityBrightness = 0.1f;
        GL11.glColor4f(entityBrightness, entityBrightness, entityBrightness, 0.5f);
        GL11.glPushMatrix();
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
        instance.startDrawingQuads();
        instance.addVertexWithUV(n, n3, n5, n8, n10);
        instance.addVertexWithUV(n2, n3, n5, n7, n10);
        instance.addVertexWithUV(n2, n4, n5, n7, n9);
        instance.addVertexWithUV(n, n4, n5, n8, n9);
        instance.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void c(final float float1) {
        final Tessellator instance = Tessellator.instance;
        final float entityBrightness = this.a.thePlayer.getEntityBrightness(float1);
        GL11.glColor4f(entityBrightness, entityBrightness, entityBrightness, 0.5f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glPushMatrix();
        final float n = 4.0f;
        final float n2 = -1.0f;
        final float n3 = 1.0f;
        final float n4 = -1.0f;
        final float n5 = 1.0f;
        final float n6 = -0.5f;
        final float n7 = -this.a.thePlayer.rotationYaw / 64.0f;
        final float n8 = this.a.thePlayer.rotationPitch / 64.0f;
        instance.startDrawingQuads();
        instance.addVertexWithUV(n2, n4, n6, n + n7, n + n8);
        instance.addVertexWithUV(n3, n4, n6, 0.0f + n7, n + n8);
        instance.addVertexWithUV(n3, n5, n6, 0.0f + n7, 0.0f + n8);
        instance.addVertexWithUV(n2, n5, n6, n + n7, 0.0f + n8);
        instance.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
    }

    private void d(final float float1) {
        final Tessellator instance = Tessellator.instance;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.9f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final float n = 1.0f;
        for (int i = 0; i < 2; ++i) {
            GL11.glPushMatrix();
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
            GL11.glTranslatef(-(i * 2 - 1) * 0.24f, -0.3f, 0.0f);
            GL11.glRotatef((i * 2 - 1) * 10.0f, 0.0f, 1.0f, 0.0f);
            instance.startDrawingQuads();
            instance.addVertexWithUV(n9, n11, n13, n6, n8);
            instance.addVertexWithUV(n10, n11, n13, n5, n8);
            instance.addVertexWithUV(n10, n12, n13, n5, n7);
            instance.addVertexWithUV(n9, n12, n13, n6, n7);
            instance.draw();
            GL11.glPopMatrix();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
    }

    public void updateEquippedItem() {
        this.d = this.c;
        if (this.f) {
            ++this.e;
            if (this.e == 8) {
                this.e = 0;
                this.f = false;
            }
        }
        final ItemStack currentItem = this.a.thePlayer.inventory.getCurrentItem();
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
