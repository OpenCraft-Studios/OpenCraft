
package net.opencraft.client.gui;

import net.opencraft.OpenCraft;
import net.opencraft.client.renderer.Tessellator;
import net.opencraft.nbt.NBTTagCompound;
import net.opencraft.world.World;

import java.io.File;

public class GuiCreateWorld extends GuiScreen {

    protected GuiScreen parentGuiScreen;
    protected String screenHeader;
    private boolean createClicked;

    private float updateCounter;

    public GuiCreateWorld(final GuiScreen dc) {
        this.updateCounter = 0.0f;
        this.screenHeader = "Select world";
        this.createClicked = false;
        this.parentGuiScreen = dc;
    }

    @Override
    public void updateScreen() {
        this.updateCounter += 0.01f;
    }

    @Override
    public void initGui() {
        this.controlList.clear();
        final File minecraftDir = OpenCraft.getMinecraftDir();
        final String[] worldNames = World.getSaveNames(minecraftDir);
        final int worldCount = worldNames.length;

        for (int i = 0; i < worldCount; ++i) {
            final NBTTagCompound potentiallySavesFolderLocation = World.potentiallySavesFolderLocation(minecraftDir, worldNames[i]);

            if (potentiallySavesFolderLocation == null) {
                continue;
            }
            String stringrererer = (worldNames[i] + " (" + potentiallySavesFolderLocation.getLong("SizeOnDisk") / 1024L * 100L / 1024L / 100.0f + " MB)");
            int numberFromWorldName = Integer.parseInt(worldNames[i].substring(5));
            this.controlList.add(new GuiButton(numberFromWorldName, this.width / 2 - 100, this.height / 6 + 24 * i, stringrererer, 200, 20));
        }
        this.initButtons();
    }

    protected String getSaveFileName(final int integer) {
        return (World.potentiallySavesFolderLocation(OpenCraft.getMinecraftDir(), new StringBuilder().append("World").append(integer).toString()) != null) ? new StringBuilder().append("World").append(integer).toString() : null;
    }

    public void initButtons() {
        this.controlList.add(new GuiButton(-5, this.width / 2 - 100, this.height / 6 + 120 + 12, "Delete world...", 200, 20));
        this.controlList.add(new GuiButton(-20, (this.width / 2 - 100) + 120, this.height / 6 + 168, "Create world", 100, 20));
        this.controlList.add(new GuiButton(-6, (this.width / 2) - 120, this.height / 6 + 168, "Cancel", 100, 20));
    }

    @Override
    protected void actionPerformed(final GuiButton iq) {
        if (!iq.enabled) {
            return;
        }
        if (iq.buttonId == -20) {
            final String[] worldNames = World.getSaveNames(OpenCraft.getMinecraftDir());
            int maximumWorldNumber = 0;
            for (String worldName : worldNames) {
                final int numberFromWorldName = Integer.parseInt(worldName.substring(5));
                if (numberFromWorldName > maximumWorldNumber) {
                    maximumWorldNumber = numberFromWorldName;
                }
            }
            this.actionPerformed(maximumWorldNumber + 1);
        } else if (iq.buttonId == -5) {
            this.id.displayGuiScreen(new GuiDeleteWorld(this));
        } else if (iq.buttonId == -6) {
            this.id.displayGuiScreen(this.parentGuiScreen);
        }

        if (iq.buttonId >= 0) {
            this.actionPerformed(iq.buttonId);
        }
    }

    public void actionPerformed(final int integer) {
        this.id.displayGuiScreen(null);
        if (this.createClicked) {
            return;
        }
        this.createClicked = true;
        this.id.startWorld(new StringBuilder().append("World").append(integer).toString());
        this.id.displayGuiScreen(null);
    }

    @Override
    public void drawScreen(final int integer1, final int integer2, final float float3) {
        this.drawDefaultBackground();
        final Tessellator instance = Tessellator.instance;
        this.drawCenteredString(this.fontRenderer, this.screenHeader, this.width / 2, 20, 16777215);
        instance.setColorOpaque_I(16777215);
        super.drawScreen(integer1, integer2, float3);
    }
}
