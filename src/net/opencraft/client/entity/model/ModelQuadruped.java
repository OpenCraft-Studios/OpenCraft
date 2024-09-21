
package net.opencraft.client.entity.model;

import net.opencraft.util.MathHelper;

public class ModelQuadruped extends ModelBase {

    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;

    public ModelQuadruped(final int integer, final float float2) {
        (this.head = new ModelRenderer(0, 0)).addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, float2);
        this.head.setRotationPoint(0.0f, (float) (18 - integer), -6.0f);
        (this.body = new ModelRenderer(28, 8)).addBox(-5.0f, -10.0f, -7.0f, 10, 16, 8, float2);
        this.body.setRotationPoint(0.0f, (float) (17 - integer), 2.0f);
        (this.leg1 = new ModelRenderer(0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, integer, 4, float2);
        this.leg1.setRotationPoint(-3.0f, (float) (24 - integer), 7.0f);
        (this.leg2 = new ModelRenderer(0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, integer, 4, float2);
        this.leg2.setRotationPoint(3.0f, (float) (24 - integer), 7.0f);
        (this.leg3 = new ModelRenderer(0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, integer, 4, float2);
        this.leg3.setRotationPoint(-3.0f, (float) (24 - integer), -5.0f);
        (this.leg4 = new ModelRenderer(0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, integer, 4, float2);
        this.leg4.setRotationPoint(3.0f, (float) (24 - integer), -5.0f);
    }

    @Override
    public void render(final float nya1, final float nya2, final float nya3, final float nya4, final float nya5, final float nya6) {
        this.setRotationAngles(nya1, nya2, nya3, nya4, nya5, nya6);
        this.head.render(nya6);
        this.body.render(nya6);
        this.leg1.render(nya6);
        this.leg2.render(nya6);
        this.leg3.render(nya6);
        this.leg4.render(nya6);
    }

    @Override
    public void setRotationAngles(final float nya1, final float nya2, final float nya3, final float nya4, final float nya5, final float nya6) {
        this.head.rotateAngleY = nya4 / 57.295776f;
        this.body.rotateAngleX = 1.5707964f;
        this.leg1.rotateAngleX = MathHelper.cos(nya1 * 0.6662f) * 1.4f * nya2;
        this.leg2.rotateAngleX = MathHelper.cos(nya1 * 0.6662f + 3.1415927f) * 1.4f * nya2;
        this.leg3.rotateAngleX = MathHelper.cos(nya1 * 0.6662f + 3.1415927f) * 1.4f * nya2;
        this.leg4.rotateAngleX = MathHelper.cos(nya1 * 0.6662f) * 1.4f * nya2;
    }
}
