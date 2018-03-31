package displayed_objects;
import java.awt.Point;

public class Player extends Killable {
	
	private static double speedQ = 1;

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
		Point next = coordinates;
		next.x = (int)(realCoord[0] + speed [0]);
		next.y = (int)(realCoord[1] + speed [1]);
		return next;
	}
	
	public void setLoc(Point p){
		coordinates = p;
	}


}
