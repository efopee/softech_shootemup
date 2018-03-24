import java.awt.Point;

abstract class Displayed{
	
	protected Point coordinates;
	protected double[] speed;
	protected double[] realCoord;
	
	Displayed(Point startingPoint, double x, double y){	
		coordinates = startingPoint;
		realCoord[0] = (double)startingPoint.x;
		realCoord[1] = (double)startingPoint.y;
		speed[0] = x;
		speed[1] = y;
	}
	
	protected Point step(){
		realCoord[0] += speed [0];
		realCoord[1] += speed [1];
		coordinates.setLocation((int)realCoord[0], (int)realCoord[1]);
		
		return coordinates;
	}
	
}
