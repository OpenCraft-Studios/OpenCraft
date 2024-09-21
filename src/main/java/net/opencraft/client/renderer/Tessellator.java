
package net.opencraft.client.renderer;

import java.nio.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

public class Tessellator {

    private static boolean b;
    private static boolean c;
    private ByteBuffer d;
    private IntBuffer e;
    private FloatBuffer f;
    private int[] g;
    private int h;
    private double i;
    private double j;
    private int k;
    private boolean l;
    private boolean m;
    private boolean n;
    private int o;
    private int p;
    private boolean q;
    private int r;
    private double s;
    private double t;
    private double u;
    private int v;
    public static final Tessellator instance;
    private boolean w;
    private boolean x;
    private IntBuffer y;
    private int z;
    private int A;
    private int B;

    private Tessellator(final int integer) {
        this.h = 0;
        this.l = false;
        this.m = false;
        this.n = false;
        this.o = 0;
        this.p = 0;
        this.q = false;
        this.w = false;
        this.x = false;
        this.z = 0;
        this.A = 10;
        this.B = integer;
        this.d = BufferUtils.createByteBuffer(integer * 4);
        this.e = this.d.asIntBuffer();
        this.f = this.d.asFloatBuffer();
        this.g = new int[integer];
        this.x = (Tessellator.c && GLContext.getCapabilities().GL_ARB_vertex_buffer_object);
        if (this.x) {
            ARBVertexBufferObject.glGenBuffersARB(this.y = BufferUtils.createIntBuffer(this.A));
        }
    }

    public void draw() {
        if (!this.w) {
            throw new IllegalStateException("Not tesselating!");
        }
        this.w = false;
        if (this.h > 0) {
            this.e.clear();
            this.e.put(this.g, 0, this.o);
            this.d.position(0);
            this.d.limit(this.o * 4);
            if (this.x) {
                this.z = (this.z + 1) % this.A;
                ARBVertexBufferObject.glBindBufferARB(34962, this.y.get(this.z));
                ARBVertexBufferObject.glBufferDataARB(34962, this.d, 35040);
            }
            if (this.m) {
                if (this.x) {
                    GL11.glTexCoordPointer(2, 5126, 32, 12L);
                } else {
                    this.f.position(3);
                    GL11.glTexCoordPointer(2, 32, this.f);
                }
                GL11.glEnableClientState(32888);
            }
            if (this.l) {
                if (this.x) {
                    GL11.glColorPointer(4, 5121, 32, 20L);
                } else {
                    this.d.position(20);
                    GL11.glColorPointer(4, true, 32, this.d);
                }
                GL11.glEnableClientState(32886);
            }
            if (this.n) {
                if (this.x) {
                    GL11.glNormalPointer(5120, 32, 24L);
                } else {
                    this.d.position(24);
                    GL11.glNormalPointer(32, this.d);
                }
                GL11.glEnableClientState(32885);
            }
            if (this.x) {
                GL11.glVertexPointer(3, 5126, 32, 0L);
            } else {
                this.f.position(0);
                GL11.glVertexPointer(3, 32, this.f);
            }
            GL11.glEnableClientState(32884);
            if (this.r == 7 && Tessellator.b) {
                GL11.glDrawArrays(4, 0, this.h);
            } else {
                GL11.glDrawArrays(this.r, 0, this.h);
            }
            GL11.glDisableClientState(32884);
            if (this.m) {
                GL11.glDisableClientState(32888);
            }
            if (this.l) {
                GL11.glDisableClientState(32886);
            }
            if (this.n) {
                GL11.glDisableClientState(32885);
            }
        }
        this.d();
    }

    private void d() {
        this.h = 0;
        this.d.clear();
        this.o = 0;
        this.p = 0;
    }

    public void startDrawingQuads() {
        this.startDrawing(7);
    }

    public void startDrawing(final int integer) {
        if (this.w) {
            throw new IllegalStateException("Already tesselating!");
        }
        this.w = true;
        this.d();
        this.r = integer;
        this.n = false;
        this.l = false;
        this.m = false;
        this.q = false;
    }

