package net.opencraft.client.renderer;

import static org.joml.Math.*;

import net.opencraft.OpenCraft;

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
        this.renderer.render(this.mc, sin(time * 0.001F) * 5F + 25F, time * -0.1F);
    }
}