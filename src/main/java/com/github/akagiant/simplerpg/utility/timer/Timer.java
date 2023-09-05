package com.github.akagiant.simplerpg.utility.timer;

import com.github.akagiant.simplerpg.SimpleRPG;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class Timer {

	private int time;
	private final double ticksBetweenRuns;
	protected BukkitTask task;

	protected Timer(int time, double ticksBetweenRuns) {
		this.time = time;
		this.ticksBetweenRuns = ticksBetweenRuns;
		}

	public abstract void count(int current);

	public final void start() {
		task = new BukkitRunnable() {
			@Override
			public void run() {
				count(time);
				if (time -- <= 0) cancel();
			}
		}.runTaskTimer(SimpleRPG.getPlugin(), 0L, (long) ticksBetweenRuns);
	}

}
