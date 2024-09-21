package net.ncraft.client.renderer;

import net.ncraft.Minecraft;
import net.ncraft.util.MathHelper;

public class RenderSkybox {
    private final Minecraft mc;
    private final RenderSkyboxCube renderer;
    private float time;

    public RenderSkybox(RenderSkyboxCube rendererIn) {
        this.renderer = rendererIn;
        this.mc = Minecraft.getMinecraft();
    }

    public void render(float partialTicks) {
        this.time += partialTicks;
        this.renderer.render(this.mc, MathHelper.sin(this.time * 0.001F) * 5.0F + 25.0F, -this.time * 0.1F);
    }
}