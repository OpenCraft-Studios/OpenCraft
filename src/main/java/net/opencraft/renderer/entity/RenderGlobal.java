
package net.opencraft.renderer.entity;

import java.nio.IntBuffer;
import java.util.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;

import net.opencraft.OpenCraft;
import net.opencraft.fi;
import net.opencraft.blocks.Block;
import net.opencraft.client.input.MovingObjectPosition;
import net.opencraft.entity.*;
import net.opencraft.item.ItemStack;
import net.opencraft.physics.AABB;
import net.opencraft.renderer.GLAllocation;
import net.opencraft.renderer.Tessellator;
import net.opencraft.renderer.culling.ICamera;
import net.opencraft.renderer.texture.Texture;
import net.opencraft.tileentity.TileEntity;
import net.opencraft.tileentity.TileEntityRenderer;
import net.opencraft.util.Mth;
import net.opencraft.util.Vec3;
import net.opencraft.world.*;

public class RenderGlobal implements IWorldAccess {

	public List a;
	private World k;
	private Renderer l;
	private List m;
	private WorldRenderer[] n;
	private WorldRenderer[] o;
	private int p;
	private int q;
	private int r;
	private int s;
	private OpenCraft t;
	private RenderBlocks u;
	private IntBuffer v;
	private boolean w;
	private int x;
	private int y;
	private int z;
	private int A;
	private int B;
	private int C;
	private int D;
	private int E;
	private int F;
	private int G;
	private int H;
	private int I;
	private int J;
	private int K;
	int[] b;
	IntBuffer c;
	private int L;
	private int M;
	private int N;
	private int O;
	private int P;
	private List Q;
	private fi[] R;
	int d;
	int e;
	double f;
	double g;
	double h;
	public float damagePartialTime;
	int j;

	public RenderGlobal(final OpenCraft aw, final Renderer id) {
		this.a = (List) new ArrayList();
		this.m = (List) new ArrayList();
		this.w = false;
		this.x = 0;
		this.H = -1;
		this.b = new int[50000];
		this.c = BufferUtils.createIntBuffer(64);
		this.Q = (List) new ArrayList();
		this.R = new fi[] { new fi(), new fi(), new fi(), new fi() };
		this.d = 0;
		this.e = GLAllocation.generateDisplayLists(1);
		this.f = -9999.0;
		this.g = -9999.0;
		this.h = -9999.0;
		this.j = 0;
		this.t = aw;
		this.l = id;
		final int n = 64;
		this.s = GLAllocation.generateDisplayLists(n * n * n * 3);
		this.w = aw.getOpenGlCapsChecker().checkARBOcclusion();
		if (this.w) {
			this.c.clear();
			(this.v = BufferUtils.createIntBuffer(n * n * n)).clear();
			this.v.position(0);
			this.v.limit(n * n * n);
			ARBOcclusionQuery.glGenQueriesARB(this.v);
		}
		this.y = GLAllocation.generateDisplayLists(3);
		GL11.glPushMatrix();
		GL11.glNewList(this.y, 4864);
		this.f();
		GL11.glEndList();
		GL11.glPopMatrix();
		final Tessellator instance = Tessellator.instance;
		GL11.glNewList(this.z = this.y + 1, 4864);
		final int n2 = 64;
		final int n3 = 256 / n2 + 2;
		float n4 = 16.0f;
		for (int i = -n2 * n3; i <= n2 * n3; i += n2) {
			for (int j = -n2 * n3; j <= n2 * n3; j += n2) {
				instance.beginQuads();
				instance.vertex(i + 0, n4, j + 0);
				instance.vertex(i + n2, n4, j + 0);
				instance.vertex(i + n2, n4, j + n2);
				instance.vertex(i + 0, n4, j + n2);
				instance.draw();
			}
		}
		GL11.glEndList();
		GL11.glNewList(this.A = this.y + 2, 4864);
		n4 = -16.0f;
		instance.beginQuads();
		for (int i = -n2 * n3; i <= n2 * n3; i += n2) {
			for (int j = -n2 * n3; j <= n2 * n3; j += n2) {
				instance.vertex(i + n2, n4, j + 0);
				instance.vertex(i + 0, n4, j + 0);
				instance.vertex(i + 0, n4, j + n2);
				instance.vertex(i + n2, n4, j + n2);
			}
		}
		instance.draw();
		GL11.glEndList();
	}

	private void f() {
		final Random random = new Random(10842L);
		final Tessellator instance = Tessellator.instance;
		instance.beginQuads();
		for (int i = 0; i < 1500; ++i) {
			double n = random.nextFloat() * 2.0f - 1.0f;
			double n2 = random.nextFloat() * 2.0f - 1.0f;
			double n3 = random.nextFloat() * 2.0f - 1.0f;
			final double n4 = 0.25f + random.nextFloat() * 0.25f;
			double n5 = n * n + n2 * n2 + n3 * n3;
			if (n5 < 1.0 && n5 > 0.01) {
				n5 = 1.0 / Math.sqrt(n5);
				n *= n5;
				n2 *= n5;
				n3 *= n5;
				final double n6 = n * 100.0;
				final double n7 = n2 * 100.0;
				final double n8 = n3 * 100.0;
				final double atan2 = Math.atan2(n, n3);
				final double sin = Math.sin(atan2);
				final double cos = Math.cos(atan2);
				final double atan3 = Math.atan2(Math.sqrt(n * n + n3 * n3), n2);
				final double sin2 = Math.sin(atan3);
				final double cos2 = Math.cos(atan3);
				final double n9 = random.nextDouble() * 3.141592653589793 * 2.0;
				final double sin3 = Math.sin(n9);
				final double cos3 = Math.cos(n9);
				for (int j = 0; j < 4; ++j) {
					final double n10 = 0.0;
					final double n11 = ((j & 0x2) - 1) * n4;
					final double n12 = ((j + 1 & 0x2) - 1) * n4;
					final double n13 = n10;
					final double n14 = n11 * cos3 - n12 * sin3;
					final double n15 = n12 * cos3 + n11 * sin3;
					final double n16 = n14 * sin2 + n13 * cos2;
					final double n17 = n13 * sin2 - n14 * cos2;
					instance.vertex(n6 + (n17 * sin - n15 * cos), n7 + n16, n8 + (n15 * sin + n17 * cos));
				}
			}
		}
		instance.draw();
	}

