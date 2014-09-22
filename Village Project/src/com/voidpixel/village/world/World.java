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
	
	public static Tile grass = new Tile(0, "Grass", new Color(60, 84, 48));
	public static Tile stone = new Tile(1, "Stone", Color.gray, 0.25);
	public static Tile settlement = new Tile(2, "Settlement", Color.darkGray);
	public static TileTree tree = new TileTree(3, "Trees", 50, 100);
	public static TileResource wheat = new TileResource(4, "Wheat","Food", 25, 50, new Color(180, 170, 103));
	
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;

		map = new int[this.width][this.height];
		meta = new int[this.width][this.height];
		
		Random rand = new Random();
		long seed = rand.nextLong();
		rand.setSeed(seed);

		System.out.println("Generating World");
		
		WorldGen[] worldBrushes = new WorldGen[] { 
				new WorldGenForest(), 
				new WorldGenWheat(),  
				new WorldGenTest() 
		};
		
		for(WorldGen brush : worldBrushes) {
			
			System.out.println(" - Generating Brush \"" + brush.getName() + "\"");
			
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
		for(int y = 0; y < height; y++) { 
			for(int x = 0; x < width; x++) {
				Tile t = getTile(x,y);				

				if(t == null) {
					g.setColor(Color.magenta);
					g.fillRect(x * World.scale, y * World.scale, World.scale, World.scale);
				}else{
					t.render(g, this, x, y, World.scale);
				}
				
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
