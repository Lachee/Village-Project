package com.voidpixel.village.main;

import java.awt.Color;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.MessageDigest;

import javax.swing.JFrame;

import com.voidpixel.api.*;
import com.voidpixel.village.game.MainGame;
import com.voidpixel.village.interfaces.ThreadListener;
import com.voidpixel.village.main.thread.GameThread;

public class Program extends JFrame implements ThreadListener {

	private static final long serialVersionUID = 1L;
	// THIS IS A COMMENT
	public static int TARGET_FRAMERATE = 60;
	public static int WIDTH = 1024;
	public static int HEIGHT = 600;
	public static String TITLE = "Village";

	//this gets passed through update, but is store here anyway.
	public double delta = 0;
	
	public int framerate = 0;
	
	public boolean isLoading = false;

	private GameThread gameThread;
	private GameThread renderThread;
	//private GameThread inputThread;

	public Canvas canvas;
	public MainGame game;
	public Input input;
	
	public DebugStatistic stats;
	
	public Program() {
		super(TITLE + ": LOADING");

		isLoading = true;
	
		canvas = new Canvas(this);
		this.setContentPane(canvas);

		game = new MainGame(this, canvas);

		input = new Input();	
		Input.canvas = canvas;
		
		this.addKeyListener(input);
		this.addMouseListener(input);
		this.addMouseMotionListener(input);
		this.addMouseWheelListener(input);

		gameThread = new GameThread(this, "game", TARGET_FRAMERATE);
		renderThread = new GameThread(this, "render", 0);
		//inputThread = new GameThread(this, "input", 10);
		
		stats = new DebugStatistic(this, canvas);
		stats.registerThread(gameThread, Color.red);
		stats.registerThread(renderThread, Color.green);
		//stats.registerThread(inputThread, Color.blue);
		
		gameThread.Start();
		renderThread.Start();
		//inputThread.Start();
	}

	public static void main(String[] args) {
		System.out.println("Loading Program...");
		System.out.println("Working Directory: " + System.getProperty("user.dir"));

		
		// This is just to test the API and make sure it is working.
		System.out.println(" - Checking Connection and getting news...");
		VoidpixelAPI api = new VoidpixelAPI("villageProject", true);
		VoidpixelNews news = api.getLatestNews();
	
		if(api.getResponse() == VoidpixelResponse.Valid) {
			System.out.println("Connection Made... Latest News Feed:");
			System.out.println(news.title + " by " + news.author + " on the " + news.date_posted + ": ");
			System.out.println(news.text);
			System.out.println();
		}else{
			System.out.println("Connection Failed!");
			System.out.println(api.getResponse().getMessage());
		}
		
		Program program = new Program();
		program.setSize(WIDTH, HEIGHT);
		program.setLocationRelativeTo(null);
		program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		program.setVisible(true);

		
		System.out.println("Loading Finished.");
	}

	public void render() {
		canvas.render();
		
		if(isLoading) {
			isLoading = false;
			this.setTitle(TITLE);
		}
	}

	public void update(double delta) {
		game.update(delta);
	}
	
	
	public static void printStackTrace() {
		new Exception("Stack Trace:").printStackTrace();
		/*try {
			double answer = 42 / 0;
			System.out.println("All these squares make a circle x" + answer);
		}catch(Exception e) {
			e.printStackTrace();
		}*/
	}
	

	@Override
	public void threadTick(GameThread thread, double delta) {
		if(thread.name == gameThread.name) {		
			framerate = thread.framerate;
			update(delta);	
			Input.update();
		}
		
		//if(thread.name == inputThread.name) {
		//	//Input.update();
		//}
		
		if(thread.name == renderThread.name)
			render();
		
		
	}

	public static String hashString(String input) {
		try {
			byte[] inputBytes = input.getBytes("UTF-8");

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(inputBytes);

			StringBuilder hexString = new StringBuilder();
			for(int i = 0; i < digest.length; i++) {
				String hex = Integer.toHexString(0xFF & digest[i]);
				if (hex.length() == 1) 
				    hexString.append('0');
				
				hexString.append(hex);
			}
			
			return hexString.toString().toLowerCase();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