	public void changeWorld(final World fe) {
		if (this.k != null) {
			this.k.removeWorldAccess(this);
		}
		this.f = -9999.0;
		this.g = -9999.0;
		this.h = -9999.0;
		RenderManager.instance.func_852_a(fe);
		this.k = fe;
		this.u = new RenderBlocks(fe);
		if (fe != null) {
			fe.addWorldAccess(this);
			this.fancyGraphics();
		}
	}

	public void fancyGraphics() {
		Block.leaves.setGraphicsLevel(this.t.options.fancyGraphics);
		this.H = this.t.options.renderDistance;
		if (this.o != null) {
			for (int i = 0; i < this.o.length; ++i) {
				this.o[i].c();
			}
		}
		int i = 64 << 3 - this.H;
		if (i > 400) {
			i = 400;
		}
		this.p = i / 16 + 1;
		this.q = 8;
		this.r = i / 16 + 1;
		this.o = new WorldRenderer[this.p * this.q * this.r];
		this.n = new WorldRenderer[this.p * this.q * this.r];
		int n = 0;
		int n2 = 0;
		this.B = 0;
		this.C = 0;
		this.D = 0;
		this.E = this.p;
		this.F = this.q;
		this.G = this.r;
		for (int j = 0; j < this.m.size(); ++j) {
			((WorldRenderer) this.m.get(j)).u = false;
		}
		this.m.clear();
		for (int j = 0; j < this.p; ++j) {
			for (int k = 0; k < this.q; ++k) {
				for (int l = 0; l < this.r; ++l) {
					if (this.o[(l * this.q + k) * this.p + j] != null) {
						this.a.removeAll((Collection) this.o[(l * this.q + k) * this.p + j].B);
					}
					this.o[(l * this.q + k) * this.p + j] = new WorldRenderer(this.k, this.a, j * 16, k * 16, l * 16,
							16, this.s + n);
					if (this.w) {
						this.o[(l * this.q + k) * this.p + j].z = this.v.get(n2);
					}
					this.o[(l * this.q + k) * this.p + j].y = false;
					this.o[(l * this.q + k) * this.p + j].x = true;
					this.o[(l * this.q + k) * this.p + j].isInFrustum = true;
					this.o[(l * this.q + k) * this.p + j].w = n2++;
					this.o[(l * this.q + k) * this.p + j].f();
					this.n[(l * this.q + k) * this.p + j] = this.o[(l * this.q + k) * this.p + j];
					this.m.add(this.o[(l * this.q + k) * this.p + j]);
					n += 3;
				}
			}
		}
		if (this.k != null) {
			final Entity player = this.k.player;
			this.b(Mth.floor_double(player.posX), Mth.floor_double(player.posY), Mth.floor_double(player.posZ));
			Arrays.sort((Object[]) this.n, (Comparator) new EntitySorter(player));
		}
	}

	public void renderEntities(final Vec3 bo, final ICamera jt, final float float3) {
		TileEntityRenderer.instance.a(this.k, this.l, this.t.fontRenderer, this.t.player, float3);
		RenderManager.instance.cacheActiveRenderInfo(this.k, this.l, this.t.fontRenderer, this.t.player,
				this.t.options, float3);
		this.I = 0;
		this.J = 0;
		this.K = 0;
		final Entity player = this.k.player;
		RenderManager.renderPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * float3;
		RenderManager.renderPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * float3;
		RenderManager.renderPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * float3;
		TileEntityRenderer.b = player.lastTickPosX + (player.posX - player.lastTickPosX) * float3;
		TileEntityRenderer.c = player.lastTickPosY + (player.posY - player.lastTickPosY) * float3;
		TileEntityRenderer.d = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * float3;
		final List loadedEntityList = this.k.getLoadedEntityList();
		this.I = loadedEntityList.size();
		for (int i = 0; i < loadedEntityList.size(); ++i) {
			final Entity eq = (Entity) loadedEntityList.get(i);
			if (eq.isInRangeToRenderVec3D(bo) && jt.isBoundingBoxInFrustum(eq.boundingBox)) {
				if (eq != this.k.player || this.t.options.thirdPersonView) {
					++this.J;
					RenderManager.instance.renderEntity(eq, float3);
				}
			}
		}
		for (int i = 0; i < this.a.size(); ++i) {
			TileEntityRenderer.instance.a((TileEntity) this.a.get(i), float3);
		}
	}

	public String getDebugInfoRenders() {
		return new StringBuilder().append("C: ").append(this.O).append("/").append(this.L).append(". F: ")
				.append(this.M).append(", O: ").append(this.N).append(", E: ").append(this.P).toString();
	}

	public String getDebugInfoEntities() {
		return new StringBuilder().append("E: ").append(this.J).append("/").append(this.I).append(". B: ")
				.append(this.K).append(", I: ").append(this.I - this.K - this.J).toString();
	}

