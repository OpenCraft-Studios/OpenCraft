
package net.opencraft.client.renderer.entity;

import net.opencraft.client.entity.models.ModelBase;

public class RenderSheep extends RenderLiving {

    public RenderSheep(final ModelBase it1, final ModelBase it2, final float float3) {
        super(it1, float3);
        this.setRenderPassModel(it2);
    }
}
