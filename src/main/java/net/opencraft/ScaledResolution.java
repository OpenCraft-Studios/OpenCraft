package net.opencraft;

import java.awt.Dimension;

public class ScaledResolution extends Dimension {

	private static final long serialVersionUID = 1L;

	public ScaledResolution(int width, int height) {
		super(width, height);
		ScaledResolution.scale(this);
	}

	public int getScaledWidth() {
		return width;
	}

	public int getScaledHeight() {
		return height;
	}

	public static void scale(Dimension d) {
		int r = findRatio(d);

		d.width /= r;
		d.height /= r;
	}
	
	public static int findRatio(Dimension d) {
		int r = 1;
		while (match(d.width, r, 320) && match(d.height, r, 240))
			++r;
		
		return r;
	}

	public static boolean match(int num, int aspectRatio, int max) {
		return num / (aspectRatio + 1) >= max;
	}
	
}
