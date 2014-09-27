package com.voidpixel.village.main.thread;

import com.voidpixel.village.interfaces.ThreadListener;

public class GameThread implements Runnable{
	
	public final String name;	
	public final int targetFramerate;
	public final ThreadListener listener;
	public Thread thread;
	
	public int framerate;
	public boolean running = false;
	
	public GameThread(ThreadListener listener, String name, int fps) {
		this.listener = listener;
		this.name = name;
		this.targetFramerate = fps;
	}
	
	public void Start() {
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		running = true;
		
		if(targetFramerate > 0)
			stableRun();
		else
			unstableRun();
		
	}
	
	protected void unstableRun() {
		long startTime = System.nanoTime();
		long tTime = 0;
		int fCount = 0;
		
		while(running) {
			// Increment the amount of frames that have passed by one
			fCount++;

			// Add the time it took to complete the last frame
			tTime += System.nanoTime() - startTime;

			// Turn the totaled time between frame and convert it into seconds.
			// If a second has passed, make the framerate the amount of frames
			// passed and reset all varibles.
			if ((double) tTime / (double) 1e9 >= 1) {
				framerate = fCount;
				tTime = 0;
				fCount = 0;
			}
			
			double delta = (double) (System.nanoTime() - startTime) / (double) 1e9;

			// Get the start time to compare against
			startTime = System.nanoTime();
			
			listener.threadTick(this, delta);

			try {
				Thread.sleep(10);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	protected void stableRun() {
		// Timing varibles
		long startTime = System.nanoTime(), waitTime, urdTime = 0;
		int targetTime = 1000 / targetFramerate;

		long tTime = 0;
		int fCount = 0;

		while (running) {
			// Increment the amount of frames that have passed by one
			fCount++;

			// Add the time it took to complete the last frame
			tTime += System.nanoTime() - startTime;

			// Turn the totaled time between frame and convert it into seconds.
			// If a second has passed, make the framerate the amount of frames
			// passed and reset all varibles.
			if ((double) tTime / (double) 1e9 >= 1) {
				framerate = fCount;
				tTime = 0;
				fCount = 0;
			}

			// Calculate the delta (time to complete last frame)
			double delta = (double) (System.nanoTime() - startTime) / (double) 1e9;

			// Get the start time to compare against
			startTime = System.nanoTime();

			// Update THEN render the game
			listener.threadTick(this, delta);
			
			// Get the difference in time from the start time to now
			// Also convert it into milliseconds so that sleep can use it
			urdTime = (System.nanoTime() - startTime) / (long) 1e6;

			// Calculate the difference in the time and target time so we can
			// sleep just the right amount
			waitTime = targetTime - urdTime;

			try {
				// Sleep the thread as long as waitTime isn't a negative value
				if (waitTime >= 0)
					Thread.sleep(waitTime);
			} catch (Exception e) {
				// Print any errors
				e.printStackTrace();
			}
		}
	}
}
