
package net.opencraft.renderer.entity;

import net.opencraft.client.entity.models.ModelSpider;
import net.opencraft.entity.EntitySpider;
import org.lwjgl.opengl.GL11;

public class RenderSpider extends RenderLiving {

    public RenderSpider() {
        super(new ModelSpider(), 1.0f);
        this.setRenderPassModel(new ModelSpider());
    }

    protected float getMaxDeathRotation(final EntitySpider eVar) {
        return 180.0f;
    }

    protected boolean shouldRenderPass(final EntitySpider eVar, final int i) {
        if (i != 0) {
            return false;
        }
        if (i != 0) {
            return false;
        }
        this.loadTexture("/assets/mob/spider_eyes.png");
        final float n = (1.0f - eVar.getEntityBrightness(1.0f)) * 0.5f;
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, n);
        return true;
    }
}
