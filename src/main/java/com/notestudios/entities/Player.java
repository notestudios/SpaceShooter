package com.notestudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.notestudios.main.Game;

public class Player extends Entity{

	public boolean right, left = false;
	
	public boolean isShooting = false;
	
	public int bulletSpeed = 4;
	
	public boolean isDamaged = false;
	public boolean powerUpX2 = false;
	
	public BufferedImage spaceShip;
	public BufferedImage damagedSpaceShip;
	
	public static Color spaceShipColor = Color.blue;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		damagedSpaceShip = Game.spritesheet.getSprite(64, 0, 16, 16);
	}
	
	public void tick() {
		
		if(spaceShipColor == Color.blue) {
			spaceShip = Game.spritesheet.getSprite(0, 0, 16, 16);
		} else if(spaceShipColor == Color.orange) {
			spaceShip = Game.spritesheet.getSprite(16, 0, 16, 16);
		} else if(spaceShipColor == Color.magenta) {
			spaceShip = Game.spritesheet.getSprite(32, 0, 16, 16);
		} else if(spaceShipColor == Color.pink) {
			spaceShip = Game.spritesheet.getSprite(48, 0, 16, 16);
		}
		
		if(Game.gameState == "inGame") {
			if(right) {
				x+=speed;
			}else if(left) {
				x-=speed;
			}
			
			if(x >= Game.WIDTH) {
				x=-16;
			}else if(x+16<=0) {
				x = Game.WIDTH;
			}
			
			if(isShooting) {
				isShooting = false;
				if(powerUpX2) {
					int xx = this.getX();
					int yy = this.getY();
					int xx2 = this.getX() + 12;
					Bullets bullets = new Bullets(xx, yy, 3, 3, bulletSpeed, null);
					Game.entities.add(bullets);
					Bullets bullets2 = new Bullets(xx2, yy, 3, 3, bulletSpeed, null);
					Game.entities.add(bullets2);
				} else if(!powerUpX2) {
					int xx = this.getX();
					int yy = this.getY();
					Bullets bullets = new Bullets(xx+6, yy, 3, 3, bulletSpeed, null);
					Game.entities.add(bullets);
				}
			}
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Enemy) {
					if(isColidding(this, e)) {
						isDamaged = true;
						
					}
				}
			}
		}

		if(Game.life <= 0) {
			Game.life = 0;
			restart();
		}
	}

	public void restart() {
		Game.life = Game.maxLife;
		Game.gameState = "";
	}
	
	public void render(Graphics g) {
		if(Game.gameState == "inGame" || Game.gameState == "pause") {
			if(!isDamaged) {
				g.drawImage(spaceShip, getX(), getY(), null);
			} else if(isDamaged) {
				isDamaged = false;
				g.drawImage(damagedSpaceShip, getX(), getY(), null);
			}
		}
	}

	

	


}
