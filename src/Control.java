import java.awt.Point;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import displayed_objects.Enemy;
import displayed_objects.Player;
import displayed_objects.Projectile;

public class Control {
	
	protected ArrayList<Player> players;
	protected ArrayList<Enemy> enemies;
	protected ArrayList<Projectile> plProjectiles;
	protected ArrayList<Projectile> enProjectiles;
	private Timer tim;
	private Gui gui;
	private Network net;
	
	public void setGUI(Gui g){
		gui = g;
	}
	public void setNetwork(Network n){
		net = n;
	}
	
	public void movePlayer(int dx, int dy){
		//TODO: Ezt Dani hívja GUIból.
	}
	
	Control(int msRate){

		players = new ArrayList<>();
		enemies = new ArrayList<>();
		plProjectiles = new ArrayList<>();
		enProjectiles = new ArrayList<>();
		
		tim = new Timer();
		tim.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				step();
				assess();
				draw();
			}
		}, null, msRate);
	}
	
	private void step(){
		Point checkCoord;
		
		for (int i=0; i<players.size(); i++){
			checkCoord = players.get(i).stepLook();
			if(gui.OutOfBounds(checkCoord)){}
			else{
				players.get(i).setLoc(checkCoord);
			}
		}
		
		for (int i=0; i<enemies.size(); i++){
			checkCoord = enemies.get(i).step();
			if(gui.OutOfBounds(checkCoord)){
				enemies.remove(i);
			}
		}
		
		for (int i=0; i<plProjectiles.size(); i++){
			checkCoord = plProjectiles.get(i).step();
			if(gui.OutOfBounds(checkCoord)){
				plProjectiles.remove(i);
			}
		}
		
		for (int i=0; i<enProjectiles.size(); i++){
			checkCoord = enProjectiles.get(i).step();
			if(gui.OutOfBounds(checkCoord)){
				enProjectiles.remove(i);
			}
		}
	}

	private void assess(){
		ArrayList<Enemy> hitEnemies = new ArrayList<Enemy>();
		ArrayList<Projectile> hitPlProjectiles = new ArrayList<Projectile>();
		for(int i=0; i<enemies.size(); i++){
			for(int j=0; j<plProjectiles.size(); j++){
				if(enemies.get(i).isHit(plProjectiles.get(j))){
					hitPlProjectiles.add(plProjectiles.get(j));
					int remainingHealth = enemies.get(i).hit(plProjectiles.get(j));
					if(1 > remainingHealth){
						hitEnemies.add(enemies.get(i));
					}
				}
			}
			enemies.removeAll(hitEnemies);
			plProjectiles.removeAll(hitPlProjectiles);
		}
		
		ArrayList<Player> hitPlayers = new ArrayList<Player>();
		ArrayList<Projectile> hitEnProjectiles = new ArrayList<Projectile>();
		for(int i=0; i<players.size(); i++){
			for(int j=0; j<enProjectiles.size(); j++){
				if(players.get(i).isHit(enProjectiles.get(j))){
					hitEnProjectiles.add(enProjectiles.get(j));
					int remainingHealth = players.get(i).hit(enProjectiles.get(j));
					if(1 > remainingHealth){
						hitEnemies.add(enemies.get(i));
					}
				}
			}
			players.removeAll(hitPlayers);
			enProjectiles.removeAll(hitEnProjectiles);
		}
	}
	
	private void draw(){}
	
	public void addEnemy(){}
	
	public void playerShoots(int index){
		if(players.size() < index){
			return;
		}
		else{
			Point p = players.get(index).getPlace();
			Projectile proj = new Projectile(p, -5.0, 1);
			plProjectiles.add(proj);
		}
	}
	
	public void enemyShoots(int index){
		if(enemies.size() < index){
			return;
		}
		else{
			Point p = enemies.get(index).getPlace();
			Projectile proj = new Projectile(p, 5.0, 1);
			enProjectiles.add(proj);
		}
	}
}
