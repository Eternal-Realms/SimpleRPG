package com.github.akagiant.simplerpg.utility;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ItemManager {
	
	private ItemManager() {
		//no instance
	}
	
	public static ItemStack makeItem(Material material, String displayName, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorManager.formatColours(displayName));
		meta.setLore(Arrays.stream(lore).map(ColorManager::formatColours).collect(Collectors.toList()));
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack makeItem(Material material, String displayName) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorManager.formatColours(displayName));
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack makeItem(Material material, int amount, String customModelData) {
		ItemStack item = new ItemStack(material);
		item.setAmount(amount);
		if (customModelData != null) {
			ItemMeta meta = item.getItemMeta();
			meta.setCustomModelData(Integer.valueOf(customModelData));
			
			item.setItemMeta(meta);
		}
		
		item.setAmount(amount);
		
		return item;
	}

	public static ItemStack getPlayerHead(Player player, String displayName, String... lore) {
		ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
		skullMeta.setOwningPlayer(player);
		skullMeta.setDisplayName(ColorManager.formatColours(displayName));
		itemStack.setItemMeta(skullMeta);

		ItemMeta meta = itemStack.getItemMeta();
		meta.setLore(Arrays.stream(lore).map(ColorManager::formatColours).collect(Collectors.toList()));
		itemStack.setItemMeta(meta);
		return itemStack;
	}
	
}

