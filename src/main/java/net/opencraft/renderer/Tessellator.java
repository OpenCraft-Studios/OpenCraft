
package net.opencraft.renderer;

import static org.joml.Math.*;
import static org.lwjgl.opengl.ARBVertexBufferObject.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

public class Tessellator {

	public static final Tessellator instance;
	private static boolean triangulate, c;

	private ByteBuffer d;
	private IntBuffer e;
	private FloatBuffer f;
	private int[] g;
	private int h, color, o, p, mode, C;
	private boolean hasColor, hasTexture, n, noColor;
	private double u, v, s, t, D;
	private boolean drawing;
	private boolean isARBCapable;
	private int z, A, B;
	private IntBuffer y;

	private Tessellator(final int integer) {
		this.h = 0;
		this.hasColor = false;
		this.hasTexture = false;
		this.n = false;
		this.o = 0;
		this.p = 0;
		this.noColor = false;
		this.drawing = false;
		this.isARBCapable = false;
		this.z = 0;
		this.A = 10;
		this.B = integer;
		this.d = BufferUtils.createByteBuffer(integer * 4);
		this.e = this.d.asIntBuffer();
		this.f = this.d.asFloatBuffer();
		this.g = new int[integer];
		this.isARBCapable = (Tessellator.c && GL.getCapabilities().GL_ARB_vertex_buffer_object);
		if (this.isARBCapable) {
			glGenBuffersARB(this.y = BufferUtils.createIntBuffer(this.A));
		}
	}

	public void draw() {
		if (!this.drawing)
			throw new IllegalStateException("Not tesselating!");

		int error = glGetError();
		this.drawing = false;
		if (this.h > 0) {
			this.e.clear();
			this.e.put(this.g, 0, this.o);
			this.d.position(0);
			this.d.limit(this.o * 4);
			error = glGetError();
			if (this.isARBCapable) {
				this.z = (this.z + 1) % this.A;
				glBindBufferARB(34962, this.y.get(this.z));
				error = glGetError();
				glBufferDataARB(34962, this.d, GL_STREAM_DRAW_ARB);
				error = glGetError();
			}
			if (this.hasTexture) {
				if (this.isARBCapable) {
					glTexCoordPointer(2, 5126, 32, 12L);
					error = glGetError();
				} else {
					this.f.position(3);
					glTexCoordPointer(2, 5126, 32, this.f);
					error = glGetError();
				}
				glEnableClientState(32888);
				error = glGetError();
			}
			if (this.hasColor) {
				if (this.isARBCapable) {
					glColorPointer(4, 5121, 32, 20L);
					error = glGetError();
				} else {
					this.d.position(20);
					// TODO: investigate
					glColorPointer(4, GL_UNSIGNED_BYTE, 32, this.d);
					error = glGetError();
				}
				glEnableClientState(32886);
				error = glGetError();
			}
			if (this.n) {
				if (this.isARBCapable) {
					glNormalPointer(5120, 32, 24L);
					error = glGetError();
				} else {
					this.d.position(24);
					glNormalPointer(5120, 32, this.d);
					error = glGetError();
				}
				glEnableClientState(32885);
				error = glGetError();
			}
			if (this.isARBCapable) {
				glVertexPointer(3, 5126, 32, 0L);
				error = glGetError();
			} else {
				this.f.position(0);
				glVertexPointer(3, 5126, 32, this.f);
				error = glGetError();
			}
			error = glGetError();
			glEnableClientState(32884);
			error = glGetError();
			if (this.mode == GL_QUADS && triangulate) {
				glDrawArrays(GL_TRIANGLES, 0, this.h);
			} else {
				glDrawArrays(this.mode, 0, this.h);
			}
			glDisableClientState(32884);
			if (this.hasTexture) {
				glDisableClientState(32888);
			}
			if (this.hasColor) {
				glDisableClientState(32886);
			}
			if (this.n) {
				glDisableClientState(32885);
			}
		}
		this.end();
	}

