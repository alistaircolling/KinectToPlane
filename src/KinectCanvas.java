import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import peasy.PeasyCam;
import processing.core.PVector;
import toxi.color.TColor;
import toxi.geom.Rect;
import toxi.math.waves.SineWave;

public class KinectCanvas {

	private KinectToPlane sketch;
	private KinectController controller;
	public Rect rect;
	private UserInterface ui;
	private SineWave wave;
	private SineWave wave2;
	private float rotation = 0;
	private PeasyCam cam;
	private float s;
	private PVector[] depthPoints;

	public KinectCanvas(KinectToPlane app) {
		sketch = app;
		controller = sketch.kinectController;
		ui = sketch.ui;
		app.smooth();
		init();
	}

	private void init() {

		wave = new SineWave(0, .005f, 1, 0);
		wave2 = new SineWave(.7f, .005f, 1, 0);
		// cam = new PeasyCam((PApplet)sketch, 0, 0, 0, 1000);

	}

	public void addRect() {
		Rect rect = new Rect();
		rect.width = 500;
		rect.height = 500;
		sketch.addRect(rect);

		ui.addRect(rect);

	}

	public void draw(ArrayList<Head> heads) {

		// show kinect images

		if (controller.kinectConnected)
			sketch.image(sketch.kinectController.kinect.depthImage(), 0, 0);
		if (sketch.showPointCloud) {
			/*
			 * if (cam==null){
			 * 
			 * }
			 */
			// cam.beginHUD();
			drawPointCloud();
			// cam.endHUD();
		} else {
			if (!controller.kinectConnected) {
				// sketch.println("no kinect connected");
			} else {

				sketch.image(sketch.kinectController.kinect.rgbImage(), 640, 0);
				sketch.noStroke();
				sketch.pushMatrix();
				// draw the rect
				sketch.fill(TColor.MAGENTA.toARGB());
				sketch.translate(ui.xPos.getValue(), ui.yPos.getValue(),
						ui.zPos.getValue());
				sketch.rotateX(ui.xAxis.getValue());
				sketch.rotateY(ui.yAxis.getValue());
				sketch.rotateZ(ui.zAxis.getValue());

				sketch.rect(0, 0, ui.rectWidth.getValue(),
						ui.rectHeight.getValue());

				// go thru the heads
				for (int i = 0; i < heads.size(); i++) {
					Head head = heads.get(i);

					drawHead(head);

				}
				sketch.popMatrix();
			}
		}

		// //draw a circle inside the rect
		// sketch.fill(TColor.RED.toARGB());
		// sketch.noStroke();
		// float wavVal = wave.update();
		// float wavVal2 = wave2.update();
		// sketch.ellipse(ui.rectWidth.getValue()*.5f,
		// ui.rectHeight.getValue()*.5f, 100*wavVal, 100*wavVal);
		// sketch.fill(TColor.CYAN.toARGB());
		// sketch.ellipse(ui.rectWidth.getValue()*.7f,
		// ui.rectHeight.getValue()*.7f, 200*wavVal2, 200*wavVal2);
		// sketch.fill(TColor.GREEN.toARGB());
		// sketch.ellipse(ui.rectWidth.getValue()*.2f,
		// ui.rectHeight.getValue()*.2f, 80*(1-wavVal2), 80*(1-wavVal2));
		//

		// sketch.rect(0,0,ui.rectSize, ui.rectSize);//

	}

	private void drawPointCloud() {

		// sketch.rotateX(sketch.radians(180));
		// sketch.rotateX(sketch.radians(90));
		// sketch.rotateY(sketch.radians(rotation));
		// sketch.rotateX(sketch.radians(180));
		// sketch.rotateZ(sketch.radians(rotation));
		rotation += 2;

		if (controller.kinectConnected) {

			depthPoints = sketch.kinectController.kinect.depthMapRealWorld();
		} else {
			try {
				//only load if null
				if (depthPoints == null) {
					depthPoints = parseTextFileToDepth();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * ***************** CODE TO WRITE TO FILE **************** String str =
		 * "";
		 * 
		 * try { FileWriter outFile = new FileWriter("myNumbers.txt");
		 * PrintWriter out = new PrintWriter(outFile);
		 * 
		 * // Also could be written as follows on one line // Printwriter out =
		 * new PrintWriter(new FileWriter(args[0]));
		 * 
		 * // Write text to file for (int i = 0; i < depthPoints.length; i++) {
		 * PVector vect = depthPoints[i];
		 * out.println(vect.x+" "+vect.y+" "+vect.z+" "); } out.close(); } catch
		 * (IOException e){ e.printStackTrace(); }
		 */

		float theX;
		float theY;
		float theZ;

		int skip = 20;
		sketch.background(0);
		sketch.pushMatrix();

		sketch.translate(sketch.width * .5f, sketch.height * .5f, -1000);
		sketch.rotateX(sketch.radians(180));
		sketch.translate(0, 0, 1000);
		float mouseRotation = sketch.map(sketch.mouseX, 0, sketch.width, -360,
				360);
		sketch.rotateY(sketch.radians(mouseRotation));
		float mouseRotationX = sketch.map(sketch.mouseY, 0, sketch.height,
				-180, 180);
		s = sketch.map(sketch.mouseY, 0, sketch.height, -10, 10);
		// rotateZ(radians(mouseRotation));
		sketch.translate(0, 0, s * -1000);
		sketch.scale(s);

		for (int i = 0; i < depthPoints.length; i += skip) {
			PVector currentP = depthPoints[i];

			theX = sketch.map(currentP.x, -1750, 1200, 0, 640);
			theY = sketch.map(currentP.y, -355, 1350, 0, 480);
			theZ = sketch.map(currentP.z, 0, 3600, 0, 480);
			sketch.stroke(currentP.z, 100, 100);
			sketch.point(theX, theY, theZ);

		}

		sketch.popMatrix();

		// TODO DRAW BOX HERE
		/*
		 * sketch.translate(ui.boxX.getValue(), ui.boxY.getValue(),
		 * ui.boxZ.getValue()); sketch.stroke(0, 100, 100); sketch.noFill();
		 * sketch.box(ui.boxWidth.getValue(), ui.boxHeight.getValue(),
		 * ui.boxDepth.getValue()); sketch.popMatrix();
		 */

	}

	private PVector[] parseTextFileToDepth() throws FileNotFoundException {
		PVector[] depthPoints = new PVector[307200];

		Scanner scan = new Scanner(new File("myNumbers.txt"));

		// Read each row.
		for (int i = 0; i < depthPoints.length; i++) {

			// For each number in the column, read a number
			// and put it in the array
			PVector vect = new PVector();
			// String obj = scan.next();
			vect.x = Float.parseFloat(scan.next());
			vect.y = Float.parseFloat(scan.next());
			vect.z = Float.parseFloat(scan.next());
			depthPoints[i] = vect;
			// advance the scanner to the next line (row).
			scan.nextLine();
		}
		return depthPoints;
	}

	private void drawHead(Head head) {
		sketch.fill(head.id, 100, 100);
		float xPos = sketch.map(head.pos.x, 0, 640, 0, ui.rectWidth.getValue());
		sketch.ellipse(xPos, ui.rectHeight.getValue() * .5f, 30, 30);

	}

}
