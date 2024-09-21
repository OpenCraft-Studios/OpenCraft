
package net.opencraft.client.gui;

import net.opencraft.client.renderer.Tessellator;
import net.opencraft.client.renderer.RenderSkybox;
import net.opencraft.client.renderer.RenderSkyboxCube;
import net.opencraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class GuiMainMenu extends GuiScreen {

    private float updateCounter;
    private String[] splashes;
    private String currentSplash;
    private final RenderSkybox panorama = new RenderSkybox(new RenderSkyboxCube("textures/gui/title/background/panorama"));

    public GuiMainMenu() {
        this.updateCounter = 0.0f;
        this.splashes = new String[]{"Pre-beta!", "As seen on TV!", "Awesome!", "100% pure!", "May contain nuts!", "Better than Prey!", "More polygons!", "Sexy!", "Limited edition!", "Flashing letters!", "Made by Notch!", "Coming soon!", "Best in class!", "When it's finished!", "Absolutely dragon free!", "Excitement!", "More than 5000 sold!", "One of a kind!", "700+ hits on YouTube!", "Indev!", "Spiders everywhere!", "Check it out!", "Holy cow, man!", "It's a game!", "Made in Sweden!", "Uses LWJGL!", "Reticulating splines!", "OpenCraft!", "Yaaay!", "Alpha version!", "Singleplayer!", "Keyboard compatible!", "Undocumented!", "Ingots!", "Exploding creepers!", "That's not a moon!", "l33t!", "Create!", "Survive!", "Dungeon!", "Exclusive!", "The bee's knees!", "Down with O.P.P.!", "Closed source!", "Classy!", "Wow!", "Not on steam!", "9.95 euro!", "Half price!", "Oh man!", "Check it out!", "Awesome community!", "Pixels!", "Teetsuuuuoooo!", "Kaaneeeedaaaa!", "Now with difficulty!", "Enhanced!", "90% bug free!", "Pretty!", "12 herbs and spices!", "Fat free!", "Absolutely no memes!", "Free dental!", "Ask your doctor!", "Minors welcome!", "Cloud computing!", "Legal in Finland!", "Hard to label!", "Technically good!", "Bringing home the bacon!", "Indie!", "GOTY!", "Ceci n'est pas une title screen!", "Euclidian!", "Now in 3D!", "Inspirational!", "Herregud!", "Complex cellular automata!", "Yes, sir!", "Played by cowboys!", "OpenGL 1.1!", "Thousands of colors!", "Try it!", "Age of Wonders is better!", "Try the mushroom stew!", "Sensational!", "Hot tamale, hot hot tamale!", "Play him off, keyboard cat!", "Guaranteed!", "Macroscopic!", "Bring it on!", "Random splash!", "Call your mother!", "Monster infighting!", "Loved by millions!", "Ultimate edition!", "Freaky!", "You've got a brand new key!", "Water proof!", "Uninflammable!", "Whoa, dude!", "All inclusive!", "Tell your friends!", "NP is not in P!", "Notch <3 Ez!", "Music by C418!"};
        this.currentSplash = this.splashes[(int) (Math.random() * this.splashes.length)];
    }

    @Override
    public void updateScreen() {
        this.updateCounter += 0.01f;
    }

    @Override
    protected void keyTyped(final char character, final int integer) {
    }

    @Override
    public void initGui() {
        this.controlList.clear();
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 48, "Singleplayer", 200, 20));
        this.controlList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 72, "Multiplayer", 200, 20));
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 114, "Options...", 95, 20));
        this.controlList.add(new GuiButton(3, this.width / 2 + 5, this.height / 4 + 114, "Quit Game", 95, 20));
    }

    @Override
    protected void actionPerformed(final GuiButton iq) {
        if (iq.buttonId == 0) {
            this.id.displayGuiScreen(new GuiOptions(this, this.id.gameSettings));
        }
        if (iq.buttonId == 1) {
            this.id.displayGuiScreen(new GuiCreateWorld(this));
        }
        if (iq.buttonId == 2) {
            this.id.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (iq.buttonId == 3) {
            this.id.shutdown();
        }
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.panorama.render(partialTicks);
        //this.drawDefaultBackground();
        final Tessellator instance = Tessellator.instance;
        GL11.glBindTexture(3553, this.id.renderEngine.getTexture("/assets/gui/logo.png"));
        final int integer3 = 256;
        final int integer4 = 49;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        instance.setColorOpaque_I(16777215);
        this.drawTexturedModalRect((this.width - integer3) / 2, 30, 0, 0, integer3, integer4);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) (this.width / 2 + 90), 70.0f, 0.0f);
        GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
        float n = 1.8f - MathHelper.abs(MathHelper.sin(System.currentTimeMillis() % 1000L / 1000.0f * 3.1415927f * 2.0f) * 0.1f);
        n = n * 100.0f / (this.fontRenderer.getStringWidth(this.currentSplash) + 32);
        GL11.glScalef(n, n, n);
        this.drawCenteredString(this.fontRenderer, this.currentSplash, 0, -8, 16776960);
        GL11.glPopMatrix();
        final String s = "OpenCraft inf-0.0.1";
        this.drawString(this.fontRenderer, s, this.width - this.fontRenderer.getStringWidth(s) - 2, this.height - 10, 16777215);
        final long maxMemory = Runtime.getRuntime().maxMemory();
        final long totalMemory = Runtime.getRuntime().totalMemory();
        final String string = new StringBuilder().append("Free memory: ").append((maxMemory - Runtime.getRuntime().freeMemory()) * 100L / maxMemory).append("% of ").append(maxMemory / 1024L / 1024L).append("MB").toString();
        this.drawString(this.fontRenderer, string, this.width - this.fontRenderer.getStringWidth(string) - 2, 2, 16777215);
        final String string2 = new StringBuilder().append("Allocated memory: ").append(totalMemory * 100L / maxMemory).append("% (").append(totalMemory / 1024L / 1024L).append("MB)").toString();
        this.drawString(this.fontRenderer, string2, this.width - this.fontRenderer.getStringWidth(string2) - 2, 12, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);

        super.id.sndManager.currentMusicTheme = "menu";
        super.id.sndManager.playRandomMusicIfReady();
    }
}
