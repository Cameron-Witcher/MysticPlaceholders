package net.mysticcloud.spigot.core.utils.functions;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface FunctionWorker {

	public abstract String run(Player player, String[] args);

}
