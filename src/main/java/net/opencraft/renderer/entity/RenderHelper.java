package net.opencraft.renderer.entity;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import net.opencraft.util.Vec3;

public class RenderHelper {

	private static FloatBuffer a;

	public static void disableStandardItemLighting() {
		glDisable(2896);
		glDisable(16384);
		glDisable(16385);
		glDisable(2903);
	}

	public static void enableStandardItemLighting() {
		glEnable(GL_LIGHTING);
		glEnable(16384);
		glEnable(16385);
		glEnable(2903);
		glColorMaterial(1032, 5634);
		final float n = 0.4f;
		final float n2 = 0.6f;
		final float n3 = 0.0f;
		final Vec3 normalize = Vec3.newTemp(0.699999988079071, 1.0, -0.20000000298023224).normalize();
		glLightfv(16384, 4611, floatBuffer(normalize.x, normalize.y, normalize.z, 0.0));
		glLightfv(16384, 4609, floatBuffer(n2, n2, n2, 1.0f));
		glLightfv(16384, 4608, floatBuffer(0.0f, 0.0f, 0.0f, 1.0f));
		glLightfv(16384, 4610, floatBuffer(n3, n3, n3, 1.0f));
		final Vec3 normalize2 = Vec3.newTemp(-0.699999988079071, 1.0, 0.20000000298023224).normalize();
		glLightfv(16385, 4611, floatBuffer(normalize2.x, normalize2.y, normalize2.z, 0.0));
		glLightfv(16385, 4609, floatBuffer(n2, n2, n2, 1.0f));
		glLightfv(16385, 4608, floatBuffer(0.0f, 0.0f, 0.0f, 1.0f));
		glLightfv(16385, 4610, floatBuffer(n3, n3, n3, 1.0f));
		glShadeModel(7424);
		glLightModelfv(2899, floatBuffer(n, n, n, 1.0f));
	}

	private static FloatBuffer floatBuffer(final double double1, final double double2, final double double3, final double double4) {
		return floatBuffer((float) double1, (float) double2, (float) double3, (float) double4);
	}

	private static FloatBuffer floatBuffer(final float float1, final float float2, final float float3, final float float4) {
		RenderHelper.a.clear();
		RenderHelper.a.put(float1).put(float2).put(float3).put(float4);
		RenderHelper.a.flip();
		return RenderHelper.a;
	}

	static {
		RenderHelper.a = BufferUtils.createFloatBuffer(16);
	}

}
