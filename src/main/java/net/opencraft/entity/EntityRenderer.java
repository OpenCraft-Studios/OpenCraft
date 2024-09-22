
package net.opencraft.entity;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

import net.opencraft.OpenCraft;
import net.opencraft.ScaledResolution;
import net.opencraft.blocks.Block;
import net.opencraft.blocks.material.Material;
import net.opencraft.client.entity.PlayerControllerTest;
import net.opencraft.client.input.MovingObjectPosition;
import net.opencraft.item.ItemRenderer;
import net.opencraft.renderer.EffectRenderer;
import net.opencraft.renderer.Tessellator;
import net.opencraft.renderer.culling.Frustrum;
import net.opencraft.renderer.culling.Frustum;
import net.opencraft.renderer.entity.RenderGlobal;
import net.opencraft.renderer.entity.RenderHelper;
import net.opencraft.util.Mth;
import net.opencraft.util.Vec3;
import net.opencraft.world.World;

public class EntityRenderer {

    private OpenCraft mc;
    private boolean anaglyphEnable;
    private float farPlaneDistance;
    public ItemRenderer itemRenderer;
    private int rendererUpdateCount;
    private Entity pointedEntity;
    private int entityRendererInt1;
    private int entityRendererInt2;
    private Random random;
    volatile int unusedVolatile0;
    volatile int unusedVolatile1;
    FloatBuffer fogColorBuffer;
    float fogColorRed;
    float fogColorGreen;
    float fogColorBlue;
    private float fogColor2;
    private float fogColor1;

    public EntityRenderer(final OpenCraft aw) {
        this.anaglyphEnable = false;
        this.farPlaneDistance = 0.0f;
        this.pointedEntity = null;
        this.random = new Random();
        this.unusedVolatile0 = 0;
        this.unusedVolatile1 = 0;
        this.fogColorBuffer = BufferUtils.createFloatBuffer(16);
        this.mc = aw;
        this.itemRenderer = new ItemRenderer(aw);
    }

    public void updateRenderer() {
        this.fogColor2 = this.fogColor1;
        final float lightBrightness = this.mc.world.getLightBrightness(Mth.floor_double(this.mc.player.posX), Mth.floor_double(this.mc.player.posY), Mth.floor_double(this.mc.player.posZ));
        final float n = (3 - this.mc.options.renderDistance) / 3.0f;
        this.fogColor1 += (lightBrightness * (1.0f - n) + n - this.fogColor1) * 0.1f;
        ++this.rendererUpdateCount;
        this.itemRenderer.updateEquippedItem();
        if (this.mc.isRaining) {
            this.addRainParticles();
        }
    }

    private Vec3 orientCamera(final float float1) {
        final EntityPlayerSP thePlayer = this.mc.player;
        return Vec3.newTemp(thePlayer.prevPosX + (thePlayer.posX - thePlayer.prevPosX) * float1, thePlayer.prevPosY + (thePlayer.posY - thePlayer.prevPosY) * float1, thePlayer.prevPosZ + (thePlayer.posZ - thePlayer.prevPosZ) * float1);
    }

