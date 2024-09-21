
package net.opencraft.client.entity.models;

public class ModelSheep1 extends ModelQuadruped {

    public ModelSheep1() {
        super(12, 0.0f);
        (this.head = new ModelRenderer(0, 0)).addBox(-3.0f, -4.0f, -4.0f, 6, 6, 6, 0.6f);
        this.head.setRotationPoint(0.0f, 6.0f, -8.0f);
        (this.body = new ModelRenderer(28, 8)).addBox(-4.0f, -10.0f, -7.0f, 8, 16, 6, 1.75f);
        this.body.setRotationPoint(0.0f, 5.0f, 2.0f);
        final float n = 0.5f;
        (this.leg1 = new ModelRenderer(0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, n);
        this.leg1.setRotationPoint(-3.0f, 12.0f, 7.0f);
        (this.leg2 = new ModelRenderer(0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, n);
        this.leg2.setRotationPoint(3.0f, 12.0f, 7.0f);
        (this.leg3 = new ModelRenderer(0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, n);
        this.leg3.setRotationPoint(-3.0f, 12.0f, -5.0f);
        (this.leg4 = new ModelRenderer(0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, n);
        this.leg4.setRotationPoint(3.0f, 12.0f, -5.0f);
    }
}