	private void b(int integer1, int integer2, int integer3) {
		integer1 -= 8;
		integer2 -= 8;
		integer3 -= 8;
		this.B = Integer.MAX_VALUE;
		this.C = Integer.MAX_VALUE;
		this.D = Integer.MAX_VALUE;
		this.E = Integer.MIN_VALUE;
		this.F = Integer.MIN_VALUE;
		this.G = Integer.MIN_VALUE;
		final int n = this.p * 16;
		final int n2 = n / 2;
		for (int i = 0; i < this.p; ++i) {
			int integer4 = i * 16;
			int n3 = integer4 + n2 - integer1;
			if (n3 < 0) {
				n3 -= n - 1;
			}
			n3 /= n;
			integer4 -= n3 * n;
			if (integer4 < this.B) {
				this.B = integer4;
			}
			if (integer4 > this.E) {
				this.E = integer4;
			}
			for (int j = 0; j < this.r; ++j) {
				int integer5 = j * 16;
				int n4 = integer5 + n2 - integer3;
				if (n4 < 0) {
					n4 -= n - 1;
				}
				n4 /= n;
				integer5 -= n4 * n;
				if (integer5 < this.D) {
					this.D = integer5;
				}
				if (integer5 > this.G) {
					this.G = integer5;
				}
				for (int k = 0; k < this.q; ++k) {
					final int integer6 = k * 16;
					if (integer6 < this.C) {
						this.C = integer6;
					}
					if (integer6 > this.F) {
						this.F = integer6;
					}
					final WorldRenderer worldRenderer = this.o[(j * this.q + k) * this.p + i];
					final boolean u = worldRenderer.u;
					worldRenderer.a(integer4, integer6, integer5);
					if (!u && worldRenderer.u) {
						this.m.add(worldRenderer);
					}
				}
			}
		}
	}

	public int sortAndRender(final EntityPlayer gi, final int integer, final double double3) {
		if (this.t.options.renderDistance != this.H) {
			this.fancyGraphics();
		}
		if (integer == 0) {
			this.L = 0;
			this.M = 0;
			this.N = 0;
			this.O = 0;
			this.P = 0;
		}
		final double n = gi.lastTickPosX + (gi.posX - gi.lastTickPosX) * double3;
		final double n2 = gi.lastTickPosY + (gi.posY - gi.lastTickPosY) * double3;
		final double n3 = gi.lastTickPosZ + (gi.posZ - gi.lastTickPosZ) * double3;
		final double n4 = gi.posX - this.f;
		final double n5 = gi.posY - this.g;
		final double n6 = gi.posZ - this.h;
		if (n4 * n4 + n5 * n5 + n6 * n6 > 16.0) {
			this.f = gi.posX;
			this.g = gi.posY;
			this.h = gi.posZ;
			this.b(Mth.floor_double(gi.posX), Mth.floor_double(gi.posY), Mth.floor_double(gi.posZ));
			Arrays.sort((Object[]) this.n, (Comparator) new EntitySorter(gi));
		}
		final int n7 = 0;
		int n9;
		if (this.w && !this.t.options.anaglyph && integer == 0) {
			final int n8 = 0;
			int i = 16;
			this.a(n8, i);
			for (int j = n8; j < i; ++j) {
				this.n[j].x = true;
			}
			n9 = n7 + this.a(n8, i, integer, double3);
			do {
				final int n10 = i;
				i *= 2;
				if (i > this.n.length) {
					i = this.n.length;
				}
				GL11.glDisable(3553);
				GL11.glDisable(2896);
				GL11.glDisable(3008);
				GL11.glDisable(2912);
				GL11.glColorMask(false, false, false, false);
				GL11.glDepthMask(false);
				this.a(n10, i);
				GL11.glPushMatrix();
				float n11 = 0.0f;
				float n12 = 0.0f;
				float n13 = 0.0f;
				for (int k = n10; k < i; ++k) {
					if (this.n[k].e()) {
						this.n[k].isInFrustum = false;
					} else {
						if (!this.n[k].isInFrustum) {
							this.n[k].x = true;
						}
						if (this.n[k].isInFrustum && !this.n[k].y) {
							final int n14 = (int) (1.0f + Mth.sqrt_float(this.n[k].chunkIndex(gi)) / 128.0f);
							if (this.x % n14 == k % n14) {
								final WorldRenderer worldRenderer = this.n[k];
								final float n15 = (float) (worldRenderer.i - n);
								final float n16 = (float) (worldRenderer.j - n2);
								final float n17 = (float) (worldRenderer.k - n3);
								final float n18 = n15 - n11;
								final float n19 = n16 - n12;
								final float n20 = n17 - n13;
								if (n18 != 0.0f || n19 != 0.0f || n20 != 0.0f) {
									GL11.glTranslatef(n18, n19, n20);
									n11 += n18;
									n12 += n19;
									n13 += n20;
								}
								ARBOcclusionQuery.glBeginQueryARB(35092, this.n[k].z);
								this.n[k].d();
								ARBOcclusionQuery.glEndQueryARB(35092);
								this.n[k].y = true;
							}
						}
					}
				}
				GL11.glPopMatrix();
				GL11.glColorMask(true, true, true, true);
				GL11.glDepthMask(true);
				GL11.glEnable(3553);
				GL11.glEnable(3008);
				GL11.glEnable(2912);
				n9 += this.a(n10, i, integer, double3);
			} while (i < this.n.length);
		} else {
			n9 = n7 + this.a(0, this.n.length, integer, double3);
		}
		return n9;
	}

