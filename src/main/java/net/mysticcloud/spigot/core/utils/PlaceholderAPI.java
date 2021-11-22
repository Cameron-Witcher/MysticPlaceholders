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
		return hold;
	}

	public static String runFunctions(Player player, String string) {
		String hold = string;
		for (Entry<String, FunctionWorker> e : FunctionUtils.getFunctions().entrySet()) {
//			while (hold.contains("$" + e.getKey() + ":")) {
//			String key = e.getKey();
			String key = "fade";
			String tmp = hold;
			// $fade:FFFF00:FF6666:This is the message-fade$
			String info = "";
//				try {
			Utils.log("Looking for " + "$" + e.getKey() + ": inside of: " + tmp);
			Utils.log(tmp.split("$" + e.getKey() + ":").length + "");
			info = tmp.split("$" + key + ":")[1].split("-" + key + "$")[0];
			Utils.log("First one worked. Info: " + info);
//				} catch (ArrayIndexOutOfBoundsException ex) {
//					info = tmp.replace("$" + key + ":", "");
//					Utils.log("Replacing first part. Info: " + info);
//					try {
//						String extra = info.split("-" + key + "$")[1];
//						info = info.split("-" + key + "$")[0];
//						Utils.log("Second one worked. Info: " + info);
//						Utils.log("Took out extra: " + extra);
//					} catch (ArrayIndexOutOfBoundsException ex2) {
//						info = info.replace("-" + key + "$", "");
//						Utils.log("Third one's a charm ig. Info: " + info);
//					}
//				}
//				Utils.log(info);
			String rpl = e.getValue().run(player, info.split(":"));
			hold = hold.replace("$" + key + ":" + info + "-" + key + "$", rpl);
//			}
		}
		return hold;
	}

	/*
	 * while (message.contains("%fade:")) String tmp = message; String info =
	 * tmp.split("%fade:")[1].split("-fade%")[0]; String from = info.split(":")[0];
	 * String to = info.split(":")[1]; String s = ""; //
	 * #FF6666[%emoticon:SWORD%%fade:FF6666:FFFF00:BEAST-fade%%emoticon:SWORD%] //
	 * info = FF6666:FFFF00:BEAST // from = FF6666 // to = FFFF00 // s = BEAST for
	 * (String a : info.split(":")) { if (a.equals(from) || a.equals(to)) continue;
	 * s = s == "" ? a : s + ":" + a; }
	 * 
	 * // DebugUtils.debug("Info: " + info); // DebugUtils.debug("From: " + from);
	 * // DebugUtils.debug("To: " + to); // DebugUtils.debug("S: " + s);
	 * 
	 * 
	 * message = message.replaceFirst("%fade:" + info + "-fade%",
	 * CoreChatUtils.fade(from, to, s)); }
	 */

}
