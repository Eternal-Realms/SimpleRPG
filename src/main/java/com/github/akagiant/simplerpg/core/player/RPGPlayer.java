package com.github.akagiant.simplerpg.core.player;

import com.github.akagiant.simplerpg.core.SkillManager;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class RPGPlayer {

	private Player player;
	public SkillManager skillManager;


	public RPGPlayer(Player player) {
		this.player = player;
		skillManager = new SkillManager(player);
	}

}
