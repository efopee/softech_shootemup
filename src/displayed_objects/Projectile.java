package displayed_objects;
import java.awt.Point;

public class Projectile extends Displayed{

	/**
	 * 
	 */
	private static final long serialVersionUID = -480974622973022784L;
	protected int damage;

	public Projectile(Point startingPoint, double nozzleVel, int dmg){
		super(startingPoint, 0, nozzleVel);
		damage = dmg;
	}
}
