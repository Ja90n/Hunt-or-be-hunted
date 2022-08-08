package com.ja90n.huntorbehunted.events;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    private HuntOrBeHunted huntOrBeHunted;

    public PlayerMove(HuntOrBeHunted huntOrBeHunted){
        this.huntOrBeHunted = huntOrBeHunted;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if (huntOrBeHunted.getArena().getGame().getSeekerCountdown().contains(event.getPlayer().getUniqueId())){
            event.setCancelled(true);
        } else if (event.getPlayer().getLocation().add(0,1,0).getBlock().getType().equals(Material.WATER)){
            huntOrBeHunted.getArena().getGame().changeTeam(event.getPlayer());
        }
    }
}
