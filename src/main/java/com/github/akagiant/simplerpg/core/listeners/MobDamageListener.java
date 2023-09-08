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

		Player player = (Player) e.getEntity();

		EntityDamageEvent.DamageCause damageCause = e.getCause();
		Logger.severe(String.valueOf(damageCause));

		switch (damageCause) {
			case ENTITY_EXPLOSION -> {

			}
		}

		// Damage Was caused by a Creeper
	}



	@EventHandler
	public void onMobDamagePlayer(EntityDamageByEntityEvent e) {
		if (!((e.getDamager()) instanceof Mob) || (!(e.getEntity() instanceof Player))) return;

		Mob mob = (Mob) e.getDamager();
		// Get Base Damage

		// Get Damage Type (Explosive, Physical etc)

		// Get Mob Level

		// Adjust Base Damage Based on Level

		Player player = (Player) e.getEntity();
		// Get Player Level

		// Get Player Defensive Stats

		// Reduce overall damage by stats

		// Apply Damage



	}

// This is just code I want to keep around encase I want to use it in the future.
//		new BukkitRunnable() {
//			int count = 0;
//			Vector movement = null;
//			List<Block> blocks = getNearbyBlocks(creeper.getLocation(), 15);
//			List<FallingBlock> fallingBlocks = new ArrayList<>();
//
//			// 20 Ticks * wanted seconds;
//			int maxTime = 300;
//
//			public void run() {
//				if (count == maxTime) {
//					cancel();
//
//					creeper.remove();
//					for (FallingBlock fallingBlock : fallingBlocks) {
//						fallingBlock.setGravity(true);
//					}
//
//					return;
//				}
//
//				Random r = new Random();
//				Block block = blocks.get(r.nextInt(blocks.size()));
//
//				FallingBlock fallingBlock = creeper.getWorld().spawnFallingBlock(block.getLocation(), block.getBlockData());
//				fallingBlock.setVelocity((fallingBlock.getLocation().toVector().subtract(creeper.getLocation().toVector())
//					.multiply(-10).normalize()));
//
//				fallingBlock.setGravity(false);
//				fallingBlock.setDropItem(false);
//				fallingBlock.setHurtEntities(true);
//
//				fallingBlocks.add(fallingBlock);
//
//				count++;
//
//			}
//		}.runTaskTimer(SimpleRPG.getPlugin(), 0, 0);

}
