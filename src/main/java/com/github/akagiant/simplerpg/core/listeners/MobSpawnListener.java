package com.github.akagiant.simplerpg.core.listeners;

import com.github.akagiant.simplerpg.core.mobs.CustomMob;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobSpawnListener implements Listener {

	@EventHandler
	public void onMobSpawn(EntitySpawnEvent e) {
		if (!(e.getEntity() instanceof Mob)) return;

		Entity entity = e.getEntity();
		Mob mob = (Mob) e.getEntity();

		CustomMob.update(entity);

	}

}
