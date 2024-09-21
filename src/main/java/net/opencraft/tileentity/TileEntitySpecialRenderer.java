
package net.opencraft.tileentity;

import net.opencraft.client.renderer.entity.Renderer;
import net.opencraft.client.renderer.font.FontRenderer;

public abstract class TileEntitySpecialRenderer<T extends TileEntity> {

    protected TileEntityRenderer tileEntityRenderer;

    public abstract void renderTileEntityMobSpawner(final T bk, final double double2, final double double3, final double double4, final float float5);

    protected void bindTextureByName(final String string) {
        final Renderer renderEngine = this.tileEntityRenderer.renderEngine;
        renderEngine.bindTexture(renderEngine.getTexture(string));
    }

    public void setTileEntityRenderer(final TileEntityRenderer js) {
        this.tileEntityRenderer = js;
    }

    public FontRenderer getFontRenderer() {
        return this.tileEntityRenderer.getFontRenderer();
    }
}
