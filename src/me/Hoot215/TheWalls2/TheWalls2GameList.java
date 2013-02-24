package me.Hoot215.TheWalls2;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TheWalls2GameList{
	private Set<String> gameList;

	public TheWalls2GameList(Set<String> queue){
		gameList=new HashSet<String>();

		for(String s:queue){
			gameList.add(s.split(":")[0]);
		}
	}

	public void notifyAll(String message){
		for(String playerName:gameList){
			Player player=Bukkit.getServer().getPlayer(playerName);
			String prefix=ChatColor.AQUA+""+ChatColor.YELLOW;
			player.sendMessage(prefix+message);
		}
	}

	public void removeFromGame(String playerName){
		gameList.remove(playerName);
	}

	public boolean isInGame(String playerName){
		if (gameList.contains(playerName))
			return true;
		return false;
	}

	public int getPlayerCount(){
		return gameList.size();
	}

	public String getLastPlayer(){
		String ret=null;
		for(String s:gameList){
			ret=s;
		}
		return ret;
	}

	public Set<String> getList(){
		return gameList;
	}

}
