import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;
	private GamePanel gamePanel;
	private int displayWidth = 800;
	private int displayHeight = 500;
	
	Gui(){
		super("Shoot'em up!");
		setSize(displayWidth, displayHeight + 50);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Start");
		
		JMenuItem menuItem = new JMenuItem("Connect...");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//ctrl.startClient();
			}
		});
		menu.add(menuItem);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		gamePanel = new GamePanel();
		gamePanel.setBounds(0, 20, displayWidth, displayHeight);

		add(gamePanel);
		
		
		gamePanel.bullets.add(new Point(100, 100));
		gamePanel.repaint();
		
		
		setVisible(true);
	}
	
	private class GamePanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		private ArrayList<Point> bullets = new ArrayList<Point>();
		private ArrayList<Point> players = new ArrayList<Point>();
	

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(Color.white);
			
			for (Point bullet : bullets){
				int[] bulletCoordsX = {bullet.x, bullet.x - 4, bullet.x + 4};
				int[] bulletCoordsY = {bullet.y, bullet.y + 8, bullet.y + 8};
				g.fillPolygon(bulletCoordsX, bulletCoordsY, 3);

			}
			
			for (Point player : players){
				
			}

		}
	}
	
	


	public boolean OutOfBounds(Point p){
		if(displayWidth < p.getX() ||
					  0 > p.getX() ||
		  displayHeight < p.getY() ||
					  0 > p.getY())
		{
			return true;
		}
		else{
			return false;
		}
	}
}
