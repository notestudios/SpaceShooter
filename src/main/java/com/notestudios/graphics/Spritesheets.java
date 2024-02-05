package com.notestudios.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Spritesheets {

	private BufferedImage spritesheet;
	public BufferedImage bg;
	public BufferedImage bg2;
	
	public Spritesheets(String path)
	{
		try {
			spritesheet = ImageIO.read(getClass().getResource(path));
			bg = ImageIO.read(getClass().getResource("/bg.png"));
			bg2 = ImageIO.read(getClass().getResource("/bg2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getSprite(int x,int y,int width,int height){
		return spritesheet.getSubimage(x, y, width, height);
	}
}
