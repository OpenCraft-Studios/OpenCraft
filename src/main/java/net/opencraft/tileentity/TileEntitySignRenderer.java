
package net.opencraft.tileentity;

import net.opencraft.client.entity.models.SignModel;
import net.opencraft.client.font.FontRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntitySignRenderer extends TileEntitySpecialRenderer<TileEntitySign> {

    private SignModel signModel;

    public TileEntitySignRenderer() {
        this.signModel = new SignModel();
    }

    public void renderTileEntityMobSpawner(final TileEntitySign jn, final double double2, final double double3, final double double4, final float float5) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) double2 + 0.5f, (float) double3 + 0.75f, (float) double4 + 0.5f);
        GL11.glRotatef(-(jn.getBlockMetadata() * 360 / 16.0f), 0.0f, 1.0f, 0.0f);
        this.bindTextureByName("/assets/item/sign.png");
        GL11.glPushMatrix();
        GL11.glScalef(1.0f, -1.0f, -1.0f);
        this.signModel.func_887_a();
        GL11.glPopMatrix();
        final float n = 0.016666668f;
        GL11.glTranslatef(0.0f, 0.5f, 0.09f);
        GL11.glScalef(n, -n, n);
        GL11.glNormal3f(0.0f, 0.0f, -1.0f * n);
        final FontRenderer fontRenderer = this.getFontRenderer();
        for (int i = 0; i < jn.signText.length; ++i) {
            final String s = jn.signText[i];
            if (i == jn.lineBeingEdited) {
                final String string = "> " + s + " <";
                fontRenderer.drawString2(string, -fontRenderer.getStringWidth(string) / 2, i * 10 - jn.signText.length * 5, 0);
            } else {
                fontRenderer.drawString2(s, -fontRenderer.getStringWidth(s) / 2, i * 10 - jn.signText.length * 5, 0);
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
}
