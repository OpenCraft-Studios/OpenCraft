
package net.opencraft.renderer.gui.options;

import net.opencraft.client.config.GameSettings;
import net.opencraft.renderer.gui.*;

public class GuiVideoSettings extends GuiScreen {

	private GuiScreen parentScreen;
	protected String screenTitle;
	private GameSettings options;
	private int buttonId;

	public GuiVideoSettings(final GuiScreen dc, final GameSettings ja) {
		this.screenTitle = "Video Settings";
		this.buttonId = -1;
		this.parentScreen = dc;
		this.options = ja;
	}

	@Override
	public void initGui() {
		this.controlList.clear();
		for ( int i = 2; i < 8; ++i ) {
			this.controlList.add(new GuiSmallButton(i, this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), this.options.getKeyBinding(i)));
		}
		this.controlList.add(new GuiSmallButton(9, this.width / 2 - 155 + 9 % 2 * 160, this.height / 6 + 24 * (9 >> 1), this.options.getKeyBinding(9)));
		this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, "Done", 200, 20));
		this.controlList.add(new GuiSlider(300, this.width / 2 - 155 + 8 % 2 * 160, this.height / 6 + 24 * (8 >> 1), 11, "Brightness", this.options.minimumBrightness, 100.0F, 0.0F, 0.3F));

	}

	@Override
	protected void actionPerformed(final GuiButton iq) {
		if (!iq.enabled) {
			return;
		}
		if (iq.buttonId < 100) {
			this.options.setOptionFloatValue(iq.buttonId, 1);
			iq.displayString = this.options.getKeyBinding(iq.buttonId);
		}
		if (iq.buttonId == 200) {
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
