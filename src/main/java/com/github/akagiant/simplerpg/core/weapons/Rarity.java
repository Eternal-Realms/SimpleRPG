package com.github.akagiant.simplerpg.core.weapons;

public enum Rarity {
	COMMON(10, 20),
	UNCOMMON(20, 30),
	RARE(30, 40),
	MYTHIC(40, 60),
	LEGENDARY(50, 85);

	int maxLevel;
	int baseDamage;

	Rarity(int maxLevel, int baseDamage) {
		this.maxLevel = maxLevel;
		this.baseDamage = baseDamage;
	}
}
