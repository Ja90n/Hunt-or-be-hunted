package com.ja90n.huntorbehunted.events;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import com.ja90n.huntorbehunted.utils.SetScoreboard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private HuntOrBeHunted huntOrBeHunted;

    public PlayerJoin(HuntOrBeHunted huntOrBeHunted){
        this.huntOrBeHunted = huntOrBeHunted;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.getPlayer().teleport(huntOrBeHunted.getConfigManager().getSpawn());
        huntOrBeHunted.getArena().addPlayer(event.getPlayer());
        new SetScoreboard(event.getPlayer(), huntOrBeHunted);
    }
}
