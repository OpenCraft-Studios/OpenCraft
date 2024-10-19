
package net.opencraft.entity;

import static net.opencraft.OpenCraft.*;
import static org.joml.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.NVFogDistance.*;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import net.opencraft.ScaledResolution;
import net.opencraft.blocks.Block;
import net.opencraft.blocks.material.EnumMaterial;
import net.opencraft.client.input.MovingObjectPosition;
import net.opencraft.item.ItemRenderer;
import net.opencraft.renderer.EffectRenderer;
import net.opencraft.renderer.Tessellator;
import net.opencraft.renderer.culling.Frustrum;
import net.opencraft.renderer.entity.RenderGlobal;
import net.opencraft.renderer.entity.RenderHelper;
import net.opencraft.util.Mth;
import net.opencraft.util.Vec3;
import net.opencraft.world.World;

public class EntityRenderer {

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

	public EntityRenderer() {
		this.anaglyphEnable = false;
		this.farPlaneDistance = 0.0f;
		this.pointedEntity = null;
		this.random = new Random();
		this.unusedVolatile0 = 0;
		this.unusedVolatile1 = 0;
		this.fogColorBuffer = BufferUtils.createFloatBuffer(16);
		this.itemRenderer = new ItemRenderer();
	}

	public void updateRenderer() {
		this.fogColor2 = this.fogColor1;
		final float lightBrightness = oc.world.getLightBrightness(Mth.floor_double(oc.player.x),
				Mth.floor_double(oc.player.y), Mth.floor_double(oc.player.z));
		final float n = (3 - oc.options.renderDistance) / 3.0f;
		this.fogColor1 += (lightBrightness * (1.0f - n) + n - this.fogColor1) * 0.1f;
		++this.rendererUpdateCount;
		this.itemRenderer.updateEquippedItem();
		if (oc.isRaining)
			this.addRainParticles();
	}

	private Vec3 orientCamera(final float float1) {
		final EntityPlayerSP thePlayer = oc.player;
		return Vec3.newTemp(thePlayer.xo + (thePlayer.x - thePlayer.xo) * float1,
				thePlayer.yo + (thePlayer.y - thePlayer.yo) * float1,
				thePlayer.zo + (thePlayer.z - thePlayer.zo) * float1);
	}

	private void getMouseOver(final float float1) {
		final EntityPlayerSP thePlayer = oc.player;
		final float n = thePlayer.prevRotationPitch + (thePlayer.xRot - thePlayer.prevRotationPitch) * float1;
		final float n2 = thePlayer.prevRotationYaw + (thePlayer.yRot - thePlayer.prevRotationYaw) * float1;
		final Vec3 orientCamera = this.orientCamera(float1);
		final float cos = cos(-n2 * 0.017453292f - PI_f);
		final float sin = sin(-n2 * 0.017453292f - PI_f);
		final float n3 = -cos(-n * 0.017453292f);
		final float sin2 = sin(-n * 0.017453292f);
		final float n4 = sin * n3;
		final float n5 = sin2;
		final float n6 = cos * n3;
		double n7 = oc.playerController.getBlockReachDistance();
		oc.hitResult = oc.world.raycastBlocks(orientCamera, orientCamera.add(n4 * n7, n5 * n7, n6 * n7));
		double distanceTo = n7;
		Vec3 orientCamera2 = this.orientCamera(float1);
		if (oc.hitResult != null)
			distanceTo = oc.hitResult.hitVec.distance(orientCamera2);
		if (distanceTo > 3.0)
			distanceTo = 3.0;
		n7 = distanceTo;

		final Vec3 addVector = orientCamera2.add(n4 * n7, n5 * n7, n6 * n7);
		this.pointedEntity = null;
		List<Entity> entitiesWithinAABBExcludingEntity = oc.world.getEntitiesWithinAABBExcludingEntity(thePlayer,
				thePlayer.bb.addCoord(n4 * n7, n5 * n7, n6 * n7));
		double n8 = 0.0;
		for (int i = 0; i < entitiesWithinAABBExcludingEntity.size(); ++i) {
			final Entity pointedEntity = entitiesWithinAABBExcludingEntity.get(i);
			if (pointedEntity.canBeCollidedWith()) {
				final float n9 = 0.1f;
				final MovingObjectPosition calculateIntercept = pointedEntity.bb.grow(n9, n9, n9)
						.calculateIntercept(orientCamera2, addVector);
				if (calculateIntercept != null) {
					final double distanceTo2 = orientCamera2.distance(calculateIntercept.hitVec);
					if (distanceTo2 < n8 || n8 == 0.0) {
						this.pointedEntity = pointedEntity;
						n8 = distanceTo2;
					}
				}
			}
		}
		if (this.pointedEntity != null)
			oc.hitResult = new MovingObjectPosition(this.pointedEntity);
	}

