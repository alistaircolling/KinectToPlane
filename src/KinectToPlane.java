import java.util.ArrayList;

import processing.core.PApplet;
import toxi.color.TColor;
import toxi.geom.Rect;
import controlP5.ControlP5;

public class KinectToPlane extends PApplet {

	

	private KinectController kinectController;
	UserInterface ui;
	private ControlP5 ctrl;
	
	private int bgCol;
	public KinectCanvas kinectCanvas;
	private ArrayList<Rect> rects;
	

	public void setup() {
		size(1024, 768, OPENGL);
		kinectController = new KinectController(this);
		println("kinect controler setup");
		
		//ui = new UserInterface(this);
		
		//bgCol = TColor.newRandom().toARGB();
		bgCol = TColor.MAGENTA.toARGB();
		kinectCanvas = new KinectCanvas(this);
		
		
		
	}

	public void draw() {

		background(60);
		//update the kinect info
		kinectController.draw();
		//kinectCanvas.draw();
		

	}

	


	public void mousePressed() {

	}

	public void mouseDragged() {
	//	println("mouse dragged");
	}

	public void mouseReleased() {
		//println("mouse released");
	}

	public void keyPressed() {
		println("key pressed:" + keyCode);
		switch (keyCode) {
		//
		case 67:
			ui.toggleShow();
			break;

		default:
			break;
		}

	}

	public void addRect(Rect rect) {
		
		rects.add(rect);
		
	}

}
;