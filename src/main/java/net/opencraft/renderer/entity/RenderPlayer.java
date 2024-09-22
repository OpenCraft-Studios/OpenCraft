
package net.opencraft.renderer.entity;

import static org.joml.Math.*;

import org.lwjgl.opengl.GL11;

import net.opencraft.client.entity.models.ModelBiped;
import net.opencraft.entity.EntityPlayer;
import net.opencraft.item.ItemStack;
import net.opencraft.item.Item;
import net.opencraft.item.ItemArmor;

public class RenderPlayer extends RenderLiving {

    private ModelBiped modelBipedMain;
    private ModelBiped modelArmorChestplate;
    private ModelBiped modelArmor;
    private static final String[] armorFilenamePrefix;

    public RenderPlayer() {
        super(new ModelBiped(0.0f), 0.5f);
        this.modelBipedMain = (ModelBiped) this.mainModel;
        this.modelArmorChestplate = new ModelBiped(1.0f);
        this.modelArmor = new ModelBiped(0.5f);
    }

    protected boolean shouldRenderPass(final EntityPlayer eVar, final int i) {
        final ItemStack armorItemInSlot = eVar.inventory.armorItemInSlot(3 - i);
        if (armorItemInSlot != null) {
            final Item item = armorItemInSlot.getItem();
            if (item instanceof ItemArmor) {
                this.loadTexture("/assets/armor/" + RenderPlayer.armorFilenamePrefix[((ItemArmor) item).renderIndex] + "_" + ((i == 2) ? 2 : 1) + ".png");
                final ModelBiped renderPassModel = (i == 2) ? this.modelArmor : this.modelArmorChestplate;
                renderPassModel.bipedHead.showModel = (i == 0);
                renderPassModel.bipedHeadwear.showModel = (i == 0);
                renderPassModel.bipedBody.showModel = (i == 1 || i == 2);
                renderPassModel.bipedRightArm.showModel = (i == 1);
                renderPassModel.bipedLeftArm.showModel = (i == 1);
                renderPassModel.bipedRightLeg.showModel = (i == 2 || i == 3);
                renderPassModel.bipedLeftLeg.showModel = (i == 2 || i == 3);
                this.setRenderPassModel(renderPassModel);
                return true;
            }
        }
        return false;
    }

    public void doRender(EntityPlayer entityLiving, double xCoord, double sqrt_double, double yCoord, float nya1, float nya2) {
        //super.doRender(entityLiving, xCoord, sqrt_double - entityLiving.yOffset, yCoord, nya1, nya2);
    	sqrt_double -= entityLiving.yOffset;
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
                float sqrt_f = (entityLiving.deathTime + nya2 - 1.0f) / 20.0f * 1.6f;
                sqrt_f = sqrt(sqrt_f);
                if (sqrt_f > 1.0f) {
                    sqrt_f = 1.0f;
                }
                GL11.glRotatef(sqrt_f * this.getMaxDeathRotation(entityLiving), 0.0f, 0.0f, 1.0f);
            }
            float sqrt_float = 0.0625f;
            GL11.glEnable(32826);
            GL11.glScalef(-1.0f, -1.0f, 1.0f);
            this.preRenderCallback(entityLiving, nya2);
            System.out.println("rendering player");
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

    public void drawFirstPersonHand() {
        this.modelBipedMain.bipedRightArm.render(0.0625f);
    }

    static {
        armorFilenamePrefix = new String[]{"cloth", "chain", "iron", "diamond", "gold"};
    }
}
