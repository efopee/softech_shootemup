import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import displayed_objects.Enemy;
import displayed_objects.Player;
import displayed_objects.Projectile;

public class Control {
	
	public static enum BUTTONS{
		UP, DOWN, LEFT, RIGHT, CNTRL		
	}
	public static enum PLAYERMODE{
		SINGLE, MULTI
	}
	public static enum CONTROLMODE{
		MASTER, SLAVE
	}
	
	protected ArrayList<Player> players;
	protected ArrayList<Enemy> enemies;
	protected ArrayList<Projectile> plProjectiles;
	protected ArrayList<Projectile> enProjectiles;
	public ReentrantLock mutex;
	private Gui gui;
	private Point dimensions;
	private Network net;
	
	public void setGui(Gui g){
		gui = g;
		dimensions = gui.getDimensions();
	}
	public void setNetwork(Network n){
		net = n;
	}
	
	public void playerButtons(BUTTONS whichButton, boolean isItPressed){	
		mutex.lock();
		switch(whichButton){
		case UP:
			players.get(0).upButton(isItPressed);
			break;
		case DOWN:
			players.get(0).downButton(isItPressed);;
			break;
		case LEFT:
			players.get(0).leftButton(isItPressed);;
			break;
		case RIGHT:
			players.get(0).rightButton(isItPressed);;
			break;
		case CNTRL:
			playerShoots(0);
			break;
		default:
			break;
		}
		mutex.unlock();
	}
	
	Control(PLAYERMODE playerNumber, CONTROLMODE master, Gui g){

		players = new ArrayList<>();
		enemies = new ArrayList<>();
		plProjectiles = new ArrayList<>();
		enProjectiles = new ArrayList<>();
		
		mutex = new ReentrantLock();
		
		Player newPlayer = new Player(new Point(100,100), 10, 10);
		players.add(newPlayer);
		
		setGui(g);
		
	}
	
	void step(){	
		for (int i=0; i<players.size(); i++){
			Point next = players.get(i).stepLook(dimensions);
			players.get(i).setLoc(next);
		}
		
		for (int i=0; i<enemies.size(); i++){
			if(enemies.get(i).step(dimensions)){
				enemies.remove(i);
			}
		}
		
		for (int i=0; i<plProjectiles.size(); i++){
			if(plProjectiles.get(i).step(dimensions)){
				plProjectiles.remove(i);
			}
		}
		
		for (int i=0; i<enProjectiles.size(); i++){
			if(enProjectiles.get(i).step(dimensions)){
				enProjectiles.remove(i);
			}
		}
	}

	void assess(){
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
	
	void draw(){
		gui.draw(enemies, players, plProjectiles, enProjectiles);
	}
	
	public void addEnemy(){}
	
	public void playerShoots(int index){
		if(players.size() < index){
			return;
		}
		else{
			Point p = players.get(index).getPlace();
			Projectile proj = new Projectile(p, -10.0, 1);
			plProjectiles.add(proj);
		}
	}
	
	public void enemyShoots(int index){
		if(enemies.size() < index){
			return;
		}
		else{
			Point p = enemies.get(index).getPlace();
			Projectile proj = new Projectile(p, 10.0, 1);
			enProjectiles.add(proj);
		}
	}
}
