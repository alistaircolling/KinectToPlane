import java.util.ArrayList;

import processing.core.PApplet;
import toxi.color.TColor;
import toxi.geom.Rect;
import controlP5.ControlP5;

public class KinectToPlane extends PApplet {

	public KinectController kinectController;
	UserInterface ui;
	private ControlP5 ctrl;

	private int bgCol;
	public KinectCanvas kinectCanvas;
	private ArrayList<Rect> rects;
	public boolean showPointCloud;
	private float hue = 0;
	public boolean cmdDown;
	public boolean sequentialPoints;

	public void setup() {
		size(1280, 768, OPENGL);
		colorMode(HSB, 1000, 1000, 100);
		ui = new UserInterface(this);

		kinectController = new KinectController(this);
		println("kinect controller setup");

		// bgCol = TColor.newRandom().toARGB();
		bgCol = TColor.MAGENTA.toARGB();
		kinectCanvas = new KinectCanvas(this);

	}

	public void draw() {

		background(60, 0, 100);
		// update the kinect info
		kinectController.draw();
		kinectCanvas.draw(kinectController.heads);

	}

	public void mousePressed() {

	}

	public void mouseDragged() {
		// println("mouse dragged");
	}

	public void mouseReleased() {
		// println("mouse released");
	}

	public void keyReleased() {
		if (keyCode==157){
			cmdDown = false;
		}
	}

	public void keyPressed() {
		println("key pressed:" + keyCode);
		switch (keyCode) {
		//
		case 67:
			ui.toggleShow();
			break;
		case 157:
			cmdDown = true;
			break;

		default:
			break;
		}

	}

	public void addRect(Rect rect) {

		rects.add(rect);

	}

	public void togglePointCloud(float value) {
		println("toggle:" + value);
		if (value == 0) {
			showPointCloud = false;
		} else {
			showPointCloud = true;
		}

	}

};
