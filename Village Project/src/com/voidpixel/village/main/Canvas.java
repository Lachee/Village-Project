package com.voidpixel.village.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Canvas extends JComponent{
	private static final long serialVersionUID = 1L;

	public Program program;
	public Image screen;
	public Image skybox;
	
	public Camera camera;
	
	
	
	public Canvas(Program program){
		this.program = program;
		createScreen();
		
		camera = new Camera(this);
		
		try {
			if(new File("./bin/resources/skybox.png").exists())
				System.out.println("File Exist");
			
			skybox = ImageIO.read(new File("./bin/resources/skybox.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createScreen() {
		screen = this.createVolatileImage(getWidth(), getHeight());
	}

	public void paintComponent(Graphics g) {
		createScreen();
	}
	
	public void render() {
		if(screen == null) return;
		
		Graphics2D g2d = (Graphics2D) screen.getGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		if(skybox != null)
			g2d.drawImage(skybox, 0, 0, getWidth(), getHeight(), null);
		
		g2d.translate(getWidth() / 2, getHeight() / 2);
		g2d.scale(camera.scale, camera.scale);
		g2d.translate(-getWidth() / 2 + camera.x, -getHeight() / 2 + camera.y);

		camera.setGraphics(g2d);		
		program.game.renderCamera(camera);
		
		g2d.dispose();
		
		g2d = (Graphics2D)this.getGraphics();
		
		/*
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.translate(getWidth() / 2, getHeight() / 2);
		g2d.scale(camera.scale, camera.scale);
		g2d.translate(-getWidth() / 2, -getHeight() / 2);
		
		g2d.drawImage(screen, 0, 0, getWidth(), getHeight(), null);
		
		g2d.dispose();*/
		
		g2d.drawImage(screen, 0, 0, getWidth(), getHeight(), null);
		
		g2d.dispose();
	}	
}
