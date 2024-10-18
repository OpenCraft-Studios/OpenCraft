package net.opencraft.physics;

import java.util.ArrayList;

public final class AABBPool extends ArrayList<AABB> {

	private static final long serialVersionUID = 1L;
	public int aabbsInUse = 0;

	private AABBPool(int size) {
		super(size);
	}
	
	private AABBPool() {
		super();
	}
	
	public static AABBPool createDynamic() {
		return new AABBPool();
	}
	
	public static AABBPool createFixed(int size) {
		return new AABBPool(size);
	}
	
}
