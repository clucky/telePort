package net.worldofclucky.teleport.utilities;

import java.io.File;
import java.io.IOException;

import org.raftpowered.Raft;
import org.raftpowered.config.Config;
import org.raftpowered.exception.ConfigFailedToLoadException;

public class ConfigManager {
	private static Config config;
	
	public static void createConfig() throws ConfigFailedToLoadException {
		File dir = new File("plugins/teleport");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File file = new File("plugins/teleport/config.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		config = Raft.loadConfig(file);
	}
}
