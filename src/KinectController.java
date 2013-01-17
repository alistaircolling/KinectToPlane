import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.IntVector;
import SimpleOpenNI.SimpleOpenNI;

public class KinectController {

	private KinectToPlane mainSketch;
	public SimpleOpenNI kinect;
	public ArrayList<Head> heads;
	public boolean kinectConnected;

	public KinectController(KinectToPlane sketch) {

		mainSketch = sketch;

		init();
	}

	private void init() {

		if (kinectConnected) {
			kinect = new SimpleOpenNI(mainSketch);
			kinect.enableDepth();
			kinect.enableRGB();
			kinect.enableUser(SimpleOpenNI.SKEL_PROFILE_NONE);
		}
		mainSketch.println("KINECT INITIALISED");

	}

	public void draw() {

		if (kinectConnected) kinect.update();

		// check if we are using the user list or the point cloud

		if (mainSketch.showPointCloud) {

			drawHitArea();

		} else {
			if (!kinectConnected) return;

			IntVector userList = new IntVector();
			kinect.getUsers(userList);
			heads = new ArrayList<Head>();

			for (int i = 0; i < userList.size(); i++) {
				int userId = userList.get(i);
				mainSketch.println("user does not exist:" + userId);
				Head head = new Head(userId);

				PVector position = new PVector();
				kinect.getCoM(userId, position);
				kinect.convertRealWorldToProjective(position, position);
				// mainSketch.fill(255,0,0);
				// mainSketch.ellipse(position.x, position.y, 25, 25);
				head.pos.x = position.x;
				head.pos.y = position.y;
				heads.add(head);
			}
		}

	}

	private void drawHitArea() {

	}

	private boolean checkIfUserExists(int userId) {
		for (int i = 0; i < heads.size(); i++) {
			Head head = heads.get(i);
			if (head.id == userId) {
				return true;
			}
		}
		return false;
	}

	private void drawSkeleton(int userID) {
		mainSketch.println("draw skeleton");

		mainSketch.stroke(0);
		mainSketch.strokeWeight(15);

		// kinect.drawLimb(userID, SimpleOpenNI.SKEL_HEAD,
		// SimpleOpenNI.SKEL_NECK);
		drawJoint(userID, SimpleOpenNI.SKEL_HEAD);

	}

	private void drawJoint(int userID, int jointID) {
		PVector joint = new PVector();
		float confidence = kinect.getJointPositionSkeleton(userID, jointID,
				joint);
		if (confidence < 0.5f) {
			return;
		}
		PVector convertedJoint = new PVector();
		kinect.convertRealWorldToProjective(joint, convertedJoint);
		mainSketch.fill(255, 0, 0);
		mainSketch.ellipse(convertedJoint.x, convertedJoint.y, 5, 5);

	}

	void onEndCalibration(int userID, boolean successful) {
		if (successful) {
			mainSketch.println("user calibrated!");
			kinect.startTrackingSkeleton(userID);
		} else {
			mainSketch.println("user calibration failed");
		}
	}

	void onNewUser(int userId) {

		mainSketch.println("new user:" + userId);
	}

}
