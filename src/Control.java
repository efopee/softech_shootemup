import java.awt.Point;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Control {
	
	protected ArrayList<Player> players;
	protected ArrayList<Enemy> enemies;
	protected ArrayList<Projectile> projectiles;
	private Timer tim;
	private Gui gui;
	private Network net;
	
	public void setGUI(Gui g){
		gui = g;
	}
	public void setNetwork(Network n){
		net = n;
	}
	
	Control(int msRate){

		players = new ArrayList<>();
		enemies = new ArrayList<>();
		projectiles = new ArrayList<>();
		
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
			checkCoord = players.get(i).step();
		}
		
		for (int i=0; i<enemies.size(); i++){
			checkCoord = enemies.get(i).step();
			if(gui.OutOfBounds(checkCoord)){
				enemies.remove(i);
			}
		}
		
		for (int i=0; i<projectiles.size(); i++){
			checkCoord = projectiles.get(i).step();
			if(gui.OutOfBounds(checkCoord)){
				projectiles.remove(i);
			}
		}
	}
	
	private void assess(){}
	private void draw(){}

}
