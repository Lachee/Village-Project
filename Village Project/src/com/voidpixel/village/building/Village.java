package com.voidpixel.village.building;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

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
		
	}
		
	@Override
	public void update(double delta) {
		Point mps = Input.getMousePosition();
		Point mp = game.canvas.camera.screenToWorld(mps);
		mouseOver = mp.x > x* World.scale && mp.x < x* World.scale + World.scale && mp.y > y* World.scale && mp.y < y* World.scale + World.scale;
		 
		if(mps.distance(450, 25) < 20 && Input.getMouseDown(Input.MOUSE_LEFT)) {
			game.cameraCyclePeople();
		}else if(Input.getMouse(Input.MOUSE_LEFT)) {
			int wx = mp.x / World.scale;
			int wy = mp.y / World.scale;
			if(game.world.inBounds(wx, wy) && game.world.getTile(wx, wy).getID() != World.tree.getID()) 
				game.world.setTile(wx, wy, World.tree.getID());
		}else if(Input.getMouse(Input.MOUSE_RIGHT)) {
			int wx = mp.x / World.scale;
			int wy = mp.y / World.scale;
			if(game.world.inBounds(wx, wy) && game.world.getTile(wx, wy).getID() != 0) 
				game.world.setTile(wx, wy, 0);
		}
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

		Point mp = Input.getMousePosition();

		startAA((Graphics2D)g);
		g.setColor(new Color(62, 69, 61));
	
		fillCircle(g, 50, 20, 25);	//Food
		fillCircle(g, 150, 20, 25);	//Wood
		fillCircle(g, 250, 20, 25);	//Metal
		fillCircle(g, 350, 20, 25);	//Stone
		
		if( mp.distance(450, 25) < 20) {
			Color c = g.getColor();
			g.setColor(Color.green);			
			for(Person p : game.people){
				Point pnt = game.canvas.camera.worldToScreen(new Point(p.getX() * World.scale + (World.scale / 2), p.getY() * World.scale+ (World.scale / 2)));
				g.drawLine(pnt.x, pnt.y, 450, 20);
			}
			
			g.setColor(c);
			fillCircle(g, 450, 20, 27);		
		}else{
			fillCircle(g, 450, 20, 25);	
		}
		
		g.fillRect(0, 0, game.canvas.getWidth(), 25);		
		
		drawImageCenter(g, "voidpixel", game.canvas.getWidth() - 42, 13, 32);
		
		g.setColor(Color.white);		
		g.drawString("x" + collectiveFood.getAmount(), 50 + 20, 20);	//Food
		g.drawString("x" + collectiveWood.getAmount(), 150 + 20, 20);	//Wood
		g.drawString("x" + collectiveMetal.getAmount(), 250 + 20, 20);	//Metal
		g.drawString("x" + collectiveStone.getAmount(), 350 + 20, 20);	//Stone
		g.drawString("x" + game.people.size(), 450 + 20, 20);	//Population

		endAA((Graphics2D)g);
		
		drawImageCenter(g, "resource_food", 50, 25, 20);
		drawImageCenter(g, "resource_wood", 150, 25, 20);
		drawImageCenter(g, "resource_metal", 250, 25, 20);
		drawImageCenter(g, "resource_stone", 350, 25, 20);
		drawImageCenter(g, "resource_people", 450, 25, 20);
		
		
		
	}
	
	void startAA(Graphics2D g2d) {

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);
		
	}
	
	void endAA(Graphics2D g2d) {

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);
		
	}
	
	void drawImageCenter(Graphics g, String image, int x, int y, int r) {
		g.drawImage(GameResources.getImage(image), x - r, y - r, r*2, r*2, null);
	}
	
	void fillCircle(Graphics g, int x, int y, int r) {
		g.fillOval(x - r, y - r, r * 2, r * 2);
	}	
	void drawCircle(Graphics g, int x, int y, int r) {
		g.drawOval(x - r, y - r, r * 2, r * 2);
	}

	
	public Point assignBuildingLocation(Building b) {
		//This method assigns a location for the new building. Does not apply the location to the building!
		//TODO: Calculate free spot for building.
		//TODO: See OrbisPartRandomiser from applet
		Point p = new Point();
		
		Random rand = new Random();
		p.x = this.x + rand.nextInt(10);
		p.y = this.y + rand.nextInt(10);
		
		buildings.add(b);
		return p;
	}

}
