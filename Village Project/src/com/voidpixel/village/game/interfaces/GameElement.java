package com.voidpixel.village.game.interfaces;

import java.awt.Graphics;

public interface GameElement {
	public void update(double delta);
	public void render(Graphics g);
}
