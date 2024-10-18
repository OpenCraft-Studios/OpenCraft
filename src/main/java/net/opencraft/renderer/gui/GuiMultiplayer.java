package net.opencraft.renderer.gui;

import static net.opencraft.OpenCraft.*;

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
		this.buttonList.clear();
		this.initButtons();
	}

	protected String getSaveFileName(final int integer) {
		return (World.potentiallySavesFolderLocation(OpenCraft.getGameDir(), new StringBuilder().append("World").append(integer).toString()) != null) ? new StringBuilder().append("World").append(integer).toString() : null;
	}

	public void initButtons() {
		this.buttonList.add(new GuiButton(-6, (this.width / 2) - 50, this.height / 6 + 168, "Done", 100, 20));
	}

	@Override
	protected void actionPerformed(final GuiButton iq) {
		if (!iq.enabled) {
			return;
		}
		if (iq.id == -6) {
			oc.displayGuiScreen(this.parentGuiScreen);
		}
	}

	@Override
	public void drawScreen(final int integer1, final int integer2, final float float3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenHeader, this.width / 2, 20, 0xFFFFFF);
		super.drawScreen(integer1, integer2, float3);
	}

}