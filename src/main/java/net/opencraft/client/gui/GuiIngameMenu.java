
package net.opencraft.client.gui;

import net.opencraft.util.MathHelper;

public class GuiIngameMenu extends GuiScreen {

    private int updateCounter2;
    private int updateCounter1;

    public GuiIngameMenu() {
        this.updateCounter2 = 0;
        this.updateCounter1 = 0;
    }

    @Override
    public void initGui() {
        this.updateCounter2 = 0;
        this.controlList.clear();
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 48, "Save and quit to title..", 200, 20));
        this.controlList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24, "Back to game", 200, 20));
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96, "Options...", 200, 20));
    }

    @Override
    protected void actionPerformed(final GuiButton iq) {
        if (iq.buttonId == 0) {
            this.id.displayGuiScreen(new GuiOptions(this, this.id.gameSettings));
        }
        if (iq.buttonId == 1) {
            this.id.changeWorld1(null);
            this.id.displayGuiScreen(new GuiMainMenu());
        }
        if (iq.buttonId == 4) {
            this.id.displayGuiScreen(null);
            this.id.setIngameFocus();
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.updateCounter1;
    }

    @Override
    public void drawScreen(final int integer1, final int integer2, final float float3) {
        this.drawDefaultBackground();
        if (!this.id.theWorld.quickSaveWorld(this.updateCounter2++) || this.updateCounter1 < 20) {
            float n = (this.updateCounter1 % 10 + float3) / 10.0f;
            n = MathHelper.sin(n * 3.1415927f * 2.0f) * 0.2f + 0.8f;
            final int n2 = (int) (255.0f * n);
            this.drawString(this.fontRenderer, "Saving level..", 8, this.height - 16, n2 << 16 | n2 << 8 | n2);
        }
        this.drawCenteredString(this.fontRenderer, "Game menu", this.width / 2, 40, 16777215);
        super.drawScreen(integer1, integer2, float3);
    }
}
