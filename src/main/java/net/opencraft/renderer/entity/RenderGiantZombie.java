
package net.opencraft.renderer.entity;

import net.opencraft.client.entity.models.ModelBase;
import net.opencraft.entity.EntityGiant;
import org.lwjgl.opengl.GL11;

public class RenderGiantZombie extends RenderLiving {

    private float scale;

    public RenderGiantZombie(final ModelBase it, final float float2, final float float3) {
        super(it, float2 * float3);
        this.scale = float3;
    }

    protected void preRenderCallback(final EntityGiant entityLiving, final float nya1) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }
}
