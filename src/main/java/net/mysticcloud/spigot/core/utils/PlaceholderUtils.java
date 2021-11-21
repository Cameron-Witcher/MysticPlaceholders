package net.mysticcloud.spigot.core.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.MysticPlaceholders;

public class PlaceholderUtils {

	private static MysticPlaceholders plugin;

	private static Map<String, Worker> placeholders = new HashMap<>();

	public static void init(MysticPlaceholders main) {
		plugin = main;
		Worker name = (Player player) -> {
			return player.getName();
		};
		registerPlaceholder("player", name);
		registerPlaceholder("name", name);
		registerPlaceholder("uuid", (Player player) -> {
			return player.getUniqueId() + "";
		});

	}

	public static MysticPlaceholders getPlugin() {
		return plugin;
	}

	public static void registerPlaceholder(String key, Worker worker) {
		placeholders.put("$" + key + "$", worker);
	}

	public static Map<String, Worker> getPlaceholders() {
		return placeholders;
	}

}
