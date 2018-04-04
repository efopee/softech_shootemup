import java.io.Serializable;
import java.util.ArrayList;

import displayed_objects.Enemy;
import displayed_objects.Player;
import displayed_objects.Projectile;

public class SerialGameState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<Enemy> enemies;
	private ArrayList<Player> players;
	private ArrayList<Projectile> plProjectiles;
	private ArrayList<Projectile> enProjectiles;
	private int score;
	
	public SerialGameState
	(
		ArrayList<Enemy> en,
		ArrayList<Player> pl,
		ArrayList<Projectile> plPr,
		ArrayList<Projectile> enPr,
		int scr
	){
		enemies = en;
		players = pl;
		plProjectiles = plPr;
		enProjectiles = enPr;
		score = scr;
	} 



	
}
