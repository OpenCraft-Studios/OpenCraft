
package net.opencraft.renderer.gui.options;

import static net.opencraft.OpenCraft.*;
import static org.lwjgl.glfw.GLFW.*;

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
		this.buttonList.clear();
		for (int i = 0; i < GameSettings.PlayerInput.values().length; i++)
			this.buttonList.add(new GuiSmallButton(i, this.width / 2 - 155 + i % 2 * 160,
					this.height / 6 + 24 * (i >> 1), this.options.getOptionDisplayString(i)));
		this.buttonList.add(new GuiButton(201, (width - 180) / 2, 175,
				"Raw Mouse Motion: ".concat(oc.options.rawMouseInput.either("ON", "OFF")), 180, 20));
		this.buttonList.add(new GuiButton(200, (width - 200) / 2, this.height / 6 + 168, "Done", 200, 20));
	}

	@Override
	protected void actionPerformed(final GuiButton iq) {

		if (iq.id == 200) {
			oc.displayGuiScreen(this.parentScreen);
			return;
		} else if (iq.id == 201) {
			oc.options.rawMouseInput.toggle();
			iq.text = "Raw Mouse Motion: ".concat(oc.options.rawMouseInput.either("ON", "OFF"));
			if (glfwRawMouseMotionSupported())
				glfwSetInputMode(oc.window, GLFW_RAW_MOUSE_MOTION,
						oc.options.rawMouseInput.either(GLFW_TRUE, GLFW_FALSE));
			return;
		} else {
			this.buttonId = iq.id;
			iq.text = "> " + this.options.getOptionDisplayString(iq.id) + " <";
		}

		for (int i = 0; i < GameSettings.PlayerInput.values().length; i++)
			((GuiButton) this.buttonList.get(i)).text = this.options.getOptionDisplayString(i);
	}

	@Override
	protected void keyTyped(final char character, final int integer) {
		if (this.buttonId >= 0) {
			this.options.setKeyBinding(this.buttonId, integer);
			((GuiButton) this.buttonList.get(this.buttonId)).text = this.options
					.getOptionDisplayString(this.buttonId);
			this.buttonId = -1;
		} else
			super.keyTyped(character, integer);
	}

	@Override
	public void drawScreen(final int integer1, final int integer2, final float float3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
		super.drawScreen(integer1, integer2, float3);
	}

}
