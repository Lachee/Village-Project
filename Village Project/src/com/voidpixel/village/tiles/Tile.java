package com.voidpixel.village.tiles;

import java.awt.Color;
import java.awt.Graphics;

import com.voidpixel.village.main.Camera;
import com.voidpixel.village.world.World;

public class Tile {
	
	public final int id;
	public final String name;
	public double speedModifier = 1;
	public Color color;
	
	public Tile(int id, String name, Color color, double smod) {
		this.name = name;
		this.color = color;
		this.id = id;
		this.speedModifier = smod;
		
		World.tiles[id] = this;
	}
	
	public Tile(int id, String name, Color color) {
		this.name = name;
		this.color = color;
		this.id = id;
		
		World.tiles[id] = this;
	}
	
	public int getID() { return id; }	
	public String getName() { return this.name; }
	public Color getColor(World world, int x, int y) { return this.color; }
	
	public void renderCamera(Camera c, World world, int x, int y, int scale) {
		c.setColor(this.getColor(world, x, y));
		c.fillRect(x * scale, y * scale, scale, scale);
	}
	
}
