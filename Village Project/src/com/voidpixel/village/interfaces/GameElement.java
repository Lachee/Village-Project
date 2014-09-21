package com.voidpixel.village.interfaces;

import java.awt.Graphics;

public interface GameElement {
	public void update(double delta);
	public void tick();
	public void render(Graphics g);
}
