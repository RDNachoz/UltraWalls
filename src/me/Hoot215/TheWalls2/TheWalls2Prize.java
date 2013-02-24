package me.Hoot215.TheWalls2;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TheWalls2Prize{
	public static void givePrize(TheWalls2 plugin, Player player){
		String prize=plugin.getConfig().getString("general.prize");
		if (prize.equals("none")){
			return;
		}
		else if (prize.equals("item")){
			giveItemPrize(plugin,player);
			return;
		}
		else if (prize.equals("money")){
			giveMoneyPrize(plugin,player);
			return;
		}
	}

	public static void giveItemPrize(TheWalls2 plugin, Player player){
		String[] prize=plugin.getConfig().getString("general.prize-item").split(":");
		int prizeID=Integer.parseInt(prize[0]);
		int prizeAmount=Integer.parseInt(prize[2]);
		short prizeData=Short.parseShort(prize[1]);
		ItemStack item=new ItemStack(prizeID,prizeAmount,prizeData);
		player.getWorld().dropItem(player.getLocation(),item);
		player.sendMessage(ChatColor.DARK_PURPLE+"[UltraWalls] "+ChatColor.GREEN+"Here's your prize!");
	}

	public static void giveMoneyPrize(TheWalls2 plugin, Player player){
		double amount=plugin.getConfig().getDouble("general.prize-money");
		TheWalls2.economy.depositPlayer(player.getName(),amount);
		player.sendMessage(ChatColor.DARK_PURPLE+"[UltraWalls] "+ChatColor.GREEN+"Here's your prize!");
		player.sendMessage(ChatColor.DARK_PURPLE+"[UltraWalls] "+ChatColor.GOLD+TheWalls2.economy.format(amount)+ChatColor.GREEN+" has been deposited into your account");
	}
}
