
package net.opencraft.client.renderer.entity;

import net.opencraft.client.font.FontRenderer;
import net.opencraft.entity.EntityItem;
import net.opencraft.item.ItemStack;
import net.opencraft.block.Block;
import java.util.Random;
import net.opencraft.util.Mth;
import net.opencraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderItem extends Render<EntityItem> {

    private RenderBlocks renderBlocks;
    private Random random;

    public RenderItem() {
        this.renderBlocks = new RenderBlocks();
        this.random = new Random();
        this.shadowSize = 0.15f;
        this.field_194_c = 0.75f;
    }

    public void doRender(final EntityItem entityLiving, final double xCoord, final double sqrt_double, final double yCoord, final float nya1, final float nya2) {
        this.random.setSeed(187L);
        final ItemStack item = entityLiving.item;
        GL11.glPushMatrix();
        final float n = Mth.sin((entityLiving.age + nya2) / 10.0f + entityLiving.hoverStart) * 0.1f + 0.1f;
        final float n2 = ((entityLiving.age + nya2) / 20.0f + entityLiving.hoverStart) * 57.295776f;
        int n3 = 1;
        if (entityLiving.item.stackSize > 1) {
            n3 = 2;
        }
        if (entityLiving.item.stackSize > 5) {
            n3 = 3;
        }
        if (entityLiving.item.stackSize > 20) {
            n3 = 4;
        }
        GL11.glTranslatef((float) xCoord, (float) sqrt_double + n, (float) yCoord);
        GL11.glEnable(32826);
        if (item.itemID < 256 && Block.blocksList[item.itemID].getRenderType() == 0) {
            GL11.glRotatef(n2, 0.0f, 1.0f, 0.0f);
            this.loadTexture("/assets/terrain.png");
            float n4 = 0.25f;
            if (!Block.blocksList[item.itemID].renderAsNormalBlock() && item.itemID != Block.slabSingle.blockID) {
                n4 = 0.5f;
            }
            GL11.glScalef(n4, n4, n4);
            for (int i = 0; i < n3; ++i) {
                GL11.glPushMatrix();
                if (i > 0) {
                    final float n5 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / n4;
                    final float n6 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / n4;
                    final float n7 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / n4;
                    GL11.glTranslatef(n5, n6, n7);
                }
                this.renderBlocks.renderBlockOnInventory(Block.blocksList[item.itemID]);
                GL11.glPopMatrix();
            }
        } else {
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            final int iconIndex = item.getIconIndex();
            if (item.itemID < 256) {
                this.loadTexture("/assets/terrain.png");
            } else {
                this.loadTexture("/assets/gui/items.png");
            }
            final Tessellator instance = Tessellator.instance;
            final float n5 = (iconIndex % 16 * 16 + 0) / 256.0f;
            final float n6 = (iconIndex % 16 * 16 + 16) / 256.0f;
            final float n7 = (iconIndex / 16 * 16 + 0) / 256.0f;
            final float n8 = (iconIndex / 16 * 16 + 16) / 256.0f;
            final float n9 = 1.0f;
            final float n10 = 0.5f;
            final float n11 = 0.25f;
            for (int j = 0; j < n3; ++j) {
                GL11.glPushMatrix();
                if (j > 0) {
                    GL11.glTranslatef((this.random.nextFloat() * 2.0f - 1.0f) * 0.3f, (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f, (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f);
                }
                GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                instance.startDrawingQuads();
                instance.setNormal(0.0f, 1.0f, 0.0f);
                instance.addVertexWithUV(0.0f - n10, 0.0f - n11, 0.0, n5, n8);
                instance.addVertexWithUV(n9 - n10, 0.0f - n11, 0.0, n6, n8);
                instance.addVertexWithUV(n9 - n10, 1.0f - n11, 0.0, n6, n7);
                instance.addVertexWithUV(0.0f - n10, 1.0f - n11, 0.0, n5, n7);
                instance.draw();
                GL11.glPopMatrix();
            }
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    public void drawItemIntoGui(final FontRenderer ej, final RenderEngine id, final ItemStack hw, final int integer4, final int integer5) {
        if (hw == null) {
            return;
        }
        if (hw.itemID < 256 && Block.blocksList[hw.itemID].getRenderType() == 0) {
            final int itemID = hw.itemID;
            id.bindTexture(id.getTexture("/assets/terrain.png"));
            final Block gs = Block.blocksList[itemID];
            GL11.glPushMatrix();
            GL11.glTranslatef((float) (integer4 - 2), (float) (integer5 + 3), 0.0f);
            GL11.glScalef(10.0f, 10.0f, 10.0f);
            GL11.glTranslatef(1.0f, 0.5f, 8.0f);
            GL11.glRotatef(210.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.renderBlocks.renderBlockOnInventory(gs);
            GL11.glPopMatrix();
        } else if (hw.getIconIndex() >= 0) {
            GL11.glDisable(2896);
            if (hw.itemID < 256) {
                id.bindTexture(id.getTexture("/assets/terrain.png"));
            } else {
                id.bindTexture(id.getTexture("/assets/gui/items.png"));
            }
            this.renderTexturedQuad(integer4, integer5, hw.getIconIndex() % 16 * 16, hw.getIconIndex() / 16 * 16, 16, 16);
            GL11.glEnable(2896);
        }
    }

    public void renderItemOverlayIntoGUI(final FontRenderer ej, final RenderEngine id, final ItemStack hw, final int integer4, final int integer5) {
        if (hw == null) {
            return;
        }
        if (hw.stackSize > 1) {
            final String string = new StringBuilder().append("").append(hw.stackSize).toString();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            ej.drawStringWithShadow2(string, integer4 + 19 - 2 - ej.getStringWidth(string), integer5 + 6 + 3, 16777215);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
        }
        if (hw.itemDamage > 0) {
            final int integer6 = 13 - hw.itemDamage * 13 / hw.isItemStackDamageable();
            final int n = 255 - hw.itemDamage * 255 / hw.isItemStackDamageable();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glDisable(3553);
            final Tessellator instance = Tessellator.instance;
            final int integer7 = 255 - n << 16 | n << 8;
            final int integer8 = (255 - n) / 4 << 16 | 0x3F00;
            this.renderQuad(instance, integer4 + 2, integer5 + 13, 13, 2, 0);
            this.renderQuad(instance, integer4 + 2, integer5 + 13, 12, 1, integer8);
            this.renderQuad(instance, integer4 + 2, integer5 + 13, integer6, 1, integer7);
            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    private void renderQuad(final Tessellator ag, final int integer2, final int integer3, final int integer4, final int integer5, final int integer6) {
        ag.startDrawingQuads();
        ag.setColorOpaque_I(integer6);
        ag.addVertex(integer2 + 0, integer3 + 0, 0.0);
        ag.addVertex(integer2 + 0, integer3 + integer5, 0.0);
        ag.addVertex(integer2 + integer4, integer3 + integer5, 0.0);
        ag.addVertex(integer2 + integer4, integer3 + 0, 0.0);
        ag.draw();
    }

    public void renderTexturedQuad(final int integer1, final int integer2, final int integer3, final int integer4, final int integer5, final int integer6) {
        final float n = 0.0f;
        final float n2 = 0.00390625f;
        final float n3 = 0.00390625f;
        final Tessellator instance = Tessellator.instance;
        instance.startDrawingQuads();
        instance.addVertexWithUV(integer1 + 0, integer2 + integer6, n, (integer3 + 0) * n2, (integer4 + integer6) * n3);
        instance.addVertexWithUV(integer1 + integer5, integer2 + integer6, n, (integer3 + integer5) * n2, (integer4 + integer6) * n3);
        instance.addVertexWithUV(integer1 + integer5, integer2 + 0, n, (integer3 + integer5) * n2, (integer4 + 0) * n3);
        instance.addVertexWithUV(integer1 + 0, integer2 + 0, n, (integer3 + 0) * n2, (integer4 + 0) * n3);
        instance.draw();
    }
}
