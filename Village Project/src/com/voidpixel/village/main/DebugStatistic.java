package com.voidpixel.village.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import com.voidpixel.village.main.thread.GameThread;


public class DebugStatistic {
	
	//Program and canvas
	public Program program;
	public Canvas canvas;
	
	//Calculate and display statics?
	public boolean enable = false;
	
	//Threads to monitor
	public ArrayList<ThreadStats> threadStats = new ArrayList<ThreadStats>();
	
	public DebugStatistic(Program program, Canvas canvas) {
		this.program = program;
		this.canvas = canvas;
	}
	
	public void registerThread(GameThread thread, Color color) {
		threadStats.add(new ThreadStats(thread, color));
	}
	
	public void toggleEnabled() {
		enable = !enable;
	}
	
	public void setEnabled(boolean b) {
		enable = b;
	}
	
	double deltaCount = 0;
	public void update() {
		if(!enable) return;
		
		for(int i = 0; i < threadStats.size(); i++) {
			ThreadStats thread = threadStats.get(i);
			thread.update();
		}
	}
	
	public void renderGUI(Graphics g) {
		if(!enable) return;
		
		update();
		
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		g.setColor(Color.white);
		
		
		
		//FRAMERATE
		renderFramerate(g, 30, 20);
		g.setColor(Color.white);
		
		
		//Game Debug
		//TODO: Make this more standard
		program.game.renderDebug(g);
	}
	
	public void renderFramerate(Graphics g, int x, int y) {
		int graphScale = 2;

		//Box behind
		g.setColor(Color.black);
		g.fillRect(x - 20, y - 10, (ThreadStats.maxSamples * graphScale) + 30, 90 * graphScale + 20);
		
		
		//Draw the indercators
		g.setColor(new Color(100,100,100));
		
		//0 FPS
		int ypnt = y;
		int xpnt = x;
		g.drawString("0  ", xpnt - 15, ypnt + 5);
		g.drawLine(xpnt, ypnt, xpnt + (ThreadStats.maxSamples * graphScale), ypnt);

		//30FPS
		ypnt = y + (30 * graphScale);
		g.drawString("30 ", xpnt - 15, ypnt + 5);
		g.drawLine(xpnt, ypnt, xpnt + (ThreadStats.maxSamples * graphScale), ypnt);
		
		//60FPS
		ypnt = y + (60 * graphScale);
		g.drawString("60 ", xpnt - 15, ypnt + 5);		
		g.drawLine(xpnt, ypnt, xpnt + (ThreadStats.maxSamples * graphScale), ypnt);
		
		//100FPS
		ypnt = y + (90 * graphScale);
		g.drawString("90", xpnt - 15, ypnt + 5);		
		g.drawLine(xpnt, ypnt, xpnt + (ThreadStats.maxSamples * graphScale), ypnt);
		
		//Render the graphs
		for(int i = 0; i < threadStats.size(); i++) {
			ThreadStats thread = threadStats.get(i);
			
			g.setColor(thread.color);
			drawFramerateGraph(g, thread.framerates, x, y, graphScale, graphScale);
			
			int framerate = thread.thread.framerate;
			String text = "- " + thread.thread.name + " [" + framerate + "]";
			g.drawString(text, x + 5 + (ThreadStats.maxSamples * graphScale), y + 4 + ((framerate > 90 ? 90 : framerate) * graphScale));
		}
		
	}
	
	public void drawFramerateGraph(Graphics g, ArrayList<Integer> rates, int xOffset, int yOffset, int xScale, int yScale) {

		Polygon poly = new Polygon();
		
		for(int i = 0; i < rates.size(); i++) {
			int rate = rates.get(i) > 90 ? 90 : rates.get(i);
			poly.addPoint(xOffset + (i * xScale), yOffset + (rate * yScale));
		}
		
		g.drawPolyline(poly.xpoints, poly.ypoints, poly.npoints);
	}
}

class ThreadStats {
	public GameThread thread;
	public Color color;
	
	public ArrayList<Integer> framerates = new ArrayList<Integer>();
	
	public static int maxSamples = 200;
	
	public ThreadStats(GameThread t, Color c) {
		this.thread = t;
		this.color = c;
	}
	
	public void update() {
		this.update(thread.framerate);
	}
	
	public void update(int framerate) {
		if(framerates.size() > maxSamples)
			framerates.remove(0);
		
		framerates.add(framerate);
	}
}
