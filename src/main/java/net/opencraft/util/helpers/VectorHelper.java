package net.opencraft.util.helpers;

import org.joml.Vector3d;

public class VectorHelper {

	private VectorHelper() {}
	
	public static Vector3d xIntermediate(Vector3d start, Vector3d end, double xValue) {
	    Vector3d direction = new Vector3d(end).sub(start);
	    if (Math.abs(direction.x) < 1e-7f)
	        return null; // Avoid zero division
	    
	    double t = (xValue - start.x) / direction.x;
	    if (t < 0.0f || t > 1.0f)
	        return null;

	    return new Vector3d(start).lerp(end, t); 
	}
	
	public static Vector3d yIntermediate(Vector3d start, Vector3d end, double yValue) {
	    Vector3d direction = new Vector3d(end).sub(start);
	    if (Math.abs(direction.y) < 1e-7f)
	        return null;
	    
	    double t = (yValue - start.y) / direction.y;
	    if (t < 0.0f || t > 1.0f)
	        return null;
	    
	    return new Vector3d(start).lerp(end, t);
	}

	public static Vector3d zIntermediate(Vector3d start, Vector3d end, double zValue) {
	    Vector3d direction = new Vector3d(end).sub(start);
	    if (Math.abs(direction.z) < 1e-7f)
	        return null;
	    
	    double t = (zValue - start.z) / direction.z;
	    if (t < 0.0f || t > 1.0f)
	        return null;
	    
	    return new Vector3d(start).lerp(end, t);
	}
	
}
