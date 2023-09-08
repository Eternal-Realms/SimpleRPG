package com.github.akagiant.simplerpg.core.mobs;

import com.github.akagiant.simplerpg.SimpleRPG;
import com.github.akagiant.simplerpg.utility.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CreeperExplosion {

	Random random = new Random();

	public CreeperExplosion(Entity entity) {

		Creeper creeper = (Creeper) entity;

		int radius = 5;

		Set<Block> blocks = Util.getBlocksInRadius(creeper.getLocation(), radius);

		Set<FallingBlock> fallingBlocks = new HashSet<>();

		for (Block block : blocks) {
			block.setType(Material.DIAMOND_BLOCK);

			float x = (float) (Math.floor(random.nextDouble() * (200 - -200 + 1) + -200) / 1200) * ((float) radius / 2);
			float y = (float) (Math.floor(random.nextDouble() * (100 -  300 + 1) + 300) / 1200) * ((float) radius / 2);
			float z = (float) (Math.floor(random.nextDouble() * (200 - -200 + 1) + -200) / 1200) * ((float) radius / 2);

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

				lists.get(index).forEach(block ->
					block.getWorld().getBlockAt(block.getLocation()).setType(Material.AIR)
				);
				index++;
			}
		}.runTaskTimer(SimpleRPG.getPlugin(), 100L, 20L);

	}
}
