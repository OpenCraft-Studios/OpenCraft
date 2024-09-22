package net.opencraft.renderer;

import static net.opencraft.OpenCraft.*;
import static org.joml.Math.*;

public class RenderSkybox {
    private final RenderSkyboxCube renderer;
    private float time;

    public RenderSkybox(RenderSkyboxCube rendererIn) {
        this.renderer = rendererIn;
    }

    public void render(float partialTicks) {
        this.time += partialTicks;
        this.renderer.render(oc, sin(time * 0.001F) * 5F + 25F, time * -0.1F);
    }
}