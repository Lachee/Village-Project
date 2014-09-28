package com.voidpixel.village.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import com.voidpixel.village.main.Camera;
import com.voidpixel.village.tiles.*;

public class World {
	public int[][] map;
	public int[][][] meta;
	
	public final int width, height;
	public static final int scale = 10;
	
	public static Tile[] tiles = new Tile[256];
	
	public static Tile grass = new Tile(0, "Grass", new Color(60, 84, 48));
	public static Tile stone = new Tile(1, "Stone", Color.gray, 0.25);
	public static Tile settlement = new Tile(2, "Settlement", Color.darkGray);
	public static TileTree tree = new TileTree(3, "Trees", 50, 100);
	public static TileResource wheat = new TileResource(4, "Wheat","Food", 25, 50, new Color(180, 170, 103));
	
	public static boolean debugMode = false;
	
	public final long seed;
	
	public World(int width, int height) {
		this(width, height, 0);
	}
	
	public World(int width, int height, long seed) {
		this.width = width;
		this.height = height;

		map = new int[this.width][this.height];
		
		//We have 10 layers of metadata
		meta = new int[this.width][this.height][10];
		
		Random rand = new Random();
		
		if(seed == 0) seed = rand.nextLong();
		this.seed = seed;
		
		rand.setSeed(seed);

		System.out.println("Generating World");
		
		WorldGen[] worldBrushes = new WorldGen[] { 
				new WorldGenForest(), 
				new WorldGenWheat(),  
				new WorldGenTest() 
		};
		
		long worldStartTime = System.nanoTime();
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
		
		System.out.println("World created with seed " + seed + " in " + ((double)(System.nanoTime() - worldStartTime) / 1e9) + "s");
	}
	
	public Tile getTile(int x, int y) {
		if(x >= width || x < 0 || y >= height || y < 0) return null;
		return World.tiles[map[x][y]];
	}
	
	public void renderCamera(Camera c) {
		//TODO: Only render within bounds
	
		/*
		int minX = -(int) ((c.getRealX() / 10) * (c.getScale()));
		int minY = 0;
		int maxX = minX + (Program.WIDTH / 10);
		int maxY = minY + (Program.HEIGHT / 10);
		*/
		
		Point minScreen = c.screenToWorld(new Point(0,0));
		int minX = minScreen.x / World.scale + (debugMode ? 10 : 0) - 2;
		int minY = minScreen.y / World.scale + (debugMode ? 10 : 0) - 2;
		
		Point maxScreen = c.screenToWorld(new Point(c.canvas.getWidth(), c.canvas.getHeight()));
		int maxX = maxScreen.x / World.scale - (debugMode ? 10 : 0) + 2;
		int maxY = maxScreen.y / World.scale - (debugMode ? 10 : 0) + 2;
		 
		if(minX < 0) minX = 0;
		if(minY < 0) minY = 0;
		if(maxX > width) maxX = width;
		if(maxY > height) maxY = height;
		
		for(int y = minY; y < maxY; y++) {
			
			for(int x = minX; x < maxX; x++) {			
				Tile t = getTile(x, y);				

				if(t == null) {
					c.setColor(Color.magenta);
					c.fillRect(x * World.scale, y * World.scale, World.scale, World.scale);
				}else{
					t.renderCamera(c, this, x, y, World.scale);
				}
				
			}
		}
		
		/*
		c.setColor(86, 63, 41);
		c.fillRect(0, height * World.scale, width * World.scale, height * World.scale / 4);
		*/
 	}

	public void setTile(int x, int y, int id) {
		if(x >= width || x < 0 || y >= height || y < 0) return;
		
		int oldID = this.map[x][y];
		this.map[x][y] = id;	
		
		if(oldID != id)
			updateNeighbours(x, y, TileUpdateType.tileChange);
	}
	
	public void setMetadata(int x, int y, int z, int meta) { 
		if(x >= width || x < 0 || y >= height || y < 0 || z < 0 || z >= 10) return;	
		
		int oldMeta = this.meta[x][y][z];
		this.meta[x][y][z] = meta;	
		
		if(oldMeta != meta)
			updateNeighbours(x, y, TileUpdateType.metaChange);
	}
	
	public int getMetadata(int x, int y, int z) {
		if(x >= width || x < 0 || y >= height || y < 0 || z < 0 || z >= 10) return 0;		
		return meta[x][y][z];
	}
	
	public void updateNeighbours(int x, int y, TileUpdateType type) {

		System.out.println("Updating Neighbours of " + x + ", " + y + " ( " + type.toString() + " )");
		if(inBounds(x, y+1)) getTile(x, y+1).OnNeighbourUpdate(this, x, y + 1, type);
		if(inBounds(x, y-1)) getTile(x, y-1).OnNeighbourUpdate(this, x, y - 1, type);
		if(inBounds(x+1, y)) getTile(x+1, y).OnNeighbourUpdate(this, x+1, y, type);
		if(inBounds(x-1, y)) getTile(x-1, y).OnNeighbourUpdate(this, x-1, y, type);
	}
	
	public boolean inBounds(int x, int y, int z) {
		return inBounds(x,y) && !(z < 0 || z >= 10);
	}
	
	public boolean inBounds(int x, int y) {
		return !(x < 0 || x >= width || y < 0 || y >= height);
	}
}