    public void a(final double double1, final double double2) {
        this.m = true;
        this.i = double1;
        this.j = double2;
    }

    public void setColorOpaque_F(final float float1, final float float2, final float float3) {
        this.a((int) (float1 * 255.0f), (int) (float2 * 255.0f), (int) (float3 * 255.0f));
    }

    public void setColorRGBA_F(final float float1, final float float2, final float float3, final float float4) {
        this.a((int) (float1 * 255.0f), (int) (float2 * 255.0f), (int) (float3 * 255.0f), (int) (float4 * 255.0f));
    }

    public void a(final int integer1, final int integer2, final int integer3) {
        this.a(integer1, integer2, integer3, 255);
    }

    public void a(int integer1, int integer2, int integer3, int integer4) {
        if (this.q) {
            return;
        }
        if (integer1 > 255) {
            integer1 = 255;
        }
        if (integer2 > 255) {
            integer2 = 255;
        }
        if (integer3 > 255) {
            integer3 = 255;
        }
        if (integer4 > 255) {
            integer4 = 255;
        }
        if (integer1 < 0) {
            integer1 = 0;
        }
        if (integer2 < 0) {
            integer2 = 0;
        }
        if (integer3 < 0) {
            integer3 = 0;
        }
        if (integer4 < 0) {
            integer4 = 0;
        }
        this.l = true;
        this.k = (integer4 << 24 | integer3 << 16 | integer2 << 8 | integer1);
    }

    public void addVertexWithUV(final double double1, final double double2, final double double3, final double double4, final double double5) {
        this.a(double4, double5);
        this.addVertex(double1, double2, double3);
    }

    public void addVertex(final double double1, final double double2, final double double3) {
        ++this.p;
        if (this.r == 7 && Tessellator.b && this.p % 4 == 0) {
            for (int i = 0; i < 2; ++i) {
                final int n = 8 * (3 - i);
                if (this.m) {
                    this.g[this.o + 3] = this.g[this.o - n + 3];
                    this.g[this.o + 4] = this.g[this.o - n + 4];
                }
                if (this.l) {
                    this.g[this.o + 5] = this.g[this.o - n + 5];
                }
                this.g[this.o + 0] = this.g[this.o - n + 0];
                this.g[this.o + 1] = this.g[this.o - n + 1];
                this.g[this.o + 2] = this.g[this.o - n + 2];
                ++this.h;
                this.o += 8;
            }
        }
        if (this.m) {
            this.g[this.o + 3] = Float.floatToRawIntBits((float) this.i);
            this.g[this.o + 4] = Float.floatToRawIntBits((float) this.j);
        }
        if (this.l) {
            this.g[this.o + 5] = this.k;
        }
        if (this.n) {
            this.g[this.o + 6] = this.v;
        }
        this.g[this.o + 0] = Float.floatToRawIntBits((float) (double1 + this.s));
        this.g[this.o + 1] = Float.floatToRawIntBits((float) (double2 + this.t));
        this.g[this.o + 2] = Float.floatToRawIntBits((float) (double3 + this.u));
        this.o += 8;
        ++this.h;
        if (this.h % 4 == 0 && this.o >= this.B - 32) {
            this.draw();
            this.w = true;
        }
    }

    public void setColorOpaque_I(final int integer) {
        this.a(integer >> 16 & 0xFF, integer >> 8 & 0xFF, integer & 0xFF);
    }

    public void c() {
        this.q = true;
    }

    public void setNormal(final float float1, final float float2, final float float3) {
        if (!this.w) {
            System.out.println("But..");
        }
        this.n = true;
        this.v = ((byte) (float1 * 128.0f) | (byte) (float2 * 127.0f) << 8 | (byte) (float3 * 127.0f) << 16);
    }

    public void setTranslationD(final double double1, final double double2, final double double3) {
        this.s = double1;
        this.t = double2;
        this.u = double3;
    }

    static {
        Tessellator.b = true;
        Tessellator.c = false;
        instance = new Tessellator(2097152);
    }
}
