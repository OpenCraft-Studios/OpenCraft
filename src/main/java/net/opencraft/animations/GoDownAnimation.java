package net.opencraft.animations;

import java.util.concurrent.atomic.AtomicReference;

public class GoDownAnimation {

	public static void animate(AtomicReference<Float> ptr, float maxY, float speed) {
		float y = ptr.get();
		if (y < maxY)
			y += speed;

		ptr.set(y);
	}

}
