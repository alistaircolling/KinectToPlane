import java.awt.geom.Rectangle2D;

import javax.swing.text.StyleContext.SmallAttributeSet;

import processing.core.PApplet;
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

	public void draw() {
		
		sketch.pushMatrix();
		//draw the rect
		sketch.fill(TColor.MAGENTA.toARGB());
		sketch.translate(ui.xPos.getValue(), ui.yPos.getValue(), ui.zPos.getValue());
		sketch.rotateX(ui.xAxis.getValue());
		sketch.rotateY(ui.yAxis.getValue());
		sketch.rotateZ(ui.zAxis.getValue());
		
		sketch.rect(0, 0, ui.rectWidth.getValue(), ui.rectHeight.getValue());
		//draw a circle inside the rect
		sketch.fill(TColor.RED.toARGB());
		sketch.noStroke();
		float wavVal = wave.update();
		float wavVal2 = wave2.update();
		sketch.ellipse(ui.rectWidth.getValue()*.5f, ui.rectHeight.getValue()*.5f, 100*wavVal, 100*wavVal);
		sketch.fill(TColor.CYAN.toARGB());
		sketch.ellipse(ui.rectWidth.getValue()*.7f, ui.rectHeight.getValue()*.7f, 200*wavVal2, 200*wavVal2);
		sketch.fill(TColor.GREEN.toARGB());
		sketch.ellipse(ui.rectWidth.getValue()*.2f, ui.rectHeight.getValue()*.2f, 80*(1-wavVal2), 80*(1-wavVal2));
		
		
		//sketch.rect(0,0,ui.rectSize, ui.rectSize);//
		
		sketch.popMatrix();
		
		
		
	}

}