    private void getMouseOver(final float float1) {
        final EntityPlayerSP thePlayer = this.mc.player;
        final float n = thePlayer.prevRotationPitch + (thePlayer.rotationPitch - thePlayer.prevRotationPitch) * float1;
        final float n2 = thePlayer.prevRotationYaw + (thePlayer.rotationYaw - thePlayer.prevRotationYaw) * float1;
        final Vec3 orientCamera = this.orientCamera(float1);
        final float cos = Mth.cos(-n2 * 0.017453292f - 3.1415927f);
        final float sin = Mth.sin(-n2 * 0.017453292f - 3.1415927f);
        final float n3 = -Mth.cos(-n * 0.017453292f);
        final float sin2 = Mth.sin(-n * 0.017453292f);
        final float n4 = sin * n3;
        final float n5 = sin2;
        final float n6 = cos * n3;
        double n7 = this.mc.playerController.getBlockReachDistance();
        this.mc.objectMouseOver = this.mc.world.rayTraceBlocks(orientCamera, orientCamera.add(n4 * n7, n5 * n7, n6 * n7));
        double distanceTo = n7;
        final Vec3 orientCamera2 = this.orientCamera(float1);
        if (this.mc.objectMouseOver != null) {
            distanceTo = this.mc.objectMouseOver.hitVec.distance(orientCamera2);
        }
        if (this.mc.playerController instanceof PlayerControllerTest) {
            n7 = (distanceTo = 32.0);
        } else {
            if (distanceTo > 3.0) {
                distanceTo = 3.0;
            }
            n7 = distanceTo;
        }
        final Vec3 addVector = orientCamera2.add(n4 * n7, n5 * n7, n6 * n7);
        this.pointedEntity = null;
        final List entitiesWithinAABBExcludingEntity = this.mc.world.getEntitiesWithinAABBExcludingEntity(thePlayer, thePlayer.boundingBox.addCoord(n4 * n7, n5 * n7, n6 * n7));
        double n8 = 0.0;
        for (int i = 0; i < entitiesWithinAABBExcludingEntity.size(); ++i) {
            final Entity pointedEntity = (Entity) entitiesWithinAABBExcludingEntity.get(i);
            if (pointedEntity.canBeCollidedWith()) {
                final float n9 = 0.1f;
                final MovingObjectPosition calculateIntercept = pointedEntity.boundingBox.expand(n9, n9, n9).calculateIntercept(orientCamera2, addVector);
                if (calculateIntercept != null) {
                    final double distanceTo2 = orientCamera2.distance(calculateIntercept.hitVec);
                    if (distanceTo2 < n8 || n8 == 0.0) {
                        this.pointedEntity = pointedEntity;
                        n8 = distanceTo2;
                    }
                }
            }
        }
        if (this.pointedEntity != null && !(this.mc.playerController instanceof PlayerControllerTest)) {
            this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity);
        }
    }

    private float getFOVModifier(final float float1) {
        final EntityPlayerSP thePlayer = this.mc.player;
        float n = this.mc.options.fov;
        if (thePlayer.isInsideOfMaterial(Material.WATER)) {
            n = 60.0f;
        }
        if (thePlayer.health <= 0) {
            n /= (1.0f - 500.0f / (thePlayer.deathTime + float1 + 500.0f)) * 2.0f + 1.0f;
        }
        return n;
    }

    private void hurtCameraEffect(final float float1) {
        final EntityPlayerSP thePlayer = this.mc.player;
        float sin = thePlayer.hurtTime - float1;
        if (thePlayer.health <= 0) {
            final float attackedAtYaw = thePlayer.deathTime + float1;
            GL11.glRotatef(40.0f - 8000.0f / (attackedAtYaw + 200.0f), 0.0f, 0.0f, 1.0f);
        }
        if (sin < 0.0f) {
            return;
        }
        sin /= thePlayer.maxHurtTime;
        sin = Mth.sin(sin * sin * sin * sin * 3.1415927f);
        final float attackedAtYaw = thePlayer.attackedAtYaw;
        GL11.glRotatef(-attackedAtYaw, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-sin * 14.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(attackedAtYaw, 0.0f, 1.0f, 0.0f);
    }

    private void setupViewBobbing(final float float1) {
        if (this.mc.options.thirdPersonView) {
            return;
        }
        final EntityPlayerSP thePlayer = this.mc.player;
        final float n = thePlayer.distanceWalkedModified + (thePlayer.distanceWalkedModified - thePlayer.prevDistanceWalkedModified) * float1;
        final float n2 = thePlayer.prevCameraYaw + (thePlayer.cameraYaw - thePlayer.prevCameraYaw) * float1;
        final float n3 = thePlayer.prevCameraPitch + (thePlayer.cameraPitch - thePlayer.prevCameraPitch) * float1;
        GL11.glTranslatef(Mth.sin(n * 3.1415927f) * n2 * 0.5f, -Math.abs(Mth.cos(n * 3.1415927f) * n2), 0.0f);
        GL11.glRotatef(Mth.sin(n * 3.1415927f) * n2 * 3.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(Math.abs(Mth.cos(n * 3.1415927f + 0.2f) * n2) * 5.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(n3, 1.0f, 0.0f, 0.0f);
    }

    private void h(final float float1) {
        final EntityPlayerSP thePlayer = this.mc.player;
        final double double1 = thePlayer.prevPosX + (thePlayer.posX - thePlayer.prevPosX) * float1;
        final double double2 = thePlayer.prevPosY + (thePlayer.posY - thePlayer.prevPosY) * float1;
        final double double3 = thePlayer.prevPosZ + (thePlayer.posZ - thePlayer.prevPosZ) * float1;
        if (this.mc.options.thirdPersonView) {
            double n = 4.0;
            final double n2 = -Mth.sin(thePlayer.rotationYaw / 180.0f * 3.1415927f) * Mth.cos(thePlayer.rotationPitch / 180.0f * 3.1415927f) * n;
            final double n3 = Mth.cos(thePlayer.rotationYaw / 180.0f * 3.1415927f) * Mth.cos(thePlayer.rotationPitch / 180.0f * 3.1415927f) * n;
            final double n4 = -Mth.sin(thePlayer.rotationPitch / 180.0f * 3.1415927f) * n;
            for (int i = 0; i < 8; ++i) {
                float n5 = (float) ((i & 0x1) * 2 - 1);
                float n6 = (float) ((i >> 1 & 0x1) * 2 - 1);
                float n7 = (float) ((i >> 2 & 0x1) * 2 - 1);
                n5 *= 0.1f;
                n6 *= 0.1f;
                n7 *= 0.1f;
                final MovingObjectPosition rayTraceBlocks = this.mc.world.rayTraceBlocks(Vec3.newTemp(double1 + n5, double2 + n6, double3 + n7), Vec3.newTemp(double1 - n2 + n5 + n7, double2 - n4 + n6, double3 - n3 + n7));
                if (rayTraceBlocks != null) {
                    final double distanceTo = rayTraceBlocks.hitVec.distance(Vec3.newTemp(double1, double2, double3));
                    if (distanceTo < n) {
                        n = distanceTo;
                    }
                }
            }
            GL11.glTranslatef(0.0f, 0.0f, (float) (-n));
        } else {
            GL11.glTranslatef(0.0f, 0.0f, -0.1f);
        }
        GL11.glRotatef(thePlayer.prevRotationPitch + (thePlayer.rotationPitch - thePlayer.prevRotationPitch) * float1, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(thePlayer.prevRotationYaw + (thePlayer.rotationYaw - thePlayer.prevRotationYaw) * float1 + 180.0f, 0.0f, 1.0f, 0.0f);
    }

    private void orientCamera(final float float1, final int integer) {
        this.farPlaneDistance = (float) (256 >> this.mc.options.renderDistance);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        final float n = 0.07f;
        if (this.mc.options.anaglyph) {
            GL11.glTranslatef(-(integer * 2 - 1) * n, 0.0f, 0.0f);
        }
        GLU.gluPerspective(this.getFOVModifier(float1), this.mc.width / (float) this.mc.height, 0.05f, this.farPlaneDistance);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        if (this.mc.options.anaglyph) {
            GL11.glTranslatef((integer * 2 - 1) * 0.1f, 0.0f, 0.0f);
        }
        this.hurtCameraEffect(float1);
        if (this.mc.options.viewBobbing) {
            this.setupViewBobbing(float1);
        }
        this.h(float1);
    }

    private void setupCameraTransform(final float float1, final int integer) {
        GL11.glLoadIdentity();
        if (this.mc.options.anaglyph) {
            GL11.glTranslatef((integer * 2 - 1) * 0.1f, 0.0f, 0.0f);
        }
        GL11.glPushMatrix();
        this.hurtCameraEffect(float1);
        if (this.mc.options.viewBobbing) {
            this.setupViewBobbing(float1);
        }
        if (!this.mc.options.thirdPersonView) {
            this.itemRenderer.renderItemInFirstPerson(float1);
        }
        GL11.glPopMatrix();
        if (!this.mc.options.thirdPersonView) {
            this.itemRenderer.renderOverlays(float1);
            this.hurtCameraEffect(float1);
        }
        if (this.mc.options.viewBobbing) {
            this.setupViewBobbing(float1);
        }
    }

    public void updateCameraAndRender(final float float1) {
        if (this.anaglyphEnable && !Display.isActive()) {
            this.mc.displayInGameMenu();
        }
        this.anaglyphEnable = Display.isActive();
        if (this.mc.inGameHasFocus) {
            final int entityRendererInt1 = Mouse.getDX() * 1;
            final int scaledWidth = Mouse.getDY() * 1;
            this.mc.mouseHelper.mouseXYChange();
            int scaledHeight = 1;
            if (this.mc.options.invertMouse) {
                scaledHeight = -1;
            }
            final int n = entityRendererInt1;// + this.mc.mouseHelper.deltaX;
            final int n2 = scaledWidth;// - this.mc.mouseHelper.deltaY;
            if (entityRendererInt1 != 0 || this.entityRendererInt1 != 0) {
//                System.out.println(new StringBuilder().append("xxo: ").append(entityRendererInt1).append(", ").append(this.entityRendererInt1).append(": ").append(this.entityRendererInt1).append(", xo: ").append(n).toString());
            }
            if (this.entityRendererInt1 != 0) {
                this.entityRendererInt1 = 0;
            }
            if (this.entityRendererInt2 != 0) {
                this.entityRendererInt2 = 0;
            }
            if (entityRendererInt1 != 0) {
                this.entityRendererInt1 = entityRendererInt1;
            }
            if (scaledWidth != 0) {
                this.entityRendererInt2 = scaledWidth;
            }
            this.mc.player.setAngles((float) n, (float) (n2 * scaledHeight));
        }
        if (this.mc.skipRenderWorld) {
            return;
        }
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc.width, this.mc.height);
        final int scaledWidth = scaledResolution.getScaledWidth();
        int scaledHeight = scaledResolution.getScaledHeight();
        final int n = Mouse.getX() * scaledWidth / this.mc.width;
        final int n2 = scaledHeight - Mouse.getY() * scaledHeight / this.mc.height - 1;
        if (this.mc.world != null) {
            this.renderWorld(float1);
            this.mc.ingameGUI.renderGameOverlay(float1, this.mc.currentScreen != null, n, n2);
        } else {
            GL11.glViewport(0, 0, this.mc.width, this.mc.height);
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GL11.glClear(16640);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            this.setupOverlayRendering();
        }
        if (this.mc.currentScreen != null) {
            GL11.glClear(256);
            this.mc.currentScreen.drawScreen(n, n2, this.mc.getTickDelta());
        }
    }

    public void renderWorld(final float float1) {
        this.getMouseOver(float1);
        final EntityPlayerSP thePlayer = this.mc.player;
        final RenderGlobal renderGlobal = this.mc.renderGlobal;
        final EffectRenderer effectRenderer = this.mc.effectRenderer;
        final double xCoord = thePlayer.lastTickPosX + (thePlayer.posX - thePlayer.lastTickPosX) * float1;
        final double yCoord = thePlayer.lastTickPosY + (thePlayer.posY - thePlayer.lastTickPosY) * float1;
        final double zCoord = thePlayer.lastTickPosZ + (thePlayer.posZ - thePlayer.lastTickPosZ) * float1;
        for (int i = 0; i < 2; ++i) {
            if (this.mc.options.anaglyph) {
                if (i == 0) {
                    GL11.glColorMask(false, true, true, false);
                } else {
                    GL11.glColorMask(true, false, false, false);
                }
            }
            GL11.glViewport(0, 0, this.mc.width, this.mc.height);
            this.updateFogColor(float1);
            GL11.glClear(16640);
            GL11.glEnable(2884);
            this.orientCamera(float1, i);
            //Frustum.getInstance();
            if (this.mc.options.renderDistance < 2) {
                this.setupFog(-1);
                renderGlobal.renderSky(float1);
            }
            GL11.glEnable(2912);
            this.setupFog(1);
            final Frustrum frustrum = new Frustrum();
            frustrum.setPosition(xCoord, yCoord, zCoord);
            this.mc.renderGlobal.clipRenderersByFrustrum(frustrum, float1);
            this.mc.renderGlobal.updateRenderers(thePlayer, false);
            this.setupFog(0);
            GL11.glEnable(2912);
            GL11.glBindTexture(3553, this.mc.renderer.getTexture("/assets/terrain.png"));
            RenderHelper.disableStandardItemLighting();
            renderGlobal.sortAndRender(thePlayer, 0, float1);
            RenderHelper.enableStandardItemLighting();
            renderGlobal.renderEntities(this.orientCamera(float1), frustrum, float1);
            effectRenderer.renderLitParticles(thePlayer, float1);
            RenderHelper.disableStandardItemLighting();
            this.setupFog(0);
            effectRenderer.renderParticles(thePlayer, float1);
            if (this.mc.objectMouseOver != null && thePlayer.isInsideOfMaterial(Material.WATER)) {
                GL11.glDisable(3008);
                renderGlobal.drawBlockBreaking(thePlayer, this.mc.objectMouseOver, 0, thePlayer.inventory.getCurrentItem(), float1);
                renderGlobal.drawSelectionBox(thePlayer, this.mc.objectMouseOver, 0, thePlayer.inventory.getCurrentItem(), float1);
                GL11.glEnable(3008);
            }
            GL11.glBlendFunc(770, 771);
            this.setupFog(0);
            GL11.glEnable(3042);
            GL11.glDisable(2884);
            GL11.glBindTexture(3553, this.mc.renderer.getTexture("/assets/terrain.png"));
            if (this.mc.options.fancyGraphics) {
                GL11.glColorMask(false, false, false, false);
                final int sortAndRender = renderGlobal.sortAndRender(thePlayer, 1, float1);
                GL11.glColorMask(true, true, true, true);
                if (this.mc.options.anaglyph) {
                    if (i == 0) {
                        GL11.glColorMask(false, true, true, false);
                    } else {
                        GL11.glColorMask(true, false, false, false);
                    }
                }
                if (sortAndRender > 0) {
                    renderGlobal.renderAllRenderLists(1, float1);
                }
            } else {
                renderGlobal.sortAndRender(thePlayer, 1, float1);
            }
            GL11.glDepthMask(true);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            if (this.mc.objectMouseOver != null && !thePlayer.isInsideOfMaterial(Material.WATER)) {
                GL11.glDisable(3008);
                renderGlobal.drawBlockBreaking(thePlayer, this.mc.objectMouseOver, 0, thePlayer.inventory.getCurrentItem(), float1);
                renderGlobal.drawSelectionBox(thePlayer, this.mc.objectMouseOver, 0, thePlayer.inventory.getCurrentItem(), float1);
                GL11.glEnable(3008);
            }
            GL11.glDisable(2912);
            if (this.mc.isRaining) {
                this.renderRainSnow(float1);
            }
            if (this.pointedEntity != null) {
            }
            this.setupFog(0);
            GL11.glEnable(2912);
            renderGlobal.renderClouds(float1);
            GL11.glDisable(2912);
            this.setupFog(1);
            GL11.glClear(256);
            this.setupCameraTransform(float1, i);
            if (!this.mc.options.anaglyph) {
                return;
            }
        }
        GL11.glColorMask(true, true, true, false);
    }

    private void addRainParticles() {
        final EntityPlayerSP thePlayer = this.mc.player;
        final World theWorld = this.mc.world;
        final int floor_double = Mth.floor_double(thePlayer.posX);
        final int floor_double2 = Mth.floor_double(thePlayer.posY);
        final int floor_double3 = Mth.floor_double(thePlayer.posZ);
        final int n = 4;
        for (int i = 0; i < 50; ++i) {
            final int n2 = floor_double + this.random.nextInt(n * 2 + 1) - n;
            final int n3 = floor_double3 + this.random.nextInt(n * 2 + 1) - n;
            final int topSolidBlock = theWorld.findTopSolidBlock(n2, n3);
            final int blockId = theWorld.getBlockId(n2, topSolidBlock - 1, n3);
            if (topSolidBlock <= floor_double2 + n && topSolidBlock >= floor_double2 - n) {
                final float nextFloat = this.random.nextFloat();
                final float nextFloat2 = this.random.nextFloat();
                if (blockId > 0) {
                    this.mc.effectRenderer.addEffect(new EntityRainFX(theWorld, n2 + nextFloat, topSolidBlock + 0.1f - Block.blocksList[blockId].minY, n3 + nextFloat2));
                }
            }
        }
    }

    private void renderRainSnow(final float float1) {
        final EntityPlayerSP thePlayer = this.mc.player;
        final World theWorld = this.mc.world;
        final int floor_double = Mth.floor_double(thePlayer.posX);
        final int floor_double2 = Mth.floor_double(thePlayer.posY);
        final int floor_double3 = Mth.floor_double(thePlayer.posZ);
        final Tessellator instance = Tessellator.instance;
        GL11.glDisable(2884);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glBindTexture(3553, this.mc.renderer.getTexture("/assets/rain.png"));
        for (int n = 5, i = floor_double - n; i <= floor_double + n; ++i) {
            for (int j = floor_double3 - n; j <= floor_double3 + n; ++j) {
                final int topSolidBlock = theWorld.findTopSolidBlock(i, j);
                int n2 = floor_double2 - n;
                int n3 = floor_double2 + n;
                if (n2 < topSolidBlock) {
                    n2 = topSolidBlock;
                }
                if (n3 < topSolidBlock) {
                    n3 = topSolidBlock;
                }
                final float n4 = 2.0f;
                if (n2 != n3) {
                    final float n5 = ((this.rendererUpdateCount + i * 3121 + j * 418711) % 32 + float1) / 32.0f;
                    final double n6 = i + 0.5f - thePlayer.posX;
                    final double n7 = j + 0.5f - thePlayer.posZ;
                    final float n8 = Mth.sqrt_double(n6 * n6 + n7 * n7) / n;
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, (1.0f - n8 * n8) * 0.7f);
                    instance.beginQuads();
                    instance.vertexUV(i + 0, n2, j + 0, 0.0f * n4, n2 * n4 / 8.0f + n5 * n4);
                    instance.vertexUV(i + 1, n2, j + 1, 1.0f * n4, n2 * n4 / 8.0f + n5 * n4);
                    instance.vertexUV(i + 1, n3, j + 1, 1.0f * n4, n3 * n4 / 8.0f + n5 * n4);
                    instance.vertexUV(i + 0, n3, j + 0, 0.0f * n4, n3 * n4 / 8.0f + n5 * n4);
                    instance.vertexUV(i + 0, n2, j + 1, 0.0f * n4, n2 * n4 / 8.0f + n5 * n4);
                    instance.vertexUV(i + 1, n2, j + 0, 1.0f * n4, n2 * n4 / 8.0f + n5 * n4);
                    instance.vertexUV(i + 1, n3, j + 0, 1.0f * n4, n3 * n4 / 8.0f + n5 * n4);
                    instance.vertexUV(i + 0, n3, j + 1, 0.0f * n4, n3 * n4 / 8.0f + n5 * n4);
                    instance.draw();
                }
            }
        }
        GL11.glEnable(2884);
        GL11.glDisable(3042);
    }

    public void setupOverlayRendering() {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc.width, this.mc.height);
        final int scaledWidth = scaledResolution.getScaledWidth();
        final int scaledHeight = scaledResolution.getScaledHeight();
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double) scaledWidth, (double) scaledHeight, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
    }

    private void updateFogColor(final float float1) {
        final World theWorld = this.mc.world;
        final EntityPlayerSP thePlayer = this.mc.player;
        float n = 1.0f / (4 - this.mc.options.renderDistance);
        n = 1.0f - (float) Math.pow((double) n, 0.25);
        final Vec3 skyColor = theWorld.getSkyColor(float1);
        final float n2 = (float) skyColor.x;
        final float n3 = (float) skyColor.y;
        final float n4 = (float) skyColor.z;
        final Vec3 fogColor = theWorld.getFogColor(float1);
        this.fogColorRed = (float) fogColor.x;
        this.fogColorGreen = (float) fogColor.y;
        this.fogColorBlue = (float) fogColor.z;
        this.fogColorRed += (n2 - this.fogColorRed) * n;
        this.fogColorGreen += (n3 - this.fogColorGreen) * n;
        this.fogColorBlue += (n4 - this.fogColorBlue) * n;
        if (thePlayer.isInsideOfMaterial(Material.WATER)) {
            this.fogColorRed = 0.02f;
            this.fogColorGreen = 0.02f;
            this.fogColorBlue = 0.2f;
        } else if (thePlayer.isInsideOfMaterial(Material.LAVA)) {
            this.fogColorRed = 0.6f;
            this.fogColorGreen = 0.1f;
            this.fogColorBlue = 0.0f;
        }
        final float n5 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * float1;
        this.fogColorRed *= n5;
        this.fogColorGreen *= n5;
        this.fogColorBlue *= n5;
        if (this.mc.options.anaglyph) {
            final float fogColorRed = (this.fogColorRed * 30.0f + this.fogColorGreen * 59.0f + this.fogColorBlue * 11.0f) / 100.0f;
            final float fogColorGreen = (this.fogColorRed * 30.0f + this.fogColorGreen * 70.0f) / 100.0f;
            final float fogColorBlue = (this.fogColorRed * 30.0f + this.fogColorBlue * 70.0f) / 100.0f;
            this.fogColorRed = fogColorRed;
            this.fogColorGreen = fogColorGreen;
            this.fogColorBlue = fogColorBlue;
        }
        GL11.glClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0f);
    }

    private void setupFog(final int integer) {
        final EntityPlayerSP thePlayer = this.mc.player;
        GL11.glFog(2918, this.setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0f));
        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (thePlayer.isInsideOfMaterial(Material.WATER)) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 0.1f);
            float n = 0.4f;
            float n2 = 0.4f;
            float n3 = 0.9f;
            if (this.mc.options.anaglyph) {
                final float n4 = (n * 30.0f + n2 * 59.0f + n3 * 11.0f) / 100.0f;
                final float n5 = (n * 30.0f + n2 * 70.0f) / 100.0f;
                final float n6 = (n * 30.0f + n3 * 70.0f) / 100.0f;
                n = n4;
                n2 = n5;
                n3 = n6;
            }
        } else if (thePlayer.isInsideOfMaterial(Material.LAVA)) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 2.0f);
            float n = 0.4f;
            float n2 = 0.3f;
            float n3 = 0.3f;
            if (this.mc.options.anaglyph) {
                final float n4 = (n * 30.0f + n2 * 59.0f + n3 * 11.0f) / 100.0f;
                final float n5 = (n * 30.0f + n2 * 70.0f) / 100.0f;
                final float n6 = (n * 30.0f + n3 * 70.0f) / 100.0f;
                n = n4;
                n2 = n5;
                n3 = n6;
            }
        } else {
            GL11.glFogi(2917, 9729);
            GL11.glFogf(2915, this.farPlaneDistance * 0.25f);
            GL11.glFogf(2916, this.farPlaneDistance);
            if (integer < 0) {
                GL11.glFogf(2915, 0.0f);
                GL11.glFogf(2916, this.farPlaneDistance * 0.8f);
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi(34138, 34139);
            }
        }
        GL11.glEnable(2903);
        GL11.glColorMaterial(1028, 4608);
    }

    private FloatBuffer setFogColorBuffer(final float float1, final float float2, final float float3, final float float4) {
        this.fogColorBuffer.clear();
        this.fogColorBuffer.put(float1).put(float2).put(float3).put(float4);
        this.fogColorBuffer.flip();
        return this.fogColorBuffer;
    }
}
