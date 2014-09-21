package com.voidpixel.village.world;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.voidpixel.village.tiles.*;

public class World {
	public int[][] map;
	public int[][] meta;
	
	public final int width, height;
	public static final int scale = 10;
	

	public static Tile[] tiles = new Tile[256];
	
	public static Tile grass = new Tile(0, "Grass", Color.green.brighter().brighter());
	public static Tile stone = new Tile(1, "Stone", Color.gray);
	public static Tile settlement = new Tile(2, "Settlement", Color.darkGray);
	public static TileResource tree = new TileResource(3, "Trees", "Wood", 50, 100, Color.green.darker());
	
	public WorldGen[] worldBrushes = new WorldGen[] { new WorldGenForest() };
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;

		map = new int[this.width][this.height];
		meta = new int[this.width][this.height];
		
		Random rand = new Random();
		long seed = rand.nextLong();
		rand.setSeed(seed);

		System.out.println("Generating World");
		for(WorldGen brush : worldBrushes) {
			
			System.out.println(" - Generating Brush \"" + brush.toString() + "\"");
			
			long stime = System.nanoTime();

			brush.generate(this, rand);
			
			long dtime = System.nanoTime() - stime;
			if(dtime > 1e9/2)
				System.out.println(" -- Slow Generation!");
			
			System.out.println(" -- Done in " + ((double)dtime / (double)1e9) + "s");
			
		}
		
		map[10][10] = World.settlement.id;
		
		System.out.println("World created with seed " + seed);
	}
	
	public Tile getTile(int x, int y) {
		if(x >= width || x < 0 || y >= height || y < 0) return null;
		return World.tiles[map[x][y]];
	}
	
	public void render(Graphics g) {
		//TODO: Only render within bounds
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				Tile t = getTile(x,y);				

				if(t == null)
					g.setColor(Color.magenta);
				else
					g.setColor(t.color);
				
				g.fillRect(x * World.scale, y * World.scale, World.scale, World.scale);
			}
		}
	}

	public void setTile(int x, int y, int id) {
		if(x >= width || x < 0 || y >= height || y < 0) return;
		this.map[x][y] = id;	
	}
	
	public void setMetadata(int x, int y, int meta) { 
		if(x >= width || x < 0 || y >= height || y < 0) return;
		this.meta[x][y] = meta;	
	}
	
	public int getMetadata(int x, int y) {
		if(x >= width || x < 0 || y >= height || y < 0) return 0;
		return meta[x][y];
	}
}
