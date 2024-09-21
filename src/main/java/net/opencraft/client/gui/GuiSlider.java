package net.opencraft.client.gui;


import net.opencraft.OpenCraft;
import net.opencraft.client.font.FontRenderer;
import net.opencraft.util.Mth;
import org.lwjgl.opengl.GL11;

public class GuiSlider extends GuiButton {
    public float sliderValue;
    public boolean dragging = false;
    private int keyId;
    private OpenCraft mc;

    private final float minValue;
    private final float maxValue;
    private final float decimalPlace;
    private final String displayStringName;

    public GuiSlider(int id, int xPosition, int yPosition, int keyId, String displayString, float sliderValue, float decimalPlace, float minValue, float maxValue) {
        super(id, xPosition, yPosition, 150, 20, displayString);
        this.keyId = keyId;
        this.sliderValue = sliderValue;
        this.displayStringName = displayString;

        this.decimalPlace = decimalPlace;
        this.minValue = minValue;
        this.maxValue = maxValue;

        if (this.decimalPlace != 0.0f) {
            this.sliderValue = Mth.roundToDecimalPlace(this.sliderValue, this.decimalPlace);
        }

        this.displayString = this.displayStringName + ": " + this.sliderValue;
    }

    public boolean mousePressed(final int mouseX, final int mouseY) {
        if (super.mousePressed(mouseX, mouseY)) {
            if (mouseX >= this.xPosition && mouseX <= this.xPosition + this.width && mouseY >= this.yPosition && mouseY <= this.yPosition + this.height) {
                this.dragging = true;
                // Calculate the position of the slider relative to the mouse position
                float sliderPos = (float) (mouseX - this.xPosition - 4) / (float) (this.width - 8);
                sliderPos = Mth.clamp(sliderPos, 0.0f, 1.0f);
                // Update the slider value within the specified range
                this.sliderValue = minValue + sliderPos * (maxValue - minValue);
                if (this.decimalPlace != 0.0f) {
                    this.sliderValue = Mth.roundToDecimalPlace(this.sliderValue, this.decimalPlace);
                }
                this.mc.gameSettings.setOptionFloatValue(this.keyId, this.sliderValue);
                this.displayString = this.displayStringName + ": " + this.sliderValue;
                return true;
            }
        }
        return false;
    }

    protected void mouseDragged(OpenCraft mc, int mouseX, int mouseY) {
        if (this.dragging) {
            // Calculate the position of the slider relative to the mouse position
            float sliderPos = (float) (mouseX - this.xPosition - 4) / (float) (this.width - 8);
            sliderPos = Mth.clamp(sliderPos, 0.0f, 1.0f);
            // Update the slider value within the specified range
            this.sliderValue = minValue + sliderPos * (maxValue - minValue);
            if (this.decimalPlace != 0.0f) {
                this.sliderValue = Mth.roundToDecimalPlace(this.sliderValue, this.decimalPlace);
            }
            this.mc.gameSettings.setOptionFloatValue(this.keyId, this.sliderValue);
            this.displayString = this.displayStringName + ": " + this.sliderValue;
        }
    }


    public void mouseReleased(int var1, int var2) {
        this.dragging = false;
    }

    @Override
    public void drawButton(OpenCraft mc, int mouseX, int mouseY) {
        this.mc = mc;
        if (!this.enabled2) {
            return;
        }
        final FontRenderer fontRenderer = mc.fontRenderer;
        GL11.glBindTexture(3553, mc.renderEngine.getTexture("/assets/gui/gui.png"));

        int colorChange = 1;
        final boolean isHovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

        if (isHovered) {
            colorChange = 2;
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46, this.width / 2, this.height);
        this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46, this.width / 2, this.height);

        // Calculate slider position based on sliderValue within the range of minValue and maxValue
        final float sliderPos = (this.sliderValue - this.minValue) / (this.maxValue - this.minValue);
        final int sliderX = (int) (this.xPosition + (sliderPos * (this.width - 8)));

        this.drawTexturedModalRect(sliderX, this.yPosition, 0, 46 + colorChange * 20, 4, 20);
        this.drawTexturedModalRect(sliderX + 4, this.yPosition, 196, 46 + colorChange * 20, 4, 20);

        if (isHovered) {
            this.drawCenteredString(fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 16777120);
        } else {
            this.drawCenteredString(fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 14737632);
        }

        if (this.dragging) {
            this.mouseDragged(mc, mouseX, mouseY);
        }
    }

}
