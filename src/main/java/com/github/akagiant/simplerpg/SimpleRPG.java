package com.github.akagiant.simplerpg;

import com.github.akagiant.simplerpg.core.listeners.MobDamageListener;
import com.github.akagiant.simplerpg.core.listeners.MobSpawnListener;
import com.github.akagiant.simplerpg.utility.Logger;
import com.github.akagiant.simplerpg.utility.configuration.ConfigManager;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.CommandAPIConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleRPG extends JavaPlugin implements Listener {

	@Getter
	private static Plugin plugin;


	@Override
	public void onLoad() {
		CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(true));
	}

	@Override
	public void onEnable() {
		// Plugin startup logic
		plugin = this;

		CommandAPI.onEnable();

		ConfigManager.registerConfigurations();

		getServer().getPluginManager().registerEvents(new MobSpawnListener(), this);
		getServer().getPluginManager().registerEvents(new MobDamageListener(), this);

		Logger.severe("Enabled");

	}

    @Override
    public void onDisable() {

		CommandAPI.onDisable();

        // Plugin shutdown logic
		Bukkit.getServer().getScheduler().cancelTasks(this);
    }
}
