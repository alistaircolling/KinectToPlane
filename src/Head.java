

import toxi.color.TColor;

import SimpleOpenNI.Vector3D;


public class Head {
	
	public int id;
	public Vector3D pos;
	public TColor col;
	
	public Head(int theId) {
		id = theId;
		col  = TColor.newRandom();
		
	}

}
