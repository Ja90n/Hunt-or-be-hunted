package com.ja90n.huntorbehunted.events;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import com.ja90n.huntorbehunted.instances.Arena;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private HuntOrBeHunted huntOrBeHunted;

    public PlayerQuit(HuntOrBeHunted huntOrBeHunted){
        this.huntOrBeHunted = huntOrBeHunted;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Arena arena = huntOrBeHunted.getArena();
        arena.removePlayer(event.getPlayer());
        event.setQuitMessage(event.getPlayer().getDisplayName() + ChatColor.RED + " has left the game");
    }
}
