package com.github.akagiant.simplerpg.core;

import org.bukkit.entity.Player;

public class Strength {

	private Player player;

	public Skill skill;
	int maxIncrease = 50;
	public double baseDamage = 5;

	public Strength(Player owner) {
		this.player = owner;
		skill = new Skill(owner, 100);
	}

	public double percentageIncrease() {
		double percentageIncrease = (float) maxIncrease/skill.maxLevel*skill.level;
		this.player.sendMessage("Max Increase: " + maxIncrease + " | Max Level: " + skill.maxLevel + " | Current Level: " + skill.level + " PI | " + percentageIncrease);
		return percentageIncrease;
	}
}
