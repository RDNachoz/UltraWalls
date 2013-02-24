package me.Hoot215.TheWalls2;

import org.bukkit.Location;

public class TheWalls2ConfigSetter{
	public static boolean isLobbyDifferent(TheWalls2 plugin, String locString){
		if (locString.isEmpty())
			return false;
		String[] locStringArray=locString.split(";");
		double x=Double.parseDouble(locStringArray[0]);
		double y=Double.parseDouble(locStringArray[1]);
		double z=Double.parseDouble(locStringArray[2]);

		Location lobby=plugin.getLocationData().getLobby();
		if (lobby.getX()!=x||lobby.getY()!=y||lobby.getZ()!=z){
			return true;
		}
		return false;
	}

	public static void updateLobbyLocation(TheWalls2 plugin, String locString){
		String[] locStringArray=locString.split(";");
		double x=Double.parseDouble(locStringArray[0]);
		double y=Double.parseDouble(locStringArray[1]);
		double z=Double.parseDouble(locStringArray[2]);
		Location lobby=new Location(plugin.getServer().getWorld(TheWalls2.worldName),x,y,z);
		plugin.getLocationData().setLobby(lobby);
	}
}
