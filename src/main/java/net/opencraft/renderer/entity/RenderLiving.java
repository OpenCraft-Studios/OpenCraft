
package net.opencraft.renderer.entity;

import net.opencraft.client.entity.models.ModelBase;
import net.opencraft.entity.EntityLiving;
import net.opencraft.entity.EntitySheep;
import net.opencraft.util.Mth;
import org.lwjgl.opengl.GL11;

public class RenderLiving extends Render<EntityLiving> {

    protected ModelBase mainModel;
    protected ModelBase renderPassModel;

    public RenderLiving(final ModelBase mainModel, final float shadowSize) {
        this.mainModel = mainModel;
        this.shadowSize = shadowSize;
    }

    public void setRenderPassModel(final ModelBase renderPassModel) {
        this.renderPassModel = renderPassModel;
    }

    public void doRender(final EntityLiving entityLiving, final double xCoord, final double sqrt_double, final double yCoord, final float nya1, final float nya2) {
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        try {
            final float n = entityLiving.prevRenderYawOffset + (entityLiving.renderYawOffset - entityLiving.prevRenderYawOffset) * nya2;
            final float n2 = entityLiving.prevRotationYaw + (entityLiving.rotationYaw - entityLiving.prevRotationYaw) * nya2;
            final float n3 = entityLiving.prevRotationPitch + (entityLiving.rotationPitch - entityLiving.prevRotationPitch) * nya2;
            GL11.glTranslatef((float) xCoord, (float) sqrt_double, (float) yCoord);
            final float n4 = entityLiving.ticksExisted + nya2;
            GL11.glRotatef(180.0f - n, 0.0f, 1.0f, 0.0f);
            if (entityLiving.deathTime > 0) {
                float sqrt_float = (entityLiving.deathTime + nya2 - 1.0f) / 20.0f * 1.6f;
                sqrt_float = Mth.sqrt_float(sqrt_float);
                if (sqrt_float > 1.0f) {
                    sqrt_float = 1.0f;
                }
                GL11.glRotatef(sqrt_float * this.getMaxDeathRotation(entityLiving), 0.0f, 0.0f, 1.0f);
            }
            float sqrt_float = 0.0625f;
            GL11.glEnable(32826);
            GL11.glScalef(-1.0f, -1.0f, 1.0f);
            this.preRenderCallback(entityLiving, nya2);
            GL11.glTranslatef(0.0f, -24.0f * sqrt_float - 0.0078125f, 0.0f);
            float n5 = entityLiving.newPosZ + (entityLiving.newRotationYaw - entityLiving.newPosZ) * nya2;
            final float n6 = entityLiving.newRotationPitch - entityLiving.newRotationYaw * (1.0f - nya2);
            if (n5 > 1.0f) {
                n5 = 1.0f;
            }
            this.loadDownloadableImageTexture(entityLiving.skinUrl, entityLiving.addToPlayerScore());
            GL11.glEnable(3008);
            this.mainModel.render(n6, n5, n4, n2 - n, n3, sqrt_float);
            for (int i = 0; i < 4; ++i) {
                if (this.shouldRenderPass(entityLiving, i)) {
                    this.renderPassModel.render(n6, n5, n4, n2 - n, n3, sqrt_float);
                    GL11.glDisable(3042);
                    GL11.glEnable(3008);
                }
            }
            final float entityBrightness = entityLiving.getEntityBrightness(nya2);
            final int colorMultiplier = this.getColorMultiplier(entityLiving, entityBrightness, nya2);
            if ((colorMultiplier >> 24 & 0xFF) > 0 || entityLiving.hurtTime > 0 || entityLiving.deathTime > 0) {
                GL11.glDisable(3553);
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDepthFunc(514);
                if (entityLiving.hurtTime > 0 || entityLiving.deathTime > 0) {
                    GL11.glColor4f(entityBrightness, 0.0f, 0.0f, 0.4f);
                    this.mainModel.render(n6, n5, n4, n2 - n, n3, sqrt_float);
                    for (int j = 0; j < 4; ++j) {
                        if (this.shouldRenderPass(entityLiving, j)) {
                            GL11.glColor4f(entityBrightness, 0.0f, 0.0f, 0.4f);
                            this.renderPassModel.render(n6, n5, n4, n2 - n, n3, sqrt_float);
                        }
                    }
                }
                if ((colorMultiplier >> 24 & 0xFF) > 0) {
                    final float n7 = (colorMultiplier >> 16 & 0xFF) / 255.0f;
                    final float n8 = (colorMultiplier >> 8 & 0xFF) / 255.0f;
                    final float n9 = (colorMultiplier & 0xFF) / 255.0f;
                    final float n10 = (colorMultiplier >> 24 & 0xFF) / 255.0f;
                    GL11.glColor4f(n7, n8, n9, n10);
                    this.mainModel.render(n6, n5, n4, n2 - n, n3, sqrt_float);
                    for (int k = 0; k < 4; ++k) {
                        if (this.shouldRenderPass(entityLiving, k)) {
                            GL11.glColor4f(n7, n8, n9, n10);
                            this.renderPassModel.render(n6, n5, n4, n2 - n, n3, sqrt_float);
                        }
                    }
                }
                GL11.glDepthFunc(515);
                GL11.glDisable(3042);
                GL11.glEnable(3008);
                GL11.glEnable(3553);
            }
            GL11.glDisable(32826);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }

    public boolean shouldRenderPass(final EntityLiving eVar, final int i) {
        if (eVar instanceof EntitySheep) {
            this.loadTexture("/assets/mob/sheep_fur.png");
            return i == 0 && !((EntitySheep) eVar).sheared;
        }
        return false;
    }

    protected float getMaxDeathRotation(final EntityLiving eVar) {
        return 90.0f;
    }

    protected int getColorMultiplier(final EntityLiving entityLiving, final float nya1, final float nya2) {
        return 0;
    }

    protected void preRenderCallback(final EntityLiving entityLiving, final float nya1) {
    }
}