	private float getFOVModifier(final float float1) {
		final EntityPlayerSP thePlayer = oc.player;
		float n = oc.options.fov;
		if (thePlayer.isInsideOfMaterial(EnumMaterial.WATER))
			n = 60.0f;
		if (thePlayer.health <= 0)
			n /= (1.0f - 500.0f / (thePlayer.deathTime + float1 + 500.0f)) * 2.0f + 1.0f;
		return n;
	}

	private void hurtCameraEffect(final float float1) {
		final EntityPlayerSP thePlayer = oc.player;
		float sin = thePlayer.hurtTime - float1;
		if (thePlayer.health <= 0) {
			final float attackedAtYaw = thePlayer.deathTime + float1;
			glRotatef(40.0f - 8000.0f / (attackedAtYaw + 200.0f), 0.0f, 0.0f, 1.0f);
		}
		if (sin < 0.0f)
			return;
		sin /= thePlayer.maxHurtTime;
		sin = sin(sin * sin * sin * sin * PI_f);
		final float attackedAtYaw = thePlayer.attackedAtYaw;
		glRotatef(-attackedAtYaw, 0.0f, 1.0f, 0.0f);
		glRotatef(-sin * 14.0f, 0.0f, 0.0f, 1.0f);
		glRotatef(attackedAtYaw, 0.0f, 1.0f, 0.0f);
	}

	private void setupViewBobbing(final float float1) {
		if (oc.options.thirdPersonView)
			return;
		final float n = oc.player.distanceWalkedModified
				+ (oc.player.distanceWalkedModified - oc.player.prevDistanceWalkedModified) * float1;
		final float n2 = oc.player.prevCameraYaw + (oc.player.cameraYaw - oc.player.prevCameraYaw) * float1;
		final float n3 = oc.player.prevCameraPitch + (oc.player.cameraPitch - oc.player.prevCameraPitch) * float1;
		glTranslatef(sin(n * PI_f) * n2 * 0.5f, -abs(cos(n * PI_f) * n2), 0.0f);
		glRotatef(sin(n * PI_f) * n2 * 3.0f, 0.0f, 0.0f, 1.0f);
		glRotatef(abs(cos(n * PI_f + 0.2f) * n2) * 5.0f, 1.0f, 0.0f, 0.0f);
		glRotatef(n3, 1.0f, 0.0f, 0.0f);
	}

	private void h(final float multiplier) {
		final EntityPlayerSP player = oc.player;
		double double1, double2, double3;

		double1 = fma(player.x - player.xo, multiplier, player.xo);
		double2 = fma(player.y - player.yo, multiplier, player.yo);
		double3 = fma(player.z - player.zo, multiplier, player.zo);

		if (oc.options.thirdPersonView) {
			double n = 4.0;

			final double n2 = -sin(toRadians(player.yRot)) * cos(toRadians(player.xRot)) * n;
			final double n3 = cos(toRadians(player.yRot)) * cos(toRadians(player.xRot)) * n;
			final double n4 = -sin(toRadians(player.xRot)) * n;
			for (int i = 0; i < 8; ++i) {
				float n5 = (i & 0x1) * 2 - 1;
				float n6 = (i >> 1 & 0x1) * 2 - 1;
				float n7 = (i >> 2 & 0x1) * 2 - 1;
				n5 *= 0.1f;
				n6 *= 0.1f;
				n7 *= 0.1f;
				final MovingObjectPosition rayTraceBlocks = oc.world.raycastBlocks(
						Vec3.newTemp(double1 + n5, double2 + n6, double3 + n7),
						Vec3.newTemp(double1 - n2 + n5 + n7, double2 - n4 + n6, double3 - n3 + n7));
				if (rayTraceBlocks != null) {
					double dist = rayTraceBlocks.hitVec.distance(Vec3.newTemp(double1, double2, double3));
					if (dist < n)
						n = dist;
				}
			}
			glTranslatef(0.0f, 0.0f, (float) (-n));
		} else
			glTranslatef(0.0f, 0.0f, -0.1f);
		glRotatef(player.prevRotationPitch + (player.xRot - player.prevRotationPitch) * multiplier, 1.0f, 0.0f, 0.0f);
		glRotatef(player.prevRotationYaw + (player.yRot - player.prevRotationYaw) * multiplier + 180.0f, 0.0f, 1.0f,
				0.0f);
	}

