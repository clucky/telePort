package net.worldofclucky.teleport.utilities;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.raftpowered.Location;
import org.raftpowered.Raft;
import org.raftpowered.config.Config;
import org.raftpowered.entity.Player;
import org.raftpowered.exception.ConfigFailedToLoadException;
import org.raftpowered.world.World;

public class Storage {
	private static Config saves;
	
	public static void createSaves() throws ConfigFailedToLoadException {
		File dir = new File("plugins/teleport");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File file = new File("plugins/teleport/saves.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		saves = Raft.loadConfig(file);
	}
	
	public static Location getHome(Player player, String name) {
		String world = "world";
		double x = 0;
		double y = 0;
		double z = 0;
		Location location = new Location(Raft.getWorld(world), x, y, z);
		return location;
	}
	
	public static void setHomeToPlayer(Player player, String name) {
		setHome(player.getMojangId(), name, player.getLocation());
	}
	
	public static void setHome(Player player, String name, Location location) {
		setHome(player.getMojangId(), name, location);
	}
	
	public static void setHome(UUID player, String name, Location location) {
		String uuid = player.toString();
		saves.set(uuid + ".homes." + name + ".x", location.getX());
		saves.set(uuid + ".homes." + name + ".y", location.getY());
		saves.set(uuid + ".homes." + name + ".z", location.getZ());
		saves.set(uuid + ".homes." + name + ".world", location.getWorld().getName());
	}
	
	public static StorageReturn setHome(String player, String name, Location location) {
		String uuid = "";
	playerSearch:
		for (String i : saves.getKeys(false)) {
			if (saves.keyExists(i + ".homes.LastKnownName") && saves.getString(i + ".homes.LastKnownName").equalsIgnoreCase(player)) {
				uuid = i;
				break playerSearch;
			}
		}
		if (uuid.equalsIgnoreCase("")) {
			return StorageReturn.NOPLAYERFOUND;
		}
		saves.set(uuid + ".homes." + name + ".x", location.getX());
		saves.set(uuid + ".homes." + name + ".y", location.getY());
		saves.set(uuid + ".homes." + name + ".z", location.getZ());
		saves.set(uuid + ".homes." + name + ".world", location.getWorld().getName());
		return StorageReturn.NORMAL;
	}
	
	public static void removeHome() {
	}
	
	public static String[] listHomes() {
		String[] homes = {"home1", "home2"};
		return homes;
	}
}
