package com.github.akagiant.simplerpg.core.mobs;

import com.github.akagiant.simplerpg.utility.configuration.ConfigManager;
import com.github.akagiant.simplerpg.utility.configuration.ConfigUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class MobUtil {

	private MobUtil() {
		//no instance
	}
	
	public static TextDisplay getHealthBar(Mob mob) {
		TextDisplay textDisplay = (TextDisplay) mob.getWorld().spawnEntity(mob.getLocation(), EntityType.TEXT_DISPLAY);

		String icon = "|";
		int requiredIcons = (int) (mob.getMaxHealth());
		textDisplay.setText(ChatColor.GREEN + icon.repeat(Math.max(0, requiredIcons)));

		Vector3f translation = textDisplay.getTransformation().getTranslation().add(0f, .5f, 0f);
		Vector3f scale = textDisplay.getTransformation().getScale().add(.5f, .5f, .5f);

		textDisplay.setTransformation(new Transformation(translation, new AxisAngle4f(), scale, new AxisAngle4f()));
		textDisplay.setBillboard(Display.Billboard.CENTER);

		// 0.00625 = 1 Block roughly.
		float viewRange = (float) (0.00625 * getUserDefinedViewRange(mob));

		textDisplay.setViewRange(viewRange);

		return textDisplay;
	}

	public static int getUserDefinedViewRange(Mob mob) {
		String mobName = mob.getName().toLowerCase().replace(" ", "_");
		return ConfigUtil.getInt(ConfigManager.mobs, mobName + ".view-distance").orElse(10);
	}

	public static int getUserDefinedViewRange(Entity entity) {
		String mobName = entity.getName().toLowerCase().replace(" ", "_");
		return ConfigUtil.getInt(ConfigManager.mobs, mobName + ".view-distance").orElse(10);
	}

	public static int getUserDefinedViewRange(String mobName) {
		mobName = mobName.toLowerCase().replace(" ", "_");
		return ConfigUtil.getInt(ConfigManager.mobs, mobName + ".view-distance").orElse(10);
	}

}