	private void orientCamera(final float float1, final int integer) {
		this.farPlaneDistance = 256 >> oc.options.renderDistance;
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		final float n = 0.07f;
		if (oc.options.anaglyph.get())
			glTranslatef(-(integer * 2 - 1) * n, 0.0f, 0.0f);

		gluPerspective(this.getFOVModifier(float1), (float) oc.width / (float) oc.height, 0.05f, this.farPlaneDistance);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		if (oc.options.anaglyph.get())
			glTranslatef((integer * 2 - 1) * 0.1f, 0.0f, 0.0f);
		this.hurtCameraEffect(float1);
		if (oc.options.viewBobbing.get())
			this.setupViewBobbing(float1);
		this.h(float1);
	}

	public static void gluPerspective(float fovy, float aspect, float near, float far) {
		// Create JOML Matrix
		Matrix4f objMatrix = new Matrix4f()
				// Use perspective
				.perspective(toRadians(fovy), aspect, near, far);

		// Save in array
		float[] arrMatrix = new float[16];
		objMatrix.get(arrMatrix);

		// Send array to OpenGL
		glMultMatrixf(arrMatrix);
	}

	private void setupCameraTransform(final float float1, final int integer) {
		glLoadIdentity();
		if (oc.options.anaglyph.get())
			glTranslatef((integer * 2 - 1) * 0.1f, 0.0f, 0.0f);
		glPushMatrix();
		this.hurtCameraEffect(float1);
		if (oc.options.viewBobbing.get())
			this.setupViewBobbing(float1);
		if (!oc.options.thirdPersonView)
			this.itemRenderer.renderItemInFP(float1);
		glPopMatrix();
		if (!oc.options.thirdPersonView) {
			this.itemRenderer.renderOverlays(float1);
			this.hurtCameraEffect(float1);
		}
		if (oc.options.viewBobbing.get())
			this.setupViewBobbing(float1);
	}

	public void updateCameraAndRender(final float float1) {
		/*
		 * TODO: reimplement whatever this does. I think it shows the pause menu when
		 * the window is not focused if (this.anaglyphEnable && !Display.isActive()) {
		 * oc.displayInGameMenu(); }
		 */
		this.anaglyphEnable = glfwGetWindowAttrib(oc.window, GLFW_FOCUSED) == GLFW_TRUE;
		if (oc.inGameHasFocus) {
			final int scaledHeight = (int) (oc.mouse.position.deltaX() * 1);
			final int scaledWidth = (int) (oc.mouse.position.deltaY() * 1);
			int directionY = oc.options.invertMouse.either(-1, 1);
			final int n = scaledHeight;// + oc.mouseHelper.deltaX;
			final int n2 = scaledWidth;// - oc.mouseHelper.deltaY;
			if (scaledHeight != 0 || this.entityRendererInt1 != 0) {
				// System.out.println("xxo: " + entityRendererInt1 + ", " + entityRendererInt1 +
				// ": " + entityRendererInt1 + ", xo: " + n);
			}
			this.entityRendererInt1 = 0;
			this.entityRendererInt2 = 0;

			if (scaledHeight != 0)
				this.entityRendererInt1 = scaledHeight;
			if (scaledWidth != 0)
				this.entityRendererInt2 = scaledWidth;

			oc.player.setAngles(n, n2 * directionY);
		}
		if (oc.skipRenderWorld)
			return;
		final ScaledResolution scaledResolution = new ScaledResolution(oc.width, oc.height);
		final int scaledWidth = scaledResolution.getScaledWidth();
		int scaledHeight = scaledResolution.getScaledHeight();
		final int n = (int) (oc.mouse.position.x * scaledWidth / oc.width);
		final int n2 = (int) (scaledHeight - oc.mouse.position.y * scaledHeight / oc.height - 1);
		if (oc.world != null) {
			this.renderWorld(float1);
			oc.ingameGUI.renderGameOverlay(float1, oc.currentScreen != null, n, n2);
		} else {
			glViewport(0, 0, oc.width, oc.height);
			glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();
			this.setupOverlayRendering();
		}
		if (oc.currentScreen != null) {
			glClear(GL_DEPTH_BUFFER_BIT);
			oc.currentScreen.drawScreen(n, n2, oc.getTickDelta());
		}
	}

