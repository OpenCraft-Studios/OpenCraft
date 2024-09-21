package net.opencraft.renderer.gui;


import net.opencraft.OpenCraft;
import net.opencraft.world.World;

public class GuiMultiplayer extends GuiScreen {

    protected GuiScreen parentGuiScreen;
    protected String screenHeader;

    public GuiMultiplayer(final GuiScreen dc) {
        this.screenHeader = "Multiplayer";
        this.parentGuiScreen = dc;
    }

    @Override
    public void initGui() {
        this.controlList.clear();
        this.initButtons();
    }

    protected String getSaveFileName(final int integer) {
        return (World.potentiallySavesFolderLocation(OpenCraft.getMinecraftDir(), new StringBuilder().append("World").append(integer).toString()) != null) ? new StringBuilder().append("World").append(integer).toString() : null;
    }

    public void initButtons() {
        this.controlList.add(new GuiButton(-6, (this.width / 2) - 50, this.height / 6 + 168, "Cancel", 100, 20));
    }
    @Override
    protected void actionPerformed(final GuiButton iq) {
        if (!iq.enabled) {
            return;
        }
        if (iq.buttonId == -6) {
            this.id.displayGuiScreen(this.parentGuiScreen);
        }
    }


    @Override
    public void drawScreen(final int integer1, final int integer2, final float float3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenHeader, this.width / 2, 20, 16777215);
        super.drawScreen(integer1, integer2, float3);
    }
}