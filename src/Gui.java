import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import displayed_objects.Enemy;
import displayed_objects.Player;
import displayed_objects.Projectile;


public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;
	private GamePanel gamePanel;
	private int displayWidth = 600;
	private int displayHeight = 500;
	private Control ctrl;
	
	
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
		gamePanel.setBackground(Color.white);
		gamePanel.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				
				int stepSize = 10;
				
				switch (e.getKeyCode()) {
				case 37: ctrl.movePlayer(-stepSize,0); //left
					ctrl.playerButtons(Control.BUTTONS.LEFT, true);
					/*
					 * TODO 
					 * Control.playerButtons (Control.BUTTONS whichButton, boolean isItPressed)
					 * ***
					 * 		Control.BUTTONS is an enum for UP, DOWN, LEFT, RIGHT, CNTRL
					 * ***
					 * 		isItPressed - true means the button has been pressed
					 * 					  false means the button has been released
					 * 					(CNTRL is only sensitive to keypress, no need to call on release)
					 */
					break;
				case 38: ctrl.movePlayer(0,-stepSize); //up
					break;
				
				case 39: ctrl.movePlayer(stepSize,0); //right
				break;
				
				case 40: ctrl.movePlayer(0,stepSize);//down
				break;

				default:
					break;
				}
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				
				
			}@Override
			public void keyTyped(KeyEvent e) {
				
				
			}
		});
		
		gamePanel.setFocusable(true);

		add(gamePanel);
		
		
		gamePanel.bullets.add(new Point(100, 100));
		gamePanel.players.add(new Point(300, 100));
		gamePanel.repaint();
		
		
		setVisible(true);
	}

	
	
	private class GamePanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		private ArrayList<Point> bullets = new ArrayList<Point>();
		private ArrayList<Point> players = new ArrayList<Point>();
		private ArrayList<Point> enemyies = new ArrayList<Point>();
		
		
		


		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
		    try {
		        Graphics2D g2D;
		        g2D = (Graphics2D) g;
		        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        String fileName = "enemy1.PNG";
		        Image img = getToolkit().getImage(fileName);
		        Image smallImage = img.getScaledInstance(30, -1, Image.SCALE_DEFAULT);
		        AffineTransform aTran = new AffineTransform();
		        aTran.translate(50.0f, 20.0f);
		        g2D.transform(aTran);
		        g2D.drawImage(img, 30, 30, 50, 50, this);
		      }
		    
		    catch (Exception e) {
		    	  System.out.println("error during loading image");
		      }
			
			g.setColor(Color.green);
			
			for (Point bullet : bullets){
				int[] bulletCoordsX = {bullet.x, bullet.x - 4, bullet.x + 4};
				int[] bulletCoordsY = {bullet.y, bullet.y + 8, bullet.y + 8};
				g.fillPolygon(bulletCoordsX, bulletCoordsY, 3);

			}
			
			for (Point player : players){
				g.fillRect(player.x, player.y, 50, 5);
				
			}
			
			

		}
	}
	
	
	
	


	public boolean outOfBounds(Point p){
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
	
	public void drawEnemies(ArrayList<Enemy> enemies){
		
	}
	public void drawPlayers(ArrayList<Player> players){
		
	}
	public void drawPlayerProjectiles(ArrayList<Projectile> plProjectiles){
		
	}
	public void drawEnemyProjectiles(ArrayList<Projectile> enProjectiles){
		
	}
}
