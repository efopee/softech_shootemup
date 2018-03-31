package displayed_objects;
import java.awt.Point;

public class Player extends Killable {
	
	private static double speedQ = 10;

	public Player(Point startingPoint, int size, int health){
		super(startingPoint, 0, 0, size, health);
	}

	protected void setSpeed(double x, double y){
		speed[0] = x;
		speed[1] = y;
	}
	
	public void addToSpeed(int dvx, int dvy){
		speed[0] += dvx*speedQ;
		speed[1] += dvy*speedQ;
	}

	public Point stepLook(){
		double[] nextCoord = new double[2];
		nextCoord[0] = (double)coordinates.getX() + speed[0];
		nextCoord[1] = (double)coordinates.getY() + speed[1];
		Point next = new Point((int)nextCoord[0], (int)nextCoord[1]);
		return next;
	}
	
	public void setLoc(Point p){
		coordinates = p;
	}


}
