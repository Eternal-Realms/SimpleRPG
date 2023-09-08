package com.github.akagiant.simplerpg.core.listeners;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.github.akagiant.simplerpg.core.mobs.MobUtil;
import com.github.akagiant.simplerpg.core.player.RPGPlayer;
import com.github.akagiant.simplerpg.core.player.RPGPlayerManager;
import com.github.akagiant.simplerpg.utility.timer.Timer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

		int requiredIcons = (int) MobUtil.getMaxHealth(mob);
		int missingHealth = (int) ((int) MobUtil.getMaxHealth(mob) - (mob.getHealth() - damage));

		double missingHealthAsPercentageOfMax = (missingHealth / MobUtil.getMaxHealth(mob)) * 100;

		NamedTextColor chatColor;
		if (missingHealthAsPercentageOfMax <= 25) {
			chatColor = NamedTextColor.GREEN;
		} else if (missingHealthAsPercentageOfMax <= 50) {
			chatColor = NamedTextColor.GOLD;
		} else {
			chatColor = NamedTextColor.RED;
		}

		// Kept due to icons eventually not being a 1-1 ratio with icons.

		String normalIcons = chatColor + icon.repeat(Math.max(0, requiredIcons - missingHealth));
		String damageIcons = NamedTextColor.GRAY + icon.repeat(Math.max(0, missingHealth));

		healthBar.text(Component.text(normalIcons + damageIcons));

	}

	private void displayDamageIndicators(Mob mob, double damage) {
		TextDisplay damageIndicator = (TextDisplay) mob.getWorld().spawnEntity(mob.getLocation(), EntityType.TEXT_DISPLAY);

		damageIndicator.text(Component.text(NamedTextColor.RED + "" + TextDecoration.BOLD  + damage));
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
