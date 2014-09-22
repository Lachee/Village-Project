package com.voidpixel.village.tiles;

import java.awt.Color;
import java.util.Random;

import com.voidpixel.village.game.Resource;
import com.voidpixel.village.world.World;

public class TileResource extends Tile{
	public final String resource;
	public final int minStart, maxStart;
	public TileResource(int id, String name, String resource, int minStart, int maxStart, Color color) {
		super(id, name, color);
		
		if(maxStart < minStart) maxStart = minStart;
		
		this.resource = resource;
		
		this.minStart = minStart;
		this.maxStart = maxStart;		
		
	}
	
	public int getStartingAmount() {
		Random rand = new Random();
		return rand.nextInt(this.maxStart - this.minStart) + this.minStart;
	}
	
	public String getResource() { return this.resource; }
	
	public Resource gatherResources(World world, int x, int y, int amount) {
		if(x >= world.width || x < 0 || y >= world.height || y < 0 || world.getTile(x, y).id != this.getID()) return null;
		
		int available = getResources(world, x, y);
		int newAmount = available - amount; if(newAmount < 0) newAmount = 0;
		int gathered = available - newAmount;
		
		world.setMetadata(x, y, newAmount);
		if(newAmount == 0)
			world.setTile(x, y, 0);
		
		return new Resource(this.resource, gathered);		
	}
	
	public int getResources(World world, int x, int y) {
		return world.getMetadata(x, y);
	}

	@Override
	public Color getColor(World world, int x, int y) { 
		if(world.getTile(x, y+1) == null || world.getTile(x, y+1).getID() != getID()) return new Color(color.getRed(), color.getGreen(), color.getBlue()).darker();
		
		Color c =  new Color(color.getRed(), color.getGreen(), color.getBlue());

		int index = x+(y*world.width)+(x * world.height * y);
		Random rand = new Random(index);
		return rand.nextDouble() <= 0.02 ? c.brighter(): c;
	}
	
}
