package com.github.akagiant.simplerpg.core.listeners;

import com.github.akagiant.simplerpg.SimpleRPG;
import com.github.akagiant.simplerpg.utility.Logger;
import com.github.akagiant.simplerpg.utility.Util;
import com.github.akagiant.simplerpg.utility.timer.Timer;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MobDamageListener implements Listener {

	@EventHandler
	public void onCreeperExplode(EntityExplodeEvent e) {

		if (!(e.getEntity() instanceof Creeper)) return;

		Creeper creeper = (Creeper) e.getEntity();

		int radius = 5;

		Set<Block> blocks = Util.getBlocksInRadius(creeper.getLocation(), radius);

		Set<FallingBlock> fallingBlocks = new HashSet<>();

		for (Block block : blocks) {
			block.setType(Material.DIAMOND_BLOCK);

			float x = (float) (Math.floor(Math.random() * (200 - -200 + 1) + -200) / 1200) * (radius / 2);
			float y = (float) (Math.floor(Math.random() * (100 -  300 + 1) + 300) / 1200) * (radius / 2);
			float z = (float) (Math.floor(Math.random() * (200 - -200 + 1) + -200) / 1200) * (radius / 2);

			FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), block.getBlockData());
			fallingBlock.setDropItem(false);
			fallingBlock.setVelocity(new Vector(x, y, z));

			fallingBlocks.add(fallingBlock);

			block.setType(Material.AIR);
		}

		List<Set<FallingBlock>> lists = Util.partition(fallingBlocks, 10);


		new BukkitRunnable() {
			int index = 0;

			@Override
			public void run() {
				if (lists.size() < index + 1) {
					cancel();
				}

				lists.get(index).forEach(block -> {
					block.getWorld().getBlockAt(block.getLocation()).setType(Material.AIR);
				});
				index++;
			}
		}.runTaskTimer(SimpleRPG.getPlugin(), 100L, 20L);

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

	public static <T> List<Set<T>> partition(Set<T> set, int partitionSize) {
		List<Set<T>> sets = new ArrayList<>((set.size() / partitionSize) + 1);

		int index = 0;
		int counter = 0;
		for (T element : set) {
			if (counter == partitionSize) {
				index++;
				counter = 0;
			}
			if (sets.size() <= index) {
				sets.add(new HashSet<>());
			}
			sets.get(index).add(element);
			counter++;
		}
		return sets;
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

}
