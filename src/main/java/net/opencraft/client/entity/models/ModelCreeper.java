
package net.opencraft.client.entity.models;

import static org.joml.Math.*;

public class ModelCreeper extends ModelBase {

	public ModelRenderer unusedCreeperHeadwear;
    public ModelRenderer head, body;
    public ModelRenderer leg1, leg2, leg3, leg4;

    public ModelCreeper() {
        final float n = 0.0f;
        final int n2 = 4;
        (this.head = new ModelRenderer(0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, n);
        this.head.setRotationPoint(0.0f, (float) n2, 0.0f);
        (this.unusedCreeperHeadwear = new ModelRenderer(32, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, n + 0.5f);
        this.unusedCreeperHeadwear.setRotationPoint(0.0f, (float) n2, 0.0f);
        (this.body = new ModelRenderer(16, 16)).addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, n);
        this.body.setRotationPoint(0.0f, (float) n2, 0.0f);
        (this.leg1 = new ModelRenderer(0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, n);
        this.leg1.setRotationPoint(-2.0f, (float) (12 + n2), 4.0f);
        (this.leg2 = new ModelRenderer(0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, n);
        this.leg2.setRotationPoint(2.0f, (float) (12 + n2), 4.0f);
        (this.leg3 = new ModelRenderer(0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, n);
        this.leg3.setRotationPoint(-2.0f, (float) (12 + n2), -4.0f);
        (this.leg4 = new ModelRenderer(0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, n);
        this.leg4.setRotationPoint(2.0f, (float) (12 + n2), -4.0f);
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
        this.head.rotateAngleY = toRadians(nya4);
        this.head.rotateAngleX = toRadians(nya5);
        this.leg1.rotateAngleX = cos(nya1 * 0.6662f) * 1.4f * nya2;
        this.leg2.rotateAngleX = cos(nya1 * 0.6662f + PI_f) * 1.4f * nya2;
        this.leg3.rotateAngleX = cos(nya1 * 0.6662f + PI_f) * 1.4f * nya2;
        this.leg4.rotateAngleX = cos(nya1 * 0.6662f) * 1.4f * nya2;
    }
}
