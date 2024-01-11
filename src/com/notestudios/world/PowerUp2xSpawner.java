package com.notestudios.world;

import com.notestudios.entities.Entity;
import com.notestudios.entities.PowerUp2x;
import com.notestudios.main.Game;

public class PowerUp2xSpawner {
	
	public int targetTime = 60*1;
	public int time = 0;
	
	
	public void tick() {
		if(Game.gameState == "inGame") {
			time++;
			if(time == targetTime) {
				time = 0;
				int yy = -16;
				int xx = Entity.rand.nextInt(Game.WIDTH-16);
				PowerUp2x powerUp = new PowerUp2x(xx, yy,16,16, Entity.rand.nextInt(3)+1, null);
				Game.entities.add(powerUp);
			}
		}
	}
	
}
