
package net.ncraft.client.gui;

import java.io.File;
import net.ncraft.Minecraft;
import net.ncraft.world.World;

public class GuiDeleteWorld extends GuiCreateWorld {

    public GuiDeleteWorld(GuiScreen dc2) {
        super(dc2);
        this.screenHeader = "Delete world";
    }

    public void initButtons() {
        this.controlList.add(new GuiButton(-6, this.width / 2 - 100, this.height / 6 + 168, "Cancel", 200, 20));
    }

    public void actionPerformed(int n) {
        String string = this.getSaveFileName(n);
        if (string != null) {
            this.id.displayGuiScreen(new GuiYesNo(this, "Are you sure you want to delete this world?", "'" + string + "' will be lost forever!", n));
        }
    }

    public void deleteWorld(boolean bl, int n) {
        if (bl) {
            File file = Minecraft.getMinecraftDir();
            World.deleteWorldDirectory(file, this.getSaveFileName(n));
        }
        this.id.displayGuiScreen(this.parentGuiScreen);
    }
}
