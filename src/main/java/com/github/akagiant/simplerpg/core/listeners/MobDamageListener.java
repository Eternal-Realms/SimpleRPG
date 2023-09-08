package com.github.akagiant.simplerpg.core.listeners;

import com.github.akagiant.simplerpg.core.mobs.CreeperExplosion;
import com.github.akagiant.simplerpg.utility.Logger;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class MobDamageListener implements Listener {

	@EventHandler
	public void onCreeperExplode(EntityExplodeEvent e) {

		if (!(e.getEntity() instanceof Creeper)) return;
		new CreeperExplosion(e.getEntity());
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) return;

		EntityDamageEvent.DamageCause damageCause = e.getCause();
		Logger.severe(String.valueOf(damageCause));

		if (damageCause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
			// Creeper
		}

		// Damage Was caused by a Creeper
	}



	@EventHandler
	public void onMobDamagePlayer(EntityDamageByEntityEvent e) {
		if (!((e.getDamager()) instanceof Mob) || (!(e.getEntity() instanceof Player))) return;

//		Mob mob = (Mob) e.getDamager();
		// Get Base Damage

		// Get Damage Type (Explosive, Physical etc)

		// Get Mob Level

		// Adjust Base Damage Based on Level

//		Player player = (Player) e.getEntity();
		// Get Player Level

		// Get Player Defensive Stats

		// Reduce overall damage by stats

		// Apply Damage



	}

}
