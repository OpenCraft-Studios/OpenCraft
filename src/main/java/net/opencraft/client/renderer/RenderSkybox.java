package net.opencraft.client.renderer;

import net.opencraft.OpenCraft;
import net.opencraft.util.MathHelper;

public class RenderSkybox {
    private final OpenCraft mc;
    private final RenderSkyboxCube renderer;
    private float time;

    public RenderSkybox(RenderSkyboxCube rendererIn) {
        this.renderer = rendererIn;
        this.mc = OpenCraft.getOpenCraft();
    }

    public void render(float partialTicks) {
        this.time += partialTicks;
        this.renderer.render(this.mc, MathHelper.sin(this.time * 0.001F) * 5.0F + 25.0F, -this.time * 0.1F);
    }
}