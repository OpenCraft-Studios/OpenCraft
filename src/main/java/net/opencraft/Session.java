
package net.opencraft;

import java.util.ArrayList;
import java.util.List;

import net.opencraft.blocks.Block;

public class Session {

    public static List registeredBlocksList;
    public String username;
    public String sessionId;
    public String mpPassParameter;

    static {
        (Session.registeredBlocksList = new ArrayList()).add(Block.stone);
        Session.registeredBlocksList.add(Block.cobblestone);
        Session.registeredBlocksList.add(Block.brick);
        Session.registeredBlocksList.add(Block.dirt);
        Session.registeredBlocksList.add(Block.planks);
        Session.registeredBlocksList.add(Block.wood);
        Session.registeredBlocksList.add(Block.leaves);
        Session.registeredBlocksList.add(Block.torch);
        Session.registeredBlocksList.add(Block.slabSingle);
        Session.registeredBlocksList.add(Block.glass);
        Session.registeredBlocksList.add(Block.mossyCobblestone);
        Session.registeredBlocksList.add(Block.sapling);
        Session.registeredBlocksList.add(Block.plantYellow);
        Session.registeredBlocksList.add(Block.plantRed);
        Session.registeredBlocksList.add(Block.mushroomBrown);
        Session.registeredBlocksList.add(Block.mushroomRed);
        Session.registeredBlocksList.add(Block.sand);
        Session.registeredBlocksList.add(Block.gravel);
        Session.registeredBlocksList.add(Block.sponge);
        Session.registeredBlocksList.add(Block.woolGray);
        Session.registeredBlocksList.add(Block.oreCoal);
        Session.registeredBlocksList.add(Block.oreIron);
        Session.registeredBlocksList.add(Block.oreGold);
        Session.registeredBlocksList.add(Block.blockSteel);
        Session.registeredBlocksList.add(Block.blockGold);
        Session.registeredBlocksList.add(Block.bookshelf);
        Session.registeredBlocksList.add(Block.tnt);
        Session.registeredBlocksList.add(Block.obsidian);
        System.out.println(Session.registeredBlocksList.size());
    }

    public Session(final String string1, final String string2) {
        this.username = string1;
        this.sessionId = string2;
    }

}
