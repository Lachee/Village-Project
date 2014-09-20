package com.voidpixel.village.world;

import java.util.Random;

public interface WorldGen {
	public void Generate(World world, Random rand, int x, int y);
	public String toString();
}