	private void a(final int integer1, final int integer2) {
		for (int i = integer1; i < integer2; ++i) {
			if (this.n[i].y) {
				this.c.clear();
				ARBOcclusionQuery.glGetQueryObjectuARB(this.n[i].z, 34919, this.c);
				if (this.c.get(0) != 0) {
					this.n[i].y = false;
					this.c.clear();
					ARBOcclusionQuery.glGetQueryObjectuARB(this.n[i].z, 34918, this.c);
					this.n[i].x = (this.c.get(0) != 0);
				}
			}
		}
	}

	private int a(final int integer1, final int integer2, final int integer3, final double double4) {
		this.Q.clear();
		int n = 0;
		for (int i = integer1; i < integer2; ++i) {
			if (integer3 == 0) {
				++this.L;
				if (this.n[i].p[integer3]) {
					++this.P;
				} else if (!this.n[i].isInFrustum) {
					++this.M;
				} else if (this.w && !this.n[i].x) {
					++this.N;
				} else {
					++this.O;
				}
			}
			if (!this.n[i].p[integer3] && this.n[i].isInFrustum && this.n[i].x && this.n[i].a(integer3) >= 0) {
				this.Q.add(this.n[i]);
				++n;
			}
		}
		final EntityPlayerSP thePlayer = this.t.player;
		final double double5 = thePlayer.lastTickPosX + (thePlayer.posX - thePlayer.lastTickPosX) * double4;
		final double double6 = thePlayer.lastTickPosY + (thePlayer.posY - thePlayer.lastTickPosY) * double4;
		final double double7 = thePlayer.lastTickPosZ + (thePlayer.posZ - thePlayer.lastTickPosZ) * double4;
		int n2 = 0;
		for (int j = 0; j < this.R.length; ++j) {
			this.R[j].b();
		}
		for (int j = 0; j < this.Q.size(); ++j) {
			final WorldRenderer worldRenderer = (WorldRenderer) this.Q.get(j);
			int n3 = -1;
			for (int k = 0; k < n2; ++k) {
				if (this.R[k].a(worldRenderer.i, worldRenderer.j, worldRenderer.k)) {
					n3 = k;
				}
			}
			if (n3 < 0) {
				n3 = n2++;
				this.R[n3].a(worldRenderer.i, worldRenderer.j, worldRenderer.k, double5, double6, double7);
			}
			this.R[n3].a(worldRenderer.a(integer3));
		}
		this.renderAllRenderLists(integer3, double4);
		return n;
	}

	public void renderAllRenderLists(final int integer, final double double2) {
		for (int i = 0; i < this.R.length; ++i) {
			this.R[i].a();
		}
	}

	public void updateClouds() {
		++this.x;
	}

	public void renderSky(final float float1) {
		GL11.glDisable(3553);
		final Vec3 skyColor = this.k.getSkyColor(float1);
		float n = (float) skyColor.x;
		float n2 = (float) skyColor.y;
		float n3 = (float) skyColor.z;
		if (this.t.options.anaglyph) {
			final float n4 = (n * 30.0f + n2 * 59.0f + n3 * 11.0f) / 100.0f;
			final float n5 = (n * 30.0f + n2 * 70.0f) / 100.0f;
			final float n6 = (n * 30.0f + n3 * 70.0f) / 100.0f;
			n = n4;
			n2 = n5;
			n3 = n6;
		}
		GL11.glColor3f(n, n2, n3);
		final Tessellator instance = Tessellator.instance;
		GL11.glDepthMask(false);
		GL11.glEnable(2912);
		GL11.glColor3f(n, n2, n3);
		GL11.glCallList(this.z);
		GL11.glEnable(3553);
		GL11.glDisable(2912);
		GL11.glDisable(3008);
		GL11.glEnable(3042);
		GL11.glBlendFunc(1, 1);
		GL11.glPushMatrix();
		final float n5 = 0.0f;
		final float n6 = 0.0f;
		final float n7 = 0.0f;
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glTranslatef(n5, n6, n7);
		GL11.glRotatef(0.0f, 0.0f, 0.0f, 1.0f);
		GL11.glRotatef(this.k.getCelestialAngle(float1) * 360.0f, 1.0f, 0.0f, 0.0f);
		float n8 = 30.0f;
		GL11.glBindTexture(3553, this.l.getTexture("/assets/terrain/sun.png"));
		instance.beginQuads();
		instance.vertexUV(-n8, 100.0, -n8, 0.0, 0.0);
		instance.vertexUV(n8, 100.0, -n8, 1.0, 0.0);
		instance.vertexUV(n8, 100.0, n8, 1.0, 1.0);
		instance.vertexUV(-n8, 100.0, n8, 0.0, 1.0);
		instance.draw();
		n8 = 20.0f;
		GL11.glBindTexture(3553, this.l.getTexture("/assets/terrain/moon.png"));
		instance.beginQuads();
		instance.vertexUV(-n8, -100.0, n8, 1.0, 1.0);
		instance.vertexUV(n8, -100.0, n8, 0.0, 1.0);
		instance.vertexUV(n8, -100.0, -n8, 0.0, 0.0);
		instance.vertexUV(-n8, -100.0, -n8, 1.0, 0.0);
		instance.draw();
		GL11.glDisable(3553);
		final float starBrightness = this.k.getStarBrightness(float1);
		if (starBrightness > 0.0f) {
			GL11.glColor4f(starBrightness, starBrightness, starBrightness, starBrightness);
			GL11.glCallList(this.y);
		}
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(3042);
		GL11.glEnable(3008);
		GL11.glEnable(2912);
		GL11.glPopMatrix();
		GL11.glColor3f(n * 0.2f + 0.04f, n2 * 0.2f + 0.04f, n3 * 0.6f + 0.1f);
		GL11.glDisable(3553);
		GL11.glCallList(this.A);
		GL11.glEnable(3553);
		GL11.glDepthMask(true);
	}

