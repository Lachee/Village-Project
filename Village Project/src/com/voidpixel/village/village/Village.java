package com.voidpixel.village.village;

import java.awt.Color;
import java.awt.Graphics;

import com.voidpixel.village.game.*;
import com.voidpixel.village.interfaces.GameElement;
import com.voidpixel.village.world.World;

public class Village  implements GameElement{
	public final MainGame game;
	public int x, y;
	
	//TODO: Change to modules or something
	public int level = 0;
	public Resource collectiveFood, collectiveWood, collectiveMetal, collectiveStone, collectiveScience;
		
	public Village (MainGame game, int x, int y){

		collectiveFood = new Resource("Food", 200);
		collectiveWood = new Resource("Wood", 200);
		collectiveMetal = new Resource("Metal", 200);
		collectiveStone = new Resource("Stone", 200);
		collectiveScience = new Resource("Science", 0);
		
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	@Override
	public void update(double delta) {
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x * World.scale - World.scale , y * World.scale - World.scale, 3 * World.scale, 3 * World.scale);
	}

}
