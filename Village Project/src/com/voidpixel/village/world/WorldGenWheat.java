package com.voidpixel.village.world;

import java.util.Random;

public class WorldGenWheat implements WorldGen{

	@Override
	public void generate(World world, Random rand) {
		for(int x = 0; x < world.width; x++) {
			for(int y = 0; y < world.height; y++) {
				generateTile(world, rand, x, y);
			}
		}
	}

	public void generateTile(World world, Random rand, int x, int y) {

		if(x > world.width / 2 || y < world.height / 2) return;
		if(rand.nextDouble()*100 >= 0.5) return;	
		
		for(int r = rand.nextInt(5) + 5; r > 0; r--) {
			
			for(int a = 0; a < 360; a++) {
				double angle = Math.toRadians(a);
				int nx = (int) (x + r * Math.cos(angle));
				int ny = (int) (y + r * Math.sin(angle));
				if(nx >= world.width || nx < 0 || ny >= world.height || ny < 0) continue;
				world.map[nx][ny] = World.wheat.id;
				world.meta[nx][ny][0] = World.wheat.getStartingAmount();
			}
			
		}
	}
	
	@Override
	public String getName() {
		return "Wheat";
	}

}
