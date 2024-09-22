
package net.opencraft.renderer.entity;

import net.opencraft.client.entity.models.ModelCreeper;
import net.opencraft.entity.EntityCreeper;
import net.opencraft.util.Mth;

import static org.joml.Math.*;

import org.lwjgl.opengl.GL11;

public class RenderCreeper extends RenderLiving {

    public RenderCreeper() {
        super(new ModelCreeper(), 0.5f);
    }

    protected void preRenderCallback(final EntityCreeper entityLiving, final float nya1) {
        float creeperFlashTime = entityLiving.setCreeperFlashTime(nya1);
        final float n = 1.0f + sin(creeperFlashTime * 100.0f) * creeperFlashTime * 0.01f;
        creeperFlashTime = clamp(0, 1, creeperFlashTime);
        
        creeperFlashTime *= creeperFlashTime;
        creeperFlashTime *= creeperFlashTime;
        final float n2 = (1.0f + creeperFlashTime * 0.4f) * n;
        GL11.glScalef(n2, (1.0f + creeperFlashTime * 0.1f) / n, n2);
    }

    protected int getColorMultiplier(final EntityCreeper entityLiving, final float nya1, final float nya2) {
        final float setCreeperFlashTime = entityLiving.setCreeperFlashTime(nya2);
        if ((int) (setCreeperFlashTime * 10.0f) % 2 == 0) {
            return 0;
        }
        int n = (int) (setCreeperFlashTime * 0.2f * 255.0f);
        if (n < 0) {
            n = 0;
        }
        if (n > 255) {
            n = 255;
        }
        return n << 24 | 255 << 16 | 255 << 8 | 0xFF;
    }
}
