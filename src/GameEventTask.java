import java.util.Random;
import java.util.TimerTask;

public class GameEventTask extends TimerTask {
	Control ctrl;
	Random rand;
	
	public GameEventTask(Control c){
		ctrl = c;
		rand = new Random();
	}
	@Override
	public void run() {
		ctrl.mutex.lock();
		
		if(0.05 > rand.nextDouble()){
			ctrl.addEnemy(rand.nextInt(400), 0, 4*rand.nextDouble()+1, 1);
		}
		
		if(0.2 > rand.nextDouble()){
			ctrl.enemyShoots(rand.nextDouble());
		}
		
		ctrl.mutex.unlock();
	}
}
