package com.voidpixel.village.main;

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
	
	public int framerate = 60;
	public boolean running = false;

	private GameThread gameThread;
	private GameThread renderThread;

	public Canvas canvas;
	public MainGame game;
	public Listener listener;
	
	public Program() {
		super(TITLE + ": LOADING");

		canvas = new Canvas(this);
		this.setContentPane(canvas);

		game = new MainGame(this, canvas);

		listener = new Listener();
		this.addKeyListener(listener);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		this.addMouseWheelListener(listener);

		gameThread = new GameThread(this, "game", TARGET_FRAMERATE);
		renderThread = new GameThread(this, "render", 0);
		
		gameThread.Start();
		renderThread.Start();
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
	}

	public void update(double delta) {
		game.update(delta);
	}

	@Override
	public void threadTick(GameThread thread, double delta) {
		if(thread.name == gameThread.name) {			
			framerate = thread.framerate;
			update(delta);
		}
		
		if(thread.name == renderThread.name) {
			render();
		}
		
		this.setTitle(TITLE + " : U" + gameThread.framerate + " R" + renderThread.framerate + " Z" + canvas.camera.scale);
		
	}

}
