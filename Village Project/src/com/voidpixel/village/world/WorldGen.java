package com.voidpixel.village.world;

import java.util.Random;

public interface WorldGen {
	public void generate(World world, Random rand);
	public String getName();
}
