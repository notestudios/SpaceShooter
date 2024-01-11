package com.notestudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import com.notestudios.entities.Entity;
import com.notestudios.entities.Player;
import com.notestudios.graphics.Spritesheets;
import com.notestudios.graphics.UI;
import com.notestudios.world.AsteroidSpawner;
import com.notestudios.world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	public int fps = 0;
	
	public static int mx, my;
	private boolean isRunning = true;
	public static final int WIDTH = 130;
	public static final int HEIGHT = 160;
	public static final int SCALE = 4;
	public double bgY = 0;
	public double bgY2 = 160;
	public double bgSpd = 2;
	public static int score = 0;
	public static double maxLife = 50;
	public static double life = maxLife;
	
	private BufferedImage image;
	
	public static World world;
	public static List<Entity> entities;
	public static Spritesheets spritesheet;
	public static Player player;
	public AsteroidSpawner spawner;
	public UI ui;
	
	public static boolean playSelected = false;
	public static boolean settingsSelected = false;
	public static boolean menuEnter = false;
	public static boolean Escape = false;
	
	//public static Color shipColor = Color.blue;
	
	public static String gameState = "startMenu";
	public static String version = "1.0";
	
	public static boolean startMenuEnter = false;
	public static boolean isPressingEnter = false;
	public static boolean pauseEsc = false;
	public static boolean suspended = false;

	
	public Game(){
		System.out.println("Starting Space Shooter...");
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		// Initializing objects
		spritesheet = new Spritesheets("/spritesheet.png");
		entities = new ArrayList<Entity>();
		player = new Player((WIDTH/2)-8,HEIGHT-18,16,16,1,spritesheet.getSprite(0, 0, 16, 16));
		spawner = new AsteroidSpawner();
		world = new World();
		ui = new UI();
		
		entities.add(player);
		
	}
	
	public void initFrame() {
		frame = new JFrame("Space Shooter");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop(){
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			isRunning = false;
		}
	}
	
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	public void tick(){
		spawner.tick();
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
		ui.tick();
		
		if(gameState == "inGame" || gameState == "startMenu") {
			bgSpd = 2;
			bgY-=bgSpd;
			if(bgY+HEIGHT <= 0) {
				bgY = HEIGHT;
			} bgY2-=bgSpd;
			if(bgY2+HEIGHT <= 0) {
				bgY2 = HEIGHT;
			}
		} if(gameState == "gameOptions") {
			bgSpd = 0.2;
			bgY-=bgSpd;
			if(bgY+HEIGHT <= 0) {
				bgY = HEIGHT;
			} bgY2-=bgSpd;
			if(bgY2+HEIGHT <= 0) {
				bgY2 = HEIGHT;
			}
		}
		if(!hasFocus() && gameState != "startMenu" && gameState != "gameOptions") {
			gameState = "pause";
			suspended = true;
		} if(suspended && hasFocus()) {
			suspended = false;
		}
	}
	


	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(10, 10, 10));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		
		/*Renderização do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		//world.render(g);
		g.drawImage(spritesheet.bg, 0, (int)bgY, null);
		g.drawImage(spritesheet.bg2, 0, (int)bgY2, null);
		
		Collections.sort(entities,Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		ui.render(g);
		if(Game.suspended) {
			g.setColor(new Color(30, 30, 31));
			g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		}
		
		g.setColor(Color.yellow);
		g.setFont(new Font("Segoe UI", Font.BOLD, 18));
		g.drawString(version, WIDTH*SCALE-100, HEIGHT*SCALE-16);
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				fps = frames;
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			
			player.right = true;
			
		} if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			
			player.left = true;
			
		} if(e.getKeyCode() == KeyEvent.VK_E) {
			
			player.isShooting = true;
			
		} if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			
			isPressingEnter = true;
			startMenuEnter = true;
			
		} if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(gameState == "inGame") {
				pauseEsc = true;
			} if(gameState == "gameOptions") {
				Escape = true;
			}
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			
			player.right = false;
			
		} if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			
			player.left = false;
			
		} if(e.getKeyCode() == KeyEvent.VK_E) {
			
			player.isShooting = false;
			
		} if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			
			isPressingEnter = false;
			startMenuEnter = false;
			
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(settingsSelected) {
				menuEnter = true;
			} else {
				menuEnter = false;
			}
			
			if(playSelected) {
				if(gameState == "pause") {
					pauseEsc = true;
				} if(gameState == "startMenu") {
					gameState = "inGame";
				}
			}
			
			if(gameState == "gameOptions") {
				ui.optionsEnter = true;
			} else {
				ui.optionsEnter = false;
			}
			
			if(gameState == "gameOptions") {
				if(ui.backMenuSelect && ui.optionsEnter) {
					ui.optionsEnter = false;
					Escape = true;
				}
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			player.isShooting = true;
		}
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			player.isShooting = false;
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(isRunning) {
			mx = e.getX();
			my = e.getY();
		}
		
		if(gameState == "pause" || gameState == "startMenu") {
			if(mx > 100 && my > 560 && mx < 422 && my < 592) {
				settingsSelected = true;
			} else {
				settingsSelected = false;
			}
			
			if(mx > 60 && my > 500 && mx < 460 && my < 550) {
				playSelected = true;
			} else {
				playSelected = false;
			}
		} if(gameState == "gameOptions") {
			if(mx > 0 && my > 240 && mx < 444 && my < 288) {
				ui.changeSpaceShipColorSelect = true;
			} else {
				ui.changeSpaceShipColorSelect = false;
			}
			
			if(mx > 0 && my > 360 && mx < 204 && my < 410) {
				ui.backMenuSelect = true;
			} else {
				ui.backMenuSelect = false;
			}
		}
		
	}

}
