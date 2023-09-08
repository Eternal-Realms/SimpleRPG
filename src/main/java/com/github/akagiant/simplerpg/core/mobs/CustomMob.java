package com.github.akagiant.simplerpg.core.mobs;

import com.github.akagiant.simplerpg.utility.configuration.Config;
import com.github.akagiant.simplerpg.utility.configuration.ConfigManager;
import com.github.akagiant.simplerpg.utility.configuration.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CustomMob {

	private static final Config config = ConfigManager.mobs;

	double health;
	double healthBarViewRange;

	public CustomMob(Entity entity) {
		Optional<?> optionalHealth = ConfigUtil.getNumber(config, entity.getName().toLowerCase().replace(" ", "_") + ".health");
		health = optionalHealth.map(o -> (double) o).orElse(5.0);

		if (health == -1) return;

		Optional<?> optionalHealthBarViewRange = ConfigUtil.getDouble(config, entity.getName().toLowerCase().replace(" ", "_") + ".view-distance");
		healthBarViewRange = optionalHealthBarViewRange.map(o -> (double) o).orElse(5.0);
	}

	/**
	 * Compares EntityTypes on startup to cache CustomMob Settings.
	 * This should only be run on startup and on configuration reloading.
	 */
	public static Set<CustomMob> initialise() {
		Set<CustomMob> customMobList = new HashSet<>();

		Arrays.stream(EntityType.values()).filter(EntityType::isSpawnable).map(
			entityType ->
				Bukkit.getWorlds().get(0).spawnEntity(
					new Location(Bukkit.getWorlds().get(0), 0, 50, 0), entityType
				)
			)
			.filter(Mob.class::isInstance).forEach(entity -> {

				String mobName = entity.getName().toLowerCase().replace(" ", "_");
				if (!ConfigUtil.isSet(ConfigManager.mobs, mobName)) {
					ConfigManager.mobs.getConfig().set(mobName + ".view-distance", 10);
					ConfigManager.mobs.getConfig().set(mobName + ".health", MobUtil.getMaxHealth(entity));
					ConfigManager.mobs.saveConfig();
				}

				CustomMob customMob = new CustomMob(entity);
				customMobList.add(customMob);

				entity.remove();
			});

		return customMobList;
	}
}