	public void renderWorld(final float float1) {
		this.getMouseOver(float1);
		final EntityPlayerSP player = oc.player;
		final RenderGlobal renderGlobal = oc.renderGlobal;
		final EffectRenderer effectRenderer = oc.effectRenderer;
		final double xCoord = player.lastTickPosX + (player.x - player.lastTickPosX) * float1;
		final double yCoord = player.lastTickPosY + (player.y - player.lastTickPosY) * float1;
		final double zCoord = player.lastTickPosZ + (player.z - player.lastTickPosZ) * float1;
		for (int i = 0; i < 2; ++i) {
			if (oc.options.anaglyph.get())
				if (i == 0)
					glColorMask(false, true, true, false);
				else
					glColorMask(true, false, false, false);
			glViewport(0, 0, oc.width, oc.height);
			this.updateFogColor(float1);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glEnable(GL_CULL_FACE);
			this.orientCamera(float1, i);
			// Frustum.getInstance();
			if (oc.options.renderDistance < 2) {
				this.setupFog(-1);
				renderGlobal.renderSky(float1);
			}
			glEnable(GL_FOG);
			this.setupFog(1);
			final Frustrum frustrum = new Frustrum();
			frustrum.setPosition(xCoord, yCoord, zCoord);
			oc.renderGlobal.clipRenderersByFrustrum(frustrum, float1);
			oc.renderGlobal.updateRenderers(player, false);
			this.setupFog(0);
			glEnable(GL_FOG);
			oc.renderer.bindTexture("/assets/terrain.png");
			RenderHelper.disableStandardItemLighting();
			renderGlobal.sortAndRender(player, 0, float1);
			RenderHelper.enableStandardItemLighting();
			renderGlobal.renderEntities(this.orientCamera(float1), frustrum, float1);
			effectRenderer.renderLitParticles(player, float1);
			RenderHelper.disableStandardItemLighting();
			this.setupFog(0);
			effectRenderer.renderParticles(player, float1);
			if (oc.hitResult != null && player.isInsideOfMaterial(EnumMaterial.WATER)) {
				glDisable(GL_ALPHA_TEST);
				renderGlobal.drawBlockBreaking(player, oc.hitResult, 0, player.inventory.getCurrentItem(), float1);
				renderGlobal.drawSelectionBox(player, oc.hitResult, 0, player.inventory.getCurrentItem(), float1);
				glEnable(GL_ALPHA_TEST);
			}
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			this.setupFog(0);
			glEnable(GL_BLEND);
			glDisable(GL_CULL_FACE);
			oc.renderer.bindTexture("/assets/terrain.png");
			if (oc.options.fancyGraphics) {
				glColorMask(false, false, false, false);
				final int sortAndRender = renderGlobal.sortAndRender(player, 1, float1);
				glColorMask(true, true, true, true);
				if (oc.options.anaglyph.get())
					if (i == 0)
						glColorMask(false, true, true, false);
					else
						glColorMask(true, false, false, false);
				if (sortAndRender > 0)
					renderGlobal.renderAllRenderLists(1, float1);
			} else
				renderGlobal.sortAndRender(player, 1, float1);
			glDepthMask(true);
			glEnable(GL_CULL_FACE);
			glDisable(GL_BLEND);
			if (oc.hitResult != null && !player.isInsideOfMaterial(EnumMaterial.WATER)) {
				glDisable(GL_ALPHA_TEST);
				renderGlobal.drawBlockBreaking(player, oc.hitResult, 0, player.inventory.getCurrentItem(), float1);
				renderGlobal.drawSelectionBox(player, oc.hitResult, 0, player.inventory.getCurrentItem(), float1);
				glEnable(GL_ALPHA_TEST);
			}
			glDisable(GL_FOG);
			if (oc.isRaining)
				this.renderRainSnow(float1);
			if (this.pointedEntity != null) {
			}
			this.setupFog(0);
			glEnable(GL_FOG);
			renderGlobal.renderClouds(float1);
			glDisable(GL_FOG);
			this.setupFog(1);
			glClear(GL_DEPTH_BUFFER_BIT);
			this.setupCameraTransform(float1, i);
			if (!oc.options.anaglyph.get())
				return;
		}
		glColorMask(true, true, true, false);
	}