	public void renderClouds(final float float1) {
		if (this.t.options.fancyGraphics) {
			this.c(float1);
			return;
		}
		GL11.glDisable(2884);
		final float n = (float) (this.t.player.lastTickPosY
				+ (this.t.player.posY - this.t.player.lastTickPosY) * float1);
		final int n2 = 32;
		final int n3 = 256 / n2;
		final Tessellator instance = Tessellator.instance;
		GL11.glBindTexture(3553, this.l.getTexture("/assets/clouds.png"));
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		final Vec3 drawClouds = this.k.drawClouds(float1);
		float float2 = (float) drawClouds.x;
		float float3 = (float) drawClouds.y;
		float float4 = (float) drawClouds.z;
		if (this.t.options.anaglyph) {
			final float n4 = (float2 * 30.0f + float3 * 59.0f + float4 * 11.0f) / 100.0f;
			final float n5 = (float2 * 30.0f + float3 * 70.0f) / 100.0f;
			final float n6 = (float2 * 30.0f + float4 * 70.0f) / 100.0f;
			float2 = n4;
			float3 = n5;
			float4 = n6;
		}
		final float n4 = 4.8828125E-4f;
		double n7 = this.k.player.prevPosX + (this.k.player.posX - this.k.player.prevPosX) * float1
				+ (this.x + float1) * 0.03f;
		double n8 = this.k.player.prevPosZ + (this.k.player.posZ - this.k.player.prevPosZ) * float1;
		final int floor_double = Mth.floor_double(n7 / 2048.0);
		final int floor_double2 = Mth.floor_double(n8 / 2048.0);
		n7 -= floor_double * 2048;
		n8 -= floor_double2 * 2048;
		final float n9 = 120.0f - n + 0.33f;
		final float n10 = (float) (n7 * n4);
		final float n11 = (float) (n8 * n4);
		instance.beginQuads();
		instance.setColorRGBA_F(float2, float3, float4, 0.8f);
		for (int i = -n2 * n3; i < n2 * n3; i += n2) {
			for (int j = -n2 * n3; j < n2 * n3; j += n2) {
				instance.vertexUV(i + 0, n9, j + n2, (i + 0) * n4 + n10, (j + n2) * n4 + n11);
				instance.vertexUV(i + n2, n9, j + n2, (i + n2) * n4 + n10, (j + n2) * n4 + n11);
				instance.vertexUV(i + n2, n9, j + 0, (i + n2) * n4 + n10, (j + 0) * n4 + n11);
				instance.vertexUV(i + 0, n9, j + 0, (i + 0) * n4 + n10, (j + 0) * n4 + n11);
			}
		}
		instance.draw();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(3042);
		GL11.glEnable(2884);
	}

