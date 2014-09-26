package com.voidpixel.village.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.voidpixel.village.main.Camera;
import com.voidpixel.village.world.World;

public class TileTree extends TileResource{

	public TileTree(int id, String name, int minStart, int maxStart) {
		super(id, name, "Wood", minStart, maxStart, Color.white);
	}

	@Override
	public Color getColor(World world, int x, int y) { 	
		Color dColor = new Color(29, 36, 28);
		Color lColor = new Color(53, 67, 52);

		Tile t = world.getTile(x, y + 1);
		
		//If there is no tile below us, or it is not grass, draw a dark tile
		if(t == null || t.id != getID()) return dColor;
		
		Color lc = getColor(world, x, y + 1);
		
		if(lc.getRGB() == dColor.getRGB()) return new Color(40, 50, 39, 1);	
		
		int index = x+(y*world.width)+(x * world.height * y);
		Random rand = new Random((long)index);
		
		int cnt = rand.nextDouble() <= 0.5 ? 2 : 3;
		if(lc.getRGB() != lColor.getRGB() && lc.getAlpha() < cnt) return new Color(40, 50, 39, lc.getAlpha()+1);
		
		return lColor;
	}
	
	public void renderCamera(Camera c, World world, int x, int y, int scale) {
		Color clr = this.getColor(world, x, y);
		
		c.setColor(clr.getRed(), clr.getGreen(), clr.getBlue(), 255);
		c.fillRect(x * scale, y * scale, scale, scale);
	}
	
}
