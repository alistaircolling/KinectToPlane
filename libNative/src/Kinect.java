import java.util.ArrayList;
import java.util.HashMap;



import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import toxi.color.TColor;
import toxi.geom.Rect;
import toxi.geom.Vec2D;
import toxi.physics2d.VerletParticle2D;
import toxi.physics2d.VerletPhysics2D;
import toxi.physics2d.VerletSpring2D;
import toxi.physics2d.behaviors.AttractionBehavior;

public class Kinect extends PApplet {

	
	private float[] depthLookUp;
	
	Simpl
	
	private int threshold = 500;
	
	private int winWidth = 640;
	private int winHeight = 480;

	public void setup() {
		size(640, 480);
		
	}

	

	public void draw() {
		background(0);
		

	}

	

	private PVector depthToWorld(int x, int y, int depthValue) {

		final double fx_d = 1.0 / 5.9421434211923247e+02;
		final double fy_d = 1.0 / 5.9104053696870778e+02;
		final double cx_d = 3.3930780975300314e+02;
		final double cy_d = 2.4273913761751615e+02;

		PVector result = new PVector();
		double depth = depthLookUp[depthValue];// rawDepthToMeters(depthValue);
		result.x = (float) ((x - cx_d) * depth * fx_d);
		result.y = (float) ((y - cy_d) * depth * fy_d);
		result.z = (float) (depth);
		return result;
	}

	public void mousePressed() {
		println("mouse pressed");
	}

	public void mouseDragged() {
		println("mouse dragged");
	}

	public void mouseReleased() {
		println("mouse released");
	}

	public void keyPressed() {
		println("key pressed:" + key);
	}

}
