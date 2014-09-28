package com.voidpixel.village.building;

import java.awt.Color;

import com.voidpixel.village.main.Camera;
import com.voidpixel.village.world.World;

public class Building {
	public int x, y, width, height;
	public String name;
	public Color color;
	
	public void renderCamera(Camera c) {
		c.drawRect(x * World.scale, y * World.scale, width * World.scale, height * World.scale);
	}
}
