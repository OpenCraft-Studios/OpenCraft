
package net.opencraft.client.entity.model;

import net.opencraft.util.MathHelper;

public class ModelBiped extends ModelBase {

    public ModelRenderer bipedHead;
    public ModelRenderer bipedHeadwear;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;

    public ModelBiped() {
        this(0.0f);
    }

    public ModelBiped(final float float1) {
        this(float1, 0.0f);
    }

    public ModelBiped(final float float1, final float float2) {
        (this.bipedHead = new ModelRenderer(0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, float1);
        this.bipedHead.setRotationPoint(0.0f, 0.0f + float2, 0.0f);
        (this.bipedHeadwear = new ModelRenderer(32, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, float1 + 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f + float2, 0.0f);
        (this.bipedBody = new ModelRenderer(16, 16)).addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, float1);
        this.bipedBody.setRotationPoint(0.0f, 0.0f + float2, 0.0f);
        (this.bipedRightArm = new ModelRenderer(40, 16)).addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, float1);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f + float2, 0.0f);
        this.bipedLeftArm = new ModelRenderer(40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, float1);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f + float2, 0.0f);
        (this.bipedRightLeg = new ModelRenderer(0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, float1);
        this.bipedRightLeg.setRotationPoint(-2.0f, 12.0f + float2, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, float1);
        this.bipedLeftLeg.setRotationPoint(2.0f, 12.0f + float2, 0.0f);
    }

    @Override
    public void render(final float nya1, final float nya2, final float nya3, final float nya4, final float nya5, final float nya6) {
        this.setRotationAngles(nya1, nya2, nya3, nya4, nya5, nya6);
        this.bipedHead.render(nya6);
        this.bipedBody.render(nya6);
        this.bipedRightArm.render(nya6);
        this.bipedLeftArm.render(nya6);
        this.bipedRightLeg.render(nya6);
        this.bipedLeftLeg.render(nya6);
        this.bipedHeadwear.render(nya6);
    }

    @Override
    public void setRotationAngles(final float nya1, final float nya2, final float nya3, final float nya4, final float nya5, final float nya6) {
        this.bipedHead.rotateAngleY = nya4 / 57.295776f;
        this.bipedHead.rotateAngleX = nya5 / 57.295776f;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedRightArm.rotateAngleX = MathHelper.cos(nya1 * 0.6662f + 3.1415927f) * 2.0f * nya2;
        this.bipedRightArm.rotateAngleZ = (MathHelper.cos(nya1 * 0.2312f) + 1.0f) * 1.0f * nya2;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(nya1 * 0.6662f) * 2.0f * nya2;
        this.bipedLeftArm.rotateAngleZ = (MathHelper.cos(nya1 * 0.2812f) - 1.0f) * 1.0f * nya2;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(nya1 * 0.6662f) * 1.4f * nya2;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(nya1 * 0.6662f + 3.1415927f) * 1.4f * nya2;
        final ModelRenderer bipedRightArm = this.bipedRightArm;
        bipedRightArm.rotateAngleZ += MathHelper.cos(nya3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleZ -= MathHelper.cos(nya3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedRightArm2 = this.bipedRightArm;
        bipedRightArm2.rotateAngleX += MathHelper.sin(nya3 * 0.067f) * 0.05f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleX -= MathHelper.sin(nya3 * 0.067f) * 0.05f;
    }
}
