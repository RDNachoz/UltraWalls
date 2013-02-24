package me.Hoot215.TheWalls2;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TheWalls2RespawnQueue
  {
    private TheWalls2 plugin;
    private Set<String> respawnQueue;
    
    public TheWalls2RespawnQueue(TheWalls2 instance)
      {
        plugin = instance;
        respawnQueue = new HashSet<String>();
      }
    
    public boolean isInRespawnQueue (String playerName)
      {
        if (respawnQueue.isEmpty())
          return false;
        
        for (String s : respawnQueue)
          {
            if (s.split(":")[0].equals(playerName))
              return true;
          }
        return false;
      }
    
    public Location getLastPlayerLocation (String playerName)
      {
        Location loc = null;
        
        for (String s : respawnQueue)
          {
            String[] a = s.split(":");
            if (a[0].equals(playerName))
              {
                loc =
                    new Location(plugin.getServer().getWorld(a[1]),
                        Double.parseDouble(a[2]), Double.parseDouble(a[3]),
                        Double.parseDouble(a[4]));
              }
          }
        
        return loc;
      }
    
    public Set<Player> getPlayerList ()
      {
        Set<Player> playerList = new HashSet<Player>();
        for (String s : respawnQueue)
          {
            String playerName = s.split(":")[0];
            playerList.add(Bukkit.getPlayer(playerName));
          }
        return playerList;
      }
    
    public Set<String> getList ()
      {
        return respawnQueue;
      }
    
    public void addPlayer (String playerName, Location loc)
      {
        respawnQueue.add(playerName + ":" + loc.getWorld().getName() + ":"
            + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ());
      }
    
    public void removePlayer (String playerName)
      {
        Iterator<String> iter = respawnQueue.iterator();
        while (iter.hasNext())
          {
            String s = iter.next();
            
            if (s.contains(playerName))
              {
                iter.remove();
              }
          }
      }
  }
