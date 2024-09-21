
package net.opencraft.client.entity.models;

import net.opencraft.PositionTextureVertex;
import net.opencraft.renderer.*;

import org.lwjgl.opengl.GL11;

public class ModelRenderer {

    private PositionTextureVertex[] corners;
    private TexturedQuad[] faces;
    private int textureOffsetX;
    private int textureOffsetY;
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    private boolean compiled;
    private int displayList;
    public boolean mirror;
    public boolean showModel;
    public boolean isHidden;

    public ModelRenderer(final int integer1, final int integer2) {
        this.compiled = false;
        this.displayList = 0;
        this.mirror = false;
        this.showModel = true;
        this.isHidden = false;
        this.textureOffsetX = integer1;
        this.textureOffsetY = integer2;
    }

    public void addBox(float float1, float float2, float float3, final int integer4, final int integer5, final int integer6, final float float7) {
        this.corners = new PositionTextureVertex[8];
        this.faces = new TexturedQuad[6];
        float n = float1 + integer4;
        float n2 = float2 + integer5;
        float n3 = float3 + integer6;
        float1 -= float7;
        float2 -= float7;
        float3 -= float7;
        n += float7;
        n2 += float7;
        n3 += float7;
        if (this.mirror) {
            final float n4 = n;
            n = float1;
            float1 = n4;
        }
        final PositionTextureVertex positionTextureVertex = new PositionTextureVertex(float1, float2, float3, 0.0f, 0.0f);
        final PositionTextureVertex positionTextureVertex2 = new PositionTextureVertex(n, float2, float3, 0.0f, 8.0f);
        final PositionTextureVertex positionTextureVertex3 = new PositionTextureVertex(n, n2, float3, 8.0f, 8.0f);
        final PositionTextureVertex positionTextureVertex4 = new PositionTextureVertex(float1, n2, float3, 8.0f, 0.0f);
        final PositionTextureVertex positionTextureVertex5 = new PositionTextureVertex(float1, float2, n3, 0.0f, 0.0f);
        final PositionTextureVertex positionTextureVertex6 = new PositionTextureVertex(n, float2, n3, 0.0f, 8.0f);
        final PositionTextureVertex positionTextureVertex7 = new PositionTextureVertex(n, n2, n3, 8.0f, 8.0f);
        final PositionTextureVertex positionTextureVertex8 = new PositionTextureVertex(float1, n2, n3, 8.0f, 0.0f);
        this.corners[0] = positionTextureVertex;
        this.corners[1] = positionTextureVertex2;
        this.corners[2] = positionTextureVertex3;
        this.corners[3] = positionTextureVertex4;
        this.corners[4] = positionTextureVertex5;
        this.corners[5] = positionTextureVertex6;
        this.corners[6] = positionTextureVertex7;
        this.corners[7] = positionTextureVertex8;
        this.faces[0] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex6, positionTextureVertex2, positionTextureVertex3, positionTextureVertex7}, this.textureOffsetX + integer6 + integer4, this.textureOffsetY + integer6, this.textureOffsetX + integer6 + integer4 + integer6, this.textureOffsetY + integer6 + integer5);
        this.faces[1] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex, positionTextureVertex5, positionTextureVertex8, positionTextureVertex4}, this.textureOffsetX + 0, this.textureOffsetY + integer6, this.textureOffsetX + integer6, this.textureOffsetY + integer6 + integer5);
        this.faces[2] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex6, positionTextureVertex5, positionTextureVertex, positionTextureVertex2}, this.textureOffsetX + integer6, this.textureOffsetY + 0, this.textureOffsetX + integer6 + integer4, this.textureOffsetY + integer6);
        this.faces[3] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex3, positionTextureVertex4, positionTextureVertex8, positionTextureVertex7}, this.textureOffsetX + integer6 + integer4, this.textureOffsetY + 0, this.textureOffsetX + integer6 + integer4 + integer4, this.textureOffsetY + integer6);
        this.faces[4] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex2, positionTextureVertex, positionTextureVertex4, positionTextureVertex3}, this.textureOffsetX + integer6, this.textureOffsetY + integer6, this.textureOffsetX + integer6 + integer4, this.textureOffsetY + integer6 + integer5);
        this.faces[5] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex5, positionTextureVertex6, positionTextureVertex7, positionTextureVertex8}, this.textureOffsetX + integer6 + integer4 + integer6, this.textureOffsetY + integer6, this.textureOffsetX + integer6 + integer4 + integer6 + integer4, this.textureOffsetY + integer6 + integer5);
        if (this.mirror) {
            for (int i = 0; i < this.faces.length; ++i) {
                this.faces[i].flipFace();
            }
        }
    }

    public void setRotationPoint(final float float1, final float float2, final float float3) {
        this.rotationPointX = float1;
        this.rotationPointY = float2;
        this.rotationPointZ = float3;
    }

    public void render(final float float1) {
        if (this.isHidden) {
            return;
        }
        if (!this.showModel) {
            return;
        }
        if (!this.compiled) {
            this.compileDisplayList(float1);
        }
        if (this.rotateAngleX != 0.0f || this.rotateAngleY != 0.0f || this.rotateAngleZ != 0.0f) {
            GL11.glPushMatrix();
            GL11.glTranslatef(this.rotationPointX * float1, this.rotationPointY * float1, this.rotationPointZ * float1);
            if (this.rotateAngleZ != 0.0f) {
                GL11.glRotatef(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            if (this.rotateAngleY != 0.0f) {
                GL11.glRotatef(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (this.rotateAngleX != 0.0f) {
                GL11.glRotatef(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            GL11.glCallList(this.displayList);
            GL11.glPopMatrix();
        } else if (this.rotationPointX != 0.0f || this.rotationPointY != 0.0f || this.rotationPointZ != 0.0f) {
            GL11.glTranslatef(this.rotationPointX * float1, this.rotationPointY * float1, this.rotationPointZ * float1);
            GL11.glCallList(this.displayList);
            GL11.glTranslatef(-this.rotationPointX * float1, -this.rotationPointY * float1, -this.rotationPointZ * float1);
        } else {
            GL11.glCallList(this.displayList);
        }
    }

    private void compileDisplayList(final float float1) {
        GL11.glNewList(this.displayList = GLAllocation.generateDisplayLists(1), 4864);
        final Tessellator instance = Tessellator.instance;
        for (int i = 0; i < this.faces.length; ++i) {
            this.faces[i].draw(instance, float1);
        }
        GL11.glEndList();
        this.compiled = true;
    }
}
