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

import net.mysticcloud.spigot.core.utils.Perm;
import net.mysticcloud.spigot.core.utils.Utils;

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
					if (Utils.update()) {
						sender.sendMessage(Utils.PREFIX + Utils.colorize(
								"Successfully downloaded MysticPlaceholders.jar. Please restart the server as soon as possible to avoid any fatal bugs"));
					}
				}
			}
		}
		return true;
	}

	
}
