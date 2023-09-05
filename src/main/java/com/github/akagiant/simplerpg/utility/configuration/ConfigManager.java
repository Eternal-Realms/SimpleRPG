package com.github.akagiant.simplerpg.utility.configuration;

public class ConfigManager {

	private ConfigManager() {
		//no instance
	}
	
	public static Config mobs;

	public static void registerConfigurations() {
		mobs = new Config("mobs");
	}

}
