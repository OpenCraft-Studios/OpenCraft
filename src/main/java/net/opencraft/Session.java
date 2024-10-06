
package net.opencraft;

import java.util.ArrayList;
import java.util.List;

import net.opencraft.blocks.Block;

public class Session {

    public static List<Block> registeredBlocksList;
    public String username;
    public String sessionId;

    static {
        registeredBlocksList = new ArrayList<>();
        registeredBlocksList.add(Block.stone);
        registeredBlocksList.add(Block.cobblestone);
        registeredBlocksList.add(Block.brick);
        registeredBlocksList.add(Block.dirt);
        registeredBlocksList.add(Block.planks);
        registeredBlocksList.add(Block.wood);
        registeredBlocksList.add(Block.leaves);
        registeredBlocksList.add(Block.torch);
        registeredBlocksList.add(Block.slabSingle);
        registeredBlocksList.add(Block.glass);
        registeredBlocksList.add(Block.mossyCobblestone);
        registeredBlocksList.add(Block.sapling);
        registeredBlocksList.add(Block.plantYellow);
        registeredBlocksList.add(Block.plantRed);
        registeredBlocksList.add(Block.mushroomBrown);
        registeredBlocksList.add(Block.mushroomRed);
        registeredBlocksList.add(Block.sand);
        registeredBlocksList.add(Block.gravel);
        registeredBlocksList.add(Block.sponge);
        registeredBlocksList.add(Block.woolGray);
        registeredBlocksList.add(Block.oreCoal);
        registeredBlocksList.add(Block.oreIron);
        registeredBlocksList.add(Block.oreGold);
        registeredBlocksList.add(Block.blockSteel);
        registeredBlocksList.add(Block.blockGold);
        registeredBlocksList.add(Block.bookshelf);
        registeredBlocksList.add(Block.tnt);
        registeredBlocksList.add(Block.obsidian);
    }

    public Session(final String username, final String sessionID) {
        this.username = username;
        this.sessionId = sessionID;
    }

}
