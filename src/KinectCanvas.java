import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.text.StyleContext.SmallAttributeSet;

import processing.core.PApplet;
import processing.core.PVector;
import toxi.color.TColor;
import toxi.geom.Rect;
import toxi.math.waves.SineWave;


public class KinectCanvas {

	private KinectToPlane sketch;
	public  Rect rect;
	private UserInterface ui;
	private SineWave wave;
	private SineWave wave2;

	public KinectCanvas(KinectToPlane app) {
		sketch  = app;
		ui = sketch.ui;
		app.smooth();
		init();
	}

	private void init() {
		wave = new SineWave(0, .005f, 1, 0);
		wave2 = new SineWave(.7f, .005f, 1, 0);
		
	}

	public void addRect() {
		Rect rect = new Rect();
		rect.width = 500;
		rect.height = 500;
		sketch.addRect(rect);
		
		ui.addRect(rect);
		
		
	}

	public void draw(ArrayList<Head> heads) {
		
		//show kinect images
		
		sketch.image(sketch.kinectController.kinect.depthImage(), 0, 0);
		if(sketch.showPointCloud){
			drawPointCloud();
		}else{
			sketch.image(sketch.kinectController.kinect.rgbImage(), 640, 0);
		}
		
		
		sketch.noStroke();
		sketch.pushMatrix();
		//draw the rect
		sketch.fill(TColor.MAGENTA.toARGB());
		sketch.translate(ui.xPos.getValue(), ui.yPos.getValue(), ui.zPos.getValue());
		sketch.rotateX(ui.xAxis.getValue());
		sketch.rotateY(ui.yAxis.getValue());
		sketch.rotateZ(ui.zAxis.getValue());
		
		sketch.rect(0, 0, ui.rectWidth.getValue(), ui.rectHeight.getValue());
		
		
		//go thru the heads
		for (int i = 0; i < heads.size(); i++) {
			Head head = heads.get(i);
			
			drawHead(head);
			
		}
		
		
//		//draw a circle inside the rect
//		sketch.fill(TColor.RED.toARGB());
//		sketch.noStroke();
//		float wavVal = wave.update();
//		float wavVal2 = wave2.update();
//		sketch.ellipse(ui.rectWidth.getValue()*.5f, ui.rectHeight.getValue()*.5f, 100*wavVal, 100*wavVal);
//		sketch.fill(TColor.CYAN.toARGB());
//		sketch.ellipse(ui.rectWidth.getValue()*.7f, ui.rectHeight.getValue()*.7f, 200*wavVal2, 200*wavVal2);
//		sketch.fill(TColor.GREEN.toARGB());
//		sketch.ellipse(ui.rectWidth.getValue()*.2f, ui.rectHeight.getValue()*.2f, 80*(1-wavVal2), 80*(1-wavVal2));
//		
		
		//sketch.rect(0,0,ui.rectSize, ui.rectSize);//
		
		sketch.popMatrix();
		
		
		
	}

	private void drawPointCloud() {
		// 
		int skip = 7;
		PVector[] depthPoints = sketch.kinectController.kinect.depthMapRealWorld();
		for (int i = 0; i < depthPoints.length; i += skip) {
			PVector currentP = depthPoints[i];
			sketch.stroke(currentP.z, 100, 100);
			sketch.point(currentP.x, currentP.y, currentP.z);
		}
		
		
	}

	private void drawHead(Head head) {
		sketch.fill(head.id, 100,100);
		float xPos = sketch.map(head.pos.x, 0, 640, 0, ui.rectWidth.getValue());
		sketch.ellipse(xPos, ui.rectHeight.getValue()*.5f, 30, 30);
		
	}

}
