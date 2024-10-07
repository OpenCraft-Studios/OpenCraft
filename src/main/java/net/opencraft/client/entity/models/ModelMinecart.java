
package net.opencraft.client.entity.models;

import static org.joml.Math.*;

public class ModelMinecart extends ModelBase {

    public ModelRenderer[] sideModels;

    public ModelMinecart() {
        (this.sideModels = new ModelRenderer[7])[0] = new ModelRenderer(0, 10);
        this.sideModels[1] = new ModelRenderer(0, 0);
        this.sideModels[2] = new ModelRenderer(0, 0);
        this.sideModels[3] = new ModelRenderer(0, 0);
        this.sideModels[4] = new ModelRenderer(0, 0);
        this.sideModels[5] = new ModelRenderer(44, 10);
        final int integer4 = 20;
        final int n = 8;
        final int integer5 = 16;
        final int n2 = 4;
        this.sideModels[0].addBox((float) (-integer4 / 2), (float) (-integer5 / 2), -1.0f, integer4, integer5, 2, 0.0f);
        this.sideModels[0].setRotationPoint(0.0f, (float) (0 + n2), 0.0f);
        this.sideModels[5].addBox((float) (-integer4 / 2 + 1), (float) (-integer5 / 2 + 1), -1.0f, integer4 - 2, integer5 - 2, 1, 0.0f);
        this.sideModels[5].setRotationPoint(0.0f, (float) (0 + n2), 0.0f);
        this.sideModels[1].addBox((float) (-integer4 / 2 + 2), (float) (-n - 1), -1.0f, integer4 - 4, n, 2, 0.0f);
        this.sideModels[1].setRotationPoint((float) (-integer4 / 2 + 1), (float) (0 + n2), 0.0f);
        this.sideModels[2].addBox((float) (-integer4 / 2 + 2), (float) (-n - 1), -1.0f, integer4 - 4, n, 2, 0.0f);
        this.sideModels[2].setRotationPoint((float) (integer4 / 2 - 1), (float) (0 + n2), 0.0f);
        this.sideModels[3].addBox((float) (-integer4 / 2 + 2), (float) (-n - 1), -1.0f, integer4 - 4, n, 2, 0.0f);
        this.sideModels[3].setRotationPoint(0.0f, (float) (0 + n2), (float) (-integer5 / 2 + 1));
        this.sideModels[4].addBox((float) (-integer4 / 2 + 2), (float) (-n - 1), -1.0f, integer4 - 4, n, 2, 0.0f);
        this.sideModels[4].setRotationPoint(0.0f, (float) (0 + n2), (float) (integer5 / 2 - 1));
        this.sideModels[0].rotateAngleX = 1.5707964f;
        this.sideModels[1].rotateAngleY = 4.712389f;
        this.sideModels[2].rotateAngleY = 1.5707964f;
        this.sideModels[3].rotateAngleY = PI_f;
        this.sideModels[5].rotateAngleX = -1.5707964f;
    }

    @Override
    public void render(final float nya1, final float nya2, final float nya3, final float nya4, final float nya5, final float nya6) {
        this.sideModels[5].rotationPointY = 4.0f - nya3;
        for (int i = 0; i < 6; ++i) {
            this.sideModels[i].render(nya6);
        }
    }

    @Override
    public void setRotationAngles(final float nya1, final float nya2, final float nya3, final float nya4, final float nya5, final float nya6) {
    }
}
