
package net.opencraft.renderer.gui;

import static net.opencraft.OpenCraft.*;

import net.opencraft.client.config.GameSettings;
import net.opencraft.renderer.gui.options.*;

public class GuiOptions extends GuiScreen {

	private GuiScreen parent;
	protected String screenTitle;
	private GameSettings options;

	public GuiOptions(final GuiScreen dc, final GameSettings ja) {
		this.screenTitle = "Options";
		this.parent = dc;
		this.options = ja;
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		this.buttonList.add(new GuiSlider(0, this.width / 2 - 155, this.height / 6, 10, "FOV", oc.options.fov, 1.0F,
				30.0F, 110.0F));

		this.buttonList
				.add(new GuiSmallButton(1, this.width / 2 - 155, this.height / 6 + 24 + +24, "Video Settings..."));
		this.buttonList.add(new GuiSmallButton(2, this.width / 2 - 155, this.height / 6 + 24 + 48, "Language..."));
		this.buttonList
				.add(new GuiSmallButton(3, this.width / 2 - 155, this.height / 6 + 24 + 72, "Resource packs..."));

		this.buttonList.add(
				new GuiSmallButton(4, this.width / 2 - 155 + 160, this.height / 6 + 24 + 24, "Music and Sounds..."));
		this.buttonList
				.add(new GuiSmallButton(5, this.width / 2 - 155 + 160, this.height / 6 + 24 + 48, "Controls..."));
		this.buttonList
				.add(new GuiSmallButton(6, this.width / 2 - 155 + 160, this.height / 6 + 24 + 72, "Chat settings..."));

		this.buttonList.add(new GuiButton(7, this.width / 2 - 100, this.height / 6 + 168, "Done", 200, 20));

		((GuiButton) this.buttonList.get(2)).enabled = false;
		((GuiButton) this.buttonList.get(3)).enabled = false;
		((GuiButton) this.buttonList.get(6)).enabled = false;
	}

	@Override
	protected void actionPerformed(final GuiButton iq) {
		if (!iq.enabled)
			return;
		if (iq.id == 1)
			oc.displayGuiScreen(new GuiVideoSettings(this, this.options));
		if (iq.id == 4)
			oc.displayGuiScreen(new GuiMusicAndSounds(this, this.options));
		if (iq.id == 5)
			oc.displayGuiScreen(new GuiControls(this, this.options));
		if (iq.id == 7) {
			oc.displayGuiScreen(parent);
			oc.options.saveOptions();
		}
	}

	@Override
	public void drawScreen(final int integer1, final int integer2, final float float3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
		super.drawScreen(integer1, integer2, float3);
	}

}
