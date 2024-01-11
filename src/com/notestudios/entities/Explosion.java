package com.notestudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.notestudios.main.Game;

public class Explosion extends Entity{

	private int frames = 0;
	private final int targetFrames = 4;
	private final int maxAnimation = 2;
	private int curAnimation = 0;
	
	private BufferedImage[] explosionSprites;
	
	public Explosion(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		explosionSprites = new BufferedImage[3];
		
		explosionSprites[0] = Game.spritesheet.getSprite(0, 48, 16, 16);
		explosionSprites[1] = Game.spritesheet.getSprite(16, 48, 16, 16);
		explosionSprites[2] = Game.spritesheet.getSprite(32, 48, 16, 16);
		
	}
	
	public void tick() {
		frames++;
		if(frames == targetFrames) {
			frames = 0;
			curAnimation++;
		} if(curAnimation >= maxAnimation) {
			Game.entities.remove(this);
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(explosionSprites[curAnimation], getX(), getY(), null);
	}
	
}
