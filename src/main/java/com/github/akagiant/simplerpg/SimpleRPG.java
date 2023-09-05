package com.github.akagiant.simplerpg;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.github.akagiant.simplerpg.core.player.RPGPlayer;
import com.github.akagiant.simplerpg.core.player.RPGPlayerManager;
import com.github.akagiant.simplerpg.utility.timer.Timer;
import com.github.akagiant.simplerpg.utility.configuration.ConfigManager;
import com.github.akagiant.simplerpg.utility.configuration.ConfigUtil;
import com.github.akagiant.simplerpg.utility.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SimpleRPG extends JavaPlugin implements Listener {

	@Getter
	private static Plugin plugin;

	@Override
	public void onEnable() {
		// Plugin startup logic
		plugin = this;

		ConfigManager.registerConfigurations();
		int test;
		for (EntityType entityType : EntityType.values()) {

			if (!entityType.isSpawnable()) continue;

			Entity entity = Bukkit.getWorlds().get(0).spawnEntity(new Location(Bukkit.getWorlds().get(0), 0, 50, 0), entityType);

			if (!(entity instanceof Mob)) continue;

			String mobName = entity.getName().toLowerCase().replace(" ", "_");


			if (ConfigUtil.isSet(ConfigManager.mobs, mobName)) {
				Logger.severe("Already Set.");
			} else {
				ConfigManager.mobs.getConfig().set(mobName + ".view-distance", 10);
				ConfigManager.mobs.saveConfig();
			}
		}

		getServer().getPluginManager().registerEvents(this, this);



	}

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

	Map<UUID, TextDisplay> armorStandUUIDMap = new HashMap<>();

	@EventHandler
	public void onRemove(EntityRemoveFromWorldEvent e) {
		if (!(e.getEntity() instanceof Mob)) return;
		if (!armorStandUUIDMap.containsKey(e.getEntity().getUniqueId())) return;

		TextDisplay healthBar = armorStandUUIDMap.get(e.getEntity().getUniqueId());
		healthBar.remove();
		armorStandUUIDMap.remove(e.getEntity().getUniqueId());
	}

	@EventHandler
	public void onMobSpawn(EntitySpawnEvent e) {
		if (!(e.getEntity() instanceof Mob)) return;
//
//		if (e.getEntity() instanceof Monster) {
//			Bukkit.getLogger().severe(e.getEntity().getName());
//		}

		Mob mob = (Mob) e.getEntity();
		mob.setMaxHealth(mob.getMaxHealth() * 2);

		TextDisplay textDisplay = (TextDisplay) mob.getWorld().spawnEntity(mob.getLocation(), EntityType.TEXT_DISPLAY);

		String icon = "|";
		int requiredIcons = (int) (mob.getMaxHealth());
		textDisplay.setText(ChatColor.GREEN + icon.repeat(Math.max(0, requiredIcons)));

		Vector3f translation = textDisplay.getTransformation().getTranslation().add(0f, .5f, 0f);
		Vector3f scale = textDisplay.getTransformation().getScale().add(.5f, .5f, .5f);

		textDisplay.setTransformation(new Transformation(translation, new AxisAngle4f(), scale, new AxisAngle4f()));

		textDisplay.setBillboard(Display.Billboard.CENTER);

		int userDefinedViewRange = ConfigUtil.getInt(ConfigManager.mobs, mob.getName().toLowerCase().replace(" ", "_") + ".view-distance").orElse(10);

		// 0.00625 = 1 Block

		float viewRange = (float) (0.00625 * userDefinedViewRange);

		textDisplay.setViewRange(viewRange);

		mob.addPassenger(textDisplay);


		armorStandUUIDMap.put(mob.getUniqueId(), textDisplay);

	}

	@EventHandler
	public void onMobDamage(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player) || (!(e.getEntity() instanceof Mob mob))) return;

		RPGPlayer playerManager = RPGPlayerManager.get((Player) e.getDamager());
		playerManager.getSkillManager().strength.skill.addExp(3, 10);
		double dmg = playerManager.getSkillManager().strength.baseDamage;
		double increase = playerManager.skillManager.strength.percentageIncrease();

		int finalValue = (int) (((dmg / 100) * increase) + dmg);

		e.setDamage(finalValue);

		e.getDamager().sendMessage("Base Damage: " + dmg + " | Increase: " + increase + " | Final: " + finalValue);
		e.getDamager().sendMessage(String.valueOf(((Mob) e.getEntity()).getHealth()));

		updateHealthBar(e.getEntity(), finalValue);
		displayDamageIndicators(mob, finalValue);
	}

	private void updateHealthBar(Entity entity, int damage) {
		Mob mob = (Mob) entity;

		TextDisplay healthBar = armorStandUUIDMap.get(entity.getUniqueId());
		String icon = "|";

		int requiredIcons = (int) (mob.getMaxHealth());
		int missingHealth = (int) (mob.getMaxHealth() - (mob.getHealth() - damage));

		double missingHealthAsPercentageOfMax = (missingHealth / mob.getMaxHealth()) * 100;

		ChatColor chatColor;
		if (missingHealthAsPercentageOfMax <= 25) {
			chatColor = ChatColor.GREEN;
		} else if (missingHealthAsPercentageOfMax <= 50) {
			chatColor = ChatColor.GOLD;
		} else {
			chatColor = ChatColor.RED;
		}

		// Kept due to icons eventually not being a 1-1 ratio with icons.
		int amountOfIconsToRemove = missingHealth;

		String normalIcons = chatColor + icon.repeat(Math.max(0, requiredIcons - amountOfIconsToRemove));
		String damageIcons = ChatColor.GRAY + icon.repeat(Math.max(0, amountOfIconsToRemove));

		healthBar.setText(normalIcons + damageIcons);

	}

	private void displayDamageIndicators(Mob mob, double damage) {
		TextDisplay damageIndicator = (TextDisplay) mob.getWorld().spawnEntity(mob.getLocation(), EntityType.TEXT_DISPLAY);

		damageIndicator.setText(ChatColor.RED + "" +  ChatColor.BOLD + "" + damage);
		damageIndicator.setBillboard(Display.Billboard.CENTER);
		Vector3f vector3f = damageIndicator.getTransformation().getTranslation().add(0f, 0.5f, 0.0f);
		damageIndicator.setTransformation(new Transformation(vector3f, new AxisAngle4f(), damageIndicator.getTransformation().getScale().add(1, 1, 1), new AxisAngle4f()));
		mob.addPassenger(damageIndicator);

		new Timer(20, 1) {
			@Override
			public void count(int current) {
				Vector3f vector3f = damageIndicator.getTransformation().getTranslation().add(0.025f, 0.025f, 0.0f);
				damageIndicator.setTransformation(new Transformation(vector3f, new AxisAngle4f(), damageIndicator.getTransformation().getScale(), new AxisAngle4f()));
				if (current == 0) {
					damageIndicator.remove();
				}
			}
		}.start();
	}

}
