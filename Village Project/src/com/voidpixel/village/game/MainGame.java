package com.voidpixel.village.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.voidpixel.village.main.*;

public class MainGame{
	
	public Program program;
	public Canvas canvas;
	
	public ArrayList<Person> people = new ArrayList<Person>();
	public Ball[] balls;
	
	public MainGame(Program program, Canvas canvas) {
		this.program = program;
		this.canvas = canvas;
		balls = new Ball[5];
		for(int i = 0; i < balls.length; i++)
			balls[i] = new Ball(100, 100);
	}
	
	public void update(double delta) {
		Ball.area = new Rectangle(10,10,canvas.getWidth() - 20, canvas.getHeight() - 20);
		for(int i = 0; i < balls.length; i++)
			balls[i].update(delta);
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < balls.length; i++) {
			balls[i].render(g);
			
			g.setColor(Color.magenta);
			if(i != 0) {
				g.drawLine(balls[i].x, balls[i].y, balls[i-1].x, balls[i-1].y);				
			}else{
				g.drawLine(balls[i].x, balls[i].y, balls[balls.length-1].x, balls[balls.length-1].y);
			}

			
			g.setColor(Color.magenta.darker());
			g.drawLine(balls[i].x, balls[i].y, balls[i / 2].x, balls[i / 2].y);
			
			g.setColor(Color.magenta.darker().darker());
			g.drawLine(balls[i].x, balls[i].y, balls[i / 4].x, balls[i / 4].y);
			
		}
		
		g.setColor(Color.black);
		g.drawRect(10,10,canvas.getWidth() - 20, canvas.getHeight() - 20);
	}
	
}
