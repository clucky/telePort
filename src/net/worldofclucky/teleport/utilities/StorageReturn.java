package net.worldofclucky.teleport.utilities;

public enum StorageReturn {
	NORMAL(true, ""),
	NOPLAYERFOUND(false, "There is no player found by this name."),
	NOHOMEFOUND(false, "There was no home found by this name."),
	NOSPAWNSET(false, "Spawn location has not been set.");
	
	private final boolean status;
	private final String reason;
	StorageReturn(boolean status, String reason) {
		this.status = status;
		this.reason = reason;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public String getReason() {
		return reason;
	}
}
