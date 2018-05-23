import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import displayed_objects.*;


public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;
	private GamePanel gamePanel;
	private UpperPanel upperPanel;
	private int displayWidth = 600;
	private int displayHeight = 500;
	private Control ctrl;
	private Gui g = this;
	private JMenu menu;
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
		
		menu = new JMenu("Start");
		JMenuItem menuItem = new JMenuItem("Start Server");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Control c = new Control(Control.PLAYERMODE.MULTI, Control.CONTROLMODE.MASTER, g, null);
				setControl(c);
				
				String IP = null;
				try {
					IP = Inet4Address.getLocalHost().getHostAddress();
				} catch (UnknownHostException e1) {
					System.out.println("Error during getting IP");
				}

				JOptionPane.showMessageDialog(
					    upperPanel, "Waiting for a cilent on IP " + IP);
				
			}
		});
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Connect to Server");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String)JOptionPane.showInputDialog(
	                    upperPanel,
	                    "Server IP address:",
	                    "IP address",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "192.168.0.0");

				//If a string was returned, say so.
				if ((s != null) && (s.length() > 0)) {
					Control c = new Control(Control.PLAYERMODE.MULTI, Control.CONTROLMODE.SLAVE, g, s);
					setControl(c);
				    return;
				}
				
				if (s != null)
					actionPerformed(e); //again
			

			}
		});
		
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Single Player");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Control c = new Control(Control.PLAYERMODE.SINGLE, Control.CONTROLMODE.MASTER, g, null);
					setControl(c);
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
		    
		    g.drawString(String.format("Score: %06d", score), 490, 15);
		}
	}
	
	
	private class GamePanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		private ArrayList<Projectile> playerBullets = new ArrayList<Projectile>();
		private ArrayList<Projectile> enemyBullets = new ArrayList<Projectile>();
		private ArrayList<Player> players = new ArrayList<Player>();
		private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		private ArrayList<Bumm> bumms = new ArrayList<Bumm>();
		
		private boolean gameOver = false;

		
	

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Color[] playerColors = {new Color(255,51,0), new Color(0,0,102)};
			
			g.setColor(new Color(0,0,102));
			
			if(gameOver){
				int fontSize = 34;

			    g.setFont(new Font("Arial", Font.PLAIN, fontSize));
			     
			    g.setColor(Color.black);
			    
			    g.drawString("Game Over", 220, 200);
			}
			else{
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
					g.setColor(new Color(255,51,0));
					int rad = (int)(bumm.age * 4);
					g.fillOval(bumm.pos.x - rad, bumm.pos.y - rad, rad*2, rad*2);
					
					bumm.age -= 0.5;
					
					if(bumm.age < 0){
						bumms.remove(bumm);
					}
				}
				int i = 0;
				for (Player player : players){
					
					if(player.getHealth() > 0){
						int width = 44; //osztható legyen 4-el
						int height = 24; //osztható legyen 2-vel
						int maxHP = 10;
						int[] coordsX = {player.getPlace().x - (width / 2), player.getPlace().x - (width/4), player.getPlace().x,
								player.getPlace().x + (width/4), player.getPlace().x + (width / 2), player.getPlace().x + (width / 2),
								player.getPlace().x - (width / 2)};
						int[] coordsY = {player.getPlace().y + (height/2), player.getPlace().y + (height/2), player.getPlace().y,
								player.getPlace().y + (height/2), player.getPlace().y + (height/2), player.getPlace().y + height,
								player.getPlace().y + height};
						
						g.setColor(playerColors[i]);
						g.fillPolygon(coordsX, coordsY, 7);
						
						
						int HPwidth = (int)((player.getHealth() / (float)maxHP) * width);
						int[] coordsHpX = {player.getPlace().x - (width / 2), player.getPlace().x + (HPwidth - width/2), player.getPlace().x + (HPwidth - width/2),
								player.getPlace().x - (width / 2)};
						int[] coordsHpY = {player.getPlace().y + height + 3, player.getPlace().y + height + 3, player.getPlace().y + height + 6,
								player.getPlace().y + height + 6};
						
						g.setColor(new Color(102, 255, 51));
						g.fillPolygon(coordsHpX, coordsHpY, 4);
						
					}
					i++;
					
					
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
	}

	 public void draw(
			 ArrayList<Enemy> enemies, 
			 ArrayList<Player> players, 
			 ArrayList<Projectile> plProjectiles, 
			 ArrayList<Projectile> enProjectiles,
			 ArrayList<Point> detonations
			 )
	 {
		 boolean gameover = true;
		 
		 for (Player player : players){
			 if(player.getHealth() > 0){
				 gameover = false;
				 break;
			 }
			 
		 }
		 
		 if(gameover){
			 gamePanel.gameOver = true;
		 
		 }
		 else{
			 gamePanel.enemies = enemies;
			 gamePanel.players = players;
			 gamePanel.enemyBullets = enProjectiles;
			 gamePanel.playerBullets = plProjectiles;
			 
			 for(int i = 0; i < detonations.size();i++){
				 gamePanel.bumms.add(new Bumm(detonations.get(i)));
			 }
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
	 
	 public void  disableStart(){
		 menu.removeAll();
	 }
	 
	 public void showError(String msg){
		 JOptionPane.showMessageDialog(upperPanel, msg);
	 }
	 
	 private class Bumm{
		 public Point pos;
		 public float age;
		 
		 Bumm(Point position){
			 pos = position;
			 age = 5;
		 }
	 }
}
