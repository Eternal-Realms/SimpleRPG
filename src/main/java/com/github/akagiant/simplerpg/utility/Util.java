package com.github.akagiant.simplerpg.utility;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Util {

	private Util() {
		//no instance
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

	public static Set<Block> getBlocksInRadius(Location location, int radius) {
		Set<Block> blocks = new HashSet<>();

		int blockX = location.getBlockX();
		int blockY = location.getBlockY();
		int blockZ = location.getBlockZ();

		int radiusSquared = radius * radius;

		for (int x = blockX - radius; x <= blockX + radius; x++) {
			for (int y = blockY - radius; y <= blockY + radius; y++) {
				for (int z = blockZ - radius; z <= blockZ + radius; z++) {
					if ((blockX - x) * (blockX - x) + (blockY - y) * (blockY - y) + (blockZ - z) * (blockZ - z) <= radiusSquared) {
						Block block = location.getWorld().getBlockAt(x, y, z);
						if (block.getType().equals(Material.AIR)) continue;
						blocks.add(block);
					}
				}
			}
		}
		return blocks;
	}

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	/**
	 *
	 * @param unsorted | A Map of unsorted values and their chance.
	 * @return a sorted Map<String, Double>
	 */
	public static Map<String, Double> sortLowestToHighest(Map<String, Double> unsorted) {
		return unsorted.entrySet().stream()
			.sorted(Comparator.comparingDouble(Map.Entry::getValue))
			.collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
				(a,b)->b, LinkedHashMap::new));
	}
	
}
