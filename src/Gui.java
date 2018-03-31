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

import displayed_objects.*;


public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;
	private GamePanel gamePanel;
	private int displayWidth = 600;
	private int displayHeight = 500;
	private Control ctrl;
	
	public void setControl(Control c){
		ctrl = c;
	}
	
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
				
				/*
				 * 
				 * Control.playerButtons (Control.BUTTONS whichButton, boolean isItPressed)
				 * ***
				 * 		Control.BUTTONS is an enum for UP, DOWN, LEFT, RIGHT, CNTRL
				 * ***
				 * 		isItPressed - true means the button has been pressed
				 * 					  false means the button has been released
				 * 					(CNTRL is only sensitive to keypress, no need to call on release)
				 */
				
				switch (e.getKeyCode()) {
				case 37: ctrl.playerButtons(Control.BUTTONS.LEFT, true);
					break;
				case 38: ctrl.playerButtons(Control.BUTTONS.UP, true);
					break;
				
				case 39: ctrl.playerButtons(Control.BUTTONS.RIGHT, true);
				break;
				
				case 40: ctrl.playerButtons(Control.BUTTONS.DOWN, true);
				break;
				
				case 17: ctrl.playerButtons(Control.BUTTONS.CNTRL, true);
				break;
				
				default:
					break;
				}
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case 37: ctrl.playerButtons(Control.BUTTONS.LEFT, false);
					break;
				case 38: ctrl.playerButtons(Control.BUTTONS.UP, false);
					break;
				
				case 39: ctrl.playerButtons(Control.BUTTONS.RIGHT, false);
				break;
				
				case 40: ctrl.playerButtons(Control.BUTTONS.DOWN, false);
				break;

				default:
					break;
				}
				
				
			}@Override
			public void keyTyped(KeyEvent e) {
				
				
			}
		});
		
		gamePanel.setFocusable(true);

		add(gamePanel);

		gamePanel.repaint();
		
		
		setVisible(true);
	}

	
	
	private class GamePanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		private ArrayList<Projectile> playerBullets = new ArrayList<Projectile>();
		private ArrayList<Projectile> enemyBullets = new ArrayList<Projectile>();
		private ArrayList<Player> players = new ArrayList<Player>();
		private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		
	

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(Color.green);
			
			for (Projectile bullet : playerBullets){
				int[] bulletCoordsX = {bullet.getPlace().x, bullet.getPlace().x - 4, bullet.getPlace().x + 4};
				int[] bulletCoordsY = {bullet.getPlace().y, bullet.getPlace().y + 8, bullet.getPlace().y + 8};
				g.fillPolygon(bulletCoordsX, bulletCoordsY, 3);

			}
			
			for (Projectile bullet : enemyBullets){
				int[] bulletCoordsX = {bullet.getPlace().x, bullet.getPlace().x - 4, bullet.getPlace().x + 4};
				int[] bulletCoordsY = {bullet.getPlace().y, bullet.getPlace().y - 8, bullet.getPlace().y - 8};
				g.fillPolygon(bulletCoordsX, bulletCoordsY, 3);

			}
			
			for (Player player : players){
				g.fillRect(player.getPlace().x, player.getPlace().y, 50, 5);
			
				
			}
			
			
		    try {
		        Graphics2D g2D;
		        g2D = (Graphics2D) g;
		        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        String fileName = "enemy1.PNG";
		        Image img = getToolkit().getImage(fileName);;
				
		        for(Enemy enemy : enemies){
			        g2D.drawImage(img, enemy.getPlace().x, enemy.getPlace().y, 50, 50, this);
				}
		      }
		    
		    catch (Exception e) {
		    	  System.out.println("error during loading image");
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
/*	
	public void drawEnemies(ArrayList<Enemy> enemies){
		gamePanel.enemies.clear();
		gamePanel.enemies.addAll(enemies);
		gamePanel.repaint();
		
	}
	public void drawPlayers(ArrayList<Player> players){
		gamePanel.players.clear();
		gamePanel.players.addAll(players);
		gamePanel.repaint();
		
	}
	public void drawPlayerProjectiles(ArrayList<Projectile> plProjectiles){
		gamePanel.playerBullets.clear();
		gamePanel.playerBullets.addAll(plProjectiles);
		gamePanel.repaint();
		
	}
	public void drawEnemyProjectiles(ArrayList<Projectile> enProjectiles){
		gamePanel.enemyBullets.clear();
		gamePanel.enemyBullets.addAll(enProjectiles);
		gamePanel.repaint();
	}
	*/
	 public void draw(ArrayList<Enemy> enemies, ArrayList<Player> players, ArrayList<Projectile> plProjectiles, ArrayList<Projectile> enProjectiles){
		 gamePanel.enemies = enemies;
		 gamePanel.players = players;
		 gamePanel.enemyBullets = enProjectiles;
		 gamePanel.playerBullets = plProjectiles;
		 
		 gamePanel.repaint();
	 }
}
