import java.util.Random;
import java.util.TimerTask;

public class GameEventTask extends TimerTask {
	Control ctrl;
	Random rand;
	int scrWidth;
	
	public GameEventTask(Control c){
		ctrl = c;
		rand = new Random();
		scrWidth = ctrl.getScrWidth();
	}
	@Override
	public void run() {
		ctrl.mutex.lock();
		
		if(0.05 > rand.nextDouble()){
			ctrl.addEnemy(rand.nextInt(scrWidth), 0, 6*rand.nextDouble()+1, 1);
		}
		
		if(0.2 > rand.nextDouble()){
			ctrl.enemyShoots(rand.nextDouble());
		}
		
		ctrl.mutex.unlock();
	}
}
