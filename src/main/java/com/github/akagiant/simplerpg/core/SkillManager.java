package com.github.akagiant.simplerpg.core;

import org.bukkit.entity.Player;

public class SkillManager {

	public Strength strength;

	public SkillManager(Player player) {
		strength = new Strength(player);
	}




}
