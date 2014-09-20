package com.voidpixel.village.world;

import java.util.Random;

public class WorldGenForest implements WorldGen{

	@Override
	public void Generate(World world, Random rand, int x, int y) {
		
		if(x < world.width / 2) return;
		if(rand.nextDouble() > 0.02) return;
		
		
		for(int r = rand.nextInt(5) + 5; r > 0; r--) {
			
			for(int a = 0; a < 360; a++) {
				double angle = Math.toRadians(a);
				int nx = (int) (x + r * Math.cos(angle));
				int ny = (int) (y + r * Math.sin(angle));
				if(nx >= world.width || nx < 0 || ny >= world.height || ny < 0) continue;
				world.map[nx][ny] = World.tree.id;
			}
			
		}
	}
	
	@Override
	public String toString() { 
		return "Forest Circles";
	}

}
