
package net.opencraft.renderer.gui;

import static net.opencraft.OpenCraft.*;

import java.io.File;

import net.opencraft.OpenCraft;
import net.opencraft.world.World;

public class GuiDeleteWorld extends GuiCreateWorld {

	public GuiDeleteWorld(GuiScreen dc2) {
		super(dc2);
		this.screenHeader = "Delete world";
	}

	public void initButtons() {
		this.buttonList.add(new GuiButton(-6, this.width / 2 - 100, this.height / 6 + 168, "Cancel", 200, 20));
	}

	public void actionPerformed(int n) {
		String string = this.getSaveFileName(n);
		if (string != null) {
			oc.displayGuiScreen(new GuiYesNo(this, "Are you sure you want to delete this world?", "'" + string + "' will be lost forever!", n));
		}
	}

	public void deleteWorld(boolean bl, int n) {
		if (bl) {
			File file = OpenCraft.getGameDir();
			World.deleteWorldDir(file, this.getSaveFileName(n));
		}
		oc.displayGuiScreen(this.parentGuiScreen);
	}

}
