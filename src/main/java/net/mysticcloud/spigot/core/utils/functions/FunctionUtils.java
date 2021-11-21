package net.mysticcloud.spigot.core.utils.functions;

import java.util.HashMap;
import java.util.Map;

import net.mysticcloud.spigot.core.utils.placeholders.PlaceholderWorker;

public class FunctionUtils {

	private static Map<String, FunctionWorker> functions = new HashMap<>();

	public static void registerFunction(String key, FunctionWorker worker) {
		functions.put(key, worker);
	}

	public static Map<String, FunctionWorker> getFunctions() {
		return functions;
	}

}
