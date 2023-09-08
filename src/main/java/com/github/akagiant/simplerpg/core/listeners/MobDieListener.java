package com.github.akagiant.simplerpg.core.listeners;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.github.akagiant.simplerpg.core.player.RPGPlayer;
import com.github.akagiant.simplerpg.core.player.RPGPlayerManager;
import com.github.akagiant.simplerpg.utility.timer.Timer;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MobDieListener implements Listener {

	Map<UUID, TextDisplay> displayMap = new HashMap<>();

	@EventHandler
	public void onRemove(EntityRemoveFromWorldEvent e) {
		if (!(e.getEntity() instanceof Mob)) return;
		if (!displayMap.containsKey(e.getEntity().getUniqueId())) return;

		TextDisplay healthBar = displayMap.get(e.getEntity().getUniqueId());
		healthBar.remove();
		displayMap.remove(e.getEntity().getUniqueId());
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

		TextDisplay healthBar = displayMap.get(entity.getUniqueId());
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
