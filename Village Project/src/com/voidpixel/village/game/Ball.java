package com.voidpixel.village.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.voidpixel.village.game.interfaces.GameElement;
import com.voidpixel.village.main.Canvas;

public class Ball implements GameElement{
	public int x, y;
	public int vx, vy;
	public int radius = 50;
	public static Rectangle area;
	public Color color;
	
	public Ball(int x, int y) {
		this.x = x;
		this.y = y;

		this.vx = randInt(50, 255) * 2 * (Math.random() > 0.5 ? -1 : 1);
		this.vy = randInt(50, 255) * 2 *  (Math.random() > 0.5 ? -1 : 1);
		
		color = Color.black;
	}
	
	@Override
	public void update(double delta) {
		if((x - radius <= area.getMinX() && vx < 0) ||( x + radius >= area.getMaxX() && vx > 0)) vx = -vx;
		if((y - radius <= area.getMinY() && vy < 0) ||( y + radius >= area.getMaxY() && vy > 0)) vy = -vy;
		
		x += vx * delta;
		y += vy * delta;

		double redSine = Math.abs(Math.sin(Math.toRadians(x)));
		double greenCos = Math.abs(Math.cos(Math.toRadians(y)));
	
		
		int r = (int) (255 * redSine);
		int g = (int) (255 * greenCos);
		int b = (int) (r + g)/255;

		color = new Color(r,g,b);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(color);
		Canvas.fillCircle(g, x, y, radius);
	}
	
	public int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
