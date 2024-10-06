
package net.opencraft.renderer.gui.options;

import net.opencraft.client.config.GameSettings;
import net.opencraft.renderer.gui.*;

public class GuiControls extends GuiScreen {

    private GuiScreen parentScreen;
    protected String screenTitle;
    private GameSettings options;
    private int buttonId;

    public GuiControls(final GuiScreen dc, final GameSettings ja) {
        this.screenTitle = "Controls";
        this.buttonId = -1;
        this.parentScreen = dc;
        this.options = ja;
    }

    @Override
    public void initGui() {
        this.controlList.clear();
        for (int i = 0; i < GameSettings.PlayerInput.values().length; i++) {
            this.controlList.add(new GuiSmallButton(i, this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), this.options.getOptionDisplayString(i)));
        }
        this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, "Done", 200, 20));
    }

    @Override
    protected void actionPerformed(final GuiButton iq) {
        for (int i = 0; i < GameSettings.PlayerInput.values().length; i++) {
            ((GuiButton) this.controlList.get(i)).displayString = this.options.getOptionDisplayString(i);
        }
        if (iq.buttonId == 200) {
            this.id.displayGuiScreen(this.parentScreen);
        } else {
            this.buttonId = iq.buttonId;
            iq.displayString = "> " + this.options.getOptionDisplayString(iq.buttonId) + " <";
        }
    }

    @Override
    protected void keyTyped(final char character, final int integer) {
        if (this.buttonId >= 0) {
            this.options.setKeyBinding(this.buttonId, integer);
            ((GuiButton) this.controlList.get(this.buttonId)).displayString = this.options.getOptionDisplayString(this.buttonId);
            this.buttonId = -1;
        } else {
            super.keyTyped(character, integer);
        }
    }

    @Override
    public void drawScreen(final int integer1, final int integer2, final float float3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
        super.drawScreen(integer1, integer2, float3);
    }
}
