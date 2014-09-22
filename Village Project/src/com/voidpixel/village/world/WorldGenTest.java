package com.voidpixel.village.world;

import java.util.Random;

public class WorldGenTest implements WorldGen{

	@Override
	public void generate(World world, Random rand) {
		/*for(int i = 0; i < world.height; i++) {
			int x = world.width / 3;
			int y = i;
			world.map[x][y] = World.stone.id;
		}*/
	}

	@Override
	public String getName() {
		return "Test Generations";
	}

}
