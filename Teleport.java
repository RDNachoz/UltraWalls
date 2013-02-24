package me.Hoot215.TheWalls2.util;

import me.Hoot215.TheWalls2.TheWalls2;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Teleport
  {
    public static void teleportPlayerToLocation (Player player, Location loc)
      {
        World world = Bukkit.getServer().getWorld(TheWalls2.worldName);
        if (world == null)
          {
            System.out.println("[UltraWalls] ERROR: World is null while "
                + "attempting to teleport player " + player.getName());
            return;
          }
        
        Chunk chunk = world.getChunkAt(loc);
        if ( !chunk.isLoaded())
          {
            chunk.load();
          }
        
        player.teleport(loc);
      }
  }
