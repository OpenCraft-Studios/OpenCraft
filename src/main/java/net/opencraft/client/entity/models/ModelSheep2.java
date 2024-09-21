
package net.opencraft.client.entity.models;

public class ModelSheep2 extends ModelQuadruped {

    public ModelSheep2() {
        super(12, 0.0f);
        (this.head = new ModelRenderer(0, 0)).addBox(-3.0f, -4.0f, -6.0f, 6, 6, 8, 0.0f);
        this.head.setRotationPoint(0.0f, 6.0f, -8.0f);
        (this.body = new ModelRenderer(28, 8)).addBox(-4.0f, -10.0f, -7.0f, 8, 16, 6, 0.0f);
        this.body.setRotationPoint(0.0f, 5.0f, 2.0f);
    }
}
