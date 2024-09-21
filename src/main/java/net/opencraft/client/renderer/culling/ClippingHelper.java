
package net.opencraft.client.renderer.culling;

public class ClippingHelper {

    public float[][] frustum;
    public float[] projectionMatrix;
    public float[] modelviewMatrix;
    public float[] clippingMatrix;

    public ClippingHelper() {
        this.frustum = new float[16][16];
        this.projectionMatrix = new float[16];
        this.modelviewMatrix = new float[16];
        this.clippingMatrix = new float[16];
    }

    public boolean isBoxInFrustum(final double double1, final double double2, final double double3, final double double4, final double double5, final double double6) {
        for (int i = 0; i < 6; ++i) {
            if (this.frustum[i][0] * double1 + this.frustum[i][1] * double2 + this.frustum[i][2] * double3 + this.frustum[i][3] <= 0.0) {
                if (this.frustum[i][0] * double4 + this.frustum[i][1] * double2 + this.frustum[i][2] * double3 + this.frustum[i][3] <= 0.0) {
                    if (this.frustum[i][0] * double1 + this.frustum[i][1] * double5 + this.frustum[i][2] * double3 + this.frustum[i][3] <= 0.0) {
                        if (this.frustum[i][0] * double4 + this.frustum[i][1] * double5 + this.frustum[i][2] * double3 + this.frustum[i][3] <= 0.0) {
                            if (this.frustum[i][0] * double1 + this.frustum[i][1] * double2 + this.frustum[i][2] * double6 + this.frustum[i][3] <= 0.0) {
                                if (this.frustum[i][0] * double4 + this.frustum[i][1] * double2 + this.frustum[i][2] * double6 + this.frustum[i][3] <= 0.0) {
                                    if (this.frustum[i][0] * double1 + this.frustum[i][1] * double5 + this.frustum[i][2] * double6 + this.frustum[i][3] <= 0.0) {
                                        if (this.frustum[i][0] * double4 + this.frustum[i][1] * double5 + this.frustum[i][2] * double6 + this.frustum[i][3] <= 0.0) {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
