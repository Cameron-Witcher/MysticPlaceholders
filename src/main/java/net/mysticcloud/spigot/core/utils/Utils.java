package net.mysticcloud.spigot.core.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

import net.md_5.bungee.api.ChatColor;
import net.mysticcloud.spigot.core.MysticPlaceholders;
import net.mysticcloud.spigot.core.utils.functions.FunctionUtils;
import net.mysticcloud.spigot.core.utils.placeholders.PlaceholderUtils;
import net.mysticcloud.spigot.core.utils.placeholders.PlaceholderWorker;

public class Utils {
	private static MysticPlaceholders plugin;

	public static final String PREFIX = colorize("&3&lM&9&lP&7 > &f");

	public static void init(MysticPlaceholders main) {
		plugin = main;
		PlaceholderWorker name = (player) -> {
			return player.getName();
		};
		PlaceholderUtils.registerPlaceholder("player", name);
		PlaceholderUtils.registerPlaceholder("name", name);
		PlaceholderUtils.registerPlaceholder("uuid", (pl) -> {
			return pl.getUniqueId() + "";
		});

		FunctionUtils.registerFunction("fade", (pl, args) -> {
			String msg = args[args.length - 1];

			int[] start = getRGB(args[0]);
			int[] last = getRGB(args[1]);

			StringBuilder sb = new StringBuilder();

			Integer dR = numberFade(start[0], last[0], msg.length());
			Integer dG = numberFade(start[1], last[1], msg.length());
			Integer dB = numberFade(start[2], last[2], msg.length());

			for (int i = 0; i < msg.length(); i++) {
				java.awt.Color c = new java.awt.Color(start[0] + dR * i, start[1] + dG * i, start[2] + dB * i);

				sb.append(net.md_5.bungee.api.ChatColor.of(c) + "" + msg.charAt(i));
			}
			return sb.toString();
		});

		FunctionUtils.registerFunction("upper", (pl, args) -> {
			String a = "";
			for (String s : args)
				a = a == "" ? s : a + ":" + s;
			return a.toUpperCase();
		});

		log(PlaceholderAPI.runFunctions(null, "%upper:test-upper%"));

	}

	private static int[] getRGB(String rgb) {
		int[] ret = new int[3];
		for (int i = 0; i < 3; i++) {
			ret[i] = hexToInt(rgb.charAt(i * 2), rgb.charAt(i * 2 + 1));
		}
		return ret;
	}

	private static int hexToInt(char a, char b) {
		int x = a < 65 ? a - 48 : a - 55;
		int y = b < 65 ? b - 48 : b - 55;
		return x * 16 + y;
	}

	private static Integer numberFade(int i, int f, int n) {
		int d = (f - i) / (n - 1);
		return d;
	}

	public static MysticPlaceholders getPlugin() {
		return plugin;
	}

	public static String colorize(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static void log(String message) {
		log(Level.INFO, message);
	}

	public static void log(Level level, String message) {
		plugin.getLogger().log(level, message);
	}

	public static boolean update() {

		boolean success = true;
		InputStream in = null;
		FileOutputStream out = null;

		try {

			URL myUrl = new URL(
					"https://jenkins.mysticcloud.net/job/MysticPlaceholders/lastSuccessfulBuild/artifact/target/MysticPlaceholders.jar");
			HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
			conn.setDoOutput(true);
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestMethod("GET");
			in = conn.getInputStream();
			out = new FileOutputStream("plugins/MysticPlaceholders.jar");
			int c;
			byte[] b = new byte[1024];
			while ((c = in.read(b)) != -1)
				out.write(b, 0, c);

		}

		catch (Exception ex) {
			Utils.log(Level.SEVERE, "There was an error updating. Check console for details.");
			ex.printStackTrace();
			success = false;
		}

		finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					Utils.log(Level.SEVERE, "There was an error updating. Check console for details.");
					e.printStackTrace();
				}
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					Utils.log(Level.SEVERE, "There was an error updating. Check console for details.");
					e.printStackTrace();
				}
		}
		return success;
	}

}
