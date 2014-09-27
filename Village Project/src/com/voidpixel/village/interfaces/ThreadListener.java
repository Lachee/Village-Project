package com.voidpixel.village.interfaces;

import com.voidpixel.village.main.thread.GameThread;

public interface ThreadListener {
	public void threadTick(GameThread thread, double delta);
}
