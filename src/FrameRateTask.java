import java.util.TimerTask;

public class FrameRateTask extends TimerTask {
	Control ctrl;
	
	public FrameRateTask(Control c){
		ctrl = c;
	}
	@Override
	public void run() {
		ctrl.mutex.lock();
		
		ctrl.step();
		ctrl.assess();
		ctrl.draw();
		
		//ctrl.sendGamestate();
		
		ctrl.mutex.unlock();
	}
}
