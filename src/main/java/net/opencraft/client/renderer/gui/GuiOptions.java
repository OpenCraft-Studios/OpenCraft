
package net.opencraft.client.renderer.gui;

import net.opencraft.client.config.GameSettings;
import net.opencraft.client.renderer.gui.options.*;

public class GuiOptions extends GuiScreen {

    private GuiScreen parentScreen;
    protected String screenTitle;
    private GameSettings options;

    public GuiOptions(final GuiScreen dc, final GameSettings ja) {
        this.screenTitle = "Options";
        this.parentScreen = dc;
        this.options = ja;
    }

    @Override
    public void initGui() {
        this.controlList.clear();
        this.controlList.add(new GuiSlider(0, this.width / 2 - 155, this.height / 6, 10, "FOV",  this.id.options.fov, 1.0F, 30.0F, 110.0F));

        this.controlList.add(new GuiSmallButton(1, this.width / 2 - 155, this.height / 6 + 24 + + 24, "Video Settings..."));
        this.controlList.add(new GuiSmallButton(2, this.width / 2 - 155, this.height / 6 + 24 + 48, "Language..."));
        this.controlList.add(new GuiSmallButton(3, this.width / 2 - 155, this.height / 6 + 24 + 72, "Resource packs..."));

        this.controlList.add(new GuiSmallButton(4, this.width / 2 - 155 + 160, this.height / 6 + 24 + 24, "Music and Sounds..."));
        this.controlList.add(new GuiSmallButton(5, this.width / 2 - 155 + 160, this.height / 6 + 24 + 48, "Controls..."));
        this.controlList.add(new GuiSmallButton(6, this.width / 2 - 155 + 160, this.height / 6 + 24 + 72, "Chat settings..."));

        this.controlList.add(new GuiButton(7, this.width / 2 - 100, this.height / 6 + 168, "Done", 200, 20));

        ((GuiButton) this.controlList.get(2)).enabled = false;
        ((GuiButton) this.controlList.get(3)).enabled = false;
        ((GuiButton) this.controlList.get(6)).enabled = false;
    }

    @Override
    protected void actionPerformed(final GuiButton iq) {
        if (!iq.enabled) {
            return;
        }
        if (iq.buttonId == 1) {
            this.id.displayGuiScreen(new GuiVideoSettings(this, this.options));
        }
        if (iq.buttonId == 4) {
            this.id.displayGuiScreen(new GuiMusicAndSounds(this, this.options));
        }
        if (iq.buttonId == 5) {
            this.id.displayGuiScreen(new GuiControls(this, this.options));
        }
        if (iq.buttonId == 7) {
            this.id.displayGuiScreen(this.parentScreen);
        }
    }

    @Override
    public void drawScreen(final int integer1, final int integer2, final float float3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
        super.drawScreen(integer1, integer2, float3);
    }
}
