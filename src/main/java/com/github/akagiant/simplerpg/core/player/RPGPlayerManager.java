package com.github.akagiant.simplerpg.core.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class RPGPlayerManager {

	private RPGPlayerManager() {
		//no instance
	}

	// Map of all stored players, online or offline.
	private static final Map<UUID, RPGPlayer> profiles = new HashMap<>();

	public static void handleProfileCreation(Player player) {
		profiles.putIfAbsent(player.getUniqueId(), new RPGPlayer(player));
	}

	public static void handleProfileCreation(UUID uuid) {
		profiles.putIfAbsent(uuid, new RPGPlayer((Player) Bukkit.getOfflinePlayer(uuid)));
	}

	public static RPGPlayer get(Player player) {
		if (!profiles.containsKey(player.getUniqueId())) {
			handleProfileCreation(player);
		}
		return profiles.get(player.getUniqueId());
	}

	public static RPGPlayer get(UUID playerUUID) {
		if (!profiles.containsKey(playerUUID)) {
			handleProfileCreation(playerUUID);
		}
		return profiles.get(playerUUID);
	}

	public static Map<UUID, RPGPlayer> getProfiles() {
		return profiles;
	}

	public static List<RPGPlayer> get() {
		List<RPGPlayer> list = new ArrayList<>();
		for (Map.Entry<UUID, RPGPlayer> entry : getProfiles().entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

	// To be used at a later date.

//	public static void start() {
//		Bukkit.getScheduler().scheduleSyncRepeatingTask(SimpleRPG.getPlugin(), () -> {
//			for (RPGPlayer rpgPlayer : RPGPlayerManager.get()) {
//				if (rpgPlayer.getPlayer().isOnline()) {
//					rpgPlayer.save();
//				}
//			}
//		}, 0L, 600L);
//	}
	
}
