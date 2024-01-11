package com.notestudios.world;

import com.notestudios.entities.Enemy;
import com.notestudios.entities.Entity;
import com.notestudios.main.Game;

public class AsteroidSpawner {
	
	public int targetTime = 60*2;
	public int curTime = 0;
	
	public void tick() {
		if(Game.gameState == "inGame") {
			curTime++;
			if(curTime == targetTime) {
				curTime = 0;
				int yy = -16;
				int xx = Entity.rand.nextInt(Game.WIDTH-16);
				Enemy enemy = new Enemy(xx, yy,16,16, Entity.rand.nextInt(3)+1, null);
				Game.entities.add(enemy);
			}
		}
	}
	
}
