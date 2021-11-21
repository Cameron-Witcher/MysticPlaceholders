package net.mysticcloud.spigot.core.commands;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.mysticcloud.spigot.core.utils.Perm;
import net.mysticcloud.spigot.core.utils.PlaceholderUtils;

public class AdminCommands implements CommandExecutor {

	public AdminCommands(JavaPlugin plugin, String... cmds) {
		for (String cmd : cmds) {
			plugin.getCommand(cmd).setExecutor(this);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mysticplaceholders")) {
			if (sender.hasPermission(Perm.ADMIN)) {
				if (args.length == 0) {
					return true;
				}
				if (args[0].equalsIgnoreCase("update")) {
					if (update()) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&9&lMP&f >&7 Successfully downloaded MysticPlaceholders.jar. Please restart the server as soon as possible to avoid any fatal bugs"));
					}
				}
			}
		}
		return true;
	}

	public static boolean update() {

		boolean success = true;
		InputStream in = null;
		FileOutputStream out = null;

		try {

			URL myUrl = new URL(
					"https://jenkins.mysticcloud.net/job/LushCore/lastSuccessfulBuild/artifact/target/LushCore.jar");
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
			out = new FileOutputStream("plugins/LushCore.jar");
			int c;
			byte[] b = new byte[1024];
			while ((c = in.read(b)) != -1)
				out.write(b, 0, c);

		}

		catch (Exception ex) {
			PlaceholderUtils.getPlugin().getLogger().log(Level.SEVERE,
					"There was an error updating. Check console for details.");
			ex.printStackTrace();
			success = false;
		}

		finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					PlaceholderUtils.getPlugin().getLogger().log(Level.SEVERE,
							"There was an error updating. Check console for details.");
					e.printStackTrace();
				}
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					PlaceholderUtils.getPlugin().getLogger().log(Level.SEVERE,
							"There was an error updating. Check console for details.");
					e.printStackTrace();
				}
		}
		return success;
	}
}