	private void addRainParticles() {
		final EntityPlayerSP player = oc.player;
		final World theWorld = oc.world;
		final int floor_double = Mth.floor_double(player.x);
		final int floor_double2 = Mth.floor_double(player.y);
		final int floor_double3 = Mth.floor_double(player.z);
		final int n = 4;
		for (int i = 0; i < 50; ++i) {
			final int n2 = floor_double + this.random.nextInt(n * 2 + 1) - n;
			final int n3 = floor_double3 + this.random.nextInt(n * 2 + 1) - n;
			final int topSolidBlock = theWorld.findTopSolidBlock(n2, n3);
			final int blockId = theWorld.getBlockId(n2, topSolidBlock - 1, n3);
			if (topSolidBlock <= floor_double2 + n && topSolidBlock >= floor_double2 - n) {
				final float nextFloat = this.random.nextFloat();
				final float nextFloat2 = this.random.nextFloat();
				if (blockId > 0)
					oc.effectRenderer.addEffect(new EntityRainFX(theWorld, n2 + nextFloat,
							topSolidBlock + 0.1f - Block.BLOCKS[blockId].minY, n3 + nextFloat2));
			}
		}
	}

	private void renderRainSnow(final float float1) {
		final EntityPlayerSP thePlayer = oc.player;
		final World theWorld = oc.world;
		final int floor_double = Mth.floor_double(thePlayer.x);
		final int floor_double2 = Mth.floor_double(thePlayer.y);
		final int floor_double3 = Mth.floor_double(thePlayer.z);
		final Tessellator t = Tessellator.instance;
		glDisable(GL_CULL_FACE);
		glNormal3f(0.0f, 1.0f, 0.0f);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		oc.renderer.bindTexture("/assets/rain.png");
		for (int n = 5, i = floor_double - n; i <= floor_double + n; ++i)
			for (int j = floor_double3 - n; j <= floor_double3 + n; ++j) {
				final int topSolidBlock = theWorld.findTopSolidBlock(i, j);
				int n2 = floor_double2 - n;
				int n3 = floor_double2 + n;
				if (n2 < topSolidBlock)
					n2 = topSolidBlock;
				if (n3 < topSolidBlock)
					n3 = topSolidBlock;
				final float n4 = 2.0f;
				if (n2 != n3) {
					final float n5 = ((this.rendererUpdateCount + i * 3121 + j * 418711) % 32 + float1) / 32.0f;
					final double n6 = i + 0.5f - thePlayer.x;
					final double n7 = j + 0.5f - thePlayer.z;
					final float n8 = Mth.sqrt_double(n6 * n6 + n7 * n7) / n;
					glColor4f(1.0f, 1.0f, 1.0f, (1.0f - n8 * n8) * 0.7f);
					t.beginQuads();
					{
						t.vertexUV(i + 0, n2, j + 0, 0.0f * n4, n2 * n4 / 8.0f + n5 * n4);
						t.vertexUV(i + 1, n2, j + 1, 1.0f * n4, n2 * n4 / 8.0f + n5 * n4);
						t.vertexUV(i + 1, n3, j + 1, 1.0f * n4, n3 * n4 / 8.0f + n5 * n4);
						t.vertexUV(i + 0, n3, j + 0, 0.0f * n4, n3 * n4 / 8.0f + n5 * n4);
					
						t.vertexUV(i + 0, n2, j + 1, 0.0f * n4, n2 * n4 / 8.0f + n5 * n4);
						t.vertexUV(i + 1, n2, j + 0, 1.0f * n4, n2 * n4 / 8.0f + n5 * n4);
						t.vertexUV(i + 1, n3, j + 0, 1.0f * n4, n3 * n4 / 8.0f + n5 * n4);
						t.vertexUV(i + 0, n3, j + 1, 0.0f * n4, n3 * n4 / 8.0f + n5 * n4);
					}
					t.render();
				}
			}
		glEnable(GL_CULL_FACE);
		glDisable(GL_BLEND);
	}

