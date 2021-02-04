package me.phantomclone.client.clientlib.utils;

public class Timer {

	private long previousTime;
	private double lastMS = 0.0D;

	public static short convert(float perSecond) {
		return (short) (int) (1000.0F / perSecond);
	}

	public static long getCurrentTime() {
		return System.nanoTime() / 1000000L;
	}

	public Timer() {
		previousTime = -1L;
	}

	public long get() {
		return previousTime;
	}

	public boolean check(float milliseconds) {
		return (float) (getCurrentTime() - previousTime) >= milliseconds;
	}

	public void reset() {
		previousTime = getCurrentTime();
	}

	public void updateLastMS() {
		this.lastMS = System.currentTimeMillis();
	}

	public boolean hasTimePassed(long MS) {
		return System.currentTimeMillis() >= this.lastMS + MS;
	}
}
