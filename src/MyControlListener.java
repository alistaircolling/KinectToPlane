import processing.core.PApplet;
import controlP5.ControlEvent;
import controlP5.ControlListener;


public class MyControlListener implements ControlListener {

	int col;
	private KinectToPlane sketch;
	private KinectCanvas canvas;
	
	public MyControlListener(KinectToPlane app) {
		sketch = app;
		canvas = app.kinectCanvas;
	}
	
	@Override
	  public void controlEvent(ControlEvent theEvent) {
		
	    StringBuilder stringBuilder = new StringBuilder();
	//	stringBuilder.append("i got an event from: ");
//		stringBuilder.append(theEvent.getController().getLabel());
	//	PApplet.println(stringBuilder.toString());
	    if (theEvent.getController().getLabel()=="createRect"){
	    	canvas.addRect();
	    }
	    if (theEvent.getController().getLabel()=="toggle Point Cloud"){
	    	sketch.println("toggle point cloud");
	    	sketch.togglePointCloud(theEvent.getController().getValue());
	    }
	    if (theEvent.getController().getLabel()=="Toggle Seq Draw"){
	    	sketch.println("Toggle Seq Draw");
	    	sketch.sequentialPoints = !sketch.sequentialPoints; 
	    }
		
	  }

}
