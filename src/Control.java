import java.awt.Point;
import java.util.ArrayList;
import java.util.Timer;
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
	protected ArrayList<Point> detonations;
	private int score;
	
	private PLAYERMODE playmode;
	private CONTROLMODE conmode;
	
	Timer tim;
	FrameRateTask frt;
	GameEventTask gvt;
	
	public ReentrantLock mutex;
	private Gui gui;
	private Point dimensions;
	private Network net;
	
	Control(PLAYERMODE playerNumber, CONTROLMODE master, Gui g, String ip){
		playmode = playerNumber;
		conmode = master;
		setGui(g);

		players = new ArrayList<>();
		enemies = new ArrayList<>();
		plProjectiles = new ArrayList<>();
		enProjectiles = new ArrayList<>();
		detonations = new ArrayList<>();
		
		tim = new Timer();
		frt = new FrameRateTask(this);
		gvt = new GameEventTask(this);
		
		mutex = new ReentrantLock();
		
		if(PLAYERMODE.SINGLE == playmode){
			Player newPlayer = new Player(new Point(dimensions.x/2,dimensions.y-100), 20, 10);
			players.add(newPlayer);
			startGame();
		}
		else{
			Player masterPlayer = new Player(new Point(dimensions.x/3,dimensions.y-100), 20, 10);
			Player slavePlayer = new Player(new Point(2*(dimensions.x/3),dimensions.y-100), 20, 10);
			players.add(masterPlayer);
			players.add(slavePlayer);

			if(CONTROLMODE.MASTER == conmode){


				if (net != null){
					net.disconnect();
				}
				net = new Server(this);
				net.connect("localhost");
			}
			else if(CONTROLMODE.SLAVE == conmode){
				if (net != null){
					net.disconnect();
				}
				net = new Client(this);
				net.connect(ip);		
			}
		}
	}
	
	private void setGui(Gui g){
		gui = g;
		dimensions = gui.getDimensions();
	}
	
	public SerialKeystroke recvedGamestate(SerialGameState gamestate){
		if((PLAYERMODE.MULTI == playmode) && (CONTROLMODE.SLAVE == conmode)){
			SerialKeystroke ret = new SerialKeystroke(
					players.get(1).getUp(),
					players.get(1).getDown(),
					players.get(1).getLeft(),
					players.get(1).getRight(),
					players.get(1).getShoot()
					);	

			gui.draw(
					gamestate.enemies,
					gamestate.players, 
					gamestate.plProjectiles,
					gamestate.enProjectiles,
					gamestate.detonations);
			gui.setScore(gamestate.score);

			return ret;
		}
		else return null;
	}
	
	public void recvedKeystroke(SerialKeystroke keystroke){
		if((PLAYERMODE.MULTI == playmode) && (CONTROLMODE.MASTER == conmode)){
			players.get(1).upButton(keystroke.up);
			players.get(1).downButton(keystroke.down);
			players.get(1).leftButton(keystroke.left);
			players.get(1).rightButton(keystroke.right);
		}
	}
	
	public void sendGamestate(){
		if(PLAYERMODE.SINGLE == playmode){
			
		}
		if(CONTROLMODE.MASTER == conmode){
			SerialGameState gamestate = new SerialGameState(
					enemies,
					players,
					plProjectiles,
					enProjectiles,
					detonations,
					score);
			net.send(gamestate);
		}
	}
	
	public void playerButtons(BUTTONS whichButton, boolean isItPressed){	
		mutex.lock();
		int whichPlayer;
		if((PLAYERMODE.MULTI == playmode) && (CONTROLMODE.SLAVE == conmode)){
			whichPlayer = 1;
		}
		else{
			whichPlayer = 0;
		}
		switch(whichButton){
		case UP:
			players.get(whichPlayer).upButton(isItPressed);
			break;
		case DOWN:
			players.get(whichPlayer).downButton(isItPressed);
			break;
		case LEFT:
			players.get(whichPlayer).leftButton(isItPressed);
			break;
		case RIGHT:
			players.get(whichPlayer).rightButton(isItPressed);
			break;
		case CNTRL:
			shootButton(isItPressed);
			break;
		default:
			break;
		}
		mutex.unlock();
	}
	
	private void shootButton(boolean pressed){
		int whichPlayer;
		if((PLAYERMODE.MULTI == playmode) && (CONTROLMODE.SLAVE == conmode)){
			whichPlayer = 1;
		}
		else{
			whichPlayer = 0;
		}
		players.get(whichPlayer).shootButton(pressed);
	}
	
	public void startGame(){
		tim.scheduleAtFixedRate(gvt, 0, 100);
		tim.scheduleAtFixedRate(frt, 0, 20);
	}
	
	void step(){	
		for (int i=0; i<players.size(); i++){
			players.get(i).stepLook(dimensions);
			if(players.get(i).isShoot()){
				Point shootFrom = players.get(i).getPlace();
				Projectile bullet = new Projectile(shootFrom, -10, 1);
				plProjectiles.add(bullet);
			}
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
		detonations = new ArrayList<Point>();
		
		ArrayList<Enemy> hitEnemies = new ArrayList<Enemy>();
		ArrayList<Projectile> hitPlProjectiles = new ArrayList<Projectile>();
		for(int i=0; i<enemies.size(); i++){
			for(int j=0; j<plProjectiles.size(); j++){
				if(enemies.get(i).isHit(plProjectiles.get(j))){
					hitPlProjectiles.add(plProjectiles.get(j));
					detonations.add(plProjectiles.get(j).getPlace());
					
					int remainingHealth = enemies.get(i).hit(plProjectiles.get(j));
					if(1 > remainingHealth){
						hitEnemies.add(enemies.get(i));
						score++;
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
					detonations.add(enProjectiles.get(j).getPlace());
					
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
		gui.draw(enemies, players, plProjectiles, enProjectiles, detonations);
		gui.setScore(score);
	}
	
	void addEnemy(int width, double vx, double vy, int health){
		Enemy newEnemy = new Enemy(new Point(width, 0), vx, vy, 20, health);
		enemies.add(newEnemy);
	}
	
	void enemyShoots(double ran){
		if(1>enemies.size() || ran>1 || ran<0){
			
		}
		else{
			int index = (int)Math.floor(ran*enemies.size());
			Point enemyShootFrom = enemies.get(index).getPlace();
			int enemyHealth = enemies.get(index).getHealth();
			Projectile newEnemyProjectile = new Projectile(enemyShootFrom, 10, enemyHealth);
			enProjectiles.add(newEnemyProjectile);
		}
	}
	
	public int getScrWidth(){
		int ret = dimensions.x;
		return ret;
	}
}
