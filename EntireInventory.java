package me.Hoot215.TheWalls2.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EntireInventory
  {
    private ItemStack[] contents;
    private ItemStack[] armourContents;
    
    public EntireInventory(PlayerInventory playerInv)
      {
        contents = playerInv.getContents();
        armourContents = playerInv.getArmorContents();
      }
    
    public ItemStack[] getContents ()
      {
        return contents;
      }
    
    public ItemStack[] getArmourContents ()
      {
        return armourContents;
      }
  }
