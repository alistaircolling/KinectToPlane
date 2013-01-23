
import toxi.color.TColor;
import toxi.geom.Rect;
import controlP5.Bang;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.ListBox;
import controlP5.Println;
import controlP5.Slider;
import controlP5.Slider2D;
import controlP5.Textarea;
import controlP5.Toggle;

public class UserInterface {

	private KinectToPlane sketch;
	public ControlP5 cp5;
	private Group rectPos;
	private int sliderWidth = 200;
	private int sliderHeight = 20;
	private int spacer = 7;

	private Slider2D xzPos;

	private int textCol = TColor.BLACK.toARGB();
	public Slider xPos;
	public Slider yPos;
	public Slider zPos;
	public Bang createRect;
	public Slider rectWidth;
	public Slider rectHeight;
	public Slider xAxis;
	public Slider yAxis;
	public Slider zAxis;
	public ListBox list;
	private MyControlListener myListener;
	private int totalRects = 0;
	public Toggle togglePointCloud;
	public Slider boxWidth;
	public Group boxPos;
	public Slider boxHeight;
	public Slider boxDepth;
	public Slider boxX;
	public Slider boxY;
	public Slider boxZ;
	private Println console;
	public Textarea myTextarea;
	private Toggle toggleSequentialDraw;
	public Slider hsb;

	
	public UserInterface(KinectToPlane app) {

		sketch = app;
		init();
		

	}

	private void init() {

		myListener = new MyControlListener(sketch);
		
		cp5 = new ControlP5(sketch);
		cp5.setAutoDraw(false);
		
		cp5.addTextlabel("CONTROLS");

		rectPos = cp5.addGroup("rectPos").setPosition(10, 10)
		// .setBackgroundColor(TColor.BLUE.toARGB())
		// .setColorForeground(TColor.CYAN.toARGB())
				.setSize(230, 400);

		

		createRect = cp5.addBang("createRect").setColorCaptionLabel(textCol)
				.setGroup("rectPos").setPosition(0, 15)
				.addListener(myListener)
				.setSize(sliderHeight, sliderHeight);

		xPos = cp5.addSlider("X POS").setPosition(0, 55)
				.setSize(sliderWidth, sliderHeight).setGroup("rectPos")
				.setRange(-1000, 1000).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(0);
		;
		yPos = cp5.addSlider("Y POS").setPosition(0, 80)
				.setSize(sliderWidth, sliderHeight).setGroup("rectPos")
				.setRange(-1000, 1000).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(0);

		zPos = cp5.addSlider("Z POS").setPosition(0, 105)
				.setSize(sliderWidth, sliderHeight).setGroup("rectPos")
				.setRange(-1000, 1000).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(0);

		rectWidth = cp5.addSlider("RECT Width").setPosition(0, 185)
				.setSize(sliderWidth, sliderHeight).setGroup("rectPos")
				.setRange(0, 1000).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(0);
		
		rectHeight = cp5.addSlider("RECT HEIGHT").setPosition(0, 210)
				.setSize(sliderWidth, sliderHeight).setGroup("rectPos")
				.setRange(0, 1000).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(0);

		xAxis = cp5.addSlider("X AXIS").setPosition(0, 240)
				.setSize(sliderWidth, sliderHeight).setGroup("rectPos")
				.setRange(0-sketch.PI, sketch.PI).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(10);

		zAxis = cp5.addSlider("Z AXIS").setPosition(0, 265)
				.setSize(sliderWidth, sliderHeight).setGroup("rectPos")
				.setRange(0-sketch.PI, sketch.PI).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(10);

		yAxis = cp5.addSlider("Y AXIS").setPosition(0, 295)
				.setSize(sliderWidth, sliderHeight).setGroup("rectPos")
				.setRange(0-sketch.PI, sketch.PI).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(10);
		
		list = cp5.addListBox("list").setPosition(0, 330).setGroup("rectPos")
				.setWidth(sliderWidth);

		togglePointCloud = cp5.addToggle("toggle Point Cloud").setColorCaptionLabel(textCol)
				.setGroup("rectPos").setPosition(0, 350)
				.addListener(myListener)
				.setSize(sliderHeight, sliderHeight);
		
		toggleSequentialDraw = cp5.addToggle("Toggle Seq Draw").setColorCaptionLabel(textCol)
				.setGroup("rectPos").setPosition(0, 385)
				.addListener(myListener)
				.setSize(sliderHeight, sliderHeight);
		
		hsb = cp5.addSlider("HSB").setPosition(0, 410)
				.setSize(sliderWidth, sliderHeight).setGroup("rectPos")
				.setRange(0, 6000).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(0);
		
		boxPos = cp5.addGroup("BOX PARAMS").setPosition(sketch.width-250, 10)
			//	 .setBackgroundColor(TColor.BLUE.toARGB())
				 .setColorForeground(TColor.CYAN.toARGB())
				 .setSize(230, 400);
				
		

		
		boxWidth = cp5.addSlider("BOX WIDTH").setPosition(0, 0)
				.setSize(sliderWidth, sliderHeight).setGroup("BOX PARAMS")
				.setRange(0, 640).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(0);
		boxHeight = cp5.addSlider("BOX HEIGHT").setPosition(0, 20)
				.setSize(sliderWidth, sliderHeight).setGroup("BOX PARAMS")
				.setRange(0, 640).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(0);
		boxDepth = cp5.addSlider("BOX DEPTH").setPosition(0, 40)
				.setSize(sliderWidth, sliderHeight).setGroup("BOX PARAMS")
				.setRange(0, 640).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(0);
		
		boxX = cp5.addSlider("BOX X").setPosition(0, 65)
				.setSize(sliderWidth, sliderHeight).setGroup("BOX PARAMS")
				.setRange(0, 640).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(0);
		boxY= cp5.addSlider("BOX Y").setPosition(0, 85)
				.setSize(sliderWidth, sliderHeight).setGroup("BOX PARAMS")
				.setRange(0, 640).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(0);
		boxZ= cp5.addSlider("BOX Z").setPosition(0, 105)
				.setSize(sliderWidth, sliderHeight).setGroup("BOX PARAMS")
				.setRange(0, 640).setColorCaptionLabel(textCol)
				.addListener(myListener)
				.setHandleSize(10).setDecimalPrecision(0);
		
		
		
		myTextarea = cp5.addTextarea("console")
                .setPosition(0, sketch.height-300)
                .setGroup("rectPos")
                .setSize(400, 300)
                .setLineHeight(14)
                .setColor(sketch.color(3,0,100))
                .setColorBackground(sketch.color(1, 0, 20))
                .setColorForeground(sketch.color(255, 0, 100));
		
		console  = cp5.addConsole(myTextarea);
		
		sketch.println("INITIALIZING KINECT.......");

	}
	
	public void addRect(Rect rect){
		list.addItem("rect"+totalRects , totalRects);
		totalRects ++;
	}

	

	public void toggleShow() {

		sketch.println("is vis:" + cp5.isVisible());
		if (cp5.isVisible()) {
			cp5.hide();
		} else {
			cp5.show();
		}

	}

	public void draw() {
		cp5.draw();
		
	}

}
