package net.mysticcloud.spigot.core.utils;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderAPI {

	public static String setPlaceholders(Player player, String string) {
		String hold = string;
		for (Entry<String, Worker> e : PlaceholderUtils.getPlaceholders().entrySet()) {
			if (hold.contains(e.getKey())) {
				hold = hold.replaceAll(e.getKey(), e.getValue().run(player));
			}
		}
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
			string = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, string);
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("MVdWPlaceholderAPI"))
			string = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(player, string);
		return hold;
	}

}
