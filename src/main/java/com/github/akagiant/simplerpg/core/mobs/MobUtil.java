package com.github.akagiant.simplerpg.core.mobs;

import com.github.akagiant.simplerpg.utility.configuration.ConfigManager;
import com.github.akagiant.simplerpg.utility.configuration.ConfigUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.Objects;

public class MobUtil {

	private MobUtil() {
		//no instance
	}
	
	public static TextDisplay getHealthBar(Mob mob) {
		TextDisplay textDisplay = (TextDisplay) mob.getWorld().spawnEntity(mob.getLocation(), EntityType.TEXT_DISPLAY);

		String icon = "|";
		int requiredIcons = (int) (mob.getMaxHealth());

		textDisplay.text(Component.text(NamedTextColor.GREEN + icon.repeat(Math.max(0, requiredIcons))));

		Vector3f translation = textDisplay.getTransformation().getTranslation().add(0f, .5f, 0f);
		Vector3f scale = textDisplay.getTransformation().getScale().add(.5f, .5f, .5f);

		textDisplay.setTransformation(new Transformation(translation, new AxisAngle4f(), scale, new AxisAngle4f()));
		textDisplay.setBillboard(Display.Billboard.CENTER);

		// 0.00625 = 1 Block roughly.
		float viewRange = (float) (0.00625 * getUserDefinedViewRange(mob));

		textDisplay.setViewRange(viewRange);

		return textDisplay;
	}

	public static double getMaxHealth(Entity entity) {
		return getMaxHealth((Mob) entity);
	}

	public static double getMaxHealth(Mob mob) {
		return Objects.requireNonNull(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
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
