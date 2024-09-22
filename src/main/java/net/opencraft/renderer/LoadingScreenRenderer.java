
package net.opencraft.renderer;

import net.opencraft.OpenCraft;
import net.opencraft.OpenCraftError;
import net.opencraft.ScaledResolution;
import net.opencraft.renderer.gui.IProgressUpdate;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class LoadingScreenRenderer implements IProgressUpdate {

    private String field_1004_a;
    private OpenCraft mc;
    private String currentlyDisplayedText;
    private long field_1006_d;
    private boolean field_1005_e;

    public LoadingScreenRenderer(final OpenCraft aw) {
        this.field_1004_a = "";
        this.currentlyDisplayedText = "";
        this.field_1006_d = System.currentTimeMillis();
        this.field_1005_e = false;
        this.mc = aw;
    }

    public void printText(final String string) {
        this.field_1005_e = false;
        this.setLoadingProgress(string);
    }

    public void displayProgressMessage(final String string) {
        this.field_1005_e = true;
        this.setLoadingProgress(this.currentlyDisplayedText);
    }

    public void setLoadingProgress(final String string) {
        if (this.mc.running) {
            this.currentlyDisplayedText = string;
            final ScaledResolution scaledResolution = new ScaledResolution(this.mc.width, this.mc.height);
            final int scaledWidth = scaledResolution.getScaledWidth();
            final int scaledHeight = scaledResolution.getScaledHeight();
            GL11.glClear(256);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, (double) scaledWidth, (double) scaledHeight, 0.0, 100.0, 300.0);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, 0.0f, -200.0f);
            return;
        }
        if (this.field_1005_e) {
            return;
        }
        throw new OpenCraftError();
    }

    public void displayLoadingString(final String string) {
        if (this.mc.running) {
            this.field_1006_d = 0L;
            this.field_1004_a = string;
            this.setLoadingProgress(-1);
            this.field_1006_d = 0L;
            return;
        }
        if (this.field_1005_e) {
            return;
        }
        throw new OpenCraftError();
    }

    public void setLoadingProgress(final int integer) {
        if (!this.mc.running) {
            if (this.field_1005_e) {
                return;
            }
            throw new OpenCraftError();
        } else {
            final long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.field_1006_d < 20L) {
                return;
            }
            this.field_1006_d = currentTimeMillis;
            final ScaledResolution scaledResolution = new ScaledResolution(this.mc.width, this.mc.height);
            final int scaledWidth = scaledResolution.getScaledWidth();
            final int scaledHeight = scaledResolution.getScaledHeight();
            GL11.glClear(256);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, (double) scaledWidth, (double) scaledHeight, 0.0, 100.0, 300.0);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, 0.0f, -200.0f);
            GL11.glClear(16640);
            final Tessellator instance = Tessellator.instance;
            GL11.glBindTexture(3553, this.mc.renderer.getTexture("/assets/dirt.png"));
            final float n = 32.0f;
            instance.beginQuads();
            instance.setColorOpaque_I(4210752);
            instance.vertexUV(0.0, scaledHeight, 0.0, 0.0, scaledHeight / n);
            instance.vertexUV(scaledWidth, scaledHeight, 0.0, scaledWidth / n, scaledHeight / n);
            instance.vertexUV(scaledWidth, 0.0, 0.0, scaledWidth / n, 0.0);
            instance.vertexUV(0.0, 0.0, 0.0, 0.0, 0.0);
            instance.draw();
            if (integer >= 0) {
                final int n2 = 100;
                final int n3 = 2;
                final int n4 = scaledWidth / 2 - n2 / 2;
                final int n5 = scaledHeight / 2 + 16;
                GL11.glDisable(3553);
                instance.beginQuads();
                instance.setColorOpaque_I(8421504);
                instance.vertex(n4, n5, 0.0);
                instance.vertex(n4, n5 + n3, 0.0);
                instance.vertex(n4 + n2, n5 + n3, 0.0);
                instance.vertex(n4 + n2, n5, 0.0);
                instance.setColorOpaque_I(8454016);
                instance.vertex(n4, n5, 0.0);
                instance.vertex(n4, n5 + n3, 0.0);
                instance.vertex(n4 + integer, n5 + n3, 0.0);
                instance.vertex(n4 + integer, n5, 0.0);
                instance.draw();
                GL11.glEnable(3553);
            }
            this.mc.fontRenderer.drawShadow(this.currentlyDisplayedText, (scaledWidth - this.mc.fontRenderer.getStringWidth(this.currentlyDisplayedText)) / 2, scaledHeight / 2 - 4 - 16, 16777215);
            this.mc.fontRenderer.drawShadow(this.field_1004_a, (scaledWidth - this.mc.fontRenderer.getStringWidth(this.field_1004_a)) / 2, scaledHeight / 2 - 4 + 8, 16777215);
            Display.update();
            try {
                Thread.yield();
            } catch (Exception ex) {
            }
        }
    }
}
