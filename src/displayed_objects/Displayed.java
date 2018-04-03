package displayed_objects;
import java.awt.Point;

abstract class Displayed{
	
	protected Point coordinates;
	protected double[] speed;
	protected double[] realCoord;
	
	Displayed(Point startingPoint, double x, double y){	
		coordinates = startingPoint;
		realCoord = new double[2];
		realCoord[0] = startingPoint.x;
		realCoord[1] = startingPoint.y;
		speed = new double[2];
		speed[0] = x;
		speed[1] = y;
	}
	
	public boolean step(Point screenSize){
		realCoord[0] += speed [0];
		realCoord[1] += speed [1];
		coordinates.setLocation(realCoord[0], realCoord[1]);
		if
		(
			0 > coordinates.getX() ||
			screenSize.getX() < coordinates.getX() ||
			0 > coordinates.getY() ||
			screenSize.getY() < coordinates.getY()
		)return true;
		else return false;
	}
	
	public Point getPlace(){
		return coordinates;
	}
	
}
