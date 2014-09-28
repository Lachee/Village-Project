package com.voidpixel.village.main;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

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
	private GameThread inputThread;

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
		this.addKeyListener(input);
		this.addMouseListener(input);
		this.addMouseMotionListener(input);
		this.addMouseWheelListener(input);

		gameThread = new GameThread(this, "game", TARGET_FRAMERATE);
		renderThread = new GameThread(this, "render", 0);
		inputThread = new GameThread(this, "input", 10);
		
		stats = new DebugStatistic(this, canvas);
		stats.registerThread(gameThread, Color.red);
		stats.registerThread(renderThread, Color.green);
		stats.registerThread(inputThread, Color.blue);
		
		gameThread.Start();
		renderThread.Start();
		inputThread.Start();
	}

	public static void main(String[] args) {
		System.out.println("Loading Program...");

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
		

		if(Input.getKeyDown(KeyEvent.VK_SPACE)) {
			//System.out.println();
			System.out.print("Space Pressed!");
		}
		
		if(Input.getKey(KeyEvent.VK_SPACE))
			System.out.print("!");		
		
		if(Input.getKeyUp(KeyEvent.VK_SPACE)) {
			System.out.println();
			System.out.println("Space Released!");
		}
	}

	@Override
	public void threadTick(GameThread thread, double delta) {
		if(thread.name == gameThread.name) {		
			framerate = thread.framerate;
			update(delta);	
			Input.update();
		}
		
		if(thread.name == inputThread.name) {
			//Input.update();
		}
		
		if(thread.name == renderThread.name)
			render();
		
		
	}

}
