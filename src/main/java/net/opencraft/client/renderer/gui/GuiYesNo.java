
package net.opencraft.client.renderer.gui;

public class GuiYesNo extends GuiScreen {

    private GuiScreen parentScreen;
    private String message1;
    private String message2;
    private int worldNumber;

    public GuiYesNo(GuiScreen dc, String string2, String string3, int integer) {
        this.parentScreen = dc;
        this.message1 = string2;
        this.message2 = string3;
        this.worldNumber = integer;
    }

    @Override
    public void initGui() {
        this.controlList.clear();
        this.controlList.add(new GuiSmallButton(0, this.width / 2 - 155 + 0, this.height / 6 + 96, "Yes"));
        this.controlList.add(new GuiSmallButton(1, this.width / 2 - 155 + 160, this.height / 6 + 96, "No"));
    }

    @Override
    protected void actionPerformed(final GuiButton iq) {
        this.parentScreen.deleteWorld(iq.buttonId == 0, this.worldNumber);
    }

    @Override
    public void drawScreen(final int integer1, final int integer2, final float float3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.message1, this.width / 2, 70, 16777215);
        this.drawCenteredString(this.fontRenderer, this.message2, this.width / 2, 90, 16777215);
        super.drawScreen(integer1, integer2, float3);
    }
}
