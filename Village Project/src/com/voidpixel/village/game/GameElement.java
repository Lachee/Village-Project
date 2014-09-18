package com.voidpixel.village.game;

import java.awt.Graphics;

public interface GameElement {
	public void update(double delta);
	public void render(Graphics g);
}
