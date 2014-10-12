package com.voidpixel.village.building;

import java.awt.Color;

import com.voidpixel.village.main.Camera;
import com.voidpixel.village.world.World;

public class Building {
	public int x, y, width, height;
	public String name;
	public Color color;
	
	public Building(int width, int height, String name, Color color) {
		this.x = -width;
		this.y = -height;
		this.width = width;
		this.height = height;
		this.name = name;
		this.color = color;
	}
		
	public Building(int x, int y, int width, int height, String name, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		this.color = color;
	}
	
	public void renderCamera(Camera c) {
		c.setColor(color);
		c.fillRect(x * World.scale, y * World.scale, width * World.scale, height * World.scale);
		
		c.setColor(color.darker().darker());
		c.drawStringCenter(this.name, x * World.scale + World.scale / 2, y * World.scale + World.scale / 2);
	}
}
