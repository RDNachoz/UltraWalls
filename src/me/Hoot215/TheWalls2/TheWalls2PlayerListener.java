package me.Hoot215.TheWalls2;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class TheWalls2PlayerListener implements Listener{
	private TheWalls2 plugin;

	public TheWalls2PlayerListener(TheWalls2 instance){
		plugin=instance;
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent event){
		Player player=event.getPlayer();
		TheWalls2GameList gameList=plugin.getGameList();

		if (player.getWorld().getName().equals(TheWalls2.worldName)){
			if (gameList==null){
				if (plugin.getQueue().isInQueue(player.getName())){
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED+"You can't do that until the game starts!");
					return;
				}
			}
			else if (gameList.isInGame(player.getName())){
				if (plugin.getLocationData().isPartOfWall(event.getBlock().getLocation())){
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED+"Don't break the rules!");
					return;
				}
				for(Location loc:plugin.getLocationData().getSlots()){
					if (loc.getBlockX()==event.getBlock().getX()&&loc.getBlockZ()==event.getBlock().getZ()){
						event.setCancelled(true);
						player.sendMessage(ChatColor.RED+"Don't break the rules!");
						return;
					}
				}
			}
		}
	}

	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerDeath(PlayerDeathEvent event){
		Player player=event.getEntity();
		String playerName=player.getName();
		TheWalls2GameList gameList=plugin.getGameList();
		TheWalls2RespawnQueue respawnQueue=plugin.getRespawnQueue();

		if (gameList==null)
			return;

		if (gameList.isInGame(playerName)){
			plugin.getGameList();
			gameList.notifyAll(ChatColor.YELLOW.toString()+playerName+ChatColor.RED.toString()+" has been defeated in "+"a game of The Walls 2!");
			gameList.removeFromGame(playerName);
			respawnQueue.addPlayer(playerName,player.getLocation());
			plugin.checkIfGameIsOver();
			return;
		}
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent event){
		Player player=event.getPlayer();
		TheWalls2GameList gameList=plugin.getGameList();

		if (player.getWorld().getName().equals(TheWalls2.worldName)){
			if (gameList==null){
				if (plugin.getQueue().isInQueue(player.getName())){
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED+"You can't do that until the game starts!");
					return;
				}
			}
			else if (gameList.isInGame(player.getName())){
				if (plugin.getLocationData().isPartOfWall(event.getBlock().getLocation())){
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED+"Don't break the rules!");
					return;
				}
				if (event.getBlock().getY()>93){
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED+"Don't break the rules!");
					return;
				}
				for(Location loc:plugin.getLocationData().getSlots()){
					if (loc.getBlockX()==event.getBlock().getX()&&loc.getBlockZ()==event.getBlock().getZ()){
						event.setCancelled(true);
						player.sendMessage(ChatColor.RED+"Don't break the rules!");
						return;
					}
				}
			}
		}
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player=event.getPlayer();

		if (player.getWorld().getName().equals(TheWalls2.worldName)){
			TheWalls2GameList gameList=plugin.getGameList();
			if (gameList==null){
				if (plugin.getQueue().isInQueue(player.getName())){
					if (event.getAction()==org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK&&event.getClickedBlock().getType()==org.bukkit.Material.STONE_BUTTON)
						return;
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED+"You can't do that until the game starts!");
				}
			}
			else{
				if (player.getItemInHand().getType()!=Material.COMPASS||!gameList.isInGame(player.getName()))
					return;

				Player randomPlayer=null;
				int count=0;
				while(true){
					List<Player> playerList=player.getWorld().getPlayers();
					int playerCount=playerList.size();
					int randomInt=new Random().nextInt(playerCount);
					randomPlayer=playerList.get(randomInt);
					if (randomPlayer!=player)
						break;
					if (count>=20){
						player.sendMessage(ChatColor.RED+"Either you are "+"extremely unlucky or there is no one else "+"playing with you!");
						return;
					}
					count++;
				}
				player.setCompassTarget(randomPlayer.getLocation());
				player.sendMessage(ChatColor.GREEN+"Random player located!");
			}
		}
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		if (!(event.getEntity() instanceof Player))
			return;

		Player player=(Player)event.getEntity();

		if (player.getWorld().getName().equals(TheWalls2.worldName)){
			if (plugin.getGameList()==null){
				if (plugin.getQueue().isInQueue(player.getName())){
					event.setCancelled(true);
				}
			}
			else{
				if (!(event.getDamager() instanceof Player))
					return;

				if (!plugin.getConfig().getBoolean("general.friendly-fire")){
					Player attacker=(Player)event.getDamager();
					TheWalls2GameTeams teams=plugin.getGameTeams();

					if (plugin.getGameList().isInGame(player.getName())&&plugin.getGameList().isInGame(attacker.getName())){
						if (teams.compareTeams(player.getName(),attacker.getName())){
							attacker.sendMessage(ChatColor.RED+"Friendly fire is disabled!");
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event){
		Player player=event.getPlayer();
		final String playerName=player.getName();
		TheWalls2GameList gameList=plugin.getGameList();
		TheWalls2PlayerQueue queue=plugin.getQueue();

		if (gameList==null){
			if (queue.isInQueue(playerName)){
				queue.removePlayer(playerName,true);
				return;
			}
			return;
		}

		if (gameList.isInGame(playerName)){
			int time=plugin.getConfig().getInt("general.disconnect-timer");
			plugin.getLogger().info(ChatColor.YELLOW+playerName+ChatColor.RED+" has disconnected! "+"They have "+String.valueOf(time)+" seconds to reconnect before they quit The Walls 2");
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,new Runnable(){
				public void run(){
					TheWalls2GameList futureGameList=plugin.getGameList();
					if (futureGameList==null)
						return;

					if (plugin.getServer().getPlayer(playerName)==null){
						futureGameList.removeFromGame(playerName);
						plugin.getLogger().info(ChatColor.YELLOW+playerName+ChatColor.RED+" has forfeit The Walls 2");
						plugin.checkIfGameIsOver();
					}
				}
			},time*20);
		}
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event){
		Player player=event.getPlayer();
		final String playerName=player.getName();
		TheWalls2RespawnQueue respawnQueue=plugin.getRespawnQueue();

		if (respawnQueue.isInRespawnQueue(playerName)){
			respawnQueue.removePlayer(playerName);
			plugin.getServer().getScheduler().runTaskLater(plugin,new Runnable(){
				public void run(){
					Player fplayer=plugin.getServer().getPlayerExact(playerName);
					if (fplayer!=null)
						plugin.getServer().dispatchCommand(fplayer,"warp Standing_Stones");
				}
			},5L);
		}
		/*
		 * event.setRespawnLocation(respawnQueue
		 * .getLastPlayerLocation(playerName));
		 * respawnQueue.removePlayer(playerName);
		 * player.getInventory().setContents(
		 * plugin.getInventory().getInventoryContents(playerName));
		 * player.getInventory().setArmorContents(
		 * plugin.getInventory().getArmourContents(playerName)); }
		 */
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player=event.getPlayer();
		final String playerName=player.getName();
		TheWalls2GameList gameList=plugin.getGameList();
		TheWalls2PlayerQueue queue=plugin.getQueue();
		if (gameList==null){
			if (player.getWorld().getName().equals(TheWalls2.worldName)){
				if (!queue.isInQueue(playerName)){
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,new Runnable(){
						public void run(){
							Player futurePlayer=plugin.getServer().getPlayer(playerName);
							if (futurePlayer==null)
								return;
							plugin.getServer().dispatchCommand(futurePlayer,"warp Standing_Stones");
						}
					},1L);
				}
			}
		}
		else{
			if (!gameList.isInGame(playerName)&&queue.isInQueue(playerName)){
				queue.removePlayer(playerName,true);
				player.getInventory().setContents(plugin.getInventory().getInventoryContents(playerName));
				player.getInventory().setArmorContents(plugin.getInventory().getArmourContents(playerName));
			}
		}
	}
}
