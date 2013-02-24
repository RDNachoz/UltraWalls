package me.Hoot215.TheWalls2;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

public class TheWalls2GameTimer implements Runnable{
	private TheWalls2 plugin;
	private boolean notify;
	private int notifyInterval;
	private int time;
	private int originalTime;

	public TheWalls2GameTimer(TheWalls2 instance,boolean notify,int notifyInterval,int time){
		plugin=instance;
		this.notify=notify;
		this.notifyInterval=notifyInterval;
		this.time=time;
		originalTime=time;
	}

	public void run(){
		while(time>0){
			if (plugin.getGameList()==null)
				return;

			if (notify&&time!=originalTime&&time%notifyInterval==0){
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,new Runnable(){
					public void run(){
						plugin.getGameList().notifyAll(ChatColor.DARK_PURPLE.toString()+String.valueOf(time)+" minutes remaining!");
						time=time-1;
					}
				});
			}
			else
				time=time-1;
			try{
				Thread.sleep(60000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}

		if (plugin.getGameList()==null)
			return;

		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,new Runnable(){
			public void run(){
				plugin.getGameList().notifyAll(ChatColor.DARK_PURPLE.toString()+"The walls are dropping!");
				Location virtualWallDrop=plugin.getLocationData().getVirtualWallDrop();
				plugin.getServer().getWorld(TheWalls2.worldName).getBlockAt(virtualWallDrop).setType(Material.REDSTONE_TORCH_ON);
			}
		});
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,new Runnable(){
			public void run(){
				Location virtualWallDrop=plugin.getLocationData().getVirtualWallDrop();
				plugin.getServer().getWorld(TheWalls2.worldName).getBlockAt(virtualWallDrop).setType(Material.AIR);
			}
		},2L);
	}
}
