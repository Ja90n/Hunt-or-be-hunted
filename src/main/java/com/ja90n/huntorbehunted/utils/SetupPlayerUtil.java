package com.ja90n.huntorbehunted.utils;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SetupPlayerUtil {
    public SetupPlayerUtil(Player player, String team){
        player.closeInventory();
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);
        player.setFoodLevel(20);

        if (team.equals("seeker")){
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) new ItemStack(Material.LEATHER_BOOTS).getItemMeta();
            leatherArmorMeta.setColor(Color.RED);

            player.getInventory().setChestplate(new ItemStack (Material.LEATHER_CHESTPLATE));
            player.getInventory().setLeggings(new ItemStack (Material.LEATHER_LEGGINGS));
            player.getInventory().setBoots(new ItemStack (Material.LEATHER_BOOTS));

            player.getInventory().getChestplate().setItemMeta(leatherArmorMeta);
            player.getInventory().getLeggings().setItemMeta(leatherArmorMeta);
            player.getInventory().getBoots().setItemMeta(leatherArmorMeta);

            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999 ,5, false, false));
        } else {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) new ItemStack(Material.LEATHER_BOOTS).getItemMeta();
            leatherArmorMeta.setColor(Color.BLUE);

            player.getInventory().setChestplate(new ItemStack (Material.LEATHER_CHESTPLATE));
            player.getInventory().setLeggings(new ItemStack (Material.LEATHER_LEGGINGS));
            player.getInventory().setBoots(new ItemStack (Material.LEATHER_BOOTS));

            player.getInventory().getChestplate().setItemMeta(leatherArmorMeta);
            player.getInventory().getLeggings().setItemMeta(leatherArmorMeta);
            player.getInventory().getBoots().setItemMeta(leatherArmorMeta);

            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,999999999,5,true,false));
        }
        player.setGameMode(GameMode.ADVENTURE);
    }
}
