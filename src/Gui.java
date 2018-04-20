import java.awt.Color;
import java.awt.Font;
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
	private UpperPanel upperPanel;
	private int displayWidth = 600;
	private int displayHeight = 500;
	private Control ctrl;
	
	public void setControl(Control c){
		ctrl = c;
	}
	
	public Point getDimensions(){
		Point ret = new Point(displayWidth, displayHeight);
		return ret;
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
		
		upperPanel = new UpperPanel();
		upperPanel.setBounds(0, 0, displayWidth, 20);
		upperPanel.score = 0;
		upperPanel.repaint();
		add(upperPanel);
		
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
				
				case 17: ctrl.playerButtons(Control.BUTTONS.CNTRL, false);
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
	
	private class UpperPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private int score;
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
		    int fontSize = 14;

		    g.setFont(new Font("Arial", Font.PLAIN, fontSize));
		     
		    g.setColor(Color.black);
		    
		    g.drawString(String.format("Score: %06d", score), 490, 15); //
		}
	}
	
	
	private class GamePanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		private ArrayList<Projectile> playerBullets = new ArrayList<Projectile>();
		private ArrayList<Projectile> enemyBullets = new ArrayList<Projectile>();
		private ArrayList<Player> players = new ArrayList<Player>();
		private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		private ArrayList<Bumm> bumms = new ArrayList<Bumm>();

		
	

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(new Color(0,0,102));
			
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
			
			for (Bumm bumm : bumms){
				int[] bummCoorsX = {bumm.pos.x - 20, bumm.pos.x - 10, bumm.pos.x - 5, bumm.pos.x - 1, bumm.pos.x + 7, bumm.pos.x + 10, bumm.pos.x + 22, bumm.pos.x + 8, bumm.pos.x + 18, bumm.pos.x + 5, bumm.pos.x - 4, bumm.pos.x - 10, bumm.pos.x - 17, bumm.pos.x - 6};
				int[] bummCoorsY = {bumm.pos.y - 0, bumm.pos.y - 2, bumm.pos.y - 15, bumm.pos.y - 4, bumm.pos.y - 17, bumm.pos.y - 5, bumm.pos.y - 18, bumm.pos.y - 3, bumm.pos.y + 20, bumm.pos.y + 3, bumm.pos.y + 16, bumm.pos.y + 2, bumm.pos.y + 19, bumm.pos.y + 6};
				g.fillPolygon(bummCoorsX, bummCoorsY, 14);
				
				bumm.age -= 1;
				
				if(bumm.age < 0){
					bumms.remove(bumm);
				}
			}
			
			for (Player player : players){
				int width = 44; //osztható legyen 4-el
				int height = 24; //osztható legyen 2-vel
				int maxHP = 10;
				int[] coordsX = {player.getPlace().x - (width / 2), player.getPlace().x - (width/4), player.getPlace().x,
						player.getPlace().x + (width/4), player.getPlace().x + (width / 2), player.getPlace().x + (width / 2),
						player.getPlace().x - (width / 2)};
				int[] coordsY = {player.getPlace().y + (height/2), player.getPlace().y + (height/2), player.getPlace().y,
						player.getPlace().y + (height/2), player.getPlace().y + (height/2), player.getPlace().y + height,
						player.getPlace().y + height};
				g.fillPolygon(coordsX, coordsY, 7);
				
				
				int HPwidth = (int)((player.getHealth() / (float)maxHP) * width);
				int[] coordsHpX = {player.getPlace().x - (width / 2), player.getPlace().x + (HPwidth - width/2), player.getPlace().x + (HPwidth - width/2),
						player.getPlace().x - (width / 2)};
				int[] coordsHpY = {player.getPlace().y + height + 3, player.getPlace().y + height + 3, player.getPlace().y + height + 6,
						player.getPlace().y + height + 6};
				
				g.setColor(new Color(44,224,113));
				g.fillPolygon(coordsHpX, coordsHpY, 4);
				System.out.println(player.getHealth());
				
				
			}
			
			
		    try {
		        Graphics2D g2D;
		        g2D = (Graphics2D) g;
		        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        String fileName = "enemy1.PNG";
		        Image img = getToolkit().getImage(fileName);
				
		        for(Enemy enemy : enemies){
			        g2D.drawImage(img, enemy.getPlace().x-25, enemy.getPlace().y-25, 50, 50, this);
				}
		      }
		    
		    catch (Exception e) {
		    	  System.out.println("error during loading image");
		      }
		    

			
		}
	}

	 public void draw(
			 ArrayList<Enemy> enemies, 
			 ArrayList<Player> players, 
			 ArrayList<Projectile> plProjectiles, 
			 ArrayList<Projectile> enProjectiles,
			 ArrayList<Point> detonations
			 )
	 {
		 gamePanel.enemies = enemies;
		 gamePanel.players = players;
		 gamePanel.enemyBullets = enProjectiles;
		 gamePanel.playerBullets = plProjectiles;
		 
		 for(Point deto : detonations){
			 gamePanel.bumms.add(new Bumm(deto));
		 }
		 
		 gamePanel.repaint();
	 }
	 
	 public void setScore(int score){
		 upperPanel.score = score;
		 upperPanel.repaint();
	 }
	 
	 public void drawBumm(Point p){
		 gamePanel.bumms.add(new Bumm(p));
	 }
	 
	 private class Bumm{
		 public Point pos;
		 public int age;
		 
		 Bumm(Point position){
			 pos = position;
			 age = 5;
		 }
	 }
}
