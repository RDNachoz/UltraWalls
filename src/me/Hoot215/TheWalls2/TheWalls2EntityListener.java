package me.Hoot215.TheWalls2;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class TheWalls2EntityListener implements Listener
  {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCreatureSpawn (CreatureSpawnEvent event)
      {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Monster)
          if (event.getLocation().getWorld().getName()
              .equals(TheWalls2.worldName))
            event.setCancelled(true);
      }
  }
