package net.worldofclucky.teleport;


import net.worldofclucky.teleport.utilities.ConfigManager;
import net.worldofclucky.teleport.utilities.Storage;

import org.raftpowered.api.Plugin;
import org.raftpowered.Raft;
import org.raftpowered.Severity;
import org.raftpowered.exception.ConfigFailedToLoadException;
import org.raftpowered.exception.InvalidCommandException;

@Plugin(description = "Limited player teleporting", name = "Teleport", version = "0.1")
public class Main {

	public void onEnable() throws InvalidCommandException, ConfigFailedToLoadException  {
		Raft.log(Severity.INFO,"Teleport started successfully!");
		Raft.addCommand(Homes.class);
		
		Storage.createSaves();
		ConfigManager.createConfig();
	}
}
