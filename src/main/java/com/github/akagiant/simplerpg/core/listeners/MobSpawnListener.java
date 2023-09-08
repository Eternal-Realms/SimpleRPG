package com.github.akagiant.simplerpg.core.listeners;

import com.github.akagiant.simplerpg.core.mobs.MobUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Objects;

public class MobSpawnListener implements Listener {

	@EventHandler
	public void onMobSpawn(EntitySpawnEvent e) {
		if (!(e.getEntity() instanceof Mob)) return;

		Entity entity = e.getEntity();
		Mob mob = (Mob) entity;

		double defaultMaxHealth = Objects.requireNonNull(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();

		double newMaxHealth = defaultMaxHealth * 2;

		Objects.requireNonNull(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(newMaxHealth);

		TextDisplay textDisplay = MobUtil.getHealthBar(mob);
		mob.addPassenger(textDisplay);
	}
}