	public void setupOverlayRendering() {
		final ScaledResolution scaledResolution = new ScaledResolution(oc.width, oc.height);
		final int scaledWidth = scaledResolution.getScaledWidth();
		final int scaledHeight = scaledResolution.getScaledHeight();
		glClear(GL_DEPTH_BUFFER_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, scaledWidth, scaledHeight, 0.0, 1000.0, 3000.0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glTranslatef(0.0f, 0.0f, -2000.0f);
	}

	private void updateFogColor(final float float1) {
		final World theWorld = oc.world;
		final EntityPlayerSP thePlayer = oc.player;
		float n = 1.0f / (4 - oc.options.renderDistance);
		n = 1.0f - (float) Math.pow(n, 0.25);
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
		if (thePlayer.isInsideOfMaterial(EnumMaterial.WATER)) {
			this.fogColorRed = 0.02f;
			this.fogColorGreen = 0.02f;
			this.fogColorBlue = 0.2f;
		} else if (thePlayer.isInsideOfMaterial(EnumMaterial.LAVA)) {
			this.fogColorRed = 0.6f;
			this.fogColorGreen = 0.1f;
			this.fogColorBlue = 0.0f;
		}
		final float n5 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * float1;
		this.fogColorRed *= n5;
		this.fogColorGreen *= n5;
		this.fogColorBlue *= n5;
		if (oc.options.anaglyph.get()) {
			final float fogColorRed = (this.fogColorRed * 30.0f + this.fogColorGreen * 59.0f
					+ this.fogColorBlue * 11.0f) / 100.0f;
			final float fogColorGreen = (this.fogColorRed * 30.0f + this.fogColorGreen * 70.0f) / 100.0f;
			final float fogColorBlue = (this.fogColorRed * 30.0f + this.fogColorBlue * 70.0f) / 100.0f;
			this.fogColorRed = fogColorRed;
			this.fogColorGreen = fogColorGreen;
			this.fogColorBlue = fogColorBlue;
		}
		glClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0f);
	}

	private void setupFog(final int integer) {
		final EntityPlayerSP player = oc.player;
		glFogfv(GL_FOG_COLOR, this.setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0f));
		glNormal3f(0.0f, -1.0f, 0.0f);
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		if (player.isInsideOfMaterial(EnumMaterial.WATER)) {
			glFogi(GL_FOG_MODE, GL_EXP);
			glFogf(GL_FOG_DENSITY, 0.1f);
			float n = 0.4f;
			float n2 = 0.4f;
			float n3 = 0.9f;
			if (oc.options.anaglyph.get()) {
				final float n4 = (n * 30.0f + n2 * 59.0f + n3 * 11.0f) / 100.0f;
				final float n5 = (n * 30.0f + n2 * 70.0f) / 100.0f;
				final float n6 = (n * 30.0f + n3 * 70.0f) / 100.0f;
				n = n4;
				n2 = n5;
				n3 = n6;
			}
		} else if (player.isInsideOfMaterial(EnumMaterial.LAVA)) {
			glFogi(GL_FOG_MODE, GL_EXP);
			glFogf(GL_FOG_DENSITY, 2.0f);
			float n = 0.4f;
			float n2 = 0.3f;
			float n3 = 0.3f;
			if (oc.options.anaglyph.get()) {
				final float n4 = (n * 30.0f + n2 * 59.0f + n3 * 11.0f) / 100.0f;
				final float n5 = (n * 30.0f + n2 * 70.0f) / 100.0f;
				final float n6 = (n * 30.0f + n3 * 70.0f) / 100.0f;
				n = n4;
				n2 = n5;
				n3 = n6;
			}
		} else {
			glFogi(GL_FOG_MODE, GL_LINEAR); // TODO: you can modify how fog behaves, wow
			glFogf(GL_FOG_START, this.farPlaneDistance * 0.25f);
			glFogf(2916, this.farPlaneDistance);
			if (integer < 0) {
				glFogf(GL_FOG_START, 0.0f);
				glFogf(GL_FOG_END, this.farPlaneDistance * 0.8f);
			}
			if (GL.getCapabilities().GL_NV_fog_distance)
				glFogi(GL_FOG_DISTANCE_MODE_NV, GL_EYE_RADIAL_NV);
		}
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_AMBIENT);
	}

	private FloatBuffer setFogColorBuffer(final float float1, final float float2, final float float3,
			final float float4) {
		this.fogColorBuffer.clear();
		this.fogColorBuffer.put(float1).put(float2).put(float3).put(float4);
		this.fogColorBuffer.flip();
		return this.fogColorBuffer;
	}

}
