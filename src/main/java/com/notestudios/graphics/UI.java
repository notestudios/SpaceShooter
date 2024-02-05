package com.notestudios.graphics;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.notestudios.entities.Player;
import com.notestudios.main.Game;

public class UI {
	
	
	public static int seconds = 0;
	public static int minutes = 0;
	public static int frames = 0;
	
	public boolean changeSpaceShipColorSelect = false;
	public boolean optionsEnter = false;
	public boolean backMenuSelect = false;
	
	public String showSpaceshipColor;
	
	public void tick() {
		if(Game.gameState == "inGame") {
			frames++;
			if(frames == 60) {
				frames = 0;
				seconds++;
				if(seconds == 60) {
					seconds = 0;
					minutes++;
				}
			}
		} if(Game.gameState == "startMenu") {
			if(Game.startMenuEnter) {
				if(Game.isPressingEnter) {
					Game.startMenuEnter = false;
				}
				Game.gameState = "inGame";
			}
		} if(Game.pauseEsc) {
			if(Game.gameState != "pause") {
				Game.pauseEsc = false;
				Game.gameState = "pause";
			} else if(Game.gameState == "pause") {
				Game.pauseEsc = false;
				Game.gameState = "inGame";
			}
		} if(Game.gameState == "startMenu" || Game.gameState == "pause") {
			if(Game.settingsSelected && Game.menuEnter) {
				Game.settingsSelected = false;
				Game.menuEnter = false;
				Game.gameState = "gameOptions";
			}
		} if(optionsEnter) {
			optionsEnter = false;
			if(changeSpaceShipColorSelect) {
				if(Player.spaceShipColor == Color.blue) {
					Player.spaceShipColor = Color.orange;
				} else if(Player.spaceShipColor == Color.orange) {
					Player.spaceShipColor = Color.magenta;
				} else if(Player.spaceShipColor == Color.magenta) {
					Player.spaceShipColor = Color.pink;
				} else if(Player.spaceShipColor == Color.pink) {
					Player.spaceShipColor = Color.blue;
				}
			}
			
		} if(Player.spaceShipColor == Color.blue) {
			showSpaceshipColor = "Blue";
		} else if(Player.spaceShipColor == Color.orange) {
			showSpaceshipColor = "Orange";
		} else if(Player.spaceShipColor == Color.magenta) {
			showSpaceshipColor = "Purple";
		} else if(Player.spaceShipColor == Color.pink) {
			showSpaceshipColor = "Pink";
		}
		
		if(Game.Escape) {
			Game.Escape = false;
			Game.gameState = "startMenu";
		}
		
		if(backMenuSelect && optionsEnter) {
			optionsEnter = false;
			Game.Escape = true;
		}
		
	}

	public void render(Graphics g) {
		if(Game.gameState == "inGame") {
			String formatTime = "";
			if(minutes < 10) {
				formatTime+="0"+minutes+":";
			}else {
				formatTime+=minutes+":";
			}
			
			if(seconds < 10) {
				formatTime+="0"+seconds;
			} else {
				formatTime+=seconds;
			}
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 16));
			g.drawString(formatTime, 10, 20);
			g.drawString("Score: "+Game.score, 10, 40);
			