	private void end() {
		this.h = 0;
		this.d.clear();
		this.o = 0;
		this.p = 0;
	}

	public void beginQuads() {
		this.begin(GL_QUADS);
	}

	public void begin(int mode) {
		if (this.drawing)
			throw new IllegalStateException("Already tesselating!");

		this.drawing = true;
		this.end();
		this.mode = mode;
		this.n = false;
		this.hasColor = false;
		this.hasTexture = false;
		this.noColor = false;
	}

	public void uv(double u, double v) {
		this.hasTexture = true;
		this.u = u;
		this.v = v;
	}

	public void color(final float float1, final float float2, final float float3) {
		this.color((int) (float1 * 255.0f), (int) (float2 * 255.0f), (int) (float3 * 255.0f));
	}

	public void color(final float float1, final float float2, final float float3, final float float4) {
		this.color((int) (float1 * 255.0f), (int) (float2 * 255.0f), (int) (float3 * 255.0f), (int) (float4 * 255.0f));
	}

	public void color(final int integer1, final int integer2, final int integer3) {
		this.color(integer1, integer2, integer3, 255);
	}

	public void color(int r, int g, int b, int a) {
		if (noColor)
			return;

		r = clamp(0, 255, r);
		g = clamp(0, 255, g);
		b = clamp(0, 255, b);
		a = clamp(0, 255, a);

		this.hasColor = true;
		this.color = (a << 24 | b << 16 | g << 8 | r);
	}

	public void vertexUV(double x, double y, double z, double u, double v) {
		this.uv(u, v);
		this.vertex(x, y, z);
	}

	public void vertex(final double double1, final double double2, final double double3) {
		++this.p;
		if (this.mode == GL_QUADS && Tessellator.triangulate && this.p % 4 == 0) {
			for ( int i = 0; i < 2; ++i ) {
				final int n = 8 * (3 - i);
				if (this.hasTexture) {
					this.g[this.o + 3] = this.g[this.o - n + 3];
					this.g[this.o + 4] = this.g[this.o - n + 4];
				}
				if (this.hasColor) {
					this.g[this.o + 5] = this.g[this.o - n + 5];
				}
				this.g[this.o + 0] = this.g[this.o - n + 0];
				this.g[this.o + 1] = this.g[this.o - n + 1];
				this.g[this.o + 2] = this.g[this.o - n + 2];
				++this.h;
				this.o += 8;
			}
		}
		if (this.hasTexture) {
			this.g[this.o + 3] = Float.floatToRawIntBits((float) this.u);
			this.g[this.o + 4] = Float.floatToRawIntBits((float) this.v);
		}
		if (this.hasColor) {
			this.g[this.o + 5] = this.color;
		}
		if (this.n) {
			this.g[this.o + 6] = this.C;
		}
		this.g[this.o + 0] = Float.floatToRawIntBits((float) (double1 + this.s));
		this.g[this.o + 1] = Float.floatToRawIntBits((float) (double2 + this.t));
		this.g[this.o + 2] = Float.floatToRawIntBits((float) (double3 + this.D));
		this.o += 8;
		++this.h;
		if (this.h % 4 == 0 && this.o >= this.B - 32) {
			this.draw();
			this.drawing = true;
		}
	}

	public void color(final int integer) {
		this.color(integer >> 16 & 0xFF, integer >> 8 & 0xFF, integer & 0xFF);
	}

	public void c() {
		this.noColor = true;
	}

	public void normal(final float float1, final float float2, final float float3) {
		if (!this.drawing)
			System.out.println("But..");

		this.n = true;
		this.C = ((byte) (float1 * 128.0f) | (byte) (float2 * 127.0f) << 8 | (byte) (float3 * 127.0f) << 16);
	}

	public void setTranslationD(final double double1, final double double2, final double double3) {
		this.s = double1;
		this.t = double2;
		this.D = double3;
	}

	static {
		Tessellator.triangulate = true;
		Tessellator.c = false;
		instance = new Tessellator(0x200000);
	}

}
