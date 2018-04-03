package displayed_objects;
import java.awt.Point;

public class Enemy extends Killable {

	public Enemy(Point startingPoint, double vx, double vy, int size, int health){
		super(startingPoint, vx, vy, size, health);
	}

}
