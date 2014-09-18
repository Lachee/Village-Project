package com.voidpixel.village.game;

import java.awt.Graphics;
import java.util.ArrayList;

import com.voidpixel.village.main.*;

public class MainGame{
	
	public Program program;
	public Canvas canvas;
	
	public ArrayList<Person> people = new ArrayList<Person>();
	
	public MainGame(Program program, Canvas canvas) {
		this.program = program;
		this.canvas = canvas;
	}
	
	public void update(double delta) {
	}
	
	public void render(Graphics g) {
	}
	
}