			g.setColor(Color.lightGray);
			g.fillRoundRect(Game.WIDTH-123, 58+30, 31, 155, 16, 16);
			g.setColor(Color.orange);
			g.fillRoundRect(Game.WIDTH-120, 60+30, 25, (int)((Game.maxLife)*3), 16, 16);
			g.setColor(Color.yellow);
			g.fillRoundRect(Game.WIDTH-120, 60+30, 25, (int)((Game.life/Game.maxLife) * 150), 16, 16);
			
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 14));
			g.drawString("Life:", 10, 80);
			
		} 
		
		if(Game.gameState == "startMenu") {
			
			g.setColor(new Color(20, 20, 21));
			g.fillRoundRect(40, 70+4, 350*2, 110, 16, 16);
			
			g.setColor(new Color(30, 30, 31));
			g.fillRoundRect(45, 70, 350*2, 110, 16, 16);
			
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 28));
			g.drawString("Space", 170+12/*+12*/, 80+35);
			g.drawString("Shooter", 170, 120+35);
			
			g.drawImage(Game.player.spaceShip, 290, 80, 96, 96, null);
			
			g.setColor(new Color(30, 30, 31));
			g.fillRoundRect(60, 500, 400, 48, 16, 16);
			
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 20));
			g.drawString("Press Enter to Start", 165, 530);
			if(!Game.playSelected)
				g.setColor(Color.white);
			else if(Game.playSelected)
				g.setColor(Player.spaceShipColor);
			g.drawRoundRect(60, 500, 400, 48, 16, 16);
			
			g.setColor(new Color(30, 30, 31));
			g.fillRoundRect(100, 560, 320, 32, 16, 16);
			g.setColor(new Color(255, 255, 255));
			g.drawRoundRect(100, 560, 320, 32, 16, 16);
			
			if(Game.settingsSelected) {
				g.setColor(Player.spaceShipColor);
				g.drawRoundRect(100, 560, 320, 32, 16, 16);
			}
			
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 18));
			g.drawString("Game Options", 200, 582);
			/*
			g.setColor(new Color(30, 30, 31));
			g.fillRoundRect(470, 365, 400, 48, 16, 16);*/
		} 
		
		if(Game.gameState == "pause") {
			
			g.setColor(new Color(20, 20, 21));
			g.fillRoundRect(40, 70+4, 350*2, 180, 16, 16);
			
			g.setColor(new Color(30, 30, 31));
			g.fillRoundRect(45, 70, 350*2, 110, 16, 16);
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 28));
			g.drawString("Space", 170+12/*+12*/, 80+35);
			g.drawString("Shooter", 170, 120+35);
			
			
			g.drawImage(Game.player.spaceShip, 290, 80, 96, 96, null);
			
			g.setColor(new Color(30, 30, 31));
			g.fillRoundRect(60, 500, 400, 48, 16, 16);
			
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 20));
			g.drawString("Press Esc to Resume", 162, 530);
			
			if(Game.playSelected) {
				g.setColor(Player.spaceShipColor);
			} else if(!Game.playSelected) {
				g.setColor(Color.white);
			}
			g.drawRoundRect(60, 500, 400, 48, 16, 16);
			
			g.setColor(Color.lightGray);
			g.setFont(new Font("Segoe UI", Font.BOLD, 50));
			g.drawString("II", 110+70, 120+65+50);
			
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 50));
			g.drawString("II", 106+70, 96+20+65+50);
			
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 26));
			g.drawString("Paused", 160+70, 110+65+50);
			/* Game Options */
			g.setColor(new Color(30, 30, 31));
			g.fillRoundRect(100, 560, 320, 32, 16, 16);
			g.setColor(new Color(255, 255, 255));
			g.drawRoundRect(100, 560, 320, 32, 16, 16);
			
			if(Game.settingsSelected) {
				g.setColor(Player.spaceShipColor);
				g.drawRoundRect(100, 560, 320, 32, 16, 16);
			}
			
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 18));
			g.drawString("Game Options", 200, 582);
			/***/
		}
		
		if(Game.gameState == "gameOptions") {
			
			g.setColor(new Color(30, 30, 31));
			g.fillRoundRect(-8, 240, 450, 48, 16, 16);
			
			g.fillRoundRect(-35, 300, 450, 48, 16, 16);
			
			if(!changeSpaceShipColorSelect) {
				g.setColor(Color.white);
			} else if(changeSpaceShipColorSelect) {
				g.setColor(Player.spaceShipColor);
			}
			g.drawRoundRect(-8, 240, 450, 48, 16, 16);
			g.setColor(Color.white);
			g.drawRoundRect(-35, 300, 450, 48, 16, 16);
			
			g.setColor(new Color(30, 30, 31));
			g.fillRoundRect(-8, 360, 210, 48, 16, 16);
			
			if(!backMenuSelect) {
				g.setColor(Color.white);
			} else if(backMenuSelect) {
				g.setColor(Player.spaceShipColor);
			}
			g.drawRoundRect(-8, 360, 210, 48, 16, 16);
			
			if(!changeSpaceShipColorSelect) {
				g.setColor(Color.white);
			} else if(changeSpaceShipColorSelect) {
				g.setColor(Player.spaceShipColor);
			}
			//g.drawRoundRect(100, 560, 320, 32, 16, 16);
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 26));
			g.drawString("Spaceship Color < "+showSpaceshipColor+" >", 45, 272);
			
			g.setFont(new Font("Segoe UI", Font.BOLD, 24));
			g.drawString("Coming Soon...", 110, 332);
			g.drawString("< Back", 55, 393);
			
		}
	}
	
}
