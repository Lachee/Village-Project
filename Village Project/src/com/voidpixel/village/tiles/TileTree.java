package com.voidpixel.village.tiles;

import java.awt.Color;

import com.voidpixel.village.main.Camera;
import com.voidpixel.village.world.World;

public class TileTree extends TileResource{

	public TileTree(int id, String name, int minStart, int maxStart) {
		super(id, name, "Wood", minStart, maxStart, Color.white);
	}

	public void calculateColor(World world, int x, int y) {
		Tile t = world.getTile(x, y + 1);
		
		if(t == null || t.id != getID()) world.setMetadata(x, y, 1, 1);
		
		int meta = world.getMetadata(x, y + 1, 1);
		if(meta >= 2) {
		
			int level = meta - 2 - 1;
			if(level > 0)
				world.setMetadata(x, y, 1, level + 2);
			else
				world.setMetadata(x, y, 1, 0);
		}else if(meta == 1) {
			world.setMetadata(x, y, 1, 3 + 2);
		}
		
		world.setMetadata(x, y, 1, 0);

/*
		Tile t = world.getTile(x, y + 1);
		
		//If there is no tile below us, or it is not grass, draw a dark tile
		if(t == null || t.id != getID()) return dColor;
		
		Color lc = getColor(world, x, y + 1);
		
		if(lc.getRGB() == dColor.getRGB()) return new Color(40, 50, 39, 1);	
		
		int index = x+(y*world.width)+(x * world.height * y);
		Random rand = new Random((long)index);
		
		int cnt = rand.nextDouble() <= 0.5 ? 2 : 3;
		if(lc.getRGB() != lColor.getRGB() && lc.getAlpha() < cnt) return new Color(40, 50, 39, lc.getAlpha()+1);*/
	}
	
	@Override
	public Color getColor(World world, int x, int y) { 	
		
		Color dColor = new Color(29, 36, 28);
		Color lColor = new Color(53, 67, 52);
		Color mColor =  new Color(40, 50, 39);

		int meta = world.getMetadata(x, y, 1);
		
		return meta == 0 ? lColor : (meta == 1 ? dColor : mColor);
	}
	
	public void renderCamera(Camera c, World world, int x, int y, int scale) {
		Color clr = this.getColor(world, x, y);
		
		c.setColor(clr.getRed(), clr.getGreen(), clr.getBlue(), 255);
		c.fillRect(x * scale, y * scale, scale, scale);
	}
	
	@Override
	public void OnNeighbourUpdate(World world, int x, int y, TileUpdateType type) { 
		calculateColor(world, x,y);
	}
	
}
