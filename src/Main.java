import java.util.Timer;

public class Main {

	public static void main(String[] args) {
		Gui g = new Gui();
		Control c = new Control(Control.PLAYERMODE.SINGLE, Control.CONTROLMODE.MASTER, g);
		g.setControl(c);
		
		Timer tim = new Timer();
		FrameRateTask frt = new FrameRateTask(c);
		GameEventTask gvt = new GameEventTask(c);
		tim.scheduleAtFixedRate(frt, 0, 20);
		tim.scheduleAtFixedRate(gvt, 0, 100);
	}
	
}
