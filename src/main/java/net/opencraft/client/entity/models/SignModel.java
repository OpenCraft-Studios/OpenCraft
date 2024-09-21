
package net.opencraft.client.entity.models;

public class SignModel {

    public ModelRenderer signBoard;
    public ModelRenderer signStick;

    public SignModel() {
        (this.signBoard = new ModelRenderer(0, 0)).addBox(-12.0f, -14.0f, -1.0f, 24, 12, 2, 0.0f);
        (this.signStick = new ModelRenderer(0, 14)).addBox(-1.0f, -2.0f, -1.0f, 2, 14, 2, 0.0f);
    }

    public void func_887_a() {
        this.signBoard.render(0.0625f);
        this.signStick.render(0.0625f);
    }
}
