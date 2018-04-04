package displayed_objects;
import java.awt.Point;

public class Enemy extends Killable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7266729611989525710L;

	public Enemy(Point startingPoint, double vx, double vy, int size, int health){
		super(startingPoint, vx, vy, size, health);
	}

}
