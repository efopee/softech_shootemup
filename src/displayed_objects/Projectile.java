package displayed_objects;
import java.awt.Point;

public class Projectile extends Displayed {
	protected int damage;

	Projectile(Point startingPoint, double nozzleVel, int dmg){
		super(startingPoint, 0, nozzleVel);
		damage = dmg;
	}

}
