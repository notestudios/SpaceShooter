package com.notestudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.notestudios.main.Game;


public class Enemy extends Entity{
	
	public boolean isDamaged = false;
	
	public BufferedImage asteroidSprite;
	public BufferedImage asteroidSprite0;
	public BufferedImage asteroidSprite1;
	public BufferedImage asteroidSprite2;
	public BufferedImage asteroidSprite3;
	
	public BufferedImage damageSprite;
	public BufferedImage damageSprite0;
	public BufferedImage damageSprite1;
	public BufferedImage damageSprite2;
	public BufferedImage damageSprite3;
	
	public static double life = 2;

	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		asteroidSprite0 = Game.spritesheet.getSprite(0, 16, 16, 16);
		asteroidSprite1 = Game.spritesheet.getSprite(16, 16, 16, 16);
		asteroidSprite2 = Game.spritesheet.getSprite(0, 32, 16, 16);
		asteroidSprite3 = Game.spritesheet.getSprite(16, 32, 16, 16);
		
		damageSprite0 = Game.spritesheet.getSprite(32, 16, 16, 16);
		damageSprite1 = Game.spritesheet.getSprite(48, 16, 16, 16);
		damageSprite2 = Game.spritesheet.getSprite(32, 32, 16, 16);
		damageSprite3 = Game.spritesheet.getSprite(48, 32, 16, 16);
		
		if(Entity.rand.nextInt(3) == 0) {
			asteroidSprite = asteroidSprite0;
			damageSprite = damageSprite0;
		} else if(Entity.rand.nextInt(3) == 1) {
			asteroidSprite = asteroidSprite1;
			damageSprite = damageSprite1;
		} else if(Entity.rand.nextInt(3) == 2) {
			asteroidSprite = asteroidSprite2;
			damageSprite = damageSprite2;
		} else if(Entity.rand.nextInt(3) == 3) {
			asteroidSprite = asteroidSprite3;
			damageSprite = damageSprite3;
		} else {
			asteroidSprite = asteroidSprite0;
			damageSprite = damageSprite0;
		}
		
		life = rand.nextInt(6);
	}
	
	public void tick() {
		if(Game.gameState == "inGame") {
			y+=speed;
			if(getY() >= Game.HEIGHT) {
				Game.entities.remove(this);
				Game.life-=rand.nextInt(6);
				return;
			}
			
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Bullets) {
					if(isColidding(this, e)) {
						life--;
						isDamaged = true;
						Game.entities.remove(e);
						if(life <= 0) {
							Game.entities.remove(this);
							Explosion explosion = new Explosion(getX(), getY(), 16, 16, 0, null);
							Game.entities.add(explosion);
							Game.score+=75;
							return;
						}
						break;
					} else {
						isDamaged = false;
					}
				}
			}
			
		}
	}
	
	public void render(Graphics g) {
		if(Game.gameState == "inGame" || Game.gameState == "pause") {
			if(isDamaged) {
				g.drawImage(damageSprite, getX(), getY(), null);
				isDamaged = false;
			} else if(!isDamaged) {
				g.drawImage(asteroidSprite, getX(), getY(), null);
			}
		}
	}


}
