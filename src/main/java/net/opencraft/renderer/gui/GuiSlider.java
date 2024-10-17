package net.opencraft.renderer.gui;

import static net.opencraft.OpenCraft.*;
import static org.joml.Math.*;

import org.lwjgl.opengl.GL11;

import net.opencraft.renderer.font.FontRenderer;
import net.opencraft.util.Mth;

public class GuiSlider extends GuiButton {

	public float sliderValue;
	public boolean dragging = false;
	private final int keyId;

	private final float minValue;
	private final float maxValue;
	private final float decimalPlace;
	private final String displayStringName;

	public GuiSlider(int id, int xPosition, int yPosition, int keyId, String displayString, float sliderValue,
			float decimalPlace, float minValue, float maxValue) {
		super(id, xPosition, yPosition, displayString, 150, 20);
		this.keyId = keyId;
		this.sliderValue = sliderValue;
		displayStringName = displayString;

		this.decimalPlace = decimalPlace;
		this.minValue = minValue;
		this.maxValue = maxValue;

		if (this.decimalPlace != 0.0f)
			this.sliderValue = Mth.roundToDecimalPlace(this.sliderValue, this.decimalPlace);

		text = displayStringName + ": " + this.sliderValue;
	}

	@Override
	public boolean mousePressed(final int mouseX, final int mouseY) {
		if (super.mousePressed(mouseX, mouseY))
			if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
				dragging = true;
				// Calculate the position of the slider relative to the mouse position
				float sliderPos = (float) (mouseX - x - 4) / (float) (width - 8);
				sliderPos = clamp(0.0f, 1.0f, sliderPos);
				// Update the slider value within the specified range
				sliderValue = minValue + sliderPos * (maxValue - minValue);
				if (decimalPlace != 0.0f)
					sliderValue = Mth.roundToDecimalPlace(sliderValue, decimalPlace);

				oc.options.setOptionFloatValue(keyId, sliderValue);
				text = displayStringName + ": " + sliderValue;
				return true;
			}
		return false;
	}

	protected void mouseDragged(int mouseX, int mouseY) {
		if (!dragging)
			return;

		// Calculate the position of the slider relative to the mouse position
		float sliderPos = (float) (mouseX - x - 4) / (float) (width - 8);
		sliderPos = clamp(0.0f, 1.0f, sliderPos);

		// Update the slider value within the specified range
		sliderValue = minValue + sliderPos * (maxValue - minValue);
		if (decimalPlace != 0.0f)
			sliderValue = Mth.roundToDecimalPlace(sliderValue, decimalPlace);

		oc.options.setOptionFloatValue(keyId, sliderValue);
		text = displayStringName + ": " + sliderValue;
	}

	@Override
	public void mouseReleased(int var1, int var2) {
		dragging = false;
	}

	@Override
	public void drawButton(int mouseX, int mouseY) {
		if (!enabled2)
			return;
		final FontRenderer font = oc.font;
		GL11.glBindTexture(3553, oc.renderer.loadTexture("/assets/gui/gui.png"));

		int colorChange = 1;
		final boolean isHovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;

		if (isHovered)
			colorChange = 2;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.drawTexturedModalRect(x, y, 0, 46, width / 2, height);
		this.drawTexturedModalRect(x + width / 2, y, 200 - width / 2, 46, width / 2, height);

		// Calculate slider position based on sliderValue within the range of minValue
		// and maxValue
		final float sliderPos = (sliderValue - minValue) / (maxValue - minValue);
		final int sliderX = (int) (x + sliderPos * (width - 8));

		this.drawTexturedModalRect(sliderX, y, 0, 46 + colorChange * 20, 4, 20);
		this.drawTexturedModalRect(sliderX + 4, y, 196, 46 + colorChange * 20, 4, 20);

		if (isHovered)
			this.drawCenteredString(font, text, x + width / 2, y + (height - 8) / 2, 16777120);
		else
			this.drawCenteredString(font, text, x + width / 2, y + (height - 8) / 2, 14737632);

		if (dragging)
			this.mouseDragged(mouseX, mouseY);
	}

}
