package com.voidpixel.village.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import sun.security.krb5.internal.TGSRep;

public class Camera {
	protected double x = 0;
	protected double y = 0;
	
	protected double scale = 1;

	protected Graphics g;
	public Canvas canvas;
	
	public Camera (Canvas canvas) {
		this.canvas = canvas;
	}
	
	public Point worldToScreen(Point p) {
		double x = p.x * scale + this.x * scale;		
		double y = p.y * scale + this.y * scale;			
		
		return new Point((int)x, (int)y);
	}
	
	
	public Point screenToWorld(Point p) {
		
		double x = (p.x - (this.scale * this.x)) / this.scale;
		double y = (p.y - (this.scale * this.y)) / this.scale;
		
		return new Point((int)x, (int)y);
	}
	
	public double getScale() { return this.scale; }
	public void setScale(double s) { 
		double so = this.scale;
		double sn = s < 0.001 ? 0.001 : s;
		
		/*
		 	== What we are basicly doing ==
			double oWidth = canvas.getWidth() / scaleOld;
			double nWidth = canvas.getWidth() / scaleNew;
			double xDiff = nWidth - oWidth;
			this.x += xDiff / 2;
		*/
		
		this.x += ((canvas.getWidth() / sn) - (canvas.getWidth() / so)) / 2;
		this.y += ((canvas.getHeight() / sn) - (canvas.getHeight() / so)) / 2;
		
		this.scale = sn;
	}

	public void centerPosition(double x, double y) {
		this.centerPosition(x, y, this.scale);
	}
	public void centerPosition(double x, double y, double scale) {
		setScale(1);
		this.x = (canvas.getWidth() / 2) - x;
		this.y = (canvas.getHeight() / 2) - y;
		setScale(scale);
	}
	
	public void zoom(double amount) { 
		setScale(scale + amount); 	
	}

	public double getRealX() { return this.x; }
	public double getRealY() { return this.y; }
	
	public int getX() { return (int) this.x; }
	public int getY() { return (int) this.y; }
	
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public void setPoint(Point p) { setX(p.x); setY(p.y); }
	public Point getPoint() { return new Point((int)this.x, (int)this.y); }
	public void translate(double x, double y) { this.x += x / this.scale; this.y += y / this.scale; }
	
	public void setGraphics(Graphics g) { if(g == null) return; this.g = g; }	
	public Graphics getGraphics() { return g; }
	
	//Colors
	public void setColor(Color c) { this.g.setColor(c); }
	public void setColor(int r, int g, int b) { this.g.setColor(new Color(r, g, b)); }
	public void setColor(int r, int g, int b, int a) { this.g.setColor(new Color(r, g, b, a)); }
	public Color getColor() { return this.g.getColor(); }
	
	//Rectangles
	public void fillRect(int x, int y, int width, int height) {
		this.g.fillRect(x, y, width, height);
	}
	public void drawRect(int x, int y, int width, int height) {
		this.g.drawRect(x, y, width, height);
	}

	//Line
	public void drawLine(int x1, int y1, int x2, int y2) {
		g.drawLine(x1, y1, x2, y2);
	}
	
	//Cirlces and ovals
	public void drawOval(int x, int y, int width, int height) {
		g.drawOval(x, y, width, height) ;
	}	
	public void fillOval(int x, int y, int width, int height) {
		g.fillOval(x, y, width, height) ;
	}
	public void drawCircle(int x, int y, int radius) {
		this.drawOval(x - radius, y - radius, radius * 2, radius * 2);
	}
	public void fillCircle(int x, int y, int radius) {
		this.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}
	
	//Images
	public void drawImage(Image image, int x, int y) {
		g.drawImage(image, x, y, null);
	}
	public void drawImage(Image image, int x, int y, int width, int height) {
		g.drawImage(image, x, y, width, height, null);
	}
	public void drawImage(String image, int x, int y) {
		drawImage(GameResources.getImage(image), x, y);
	}
	public void drawImage(String image, int x, int y, int width, int height) {
		drawImage(GameResources.getImage(image), x, y, width, height);
	}
	
	
	//String. These get scaled too as a warning
	public void drawString(String string, int x, int y) {
		g.drawString(string, x, y);
	}
	public void drawStringCenter(String string, int x, int y) {
		FontMetrics fm = g.getFontMetrics();
		
		int width = fm.stringWidth(string);
		this.drawString(string, x - (width/2), y);
	}
	
	public Rectangle getClipBounds() {
		Rectangle clip = new Rectangle();
		clip.x = (int) (getX() * getScale());
		clip.y = (int) (getY() * getScale());
		clip.width = (int) getScale();
		clip.height = (int) getScale();
		return clip;
	}

}
