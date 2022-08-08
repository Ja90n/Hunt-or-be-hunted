package com.ja90n.huntorbehunted.runnables;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import com.ja90n.huntorbehunted.instances.Arena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class SeekerStartCountdownRunnable extends BukkitRunnable {

    private HuntOrBeHunted huntOrBeHunted;
    private Player player;
    private Arena arena;
    private int countdownSeconds;

    public SeekerStartCountdownRunnable(HuntOrBeHunted huntOrBeHunted, Player player, Arena arena){
        this.player = player;
        this.arena = arena;
        this.huntOrBeHunted = huntOrBeHunted;
        this.countdownSeconds = huntOrBeHunted.getConfigManager().getSeekerWaitTime();
        player.teleport(huntOrBeHunted.getConfigManager().getSeekerSpawn());
        player.sendMessage(ChatColor.LIGHT_PURPLE + "You are a hunter!");
        player.sendTitle(ChatColor.RED + "You are a hunter!", ChatColor.GRAY + "You have to wait " + huntOrBeHunted.getConfigManager().getSeekerWaitTime() + " seconds to be released!");
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,20 * huntOrBeHunted.getConfigManager().getSeekerWaitTime(),10,false,false));
        arena.getGame().toggleRunnableCheck(player,true);
        runTaskTimer(huntOrBeHunted,2,20);
    }

    @Override
    public void run() {
        if (countdownSeconds == 0){
            player.sendTitle(ChatColor.BOLD.toString() + ChatColor.BLUE + "You got released!" , ChatColor.GRAY + "Good luck with catching the runners!");
            player.teleport(huntOrBeHunted.getConfigManager().getSeekerSpawn());
            arena.sendMessage(ChatColor.RED + "The hunter has been released!");
            arena.getGame().toggleRunnableCheck(player,false);
            cancel();
        }
        player.sendTitle( ChatColor.BLUE + "You will be released in " + ChatColor.WHITE + countdownSeconds + ChatColor.BLUE + " second" + (countdownSeconds == 1 ? "" : "s") + "."," ");
        if (countdownSeconds <= 10 || countdownSeconds % 15 == 0){
            arena.sendMessage(ChatColor.RED + "The hunter will be released in " + countdownSeconds + " second" + (countdownSeconds == 1 ? "" : "s") + ".");
        }
        countdownSeconds--;
    }
}