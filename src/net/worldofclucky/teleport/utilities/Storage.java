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
	
	public static String findUUID(String player) {
		for (String i : saves.getKeys(false)) {
			if (saves.keyExists("player." + i + ".LastKnownName") && saves.getString("player." + i + ".LastKnownName").equalsIgnoreCase(player)) {
				return i;
			}
		}
		return null;
	}
	
	public static void setLastKnownName(String uuid, String player) {
		saves.set("player." + uuid + ".LastKnownName", player);
	}
	
	public static Location getHome(String player, String name) {
		String uuid = Storage.findUUID(player);
		if (uuid == null) {
			return null;
		}
		String world = saves.getString("player." + uuid + ".homes." + name + ".world");
		double x = saves.getDouble("player." + uuid + ".homes." + name + ".x");
		double y = saves.getDouble("player." + uuid + ".homes." + name + ".y");
		double z = saves.getDouble("player." + uuid + ".homes." + name + ".z");
		Location location = new Location(Raft.getWorld(world), x, y, z);
		return location;
	}
	
	public static Location getHome(Player player, String name) {
		String uuid = player.getMojangId().toString();
		String world = saves.getString("player." + uuid + ".homes." + name + ".world");
		double x = saves.getDouble("player." + uuid + ".homes." + name + ".x");
		double y = saves.getDouble("player." + uuid + ".homes." + name + ".y");
		double z = saves.getDouble("player." + uuid + ".homes." + name + ".z");
		Location location = new Location(Raft.getWorld(world), x, y, z);
		return location;
	}
	
	public static void setHomeToPlayer(Player player, String name) {
		setHome(player, name, player.getLocation());
	}
	
	public static void setHome(Player player, String name, Location location) {
		String uuid = player.getMojangId().toString();
		setLastKnownName(uuid, player.toString()); //Temporary solution to absence of PlayerLoginEvent
		saves.set("player." + uuid + ".homes." + name + ".x", location.getX());
		saves.set("player." + uuid + ".homes." + name + ".y", location.getY());
		saves.set("player." + uuid + ".homes." + name + ".z", location.getZ());
		saves.set("player." + uuid + ".homes." + name + ".world", location.getWorld().getName());
	}
	
	public static StorageReturn setHome(String player, String name, Location location) {
		String uuid = Storage.findUUID(player);
		if (uuid == null) {
			return StorageReturn.NOPLAYERFOUND;
		}
		setLastKnownName(uuid, player); //Temporary solution to absence of PlayerLoginEvent
		saves.set("player." + uuid + ".homes." + name + ".x", location.getX());
		saves.set("player." + uuid + ".homes." + name + ".y", location.getY());
		saves.set("player." + uuid + ".homes." + name + ".z", location.getZ());
		saves.set("player." + uuid + ".homes." + name + ".world", location.getWorld().getName());
		return StorageReturn.NORMAL;
	}
	
	public static void removeHome() {
	}
	
	public static String[] listHomes() {
		String[] homes = {"home1", "home2"};
		return homes;
	}
}
