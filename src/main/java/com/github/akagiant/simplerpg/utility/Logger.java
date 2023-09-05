package com.github.akagiant.simplerpg.utility;

import com.github.akagiant.simplerpg.SimpleRPG;
import org.bukkit.Bukkit;

public class Logger {
	
	private Logger() {
		//no instance
	}
	
	public static void warn(String msg) {
		Bukkit.getConsoleSender().sendMessage(
			ColorManager.formatColours("&8[&6" + SimpleRPG.getPlugin().getName() + " &6&lWARN&8] " + msg)
		);
	}
	
	
	public static void severe(String msg) {
		Bukkit.getConsoleSender().sendMessage(
			ColorManager.formatColours("&8[&c" + SimpleRPG.getPlugin().getName() + " &c&lSEVERE&8] &f" + msg)
		);
	}
	
	public static void toConsole(String msg) {
		Bukkit.getConsoleSender().sendMessage(
			ColorManager.formatColours("&8[&b" + SimpleRPG.getPlugin().getName() + "&8] " + msg)
		);
	}
}
