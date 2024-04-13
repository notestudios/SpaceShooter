package com.notestudios.entities;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

import com.notestudios.main.Game;

public class Bullets extends Entity {

	private Color bulletColor = Player.spaceShipColor;

	public Bullets(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		if(Player.spaceShipColor == Color.black) 
			bulletColor = Color.white;
		
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
			g.setColor(bulletColor);
			g.fillRect(getX(), getY(), width, height);
		}
	}

}
