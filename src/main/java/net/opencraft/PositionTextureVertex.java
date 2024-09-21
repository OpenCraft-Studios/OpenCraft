
package net.opencraft;

import net.opencraft.util.Vec3;

public class PositionTextureVertex {

    public Vec3 vec;
    public float texturePositionX;
    public float texturePositionY;

    public PositionTextureVertex(final float float1, final float float2, final float float3, final float float4, final float float5) {
        this(Vec3.createVectorHelper(float1, float2, float3), float4, float5);
    }


    public PositionTextureVertex(final PositionTextureVertex hi, final float float2, final float float3) {
        this.vec = hi.vec;
        this.texturePositionX = float2;
        this.texturePositionY = float3;
    }

    public PositionTextureVertex(final Vec3 bo, final float float2, final float float3) {
        this.vec = bo;
        this.texturePositionX = float2;
        this.texturePositionY = float3;
    }

    public PositionTextureVertex setTexturePosition(final float float1, final float float2) {
        return new PositionTextureVertex(this, float1, float2);
    }
}
