
package net.opencraft.util;

import static org.joml.Math.*;

public class Mth {

	public static final float sqrt_double(final double double1) {
		return (float) Math.sqrt(double1);
	}

	public static int floor_double(final double double1) {
		final int n = (int) double1;
		return (double1 < n) ? (n - 1) : n;
	}

	public static float roundToDecimalPoints(float value, int decimalPoints) {
		return (float) (round(value * Math.pow(10, decimalPoints)) / Math.pow(10, decimalPoints));
	}

	public static float roundToDecimalPlace(float value, float decimalPlace) {
		return (float) (round(value * decimalPlace) / decimalPlace);
	}

	public static int roundUp(int number, int interval) {
	    if (interval == 0)
	        return 0;
	    
	    int mod = Math.floorMod(number, interval);
	    return mod == 0 ? number : number + interval - mod;
	}

}
