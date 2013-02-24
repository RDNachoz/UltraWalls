package me.Hoot215.TheWalls2;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_4_R1.CraftWorld;
import org.bukkit.entity.Player;

public class TheWalls2World{
	public static boolean isRestoring=false;

	public static void reloadWorld(final TheWalls2 plugin){
		plugin.getLogger().info(ChatColor.YELLOW+"World is being unloaded...");
		isRestoring=true;
		World world=plugin.getServer().getWorld(TheWalls2.worldName);
		for(Player player:world.getPlayers()){
			if (player==null)break;
			player.kickPlayer("[TheWalls2] You can't be in the world when it unloads! Please re-join in a few seconds.");
		}
		for(Player player:plugin.getRespawnQueue().getPlayerList()){
			if (player==null)break;

			player.kickPlayer("[TheWalls2] You can't be in the world when it unloads! Please re-join in a few seconds.");
		}

		CraftWorld cw=(CraftWorld)world;
		cw.getHandle().players.clear();

		if (plugin.getServer().unloadWorld(TheWalls2.worldName,false)){
			plugin.getLogger().info(ChatColor.YELLOW+"World is being loaded...");
			WorldCreator wc=new WorldCreator(TheWalls2.worldName);
			World newWorld=plugin.getServer().createWorld(wc);
			newWorld.setAutoSave(false);
			plugin.getLocationData().setWorld(newWorld);
			isRestoring=false;
		}
		else{
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,new Runnable(){
				public void run(){
					if (Bukkit.getServer().unloadWorld(TheWalls2.worldName,false)){
						WorldCreator wc=new WorldCreator(TheWalls2.worldName);
						Bukkit.getServer().createWorld(wc).setAutoSave(false);
					}
					else{
						System.out.println("[TheWalls2] The world failed to unload!");
					}
					isRestoring=false;
				}
			},60L);
		}
	}
}
