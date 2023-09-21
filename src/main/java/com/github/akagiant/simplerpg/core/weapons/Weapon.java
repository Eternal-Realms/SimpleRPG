package com.github.akagiant.simplerpg.core.weapons;

import com.github.akagiant.simplerpg.utility.ItemManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Weapon {
	Rarity rarity;

	@Getter
	final int baseDamage;
	@Getter
	final int maxLevel;
	@Getter @Setter
	int level;

	@Getter
	int damagePerAttack;

	public Weapon(Rarity rarity) {
		this.rarity = rarity;
		this.baseDamage = rarity.baseDamage;
		this.maxLevel = rarity.maxLevel;
		this.level = 1;
		this.damagePerAttack = baseDamage * level;
	}

	public void levelUp() {
		this.level++;
	}

	public void give(Player player) {
		ItemStack itemStack = ItemManager.makeItem(Material.DIAMOND_SWORD, this.rarity.name(), "", "Level: " + this.level, "Damage: " + this.damagePerAttack);
		ItemMeta meta = itemStack.getItemMeta();
		
	}
}
