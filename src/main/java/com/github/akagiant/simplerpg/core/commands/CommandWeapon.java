package com.github.akagiant.simplerpg.core.commands;

import com.github.akagiant.simplerpg.core.weapons.Rarity;
import com.github.akagiant.simplerpg.core.weapons.Weapon;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;

public class CommandWeapon {

	public void register() {
		new CommandAPICommand("weapon")
			.withArguments(new MultiLiteralArgument("rarity", "common", "uncommon", "rare", "mythic", "legendary"))
			.executesPlayer((player, commandArguments) -> {
				String rarity = (String) commandArguments.get("rarity");
				Rarity rarity1 = Rarity.valueOf(rarity);

				Weapon weapon = new Weapon(rarity1);
			})
			.register();
	}

}