	public void c(final float float1) {
		GL11.glDisable(2884);
		final float n = (float) (this.t.player.lastTickPosY
				+ (this.t.player.posY - this.t.player.lastTickPosY) * float1);
		final Tessellator instance = Tessellator.instance;
		final float n2 = 12.0f;
		final float n3 = 4.0f;
		double n4 = (this.k.player.prevPosX + (this.k.player.posX - this.k.player.prevPosX) * float1
				+ (this.x + float1) * 0.03f) / n2;
		double n5 = (this.k.player.prevPosZ + (this.k.player.posZ - this.k.player.prevPosZ) * float1) / n2
				+ 0.33000001311302185;
		final float n6 = 108.0f - n + 0.33f;
		final int floor_double = Mth.floor_double(n4 / 2048.0);
		final int floor_double2 = Mth.floor_double(n5 / 2048.0);
		n4 -= floor_double * 2048;
		n5 -= floor_double2 * 2048;
		GL11.glBindTexture(3553, this.l.getTexture("/assets/clouds.png"));
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		final Vec3 drawClouds = this.k.drawClouds(float1);
		float float2 = (float) drawClouds.x;
		float float3 = (float) drawClouds.y;
		float float4 = (float) drawClouds.z;
		if (this.t.options.anaglyph) {
			final float n7 = (float2 * 30.0f + float3 * 59.0f + float4 * 11.0f) / 100.0f;
			final float n8 = (float2 * 30.0f + float3 * 70.0f) / 100.0f;
			final float n9 = (float2 * 30.0f + float4 * 70.0f) / 100.0f;
			float2 = n7;
			float3 = n8;
			float4 = n9;
		}
		float n7 = (float) (n4 * 0.0);
		float n8 = (float) (n5 * 0.0);
		final float n9 = 0.00390625f;
		n7 = Mth.floor_double(n4) * n9;
		n8 = Mth.floor_double(n5) * n9;
		final float n10 = (float) (n4 - Mth.floor_double(n4));
		final float n11 = (float) (n5 - Mth.floor_double(n5));
		final int n12 = 8;
		final int n13 = 3;
		final float n14 = 9.765625E-4f;
		GL11.glScalef(n2, 1.0f, n2);
		for (int i = 0; i < 2; ++i) {
			if (i == 0) {
				GL11.glColorMask(false, false, false, false);
			} else {
				GL11.glColorMask(true, true, true, true);
			}
			for (int j = -n13 + 1; j <= n13; ++j) {
				for (int k = -n13 + 1; k <= n13; ++k) {
					instance.beginQuads();
					final float n15 = (float) (j * n12);
					final float n16 = (float) (k * n12);
					final float n17 = n15 - n10;
					final float n18 = n16 - n11;
					if (n6 > -n3 - 1.0f) {
						instance.setColorRGBA_F(float2 * 0.7f, float3 * 0.7f, float4 * 0.7f, 0.8f);
						instance.setNormal(0.0f, -1.0f, 0.0f);
						instance.vertexUV(n17 + 0.0f, n6 + 0.0f, n18 + n12, (n15 + 0.0f) * n9 + n7,
								(n16 + n12) * n9 + n8);
						instance.vertexUV(n17 + n12, n6 + 0.0f, n18 + n12, (n15 + n12) * n9 + n7,
								(n16 + n12) * n9 + n8);
						instance.vertexUV(n17 + n12, n6 + 0.0f, n18 + 0.0f, (n15 + n12) * n9 + n7,
								(n16 + 0.0f) * n9 + n8);
						instance.vertexUV(n17 + 0.0f, n6 + 0.0f, n18 + 0.0f, (n15 + 0.0f) * n9 + n7,
								(n16 + 0.0f) * n9 + n8);
					}
					if (n6 <= n3 + 1.0f) {
						instance.setColorRGBA_F(float2, float3, float4, 0.8f);
						instance.setNormal(0.0f, 1.0f, 0.0f);
						instance.vertexUV(n17 + 0.0f, n6 + n3 - n14, n18 + n12, (n15 + 0.0f) * n9 + n7,
								(n16 + n12) * n9 + n8);
						instance.vertexUV(n17 + n12, n6 + n3 - n14, n18 + n12, (n15 + n12) * n9 + n7,
								(n16 + n12) * n9 + n8);
						instance.vertexUV(n17 + n12, n6 + n3 - n14, n18 + 0.0f, (n15 + n12) * n9 + n7,
								(n16 + 0.0f) * n9 + n8);
						instance.vertexUV(n17 + 0.0f, n6 + n3 - n14, n18 + 0.0f, (n15 + 0.0f) * n9 + n7,
								(n16 + 0.0f) * n9 + n8);
					}
					instance.setColorRGBA_F(float2 * 0.9f, float3 * 0.9f, float4 * 0.9f, 0.8f);
					if (j > -1) {
						instance.setNormal(-1.0f, 0.0f, 0.0f);
						for (int l = 0; l < n12; ++l) {
							instance.vertexUV(n17 + l + 0.0f, n6 + 0.0f, n18 + n12, (n15 + l + 0.5f) * n9 + n7,
									(n16 + n12) * n9 + n8);
							instance.vertexUV(n17 + l + 0.0f, n6 + n3, n18 + n12, (n15 + l + 0.5f) * n9 + n7,
									(n16 + n12) * n9 + n8);
							instance.vertexUV(n17 + l + 0.0f, n6 + n3, n18 + 0.0f, (n15 + l + 0.5f) * n9 + n7,
									(n16 + 0.0f) * n9 + n8);
							instance.vertexUV(n17 + l + 0.0f, n6 + 0.0f, n18 + 0.0f, (n15 + l + 0.5f) * n9 + n7,
									(n16 + 0.0f) * n9 + n8);
						}
					}
					if (j <= 1) {
						instance.setNormal(1.0f, 0.0f, 0.0f);
						for (int l = 0; l < n12; ++l) {
							instance.vertexUV(n17 + l + 1.0f - n14, n6 + 0.0f, n18 + n12,
									(n15 + l + 0.5f) * n9 + n7, (n16 + n12) * n9 + n8);
							instance.vertexUV(n17 + l + 1.0f - n14, n6 + n3, n18 + n12,
									(n15 + l + 0.5f) * n9 + n7, (n16 + n12) * n9 + n8);
							instance.vertexUV(n17 + l + 1.0f - n14, n6 + n3, n18 + 0.0f,
									(n15 + l + 0.5f) * n9 + n7, (n16 + 0.0f) * n9 + n8);
							instance.vertexUV(n17 + l + 1.0f - n14, n6 + 0.0f, n18 + 0.0f,
									(n15 + l + 0.5f) * n9 + n7, (n16 + 0.0f) * n9 + n8);
						}
					}
					instance.setColorRGBA_F(float2 * 0.8f, float3 * 0.8f, float4 * 0.8f, 0.8f);
					if (k > -1) {
						instance.setNormal(0.0f, 0.0f, -1.0f);
						for (int l = 0; l < n12; ++l) {
							instance.vertexUV(n17 + 0.0f, n6 + n3, n18 + l + 0.0f, (n15 + 0.0f) * n9 + n7,
									(n16 + l + 0.5f) * n9 + n8);
							instance.vertexUV(n17 + n12, n6 + n3, n18 + l + 0.0f, (n15 + n12) * n9 + n7,
									(n16 + l + 0.5f) * n9 + n8);
							instance.vertexUV(n17 + n12, n6 + 0.0f, n18 + l + 0.0f, (n15 + n12) * n9 + n7,
									(n16 + l + 0.5f) * n9 + n8);
							instance.vertexUV(n17 + 0.0f, n6 + 0.0f, n18 + l + 0.0f, (n15 + 0.0f) * n9 + n7,
									(n16 + l + 0.5f) * n9 + n8);
						}
					}
					if (k <= 1) {
						instance.setNormal(0.0f, 0.0f, 1.0f);
						for (int l = 0; l < n12; ++l) {
							instance.vertexUV(n17 + 0.0f, n6 + n3, n18 + l + 1.0f - n14, (n15 + 0.0f) * n9 + n7,
									(n16 + l + 0.5f) * n9 + n8);
							instance.vertexUV(n17 + n12, n6 + n3, n18 + l + 1.0f - n14, (n15 + n12) * n9 + n7,
									(n16 + l + 0.5f) * n9 + n8);
							instance.vertexUV(n17 + n12, n6 + 0.0f, n18 + l + 1.0f - n14, (n15 + n12) * n9 + n7,
									(n16 + l + 0.5f) * n9 + n8);
							instance.vertexUV(n17 + 0.0f, n6 + 0.0f, n18 + l + 1.0f - n14,
									(n15 + 0.0f) * n9 + n7, (n16 + l + 0.5f) * n9 + n8);
						}
					}
					instance.draw();
				}
			}
		}
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(3042);
		GL11.glEnable(2884);
	}

