package com.voidpixel.village.tiles;

import java.awt.Color;

import com.voidpixel.village.world.World;

public class Tile {
	
	public final int id;
	public final String name;
	public Color color;
	
	public Tile(int id, String name, Color color) {
		this.name = name;
		this.color = color;
		this.id = id;
		World.tiles[id] = this;
	}
	
	public int getID() { return id; }	
	public String getName() { return this.name; }
	public Color getColor() { return this.color; }
	
}
