import java.io.Serializable;

public class SerialKeystroke implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6405871973602096743L;
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	public boolean shoot;
	
	public SerialKeystroke(
			boolean upKey,
			boolean downKey,
			boolean leftKey,
			boolean rightKey,
			boolean shootKey)
	{
		up = upKey;
		down = downKey;
		left = leftKey;
		right = rightKey;
		shoot = shootKey;
	}

}
