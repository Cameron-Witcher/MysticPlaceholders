package net.mysticcloud.spigot.core.utils;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.core.utils.functions.FunctionUtils;
import net.mysticcloud.spigot.core.utils.functions.FunctionWorker;
import net.mysticcloud.spigot.core.utils.placeholders.PlaceholderUtils;
import net.mysticcloud.spigot.core.utils.placeholders.PlaceholderWorker;

public class PlaceholderAPI {

	public static String setPlaceholders(Player player, String string) {
		return setPlaceholders(player, string, true);
	}

	public static String setPlaceholders(Player player, String string, boolean function) {
		String hold = string;
		for (Entry<String, PlaceholderWorker> e : PlaceholderUtils.getPlaceholders().entrySet()) {
			if (hold.contains(e.getKey())) {
				hold = hold.replaceAll(e.getKey(), e.getValue().run(player));
			}
		}
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
			hold = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, string);
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("MVdWPlaceholderAPI"))
			hold = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(player, string);
		if (function)
			hold = runFunctions(player, hold);
		return hold;
	}

	public static String runFunctions(Player player, String string) {
		String hold = string;
		for (Entry<String, FunctionWorker> e : FunctionUtils.getFunctions().entrySet()) {
			while (hold.contains("%" + e.getKey() + ":")) {
				String key = e.getKey();
				String tmp = hold;
				String info = "";
				info = tmp.split("%" + key + ":")[1].split("-" + key + "%")[0];
				String rpl = e.getValue().run(player, info.split(":"));
				hold = hold.replace("%" + key + ":" + info + "-" + key + "%", rpl);
			}
		}
		return hold;
	}

}
