package net.mysticcloud.spigot.core.utils.placeholders;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderUtils {

	private static Map<String, PlaceholderWorker> placeholders = new HashMap<>();

	public static void registerPlaceholder(String key, PlaceholderWorker worker) {
		placeholders.put("$" + key + "$", worker);
	}

	public static Map<String, PlaceholderWorker> getPlaceholders() {
		return placeholders;
	}

}
