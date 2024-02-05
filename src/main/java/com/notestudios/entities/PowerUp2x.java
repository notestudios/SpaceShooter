package com.notestudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.notestudios.main.Game;

public class PowerUp2x extends Entity {
	
	public BufferedImage PowerUpSprite;
	
	private int powerUpTime = 0;
	private int maxPowerUpTime = 60*4;
	
	public PowerUp2x(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		PowerUpSprite = Game.spritesheet.getSprite(0, 64, 16, 16);
		
	}
	
	public void tick() {
		if(Game.gameState == "inGame") {
			y+=speed;
			if(getY() >= Game.HEIGHT) {
				Game.entities.remove(this);
				return;
			}
			
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Player) {
					if(isColidding(this, e)) {
						Game.player.powerUpX2 = true;
						Game.entities.remove(this);
						break;
					}
				}
			}
		} if(Game.player.powerUpX2) {
			powerUpTime++;
			if(powerUpTime == maxPowerUpTime) {
				powerUpTime = 0;
				Game.player.powerUpX2 = false;
			}
		}
	}
	
	public void render(Graphics g) {
		if(Game.gameState == "inGame" || Game.gameState == "pause") {
			g.drawImage(PowerUpSprite, getX(), getY(), null);
		}
	}

}
