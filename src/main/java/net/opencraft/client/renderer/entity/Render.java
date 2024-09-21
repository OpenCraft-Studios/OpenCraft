
package net.opencraft.client.renderer.entity;

import net.opencraft.util.Mth;
import net.opencraft.client.entity.models.ModelBase;
import net.opencraft.client.entity.models.ModelBiped;
import net.opencraft.client.renderer.Tessellator;
import net.opencraft.world.World;
import net.opencraft.entity.Entity;
import net.opencraft.physics.AABB;
import net.opencraft.block.Block;
import org.lwjgl.opengl.GL11;

public abstract class Render<T extends Entity> {

    protected RenderManager renderManager;
    private ModelBase modelBase;
    private RenderBlocks renderBlocksVar;
    protected float shadowSize;
    protected float field_194_c;

    public Render() {
        this.modelBase = new ModelBiped();
        this.renderBlocksVar = new RenderBlocks();
        this.shadowSize = 0.0f;
        this.field_194_c = 1.0f;
    }

    public abstract void doRender(final T entityLiving, final double xCoord, final double sqrt_double, final double yCoord, final float nya1, final float nya2);

    protected void loadTexture(final String string) {
        final Renderer renderEngine = this.renderManager.renderEngine;
        renderEngine.bindTexture(renderEngine.getTexture(string));
    }

    protected void loadDownloadableImageTexture(final String string1, final String string2) {
        final Renderer renderEngine = this.renderManager.renderEngine;
        renderEngine.bindTexture(renderEngine.a(string1, string2));
    }

