package displayed_objects;

import java.awt.Point;

public class Killable extends Displayed {
	protected int health;
	private double size;
	
	Killable(Point startingPoint, double x, double y, double s, int h){
		super(startingPoint, x, y);
		size = s;
		health = h;
	}
	
	public boolean isHit(Projectile bullet){
		if(size > bullet.coordinates.distance(coordinates)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public int hit (Projectile bullet){
		health -= bullet.damage;
		return health;
	}
}
