package com.github.akagiant.simplerpg.core.mobs;

import com.github.akagiant.simplerpg.utility.configuration.Config;
import com.github.akagiant.simplerpg.utility.configuration.ConfigManager;
import com.github.akagiant.simplerpg.utility.configuration.ConfigUtil;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBTCompoundList;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomMob {

	private static Config config = ConfigManager.mobs;

	private static Map<EntityType, CustomMob> cachedCustomMobs = new HashMap<>();

	private Entity entity;

	double health;
	double healthBarViewRange;

	public CustomMob(Entity entity) {
		this.entity = entity;

		if (cachedCustomMobs.containsKey(entity.getType())) return;


		Optional<?> optionalHealth = ConfigUtil.getNumber(config, entity.getName().toLowerCase().replace(" ", "_") + ".health");
		health = optionalHealth.map(o -> (double) o).orElse(5.0);

		if (health == -1) return;

		Optional<?> optionalHealthBarViewRange = ConfigUtil.getDouble(config, entity.getName().toLowerCase().replace(" ", "_") + ".view-distance");
		healthBarViewRange = optionalHealthBarViewRange.map(o -> (double) o).orElse(5.0);

		cachedCustomMobs.putIfAbsent(entity.getType(), this);

		NBT.modify(entity, nbt -> {
			ReadWriteNBTCompoundList list = nbt.getCompoundList("Attributes");
			for (int i = 0; i < list.size(); i++) {
				ReadWriteNBT lc = list.get(i);
				if (lc.getString("Name").equals("generic.attackDamage")) {
					lc.setDouble("Base", 10.0d);
				}
			}
		});

	}

	public static void update(Entity entity) {
		Mob mob = (Mob) entity;
		mob.setMaxHealth(mob.getMaxHealth() * 2);

//		((Mob) entity).getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1000D);


		TextDisplay textDisplay = getHealthBar(mob);
		mob.addPassenger(textDisplay);

	}

//	private static double getMobAttackDamage

	private static TextDisplay getHealthBar(Mob mob) {
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

		return textDisplay;
	}


}
