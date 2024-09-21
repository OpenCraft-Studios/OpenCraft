
package net.opencraft.renderer.entity;

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

    public void doRender(final EntityPlayer entityLiving, final double xCoord, final double sqrt_double, final double yCoord, final float nya1, final float nya2) {
        super.doRender(entityLiving, xCoord, sqrt_double - entityLiving.yOffset, yCoord, nya1, nya2);
    }

    public void drawFirstPersonHand() {
        this.modelBipedMain.bipedRightArm.render(0.0625f);
    }

    static {
        armorFilenamePrefix = new String[]{"cloth", "chain", "iron", "diamond", "gold"};
    }
}
