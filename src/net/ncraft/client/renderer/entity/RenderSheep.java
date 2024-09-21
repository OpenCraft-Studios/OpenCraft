
package net.ncraft.client.renderer.entity;

import net.ncraft.client.entity.model.ModelBase;

public class RenderSheep extends RenderLiving {

    public RenderSheep(final ModelBase it1, final ModelBase it2, final float float3) {
        super(it1, float3);
        this.setRenderPassModel(it2);
    }
}
