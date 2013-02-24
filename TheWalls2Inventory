package me.Hoot215.TheWalls2;

import java.util.HashMap;
import java.util.Map;

import me.Hoot215.TheWalls2.util.EntireInventory;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class TheWalls2Inventory
  {
    private Map<String, EntireInventory> inventoryMap;
    
    public TheWalls2Inventory()
      {
        inventoryMap = new HashMap<String, EntireInventory>();
      }
    
    public ItemStack[] getInventoryContents (String playerName)
      {
        return inventoryMap.get(playerName).getContents();
      }
    
    public ItemStack[] getArmourContents (String playerName)
      {
        return inventoryMap.get(playerName).getArmourContents();
      }
    
    public boolean hasInventory (String playerName)
      {
        return inventoryMap.containsKey(playerName);
      }
    
    public void addInventory (String playerName, PlayerInventory inv)
      {
        inventoryMap.put(playerName, new EntireInventory(inv));
      }
    
    public void removeInventory (String playerName)
      {
        inventoryMap.remove(playerName);
      }
    
    public void clearInventory (Player player)
      {
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setExp(0);
        player.setGameMode(GameMode.SURVIVAL);
      }
  }
