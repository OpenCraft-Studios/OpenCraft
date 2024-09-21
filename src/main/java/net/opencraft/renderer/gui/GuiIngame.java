
package net.opencraft.renderer.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.opencraft.OpenCraft;
import net.opencraft.ScaledResolution;
import net.opencraft.blocks.material.Material;
import net.opencraft.inventory.InventoryPlayer;
import net.opencraft.item.ItemStack;
import net.opencraft.renderer.Tessellator;
import net.opencraft.renderer.entity.RenderHelper;
import net.opencraft.renderer.entity.RenderItem;
import net.opencraft.renderer.font.FontRenderer;

import org.lwjgl.opengl.GL11;

public class GuiIngame extends GuiElement {

    private static RenderItem itemRenderer = new RenderItem();
    private List<ChatLine> chatMessageList;
    private Random rand;
    private OpenCraft mc;
    public String field_933_a;
    private int updateCounter;
    public float damageGuiPartialTime;
    float prevVignetteBrightness;

    public GuiIngame(final OpenCraft aw) {
        this.chatMessageList = new ArrayList<>();
        this.rand = new Random();
        this.field_933_a = null;
        this.updateCounter = 0;
        this.prevVignetteBrightness = 1.0f;
        this.mc = aw;
    }

    public void renderGameOverlay(final float float1, final boolean boolean2, final int integer3, final int integer4) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc.width, this.mc.height);
        final int scaledWidth = scaledResolution.getScaledWidth();
        final int scaledHeight = scaledResolution.getScaledHeight();
        final FontRenderer fontRenderer = this.mc.fontRenderer;
        this.mc.entityRenderer.setupOverlayRendering();
        GL11.glEnable(3042);
        if (this.mc.gameSettings.fancyGraphics) {
            this.renderVignette(this.mc.player.getEntityBrightness(float1), scaledWidth, scaledHeight);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBindTexture(3553, this.mc.renderEngine.getTexture("/assets/gui/gui.png"));
        final InventoryPlayer inventory = this.mc.player.inventory;
        this.zLevel = -90.0f;
        this.drawTexturedModalRect(scaledWidth / 2 - 91, scaledHeight - 22, 0, 0, 182, 22);
        this.drawTexturedModalRect(scaledWidth / 2 - 91 - 1 + inventory.currentItem * 20, scaledHeight - 22 - 1, 0, 22, 24, 22);
        GL11.glBindTexture(3553, this.mc.renderEngine.getTexture("/assets/gui/icons.png"));
        GL11.glEnable(3042);
        GL11.glBlendFunc(775, 769);
        this.drawTexturedModalRect(scaledWidth / 2 - 7, scaledHeight / 2 - 7, 0, 0, 16, 16);
        GL11.glDisable(3042);
        boolean b = this.mc.player.heartsLife / 3 % 2 == 1;
        if (this.mc.player.heartsLife < 10) {
            b = false;
        }
        final int health = this.mc.player.health;
        final int prevHealth = this.mc.player.prevHealth;
        this.rand.setSeed((long) (this.updateCounter * 312871));
        if (this.mc.playerController.shouldDrawHUD()) {
            final int i = this.mc.player.getPlayerArmorValue();
            for (int j = 0; j < 10; ++j) {
                int integer5 = scaledHeight - 32;
                if (i > 0) {
                    final int integer6 = scaledWidth / 2 + 91 - j * 8 - 9;
                    if (j * 2 + 1 < i) {
                        this.drawTexturedModalRect(integer6, integer5, 34, 9, 9, 9);
                    }
                    if (j * 2 + 1 == i) {
                        this.drawTexturedModalRect(integer6, integer5, 25, 9, 9, 9);
                    }
                    if (j * 2 + 1 > i) {
                        this.drawTexturedModalRect(integer6, integer5, 16, 9, 9, 9);
                    }
                }
                int n = 0;
                if (b) {
                    n = 1;
                }
                final int integer7 = scaledWidth / 2 - 91 + j * 8;
                if (health <= 4) {
                    integer5 += this.rand.nextInt(2);
                }
                this.drawTexturedModalRect(integer7, integer5, 16 + n * 9, 0, 9, 9);
                if (b) {
                    if (j * 2 + 1 < prevHealth) {
                        this.drawTexturedModalRect(integer7, integer5, 70, 0, 9, 9);
                    }
                    if (j * 2 + 1 == prevHealth) {
                        this.drawTexturedModalRect(integer7, integer5, 79, 0, 9, 9);
                    }
                }
                if (j * 2 + 1 < health) {
                    this.drawTexturedModalRect(integer7, integer5, 52, 0, 9, 9);
                }
                if (j * 2 + 1 == health) {
                    this.drawTexturedModalRect(integer7, integer5, 61, 0, 9, 9);
                }
            }
            if (this.mc.player.isInsideOfMaterial(Material.WATER)) {
                for (int j = (int) Math.ceil((this.mc.player.air - 2) * 10.0 / 300.0), integer5 = (int) Math.ceil(this.mc.player.air * 10.0 / 300.0) - j, k = 0; k < j + integer5; ++k) {
                    if (k < j) {
                        this.drawTexturedModalRect(scaledWidth / 2 - 91 + k * 8, scaledHeight - 32 - 9, 16, 18, 9, 9);
                    } else {
                        this.drawTexturedModalRect(scaledWidth / 2 - 91 + k * 8, scaledHeight - 32 - 9, 25, 18, 9, 9);
                    }
                }
            }
        }
        GL11.glDisable(3042);
        GL11.glEnable(32826);
        GL11.glPushMatrix();
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        for (int i = 0; i < 9; ++i) {
            final int j = scaledWidth / 2 - 90 + i * 20 + 2;
            final int integer5 = scaledHeight - 16 - 3;
            this.renderInventorySlot(i, j, integer5, float1);
        }
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);
        if (this.mc.gameSettings.showDebugInfo) {
            fontRenderer.drawStringWithShadow2("nCraft Infdev (" + this.mc.debug + ")", 2, 2, 16777215);
            fontRenderer.drawStringWithShadow2(this.mc.debugInfoRenders(), 2, 12, 16777215);
            fontRenderer.drawStringWithShadow2(this.mc.func_6262_n(), 2, 22, 16777215);
            fontRenderer.drawStringWithShadow2(this.mc.debugInfoEntities(), 2, 32, 16777215);
            final long maxMemory = Runtime.getRuntime().maxMemory();
            final long totalMemory = Runtime.getRuntime().totalMemory();
            final long n2 = totalMemory - Runtime.getRuntime().freeMemory();
            final String string = new StringBuilder().append("Used memory: ").append(n2 * 100L / maxMemory).append("% (").append(n2 / 1024L / 1024L).append("MB) of ").append(maxMemory / 1024L / 1024L).append("MB").toString();
            this.drawString(fontRenderer, string, scaledWidth - fontRenderer.getStringWidth(string) - 2, 2, 14737632);
            final String string2 = new StringBuilder().append("Allocated memory: ").append(totalMemory * 100L / maxMemory).append("% (").append(totalMemory / 1024L / 1024L).append("MB)").toString();
            this.drawString(fontRenderer, string2, scaledWidth - fontRenderer.getStringWidth(string2) - 2, 12, 14737632);
        } else {
            fontRenderer.drawStringWithShadow2("nCraft Infdev", 2, 2, 16777215);
        }
        int i = 10;
        final boolean b2 = true;
        for (int integer5 = 0; integer5 < this.chatMessageList.size() && integer5 < i; ++integer5) {
            if (((ChatLine) this.chatMessageList.get(integer5)).updateCounter < 200 || b2) {
                fontRenderer.drawStringWithShadow2(((ChatLine) this.chatMessageList.get(integer5)).message, 2, scaledHeight - 8 - integer5 * 9 - 20, 16777215);
            }
        }
    }

    private void renderVignette(float float1, final int integer2, final int integer3) {
        float1 = 1.0f - float1;
        if (float1 < 0.0f) {
            float1 = 0.0f;
        }
        if (float1 > 1.0f) {
            float1 = 1.0f;
        }
        this.prevVignetteBrightness += (float) ((float1 - this.prevVignetteBrightness) * 0.01);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(0, 769);
        GL11.glColor4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0f);
        GL11.glBindTexture(3553, this.mc.renderEngine.getTexture("/assets/misc/vignette.png"));
        final Tessellator instance = Tessellator.instance;
        instance.beginQuads();
        instance.vertexUV(0.0, integer3, -90.0, 0.0, 1.0);
        instance.vertexUV(integer2, integer3, -90.0, 1.0, 1.0);
        instance.vertexUV(integer2, 0.0, -90.0, 1.0, 0.0);
        instance.vertexUV(0.0, 0.0, -90.0, 0.0, 0.0);
        instance.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBlendFunc(770, 771);
    }

    private void renderInventorySlot(final int integer1, final int integer2, final int integer3, final float float4) {
        final ItemStack itemStack = this.mc.player.inventory.mainInventory[integer1];
        if (itemStack == null) {
            return;
        }
        final float n = itemStack.animationsToGo - float4;
        if (n > 0.0f) {
            GL11.glPushMatrix();
            final float n2 = 1.0f + n / 5.0f;
            GL11.glTranslatef((float) (integer2 + 8), (float) (integer3 + 12), 0.0f);
            GL11.glScalef(1.0f / n2, (n2 + 1.0f) / 2.0f, 1.0f);
            GL11.glTranslatef((float) (-(integer2 + 8)), (float) (-(integer3 + 12)), 0.0f);
        }
        GuiIngame.itemRenderer.drawItemIntoGui(this.mc.fontRenderer, this.mc.renderEngine, itemStack, integer2, integer3);
        if (n > 0.0f) {
            GL11.glPopMatrix();
        }
        GuiIngame.itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, itemStack, integer2, integer3);
    }

    public void updateTick() {
        ++this.updateCounter;
        for (int i = 0; i < this.chatMessageList.size(); ++i) {
            final ChatLine chatLine = (ChatLine) this.chatMessageList.get(i);
            ++chatLine.updateCounter;
        }
    }

}
