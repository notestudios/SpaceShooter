package com.notestudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.notestudios.main.Game;

public class Bullets extends Entity{

	public Bullets(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		if(Game.gameState == "inGame") {
			y-=speed;
			if(getY() <= 0) {
				Game.entities.remove(this);
				return;
			}
		}
	}
	
	public void render(Graphics g) {
		if(Game.gameState == "inGame" || Game.gameState == "pause") {
			g.setColor(Player.spaceShipColor);
			g.fillRect(getX(), getY(), width, height);
		}
	}

}
