package displayed_objects;
import java.awt.Point;

public class Player extends Killable {
	
	double[] nextCoord;
	
	private static double speedQ = 7;
	
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;

	public Player(Point startingPoint, int size, int health){
		super(startingPoint, 0, 0, size, health);
		nextCoord = new double[2];
	}

	protected void setSpeed(){
		if(up == down){
			speed[1] = 0;
		}
		else if(up){
			speed[1] = -speedQ;
		}
		else{
			speed[1] = speedQ;
		}

		if(left == right){
			speed[0] = 0;
		}
		else if(left){
			speed[0] = -speedQ;
		}
		else{
			speed[0] = speedQ;
		}	
	}
	
	public void addToSpeed(int dvx, int dvy){		
		speed[0] += dvx*speedQ;
		speed[1] += dvy*speedQ;
	}
	
	public void upButton(boolean pressed){
		up = pressed;
		setSpeed();
	}
	public void downButton(boolean pressed){
		down = pressed;
		setSpeed();
	}
	public void leftButton(boolean pressed){
		left = pressed;
		setSpeed();
	}
	public void rightButton(boolean pressed){
		right = pressed;
		setSpeed();
	}

	public Point stepLook(Point screenSize){
		nextCoord[0] = (double)coordinates.getX() + speed[0];
		nextCoord[1] = (double)coordinates.getY() + speed[1];
		Point next = new Point((int)nextCoord[0], (int)nextCoord[1]);
		if
		(
			0 > nextCoord[0] ||
			screenSize.getX() < nextCoord[0]+50 ||
			0 > nextCoord[1] ||
			screenSize.getY() < nextCoord[1]+50
		){
			return coordinates;
		}
		else{
			return next;
		}
	}
	
	public void setLoc(Point p){
		coordinates = p;
	}


}
