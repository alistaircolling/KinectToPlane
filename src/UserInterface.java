
import toxi.color.TColor;
import toxi.geom.Rect;
import controlP5.Bang;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.ListBox;
import controlP5.Slider;
import controlP5.Slider2D;

public class UserInterface {

	private KinectToPlane sketch;
	private ControlP5 cp5;
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

	
	public UserInterface(KinectToPlane app) {

		sketch = app;
		init();
		

	}

	private void init() {

		myListener = new MyControlListener(sketch);
		
		cp5 = new ControlP5(sketch);
		
		cp5.addTextlabel("CONTROLS");

		rectPos = cp5.addGroup("rectPos").setPosition(10, 10)
		// .setBackgroundColor(TColor.BLUE.toARGB())
		// .setColorForeground(TColor.CYAN.toARGB())
				.setSize(230, 400)

		;

		createRect = cp5.addBang("createRect").setColorCaptionLabel(textCol)
				.setGroup("rectPos").setPosition(0, 15)
				.addListener(myListener)
				.setSize(sliderHeight, sliderHeight);

		/*
		 * xzPos = cp5.addSlider2D("X Z POS") .setColorCaptionLabel(textCol)
		 * .setPosition(0, sliderHeight+spacer+15).setGroup("rectPos")
		 * .setSize(100,100) .setArrayValue(new float[] {50, 50})
		 */
		// .disableCrosshair()

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

}
