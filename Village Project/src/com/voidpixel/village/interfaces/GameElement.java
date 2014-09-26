package com.voidpixel.village.interfaces;

import java.awt.Graphics;
import com.voidpixel.village.main.Camera;

public interface GameElement {
	public void update(double delta);
	public void tick();
	public void renderCamera(Camera c);
	public void renderGUI(Graphics g);
}
