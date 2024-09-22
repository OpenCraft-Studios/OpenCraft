
package net.opencraft.client.entity.models;

import static org.joml.Math.*;

import net.opencraft.util.Mth;

public class ModelSpider extends ModelBase {

    public ModelRenderer spiderHead;
    public ModelRenderer spiderNeck;
    public ModelRenderer spiderBody;
    public ModelRenderer spiderLeg1;
    public ModelRenderer spiderLeg2;
    public ModelRenderer spiderLeg3;
    public ModelRenderer spiderLeg4;
    public ModelRenderer spiderLeg5;
    public ModelRenderer spiderLeg6;
    public ModelRenderer spiderLeg7;
    public ModelRenderer spiderLeg8;

    public ModelSpider() {
        final float float7 = 0.0f;
        final int n = 15;
        (this.spiderHead = new ModelRenderer(32, 4)).addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, float7);
        this.spiderHead.setRotationPoint(0.0f, (float) (0 + n), -3.0f);
        (this.spiderNeck = new ModelRenderer(0, 0)).addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6, float7);
        this.spiderNeck.setRotationPoint(0.0f, (float) n, 0.0f);
        (this.spiderBody = new ModelRenderer(0, 12)).addBox(-5.0f, -4.0f, -6.0f, 10, 8, 12, float7);
        this.spiderBody.setRotationPoint(0.0f, (float) (0 + n), 9.0f);
        (this.spiderLeg1 = new ModelRenderer(18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, float7);
        this.spiderLeg1.setRotationPoint(-4.0f, (float) (0 + n), 2.0f);
        (this.spiderLeg2 = new ModelRenderer(18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, float7);
        this.spiderLeg2.setRotationPoint(4.0f, (float) (0 + n), 2.0f);
        (this.spiderLeg3 = new ModelRenderer(18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, float7);
        this.spiderLeg3.setRotationPoint(-4.0f, (float) (0 + n), 1.0f);
        (this.spiderLeg4 = new ModelRenderer(18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, float7);
        this.spiderLeg4.setRotationPoint(4.0f, (float) (0 + n), 1.0f);
        (this.spiderLeg5 = new ModelRenderer(18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, float7);
        this.spiderLeg5.setRotationPoint(-4.0f, (float) (0 + n), 0.0f);
        (this.spiderLeg6 = new ModelRenderer(18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, float7);
        this.spiderLeg6.setRotationPoint(4.0f, (float) (0 + n), 0.0f);
        (this.spiderLeg7 = new ModelRenderer(18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, float7);
        this.spiderLeg7.setRotationPoint(-4.0f, (float) (0 + n), -1.0f);
        (this.spiderLeg8 = new ModelRenderer(18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, float7);
        this.spiderLeg8.setRotationPoint(4.0f, (float) (0 + n), -1.0f);
    }

    @Override
    public void render(final float nya1, final float nya2, final float nya3, final float nya4, final float nya5, final float nya6) {
        this.setRotationAngles(nya1, nya2, nya3, nya4, nya5, nya6);
        this.spiderHead.render(nya6);
        this.spiderNeck.render(nya6);
        this.spiderBody.render(nya6);
        this.spiderLeg1.render(nya6);
        this.spiderLeg2.render(nya6);
        this.spiderLeg3.render(nya6);
        this.spiderLeg4.render(nya6);
        this.spiderLeg5.render(nya6);
        this.spiderLeg6.render(nya6);
        this.spiderLeg7.render(nya6);
        this.spiderLeg8.render(nya6);
    }

    @Override
    public void setRotationAngles(final float nya1, final float nya2, final float nya3, final float nya4, final float nya5, final float nya6) {
        this.spiderHead.rotateAngleY = nya4 / 57.295776f;
        this.spiderHead.rotateAngleX = nya5 / 57.295776f;
        final float n = 0.7853982f;
        this.spiderLeg1.rotateAngleZ = -n;
        this.spiderLeg2.rotateAngleZ = n;
        this.spiderLeg3.rotateAngleZ = -n * 0.74f;
        this.spiderLeg4.rotateAngleZ = n * 0.74f;
        this.spiderLeg5.rotateAngleZ = -n * 0.74f;
        this.spiderLeg6.rotateAngleZ = n * 0.74f;
        this.spiderLeg7.rotateAngleZ = -n;
        this.spiderLeg8.rotateAngleZ = n;
        final float n2 = -0.0f;
        final float n3 = 0.3926991f;
        this.spiderLeg1.rotateAngleY = n3 * 2.0f + n2;
        this.spiderLeg2.rotateAngleY = -n3 * 2.0f - n2;
        this.spiderLeg3.rotateAngleY = n3 * 1.0f + n2;
        this.spiderLeg4.rotateAngleY = -n3 * 1.0f - n2;
        this.spiderLeg5.rotateAngleY = -n3 * 1.0f + n2;
        this.spiderLeg6.rotateAngleY = n3 * 1.0f - n2;
        this.spiderLeg7.rotateAngleY = -n3 * 2.0f + n2;
        this.spiderLeg8.rotateAngleY = n3 * 2.0f - n2;
        final float n4 = -(Mth.cos(nya1 * 0.6662f * 2.0f + 0.0f) * 0.4f) * nya2;
        final float n5 = -(Mth.cos(nya1 * 0.6662f * 2.0f + PI_f) * 0.4f) * nya2;
        final float n6 = -(Mth.cos(nya1 * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * nya2;
        final float n7 = -(Mth.cos(nya1 * 0.6662f * 2.0f + 4.712389f) * 0.4f) * nya2;
        final float n8 = Math.abs(sin(nya1 * 0.6662f + 0.0f) * 0.4f) * nya2;
        final float n9 = Math.abs(sin(nya1 * 0.6662f + PI_f) * 0.4f) * nya2;
        final float n10 = Math.abs(sin(nya1 * 0.6662f + 1.5707964f) * 0.4f) * nya2;
        final float n11 = Math.abs(sin(nya1 * 0.6662f + 4.712389f) * 0.4f) * nya2;
        final ModelRenderer spiderLeg1 = this.spiderLeg1;
        spiderLeg1.rotateAngleY += n4;
        final ModelRenderer spiderLeg2 = this.spiderLeg2;
        spiderLeg2.rotateAngleY += -n4;
        final ModelRenderer spiderLeg3 = this.spiderLeg3;
        spiderLeg3.rotateAngleY += n5;
        final ModelRenderer spiderLeg4 = this.spiderLeg4;
        spiderLeg4.rotateAngleY += -n5;
        final ModelRenderer spiderLeg5 = this.spiderLeg5;
        spiderLeg5.rotateAngleY += n6;
        final ModelRenderer spiderLeg6 = this.spiderLeg6;
        spiderLeg6.rotateAngleY += -n6;
        final ModelRenderer spiderLeg7 = this.spiderLeg7;
        spiderLeg7.rotateAngleY += n7;
        final ModelRenderer spiderLeg8 = this.spiderLeg8;
        spiderLeg8.rotateAngleY += -n7;
        final ModelRenderer spiderLeg9 = this.spiderLeg1;
        spiderLeg9.rotateAngleZ += n8;
        final ModelRenderer spiderLeg10 = this.spiderLeg2;
        spiderLeg10.rotateAngleZ += -n8;
        final ModelRenderer spiderLeg11 = this.spiderLeg3;
        spiderLeg11.rotateAngleZ += n9;
        final ModelRenderer spiderLeg12 = this.spiderLeg4;
        spiderLeg12.rotateAngleZ += -n9;
        final ModelRenderer spiderLeg13 = this.spiderLeg5;
        spiderLeg13.rotateAngleZ += n10;
        final ModelRenderer spiderLeg14 = this.spiderLeg6;
        spiderLeg14.rotateAngleZ += -n10;
        final ModelRenderer spiderLeg15 = this.spiderLeg7;
        spiderLeg15.rotateAngleZ += n11;
        final ModelRenderer spiderLeg16 = this.spiderLeg8;
        spiderLeg16.rotateAngleZ += -n11;
    }
}
