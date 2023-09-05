package com.github.akagiant.simplerpg.core.mobs;

import com.github.akagiant.simplerpg.utility.configuration.Config;
import com.github.akagiant.simplerpg.utility.configuration.ConfigManager;
import com.github.akagiant.simplerpg.utility.configuration.ConfigUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class CustomMob {

	private static Config config = ConfigManager.mobs;

	private static Map<EntityType, CustomMob> cachedCustomMobs = new HashMap<>();

	int health;
	int healthBarViewRange;

	public CustomMob(Entity entity) {
		if (cachedCustomMobs.containsKey(entity.getType())) return;

		health = ConfigUtil.getInt(config, entity.getName().toLowerCase().replace(" ", "_")).orElse(-1);
		if (health == -1) return;

		healthBarViewRange = ConfigUtil.getInt(config, entity.getName().toLowerCase().replace(" ", "_")).orElse(25);

		cachedCustomMobs.putIfAbsent(entity.getType(), this);
	}
}
