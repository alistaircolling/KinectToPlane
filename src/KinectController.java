import processing.core.PApplet;
import SimpleOpenNI.SimpleOpenNI;


public class KinectController {
	
	private PApplet mainSketch;
	private SimpleOpenNI kinect;
	private Head[] heads;
	

	public KinectController(PApplet sketch) {
		
		mainSketch = sketch;
		
		init();
	}

	private void init() {
		
		kinect = new SimpleOpenNI(mainSketch);
		kinect.enableDepth();
		
	}
	

}