    private void renderEntityOnFire(final Entity eq, final double double2, final double double3, final double double4, final float float5) {
        GL11.glDisable(2896);
        final int blockIndexInTexture = Block.fire.blockIndexInTexture;
        final int n = (blockIndexInTexture & 0xF) << 4;
        final int n2 = blockIndexInTexture & 0xF0;
        final float n3 = n / 256.0f;
        final float n4 = (n + 15.99f) / 256.0f;
        final float n5 = n2 / 256.0f;
        final float n6 = (n2 + 15.99f) / 256.0f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) double2, (float) double3, (float) double4);
        final float n7 = eq.width * 1.4f;
        GL11.glScalef(n7, n7, n7);
        this.loadTexture("/assets/terrain.png");
        final Tessellator instance = Tessellator.instance;
        float n8 = 1.0f;
        final float n9 = 0.5f;
        float n10 = 0.0f;
        float n11 = eq.height / eq.width;
        GL11.glRotatef(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(0.0f, 0.0f, 0.4f + (int) n11 * 0.02f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        instance.startDrawingQuads();
        while (n11 > 0.0f) {
            instance.addVertexWithUV(n8 - n9, 0.0f - n10, 0.0, n4, n6);
            instance.addVertexWithUV(0.0f - n9, 0.0f - n10, 0.0, n3, n6);
            instance.addVertexWithUV(0.0f - n9, 1.4f - n10, 0.0, n3, n5);
            instance.addVertexWithUV(n8 - n9, 1.4f - n10, 0.0, n4, n5);
            --n11;
            --n10;
            n8 *= 0.9f;
            GL11.glTranslatef(0.0f, 0.0f, -0.04f);
        }
        instance.draw();
        GL11.glPopMatrix();
        GL11.glEnable(2896);
    }

    private void renderShadow(final Entity eq, final double double2, final double double3, final double double4, final float float5, final float float6) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final Renderer renderEngine = this.renderManager.renderEngine;
        renderEngine.bindTexture(renderEngine.getTexture("%%/assets/shadow.png"));
        final World worldFromRenderManager = this.getWorldFromRenderManager();
        GL11.glDepthMask(false);
        final float shadowSize = this.shadowSize;
        final double n = eq.lastTickPosX + (eq.posX - eq.lastTickPosX) * float6;
        final double double5 = eq.lastTickPosY + (eq.posY - eq.lastTickPosY) * float6;
        final double n2 = eq.lastTickPosZ + (eq.posZ - eq.lastTickPosZ) * float6;
        final int floor_double = Mth.floor_double(n - shadowSize);
        final int floor_double2 = Mth.floor_double(n + shadowSize);
        final int floor_double3 = Mth.floor_double(double5 - shadowSize);
        final int floor_double4 = Mth.floor_double(double5);
        final int floor_double5 = Mth.floor_double(n2 - shadowSize);
        final int floor_double6 = Mth.floor_double(n2 + shadowSize);
        final double double6 = double2 - n;
        final double double7 = double3 - double5;
        final double double8 = double4 - n2;
        final Tessellator instance = Tessellator.instance;
        instance.startDrawingQuads();
        for (int i = floor_double; i <= floor_double2; ++i) {
            for (int j = floor_double3; j <= floor_double4; ++j) {
                for (int k = floor_double5; k <= floor_double6; ++k) {
                    final int blockId = worldFromRenderManager.getBlockId(i, j - 1, k);
                    if (blockId > 0 && worldFromRenderManager.getBlockLightValue(i, j, k) > 3) {
                        this.renderShadowOnBlock(Block.blocksList[blockId], double2, double3, double4, i, j, k, float5, shadowSize, double6, double7, double8);
                    }
                }
            }
        }
        instance.draw();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
    }

    private World getWorldFromRenderManager() {
        return this.renderManager.worldObj;
    }

    private void renderShadowOnBlock(final Block gs, final double double2, final double double3, final double double4, final int integer5, final int integer6, final int integer7, final float float8, final float float9, final double double10, final double double11, final double double12) {
        final Tessellator instance = Tessellator.instance;
        if (!gs.renderAsNormalBlock()) {
            return;
        }
        double n = (float8 - (double3 - (integer6 + double11)) / 2.0) * 0.5 * this.getWorldFromRenderManager().getLightBrightness(integer5, integer6, integer7);
        if (n < 0.0) {
            return;
        }
        if (n > 1.0) {
            n = 1.0;
        }
        instance.setColorRGBA_F(1.0f, 1.0f, 1.0f, (float) n);
        final double n2 = integer5 + gs.minX + double10;
        final double n3 = integer5 + gs.maxX + double10;
        final double n4 = integer6 + gs.minY + double11 + 0.015625;
        final double n5 = integer7 + gs.minZ + double12;
        final double n6 = integer7 + gs.maxZ + double12;
        final float n7 = (float) ((double2 - n2) / 2.0 / float9 + 0.5);
        final float n8 = (float) ((double2 - n3) / 2.0 / float9 + 0.5);
        final float n9 = (float) ((double4 - n5) / 2.0 / float9 + 0.5);
        final float n10 = (float) ((double4 - n6) / 2.0 / float9 + 0.5);
        instance.addVertexWithUV(n2, n4, n5, n7, n9);
        instance.addVertexWithUV(n2, n4, n6, n7, n10);
        instance.addVertexWithUV(n3, n4, n6, n8, n10);
        instance.addVertexWithUV(n3, n4, n5, n8, n9);
    }

    public static void renderOffsetAABB(final AABB en, final double double2, final double double3, final double double4) {
        GL11.glDisable(3553);
        final Tessellator instance = Tessellator.instance;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        instance.startDrawingQuads();
        instance.setTranslationD(double2, double3, double4);
        instance.setNormal(0.0f, 0.0f, -1.0f);
        instance.addVertex(en.minX, en.maxY, en.minZ);
        instance.addVertex(en.maxX, en.maxY, en.minZ);
        instance.addVertex(en.maxX, en.minY, en.minZ);
        instance.addVertex(en.minX, en.minY, en.minZ);
        instance.setNormal(0.0f, 0.0f, 1.0f);
        instance.addVertex(en.minX, en.minY, en.maxZ);
        instance.addVertex(en.maxX, en.minY, en.maxZ);
        instance.addVertex(en.maxX, en.maxY, en.maxZ);
        instance.addVertex(en.minX, en.maxY, en.maxZ);
        instance.setNormal(0.0f, -1.0f, 0.0f);
        instance.addVertex(en.minX, en.minY, en.minZ);
        instance.addVertex(en.maxX, en.minY, en.minZ);
        instance.addVertex(en.maxX, en.minY, en.maxZ);
        instance.addVertex(en.minX, en.minY, en.maxZ);
        instance.setNormal(0.0f, 1.0f, 0.0f);
        instance.addVertex(en.minX, en.maxY, en.maxZ);
        instance.addVertex(en.maxX, en.maxY, en.maxZ);
        instance.addVertex(en.maxX, en.maxY, en.minZ);
        instance.addVertex(en.minX, en.maxY, en.minZ);
        instance.setNormal(-1.0f, 0.0f, 0.0f);
        instance.addVertex(en.minX, en.minY, en.maxZ);
        instance.addVertex(en.minX, en.maxY, en.maxZ);
        instance.addVertex(en.minX, en.maxY, en.minZ);
        instance.addVertex(en.minX, en.minY, en.minZ);
        instance.setNormal(1.0f, 0.0f, 0.0f);
        instance.addVertex(en.maxX, en.minY, en.minZ);
        instance.addVertex(en.maxX, en.maxY, en.minZ);
        instance.addVertex(en.maxX, en.maxY, en.maxZ);
        instance.addVertex(en.maxX, en.minY, en.maxZ);
        instance.setTranslationD(0.0, 0.0, 0.0);
        instance.draw();
        GL11.glEnable(3553);
    }

    public static void renderAABB(final AABB en) {
        final Tessellator instance = Tessellator.instance;
        instance.startDrawingQuads();
        instance.addVertex(en.minX, en.maxY, en.minZ);
        instance.addVertex(en.maxX, en.maxY, en.minZ);
        instance.addVertex(en.maxX, en.minY, en.minZ);
        instance.addVertex(en.minX, en.minY, en.minZ);
        instance.addVertex(en.minX, en.minY, en.maxZ);
        instance.addVertex(en.maxX, en.minY, en.maxZ);
        instance.addVertex(en.maxX, en.maxY, en.maxZ);
        instance.addVertex(en.minX, en.maxY, en.maxZ);
        instance.addVertex(en.minX, en.minY, en.minZ);
        instance.addVertex(en.maxX, en.minY, en.minZ);
        instance.addVertex(en.maxX, en.minY, en.maxZ);
        instance.addVertex(en.minX, en.minY, en.maxZ);
        instance.addVertex(en.minX, en.maxY, en.maxZ);
        instance.addVertex(en.maxX, en.maxY, en.maxZ);
        instance.addVertex(en.maxX, en.maxY, en.minZ);
        instance.addVertex(en.minX, en.maxY, en.minZ);
        instance.addVertex(en.minX, en.minY, en.maxZ);
        instance.addVertex(en.minX, en.maxY, en.maxZ);
        instance.addVertex(en.minX, en.maxY, en.minZ);
        instance.addVertex(en.minX, en.minY, en.minZ);
        instance.addVertex(en.maxX, en.minY, en.minZ);
        instance.addVertex(en.maxX, en.maxY, en.minZ);
        instance.addVertex(en.maxX, en.maxY, en.maxZ);
        instance.addVertex(en.maxX, en.minY, en.maxZ);
        instance.draw();
    }

    public void setRenderManager(final RenderManager fl) {
        this.renderManager = fl;
    }

    public void doRenderShadowAndFire(final Entity eq, final double double2, final double double3, final double double4, final float float5, final float float6) {
        if (this.renderManager.options.fancyGraphics && this.shadowSize > 0.0f) {
            final float float7 = (float) ((1.0 - this.renderManager.func_851_a(eq.posX, eq.posY, eq.posZ) / 256.0) * this.field_194_c);
            if (float7 > 0.0f) {
                this.renderShadow(eq, double2, double3, double4, float7, float6);
            }
        }
        if (eq.fire > 0) {
            this.renderEntityOnFire(eq, double2, double3, double4, float6);
        }
    }
}
