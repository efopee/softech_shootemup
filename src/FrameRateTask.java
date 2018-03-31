import java.util.TimerTask;

public class FrameRateTask extends TimerTask {
	Control ctrl;
	
	public FrameRateTask(Control c){
		ctrl = c;
	}
	@Override
	public void run() {
		ctrl.step();
		ctrl.assess();
		ctrl.draw();
	}

}
