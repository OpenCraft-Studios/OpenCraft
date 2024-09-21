
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

    public boolean isBoxInFrustum(double a, double b, double c, double d, double e, double f) {
        for (int i = 0; i < 6; ++i) {
            if (isOutsidePlane(i, a, b, c) &&
                isOutsidePlane(i, d, b, c) &&
                isOutsidePlane(i, a, e, c) &&
                isOutsidePlane(i, d, e, c) &&
                isOutsidePlane(i, a, b, f) &&
                isOutsidePlane(i, d, b, f) &&
                isOutsidePlane(i, a, e, f) &&
                isOutsidePlane(i, d, e, f)) {
                return false;
            }
        }
        return true;
    }

    private boolean isOutsidePlane(int i, double x, double y, double z) {
        return this.frustum[i][0] * x + this.frustum[i][1] * y + this.frustum[i][2] * z + this.frustum[i][3] <= 0.0;
    }

}
