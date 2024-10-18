
package net.opencraft.renderer.gui.options;

import static net.opencraft.OpenCraft.*;

import net.opencraft.client.config.GameSettings;
import net.opencraft.renderer.gui.*;

public class GuiMusicAndSounds extends GuiScreen {

	private GuiScreen parentScreen;
	protected String screenTitle;
	private GameSettings options;
	private int buttonId;

	public GuiMusicAndSounds(final GuiScreen dc, final GameSettings ja) {
		this.screenTitle = "Music and Sounds";
		this.buttonId = -1;
		this.parentScreen = dc;
		this.options = ja;
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		for ( int i = 0; i < 2; ++i ) {
			this.buttonList.add(new GuiSmallButton(i, this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), this.options.getKeyBinding(i)));
		}
		this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, "Done", 200, 20));
	}

	@Override
	protected void actionPerformed(final GuiButton iq) {
		if (!iq.enabled) {
			return;
		}
		if (iq.id < 100) {
			this.options.setOptionFloatValue(iq.id, 1);
			iq.text = this.options.getKeyBinding(iq.id);
		}
		if (iq.id == 200) {
			oc.displayGuiScreen(this.parentScreen);
		}
	}

	@Override
	public void drawScreen(final int integer1, final int integer2, final float float3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
		super.drawScreen(integer1, integer2, float3);
	}

}
