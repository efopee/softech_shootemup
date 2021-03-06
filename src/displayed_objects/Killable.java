package displayed_objects;

import java.awt.Point;

public class Killable extends Displayed{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2910201532306886364L;
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
		if(health > bullet.damage){
			health -= bullet.damage;
		}
		else{
			health = 0;
		}
		int ret = health;
		return ret;
	}
	
	public int getHealth(){
		int ret = health;
		return ret;
	}
}
