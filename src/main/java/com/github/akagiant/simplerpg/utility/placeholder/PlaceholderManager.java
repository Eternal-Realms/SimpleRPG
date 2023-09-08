package com.github.akagiant.simplerpg.utility.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class PlaceholderManager extends PlaceholderExpansion {
	@Override
	public @NotNull String getIdentifier() {
		return "PlanetaryCore";
	}
	
	@Override
	public @NotNull String getAuthor() {
		return "AkaGiant";
	}
	
	@Override
	public @NotNull String getVersion() {
		return "1.0.0";
	}
	
	@Override
	public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {

		DecimalFormat df = new DecimalFormat("###,###,###,###,###.##");
		return df.format(null);
	}
	
}