	public boolean updateRenderers(final EntityPlayer gi, final boolean boolean2) {
		Collections.sort(this.m, (Comparator) new RenderSorter(gi));
		final int n = this.m.size() - 1;
		for (int size = this.m.size(), i = 0; i < size; ++i) {
			final WorldRenderer worldRenderer = (WorldRenderer) this.m.get(n - i);
			if (!boolean2) {
				if (worldRenderer.chunkIndex(gi) > 1024.0f) {
					if (worldRenderer.isInFrustum) {
						if (i >= 3) {
							return false;
						}
					} else if (i >= 1) {
						return false;
					}
				}
			} else if (!worldRenderer.isInFrustum) {
				continue;
			}
			worldRenderer.a();
			this.m.remove(worldRenderer);
			worldRenderer.u = false;
		}
		return this.m.size() == 0;
	}

	public void drawBlockBreaking(final EntityPlayer gi, final MovingObjectPosition hb, final int integer,
			final ItemStack hw, final float float5) {
		final Tessellator instance = Tessellator.instance;
		GL11.glEnable(3042);
		GL11.glEnable(3008);
		GL11.glBlendFunc(770, 1);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, (Mth.sin(System.currentTimeMillis() / 100.0f) * 0.2f + 0.4f) * 0.5f);
		if (integer == 0) {
			if (this.damagePartialTime > 0.0f) {
				GL11.glBlendFunc(774, 768);
				GL11.glBindTexture(3553, this.l.getTexture("/assets/terrain.png"));
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
				GL11.glPushMatrix();
				final int n = this.k.getBlockId(hb.blockX, hb.blockY, hb.blockZ);
				Block stone = (n > 0) ? Block.blocksList[n] : null;
				GL11.glDisable(3008);
				GL11.glPolygonOffset(-3.0f, -3.0f);
				GL11.glEnable(32823);
				instance.beginQuads();
				instance.setTranslationD(-(gi.lastTickPosX + (gi.posX - gi.lastTickPosX) * float5),
						-(gi.lastTickPosY + (gi.posY - gi.lastTickPosY) * float5),
						-(gi.lastTickPosZ + (gi.posZ - gi.lastTickPosZ) * float5));
				instance.c();
				if (stone == null) {
					stone = Block.stone;
				}
				this.u.a(stone, hb.blockX, hb.blockY, hb.blockZ, 240 + (int) (this.damagePartialTime * 10.0f));
				instance.draw();
				instance.setTranslationD(0.0, 0.0, 0.0);
				GL11.glPolygonOffset(0.0f, 0.0f);
				GL11.glDisable(32823);
				GL11.glEnable(3008);
				GL11.glDepthMask(true);
				GL11.glPopMatrix();
			}
		} else if (hw != null) {
			GL11.glBlendFunc(770, 771);
			final float n2 = Mth.sin(System.currentTimeMillis() / 100.0f) * 0.2f + 0.8f;
			GL11.glColor4f(n2, n2, n2, Mth.sin(System.currentTimeMillis() / 200.0f) * 0.2f + 0.5f);
			final int n = this.l.getTexture("/assets/terrain.png");
			GL11.glBindTexture(3553, n);
			int blockX = hb.blockX;
			int blockY = hb.blockY;
			int blockZ = hb.blockZ;
			if (hb.sideHit == 0) {
				--blockY;
			}
			if (hb.sideHit == 1) {
				++blockY;
			}
			if (hb.sideHit == 2) {
				--blockZ;
			}
			if (hb.sideHit == 3) {
				++blockZ;
			}
			if (hb.sideHit == 4) {
				--blockX;
			}
			if (hb.sideHit == 5) {
				++blockX;
			}
		}
		GL11.glDisable(3042);
		GL11.glDisable(3008);
	}

	public void drawSelectionBox(final EntityPlayer gi, final MovingObjectPosition hb, final int integer,
			final ItemStack hw, final float float5) {
		if (integer == 0 && hb.typeOfHit == 0) {
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.4f);
			GL11.glLineWidth(2.0f);
			GL11.glDisable(3553);
			GL11.glDepthMask(false);
			final float n = 0.002f;
			final int blockId = this.k.getBlockId(hb.blockX, hb.blockY, hb.blockZ);
			if (blockId > 0) {
				this.a(Block.blocksList[blockId].getSelectedBoundingBoxFromPool(this.k, hb.blockX, hb.blockY, hb.blockZ)
						.expand(n, n, n).getOffsetBoundingBox(-(gi.lastTickPosX + (gi.posX - gi.lastTickPosX) * float5),
								-(gi.lastTickPosY + (gi.posY - gi.lastTickPosY) * float5),
								-(gi.lastTickPosZ + (gi.posZ - gi.lastTickPosZ) * float5)));
			}
			GL11.glDepthMask(true);
			GL11.glEnable(3553);
			GL11.glDisable(3042);
		}
	}

	private void a(final AABB en) {
		final Tessellator instance = Tessellator.instance;
		instance.begin(3);
		instance.vertex(en.minX, en.minY, en.minZ);
		instance.vertex(en.maxX, en.minY, en.minZ);
		instance.vertex(en.maxX, en.minY, en.maxZ);
		instance.vertex(en.minX, en.minY, en.maxZ);
		instance.vertex(en.minX, en.minY, en.minZ);
		instance.draw();
		instance.begin(3);
		instance.vertex(en.minX, en.maxY, en.minZ);
		instance.vertex(en.maxX, en.maxY, en.minZ);
		instance.vertex(en.maxX, en.maxY, en.maxZ);
		instance.vertex(en.minX, en.maxY, en.maxZ);
		instance.vertex(en.minX, en.maxY, en.minZ);
		instance.draw();
		instance.begin(1);
		instance.vertex(en.minX, en.minY, en.minZ);
		instance.vertex(en.minX, en.maxY, en.minZ);
		instance.vertex(en.maxX, en.minY, en.minZ);
		instance.vertex(en.maxX, en.maxY, en.minZ);
		instance.vertex(en.maxX, en.minY, en.maxZ);
		instance.vertex(en.maxX, en.maxY, en.maxZ);
		instance.vertex(en.minX, en.minY, en.maxZ);
		instance.vertex(en.minX, en.maxY, en.maxZ);
		instance.draw();
	}

	public void a(final int integer1, final int integer2, final int integer3, final int integer4, final int integer5,
			final int integer6) {
		final int a = Mth.a(integer1, 16);
		final int a2 = Mth.a(integer2, 16);
		final int a3 = Mth.a(integer3, 16);
		final int a4 = Mth.a(integer4, 16);
		final int a5 = Mth.a(integer5, 16);
		final int a6 = Mth.a(integer6, 16);
		for (int i = a; i <= a4; ++i) {
			int n = i % this.p;
			if (n < 0) {
				n += this.p;
			}
			for (int j = a2; j <= a5; ++j) {
				int n2 = j % this.q;
				if (n2 < 0) {
					n2 += this.q;
				}
				for (int k = a3; k <= a6; ++k) {
					int n3 = k % this.r;
					if (n3 < 0) {
						n3 += this.r;
					}
					final WorldRenderer worldRenderer = this.o[(n3 * this.q + n2) * this.p + n];
					if (!worldRenderer.u) {
						this.m.add(worldRenderer);
					}
					worldRenderer.f();
				}
			}
		}
	}

	public void markBlockAndNeighborsNeedsUpdate(final int integer1, final int integer2, final int integer3) {
		this.a(integer1 - 1, integer2 - 1, integer3 - 1, integer1 + 1, integer2 + 1, integer3 + 1);
	}

	public void markBlockRangeNeedsUpdate(final int integer1, final int integer2, final int integer3,
			final int integer4, final int integer5, final int integer6) {
		this.a(integer1 - 1, integer2 - 1, integer3 - 1, integer4 + 1, integer5 + 1, integer6 + 1);
	}

	public void clipRenderersByFrustrum(final ICamera jt, final float float2) {
		for (int i = 0; i < this.o.length; ++i) {
			if (!this.o[i].e() && (!this.o[i].isInFrustum || (i + this.j & 0xF) == 0x0)) {
				this.o[i].a(jt);
			}
		}
		++this.j;
	}

	public void playSound(final String soundName, final double xCoord, final double yCoord, final double zCoord,
			final float volume, final float pitch) {
		this.t.sndManager.playSound(soundName, (float) xCoord, (float) yCoord, (float) zCoord, volume, pitch);
	}

	public void spawnParticle(final String particle, final double xCoordBlock, final double yCoordBlock,
			final double zCoordBlock, final double xPosition, final double yPosition, final double zPosition) {
		final double n = this.k.player.posX - xCoordBlock;
		final double n2 = this.k.player.posY - yCoordBlock;
		final double n3 = this.k.player.posZ - zCoordBlock;
		if (n * n + n2 * n2 + n3 * n3 > 256.0) {
			return;
		}
		if (particle == "bubble") {
			this.t.effectRenderer.addEffect(
					new EntityBubbleFX(this.k, xCoordBlock, yCoordBlock, zCoordBlock, xPosition, yPosition, zPosition));
		} else if (particle == "smoke") {
			this.t.effectRenderer.addEffect(new EntitySmokeFX(this.k, xCoordBlock, yCoordBlock, zCoordBlock));
		} else if (particle == "explode") {
			this.t.effectRenderer.addEffect(new EntityExplodeFX(this.k, xCoordBlock, yCoordBlock, zCoordBlock,
					xPosition, yPosition, zPosition));
		} else if (particle == "flame") {
			this.t.effectRenderer.addEffect(
					new EntityFlameFX(this.k, xCoordBlock, yCoordBlock, zCoordBlock, xPosition, yPosition, zPosition));
		} else if (particle == "lava") {
			this.t.effectRenderer.addEffect(new EntityLavaFX(this.k, xCoordBlock, yCoordBlock, zCoordBlock));
		} else if (particle == "splash") {
			this.t.effectRenderer.addEffect(
					new EntitySplashFX(this.k, xCoordBlock, yCoordBlock, zCoordBlock, xPosition, yPosition, zPosition));
		} else if (particle == "largesmoke") {
			this.t.effectRenderer.addEffect(new EntitySmokeFX(this.k, xCoordBlock, yCoordBlock, zCoordBlock, 2.5f));
		}
	}

	public void obtainEntitySkin(final Entity entity) {
		if (entity.skinUrl != null) {
			this.l.a(entity.skinUrl, new Texture());
		}
	}

	public void releaseEntitySkin(final Entity entity) {
		if (entity.skinUrl != null) {
			this.l.b(entity.skinUrl);
		}
	}

	public void updateAllRenderers() {
		for (int i = 0; i < this.o.length; ++i) {
			if (this.o[i].A) {
				if (!this.o[i].u) {
					this.m.add(this.o[i]);
				}
				this.o[i].f();
			}
		}
	}
}
