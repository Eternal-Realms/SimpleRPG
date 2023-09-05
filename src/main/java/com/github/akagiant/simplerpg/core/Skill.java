package com.github.akagiant.simplerpg.core;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Random;

public class Skill {

	private final Player player;

	@Getter
	public int maxLevel;

	@Getter
	public int level;
	@Getter
	public double exp;

	private static double constant = 0.25;
	private static double power = 2.5;

	Random random;

	public Skill(Player player, int maxLevel) {
		this.player = player;
		this.maxLevel = maxLevel;
	}

	public void addExp(double min, double max) {
		if (this.level == maxLevel) return;

		this.exp += random.doubles(min, max + 1).findAny().getAsDouble();
		player.sendMessage("New Exp: " + this.exp);
		if (this.exp >= getExpFromCurrentToNext()) {
			this.level++;
			this.player.sendMessage("Leveled Up!");
		}
	}

	public static double convertLevelsToExp(int level) {
		return Math.pow((level/constant), power);
	}

	public double getExpFromCurrentToNext() {
		return convertLevelsToExp(this.level + 1);
	}

	public double getPercentageToNextLevel() {
		return (this.exp / getExpFromCurrentToNext()) * 100;
	}

}
