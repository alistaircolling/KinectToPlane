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
import toxi.geom.Vec3D;
import toxi.geom.mesh.TriangleMesh;
import toxi.geom.mesh.Vertex;
import toxi.math.waves.SineWave;
import toxi.processing.ToxiclibsSupport;

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
	private float mouseRotation;
	private float mouseRotationX;
	private int pointsInBox = 0;
	private int topCount;
	private ToxiclibsSupport gfx;
	float camX, camY, camZ;

	public KinectCanvas(KinectToPlane app) {
		sketch = app;
		controller = sketch.kinectController;
		ui = sketch.ui;
		app.smooth();
		init();
	}

	private void init() {
		topCount = 0;
		wave = new SineWave(0, .005f, 1, 0);
		wave2 = new SineWave(.7f, .005f, 1, 0);
		gfx = new ToxiclibsSupport(sketch);
//		cam = new PeasyCam((PApplet)sketch, 0, 0, 0, 1000);
	//	cam = new PeasyCam(sketch, 0,0,0,1000);

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
			getDepthPoints();
			// drawPointCloud();
			drawMesh();

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

	public void drawMesh() {

		sketch.background(0);
		sketch.lights();

		// mesh to go behind objects

		TriangleMesh bgMesh = new TriangleMesh();

		TriangleMesh triMesh = null;
		TriangleMesh triMesh2 = null;
		int skip = 5;
		float maxX = 0;
		float maxY = 0;
		float maxZ = 0;
		int minZ = 500;
		float lastZ = 0;
		
		
		for (int x = 0; x < 640 - skip; x+=skip) {
			for (int y = 1; y < 480 - skip; y+=skip) {

				triMesh = new TriangleMesh();
				triMesh2 = new TriangleMesh();

				PVector vect1 = depthPoints[x + (y * 640)];
				PVector vect2 = depthPoints[(x + (y * 640) + skip)];
				PVector vect3 = depthPoints[(x + ((y + skip) * 640))];
				PVector vect4 = depthPoints[(x + ((y + skip) * 640) + skip)];
				
				
				if (vect1.x>maxX) maxX = vect1.x;
				if (vect2.x>maxX) maxX = vect2.x;
				if (vect3.x>maxX) maxX = vect3.x;
				if (vect4.x>maxX) maxX = vect4.x;
				if (vect1.y>maxY) maxY = vect1.y;
				if (vect2.y>maxY) maxY = vect2.y;
				if (vect3.y>maxY) maxY = vect3.y;
				if (vect4.y>maxY) maxY = vect4.y;
				if (vect1.z>maxZ) maxZ = vect1.z;
				if (vect2.z>maxZ) maxZ = vect2.z;
				if (vect3.z>maxZ) maxZ = vect3.z;
				if (vect4.z>maxZ) maxZ = vect4.z;
				
				maxX = 1500;
				maxY = 1300;
				maxZ = 6500;
				
				
				//if any of the vectors have a z value of less than X set to the highest val
				
				if (vect1.z <minZ) vect1.z = maxZ;
				if (vect2.z <minZ) vect2.z = maxZ;
				if (vect3.z <minZ) vect3.z = maxZ;
				if (vect4.z <minZ) vect4.z = maxZ;
		
				/*
				if (vect1.z <minZ) {
					vect1.z = lastZ;
				}else{
					lastZ = vect1.z;
				}
				if (vect2.z <minZ) {
					vect2.z = lastZ;
				}else{
					lastZ = vect2.z;
				}
				if (vect3.z <minZ) {
					vect3.z = lastZ;
				}else{
					lastZ = vect3.z;
				}
				if (vect4.z <minZ) {
					vect4.z = lastZ;
				}else{
					lastZ = vect4.z;
				}
			*/
				
				
				
				
				Vertex v1 = new Vertex(new Vec3D(vect1.x, vect1.y, vect1.z), 0);
				Vertex v2 = new Vertex(new Vec3D(vect2.x, vect2.y, vect2.z), 0);
				Vertex v3 = new Vertex(new Vec3D(vect3.x, vect3.y, vect3.z), 0);
				Vertex v4 = new Vertex(new Vec3D(vect4.x, vect4.y, vect4.z), 0);

				triMesh.addFace(v1, v2, v3);
				triMesh2.addFace(v2, v3, v4);
				

				triMesh.translate(new Vec3D(0-(maxX*.5f), 0-(maxY*.5f), 0-(maxZ*.5f)));
				triMesh2.translate(new Vec3D(0-(maxX*.5f), 0-(maxY*.5f), 0-(maxZ*0.5f)));

				triMesh.rotateX(sketch.radians(180));
				triMesh2.rotateX(sketch.radians(180));
				
				triMesh.rotateY(rotation);
				triMesh2.rotateY(rotation);

				// triMesh.rotateX(rotationX);
				// triMesh2.rotateX(rotationX);

				sketch.noStroke();
				sketch.fill(x, 1000, 100);
				gfx.mesh(triMesh);
				gfx.mesh(triMesh2);

			//	bgMesh.addMesh(triMesh);
			//	bgMesh.addMesh(triMesh2);

			}
			

		}
		
		
		if (sketch.cmdDown){
			rotation += sketch.map(sketch.mouseY, 0, sketch.height, -.1f, .1f);
		}

		// set cam positions
		camX = 0;// map(mouseX, 0, width, -500, 500);
		camY = 0;
		camZ = 2000;// map(mouseY, 0, height, -8000, 8000);

		sketch.camera(camX, camY, camZ, 0, 0, 0, 0, 1, 0);

	}

	private boolean isAHole(PVector[] vects) {
		
		
		
		for (int i = 0; i < vects.length; i++) {
			PVector vect = vects[i];
			if (vect.x == 0 && vect.y==0 &&vect.z==0){
				return true;
			}
		}
		
		return false;
	}

	private void drawPointCloud() {

		// sketch.rotateX(sketch.radians(180));
		// sketch.rotateX(sketch.radians(90));
		// sketch.rotateY(sketch.radians(rotation));
		// sketch.rotateX(sketch.radians(180));
		// sketch.rotateZ(sketch.radians(rotation));
		rotation += 2;

		
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

		int skip = 10;
		sketch.background(0);
		sketch.pushMatrix();

		// allow control via mouse
		if (sketch.cmdDown) {
			mouseRotation = sketch.map(sketch.mouseX, 0, sketch.width, -360,
					360);
			mouseRotationX = sketch.map(sketch.mouseY, 0, sketch.height, -180,
					180);
			s = sketch.map(sketch.mouseY, 0, sketch.height, -10, 10);
		}
		sketch.translate(sketch.width * .5f, sketch.height * .5f, -1000);
		sketch.rotateX(sketch.radians(180));
		sketch.translate(0, 0, 1000);

		sketch.rotateY(sketch.radians(mouseRotation));

		// rotateZ(radians(mouseRotation));
		sketch.translate(-300, -500, s * -1000);
		sketch.scale(s);

		int currPointsInBox = 0;

		if (!sketch.sequentialPoints) {
			topCount = depthPoints.length;
			sketch.strokeWeight(5);
			skip = 5;
		} else {
			sketch.strokeWeight(3);
			skip = 2;
		}

		// for (int i = 0; i < depthPoints.length; i += skip) {
		for (int i = 0; i < topCount; i += skip) {
			PVector currentP = depthPoints[i];

			theX = sketch.map(currentP.x, -1750, 1200, 0, 640);
			theY = sketch.map(currentP.y, -355, 1350, 0, 480);
			theZ = sketch.map(currentP.z, 0, 3600, 0, 480);
			sketch.stroke(currentP.z, 100, 100);
			sketch.point(theX, theY, theZ);
			// check if it is in the box

			if (currentP.x > ui.boxX.getValue() - ui.boxWidth.getValue() * .5f
					&& currentP.x < ui.boxX.getValue() + ui.boxWidth.getValue()
							* .5f) {

				if (currentP.y > ui.boxY.getValue() - ui.boxHeight.getValue()
						* .5f
						&& currentP.y < ui.boxY.getValue()
								+ ui.boxHeight.getValue() * .5f) {

					if (currentP.z > ui.boxZ.getValue()
							- ui.boxDepth.getValue() * .5f
							&& currentP.z < ui.boxZ.getValue()
									+ ui.boxDepth.getValue() * .5f) {
						// is in box!
						if (currentP.x == 0 && currentP.y == 0
								&& currentP.z == 0) {
							// nothgin
						} else {
							currPointsInBox++;

						}
					}

				}

			}

		}
		if (currPointsInBox != pointsInBox) {
			pointsInBox = currPointsInBox;
			sketch.println("POINTS IN BOX:" + pointsInBox);
		}
		// TODO DRAW BOX HERE

		sketch.translate(ui.boxX.getValue(), ui.boxY.getValue(),
				ui.boxZ.getValue());
		sketch.stroke(0, 100, 100);
		sketch.noFill();
		sketch.box(ui.boxWidth.getValue(), ui.boxHeight.getValue(),
				ui.boxDepth.getValue());
		// check how may points

		sketch.popMatrix();
		if (topCount < depthPoints.length - 1) {
			topCount += 512;
		} else {
			topCount = 0;
		}

	}

	private void getDepthPoints() {
		
		if (controller.kinectConnected) {

			depthPoints = sketch.kinectController.kinect.depthMapRealWorld();
		} else {
			try {
				// only load if null
				if (depthPoints == null) {
					depthPoints = parseTextFileToDepth();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
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
