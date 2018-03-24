import java.awt.Point;

public class Gui {
	private int displayWidth;
	private int displayHeight;
	
	public boolean OutOfBounds(Point p){
		if(displayWidth < p.getX() || displayHeight < p.getY()){
			return true;
		}
		else{
			return false;
		}
	}
}
