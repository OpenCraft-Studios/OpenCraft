
package net.opencraft.client.entity.models;

import static org.joml.Math.*;

import net.opencraft.util.Mth;

public class ModelZombie extends ModelBiped {

    @Override
    public void setRotationAngles(final float nya1, final float nya2, final float nya3, final float nya4, final float nya5, final float nya6) {
        super.setRotationAngles(nya1, nya2, nya3, nya4, nya5, nya6);
        final float sin = sin(this.onGround * PI_f);
        final float sin2 = sin((1.0f - (1.0f - this.onGround) * (1.0f - this.onGround)) * PI_f);
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightArm.rotateAngleY = -(0.1f - sin * 0.6f);
        this.bipedLeftArm.rotateAngleY = 0.1f - sin * 0.6f;
        this.bipedRightArm.rotateAngleX = -1.5707964f;
        this.bipedLeftArm.rotateAngleX = -1.5707964f;
        final ModelRenderer bipedRightArm = this.bipedRightArm;
        bipedRightArm.rotateAngleX -= sin * 1.2f - sin2 * 0.4f;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleX -= sin * 1.2f - sin2 * 0.4f;
        final ModelRenderer bipedRightArm2 = this.bipedRightArm;
        bipedRightArm2.rotateAngleZ += Mth.cos(nya3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleZ -= Mth.cos(nya3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedRightArm3 = this.bipedRightArm;
        bipedRightArm3.rotateAngleX += sin(nya3 * 0.067f) * 0.05f;
        final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
        bipedLeftArm3.rotateAngleX -= sin(nya3 * 0.067f) * 0.05f;
    }
}
