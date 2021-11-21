package net.mysticcloud.spigot.core;

import org.bukkit.plugin.java.JavaPlugin;

import net.mysticcloud.spigot.core.commands.AdminCommands;
import net.mysticcloud.spigot.core.utils.Utils;

public class MysticPlaceholders extends JavaPlugin {
	
	
	@Override
	public void onEnable() {
		Utils.init(this);
		
		new AdminCommands(this,"mysticplaceholders");
	}

}
