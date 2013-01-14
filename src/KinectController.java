import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.IntVector;
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
		kinect.enableUser(SimpleOpenNI.SKEL_PROFILE_NONE);
		
		
	}
	
	public void draw(){
		
		kinect.update();
		mainSketch.image(kinect.depthImage(), 0, 0);
		
		IntVector userList = new IntVector();
		kinect.getUsers(userList);
			
		for (int i = 0; i < userList.size(); i++) {
			int userId  = userList.get(i);
			PVector position = new PVector();
			kinect.getCoM(userId, position);
			kinect.convertRealWorldToProjective(position, position);
			mainSketch.fill(255,0,0);
			mainSketch.ellipse(position.x, position.y, 25, 25);
			mainSketch.println(position.x);
		}
	}

	private void drawSkeleton(int userID) {
		mainSketch.println("draw skeleton");
		
		mainSketch.stroke(0);
		mainSketch.strokeWeight(15);
		
		//kinect.drawLimb(userID, SimpleOpenNI.SKEL_HEAD, SimpleOpenNI.SKEL_NECK);
		drawJoint(userID, SimpleOpenNI.SKEL_HEAD);
		
	}
	
	private void drawJoint(int userID, int jointID) {
		PVector joint = new PVector();
		float confidence = kinect.getJointPositionSkeleton(userID, jointID, joint);
		if (confidence<0.5f){
			return;
		}
		PVector convertedJoint = new PVector();
		kinect.convertRealWorldToProjective(joint, convertedJoint);
		mainSketch.fill(255, 0,0);
		mainSketch.ellipse(convertedJoint.x, convertedJoint.y, 5, 5);
		
		
	}

	void onEndCalibration(int userID, boolean successful){
		if (successful){
			mainSketch.println("user calibrated!");
			kinect.startTrackingSkeleton(userID);
		}else{
			mainSketch.println("user calibration failed");
		}
	}
	
	void onNewUser(int userId){
		
		mainSketch.println("new user:"+userId);
	}

}
