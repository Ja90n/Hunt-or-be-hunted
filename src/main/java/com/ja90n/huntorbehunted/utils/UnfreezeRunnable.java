package com.ja90n.huntorbehunted.utils;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UnfreezeRunnable extends BukkitRunnable {

    private Player player;
    private HuntOrBeHunted huntOrBeHunted;

    public UnfreezeRunnable(Player player, HuntOrBeHunted huntOrBeHunted){
        this.player = player;
        this.huntOrBeHunted = huntOrBeHunted;
        runTaskLater(huntOrBeHunted,40);
    }

    @Override
    public void run() {
        huntOrBeHunted.getArena().getGame().getSeekerCountdown().remove(player.getUniqueId());
    }
}
