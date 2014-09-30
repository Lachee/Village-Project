package com.voidpixel.village.main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Canvas extends JComponent{
	private static final long serialVersionUID = 1L;

	public Program program;
	public Image screenCamera, screenGUI;
	public Camera camera;
		
	public Canvas(Program program){
		this.program = program;
		createScreen();
		
		camera = new Camera(this);

		// This code set's the cursor to nothing, so you have no cursor anymore!
		/*
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		this.setCursor(blankCursor);
		*/
	}
	
	public void createScreen() {
		screenCamera = this.createVolatileImage(getWidth(), getHeight());
		screenGUI = this.createVolatileImage(getWidth(), getHeight());
	}

	public void paintComponent(Graphics g) {
		createScreen();
	}
	
	public void render() {
		if(screenGUI == null || screenCamera == null) return;
		
		//render the camera
		renderCamera();
		
		//render the gui
		Graphics g = screenGUI.getGraphics();
		
		//The background (in this case, the main game camera
		g.drawImage(screenCamera, 0, 0, null);

		//The gui
		program.game.renderGUI(g);
		program.stats.renderGUI(g);
		
		//Dispose of the graphics
		g.dispose();
		
		//Applies all rendering to the canvas
		renderCanvas();
	}
	
	public void renderCanvas() {
		if(screenCamera == null || screenGUI == null) return;
		
		Graphics g = this.getGraphics();
		g.drawImage(screenGUI, 0, 0, null);
		g.dispose();
	}
	
	public void renderCamera() {
		if(screenCamera == null) return;
		
		Graphics2D g2d = (Graphics2D) screenCamera.getGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		g2d.drawImage(GameResources.getImage("skybox"), 0, 0, getWidth(), getHeight(), null);

		AffineTransform otx = g2d.getTransform();
		
		//Transform the graphics so we scale in the middle
		g2d.scale(camera.scale, camera.scale);
		g2d.translate(camera.x, camera.y);

		camera.setGraphics(g2d);		
		program.game.renderCamera(camera);

		//Revert the transformation
		g2d.setTransform(otx);
		
		//Basic Parallax Code!
		//g2d.fillRect(camera.getX(), camera.getY(), 10, 10);
		
		
		g2d.dispose();
	}	
}
