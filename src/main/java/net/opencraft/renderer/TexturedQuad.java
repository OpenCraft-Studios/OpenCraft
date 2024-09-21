
package net.opencraft.renderer;

import net.opencraft.PositionTextureVertex;
import net.opencraft.util.Vec3;

public class TexturedQuad {

    public PositionTextureVertex[] vertexPositions;
    public int nVertices;

    public TexturedQuad(final PositionTextureVertex[] arr) {
        this.nVertices = 0;
        this.vertexPositions = arr;
        this.nVertices = arr.length;
    }

    public TexturedQuad(final PositionTextureVertex[] arr, final int integer2, final int integer3, final int integer4, final int integer5) {
        this(arr);
        final float n = 0.0015625f;
        final float n2 = 0.003125f;
        arr[0] = arr[0].setTexturePosition(integer4 / 64.0f - n, integer3 / 32.0f + n2);
        arr[1] = arr[1].setTexturePosition(integer2 / 64.0f + n, integer3 / 32.0f + n2);
        arr[2] = arr[2].setTexturePosition(integer2 / 64.0f + n, integer5 / 32.0f - n2);
        arr[3] = arr[3].setTexturePosition(integer4 / 64.0f - n, integer5 / 32.0f - n2);
    }

    public void flipFace() {
        final PositionTextureVertex[] vertexPositions = new PositionTextureVertex[this.vertexPositions.length];
        for (int i = 0; i < this.vertexPositions.length; ++i) {
            vertexPositions[i] = this.vertexPositions[this.vertexPositions.length - i - 1];
        }
        this.vertexPositions = vertexPositions;
    }

    public void draw(final Tessellator ag, final float float2) {
        final Vec3 normalize = this.vertexPositions[1].vec.subtract(this.vertexPositions[2].vec).cross(this.vertexPositions[1].vec.subtract(this.vertexPositions[0].vec)).normalize();
        ag.beginQuads();
        ag.setNormal((float) normalize.x, (float) normalize.y, (float) normalize.z);
        for (int i = 0; i < 4; ++i) {
            final PositionTextureVertex positionTextureVertex = this.vertexPositions[i];
            ag.vertexUV((float) positionTextureVertex.vec.x * float2, (float) positionTextureVertex.vec.y * float2, (float) positionTextureVertex.vec.z * float2, positionTextureVertex.texturePositionX, positionTextureVertex.texturePositionY);
        }
        ag.draw();
    }
}
