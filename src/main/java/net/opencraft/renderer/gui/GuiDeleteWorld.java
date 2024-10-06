
package net.opencraft.renderer.gui;

import java.io.File;
import net.opencraft.OpenCraft;
import net.opencraft.world.World;

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
            File file = OpenCraft.getGameDir();
            World.deleteWorldDirectory(file, this.getSaveFileName(n));
        }
        this.id.displayGuiScreen(this.parentGuiScreen);
    }
}
