package com.voidpixel.village.building;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.voidpixel.village.game.*;
import com.voidpixel.village.interfaces.GameElement;
import com.voidpixel.village.main.Camera;
import com.voidpixel.village.main.GameResources;
import com.voidpixel.village.main.Input;
import com.voidpixel.village.world.World;

public class Village  implements GameElement{
	public final MainGame game;
	public int x, y;
	public boolean mouseOver = false;
	
	public Resource collectiveFood, collectiveWood, collectiveMetal, collectiveStone, collectiveScience;
	
	public ArrayList<Building> buildings = new ArrayList<Building>();
	
	public Village (MainGame game, int x, int y){

		collectiveFood = new Resource("Food", 200);
		collectiveWood = new Resource("Wood", 200);
		collectiveMetal = new Resource("Metal", 200);
		collectiveStone = new Resource("Stone", 200);
		collectiveScience = new Resource("Science", 0);
		
		this.x = x;
		this.y = y;
		this.game = game;
		
		buildings.add(new Building(x + 10, y + 2, 1, 1, "Tent", Color.DARK_GRAY));
	}
		
	@Override
	public void update(double delta) {
		
	}

	@Override
	public void tick() {
	}

	@Override
	public void renderCamera(Camera c) {
		int hs = World.scale / 2;
	
		for(final Building b : buildings) {
			if(mouseOver) {
				c.setColor(Color.green);
				c.drawLine(x * World.scale + hs, y * World.scale + hs, b.x * World.scale + hs, b.y * World.scale + hs);
			}
			
			b.renderCamera(c);
		}
		
		c.setColor(Color.darkGray);
		c.fillRect(x * World.scale , y * World.scale, World.scale, World.scale);
		
		
	
		c.setColor(Color.black);
		c.drawStringCenter("Town Center", x * World.scale + hs, y * World.scale + hs);
	}

	@Override
	public void renderGUI(Graphics g) {
		
		g.setColor(new Color(62, 69, 61));
		g.fillRect(0, 0, game.canvas.getWidth(), 25);
		
		fillCircle(g, 50, 20, 25);	//Food
		fillCircle(g, 150, 20, 25);	//Wood
		fillCircle(g, 250, 20, 25);	//Metal
		fillCircle(g, 350, 20, 25);	//Stone

		drawImageCenter(g, "voidpixel", game.canvas.getWidth() - 42, 13, 32);
		
		g.setColor(Color.white);		
		g.drawString("x" + collectiveFood.getAmount(), 50 + 20, 20);	//Food
		g.drawString("x" + collectiveWood.getAmount(), 150 + 20, 20);	//Wood
		g.drawString("x" + collectiveMetal.getAmount(), 250 + 20, 20);	//Metal
		g.drawString("x" + collectiveStone.getAmount(), 350 + 20, 20);	//Stone

		drawImageCenter(g, "resource_food", 50, 25, 20);
		drawImageCenter(g, "resource_wood", 150, 25, 20);
		drawImageCenter(g, "resource_metal", 250, 25, 20);
		drawImageCenter(g, "resource_stone", 350, 25, 20);
		
		
		
		g.setColor(Color.magenta);
		Point mp = Input.getMousePosition();
		
		
		
		g.drawLine(mp.x - 5, mp.y, mp.x + 5, mp.y);
		g.drawLine(mp.x, mp.y - 5, mp.x, mp.y + 5);
		
	}
	
	void drawImageCenter(Graphics g, String image, int x, int y, int r) {
		g.drawImage(GameResources.getImage(image), x - r, y - r, r*2, r*2, null);
	}
	
	void fillCircle(Graphics g, int x, int y, int r) {
		g.fillOval(x - r, y - r, r * 2, r * 2);
	}

}
