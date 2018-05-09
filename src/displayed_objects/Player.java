package displayed_objects;
import java.awt.Point;
import java.util.concurrent.locks.ReentrantLock;

public class Player extends Killable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1858955017752315299L;

	double[] nextCoord;
	
	private static double speedQ = 7;
	
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean shoot = false;
	private ReentrantLock shootLock;

	public Player(Point startingPoint, int size, int health){
		super(startingPoint, 0, 0, size, health);
		nextCoord = new double[2];
		shootLock = new ReentrantLock();

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
	public void shootButton(boolean pressed){

		if(pressed){
			if(shootLock.isLocked()){

			}
			else{
				shootLock.lock();
				shoot = pressed;
			}
		}
		else if(!pressed){
			if(shootLock.isLocked()){
				shootLock.unlock();
				shoot = pressed;
			}
			else{

			}
		}
	}
	
	public void setButtons(
			boolean upPressed,
			boolean downPressed,
			boolean leftPressed,
			boolean rightPressed
			)
	{
		up = upPressed;
		down = downPressed;
		left = leftPressed;
		right = rightPressed;
		
		setSpeed();
	}
	
	public boolean getUp(){
		return up;
	}
	public boolean getDown(){
		return down;
	}
	public boolean getLeft(){
		return left;
	}
	public boolean getRight(){
		return right;
	}
	public boolean getShoot(){
		return shoot;
	}

	public boolean isShoot(){
		boolean ret = shoot;
		shoot = false;
		return ret;
	}
	
	public void stepLook(Point screenSize){
		nextCoord[0] = (double)coordinates.getX() + speed[0];
		nextCoord[1] = (double)coordinates.getY() + speed[1];
		Point next = new Point((int)nextCoord[0], (int)nextCoord[1]);
		if
		(
			0 > next.getX() ||
			screenSize.getX() < next.getX()
		){
			next.x = coordinates.x;
		}
		if
		(
			0 > next.getY() ||
			screenSize.getY() < next.getY()
		){
			next.y = coordinates.y;
		}
		coordinates = next;
	}

}
