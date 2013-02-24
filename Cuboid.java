package me.Hoot215.TheWalls2.util;

import org.bukkit.Location;

public class Cuboid
  {
    private int xMin, xMax, yMin, yMax, zMin, zMax;
    
    public Cuboid(Location loc1, Location loc2)
      {
        this.xMin = Math.min(loc1.getBlockX(), loc2.getBlockX());
        this.xMax = Math.max(loc1.getBlockX(), loc2.getBlockX());
        this.yMin = Math.min(loc1.getBlockY(), loc2.getBlockY());
        this.yMax = Math.max(loc1.getBlockY(), loc2.getBlockY());
        this.zMin = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        this.zMax = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
      }
    
    public int getXMin ()
      {
        return xMin;
      }
    
    public int getXMax ()
      {
        return xMax;
      }
    
    public int getYMin ()
      {
        return yMin;
      }
    
    public int getYMax ()
      {
        return yMax;
      }
    
    public int getZMin ()
      {
        return zMin;
      }
    
    public int getZMax ()
      {
        return zMax;
      }
    
    public boolean isIn (Location loc)
      {
        if (loc.getBlockX() < xMin)
          return false;
        if (loc.getBlockX() > xMax)
          return false;
        if (loc.getBlockY() < yMin)
          return false;
        if (loc.getBlockY() > yMax)
          return false;
        if (loc.getBlockZ() < zMin)
          return false;
        if (loc.getBlockZ() > zMax)
          return false;
        return true;
      }
  }
