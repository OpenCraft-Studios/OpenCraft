
package net.opencraft;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class fi {

    private int a, b, c;
    private float d;
    private float e;
    private float f;
    private final IntBuffer g;
    private boolean h;
    private boolean i;

    public fi() {
        this.g = BufferUtils.createIntBuffer(0x10000);
        this.h = false;
        this.i = false;
    }

    public void a(final int integer1, final int integer2, final int integer3, final double double4, final double double5, final double double6) {
        this.h = true;
        this.g.clear();
        this.a = integer1;
        this.b = integer2;
        this.c = integer3;
        this.d = (float) double4;
        this.e = (float) double5;
        this.f = (float) double6;
    }

    public boolean a(final int integer1, final int integer2, final int integer3) {
        return this.h && integer1 == this.a && integer2 == this.b && integer3 == this.c;
    }

    public void a(final int integer) {
        this.g.put(integer);
        if (this.g.remaining() == 0) {
            this.a();
        }
    }

    public void a() {
        if (!this.h) {
            return;
        }
        if (!this.i) {
            this.g.flip();
            this.i = true;
        }
        if (this.g.remaining() > 0) {
            GL11.glPushMatrix();
            GL11.glTranslatef(this.a - this.d, this.b - this.e, this.c - this.f);
            GL11.glCallLists(this.g);
            GL11.glPopMatrix();
        }
    }

    public void b() {
        this.h = false;
        this.i = false;
    }
}
