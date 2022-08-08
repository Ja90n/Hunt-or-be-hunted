package com.ja90n.huntorbehunted.events;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import com.ja90n.huntorbehunted.GameState;
import com.ja90n.huntorbehunted.instances.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamage implements Listener {

    private HuntOrBeHunted huntOrBeHunted;

    public EntityDamage(HuntOrBeHunted huntOrBeHunted){
        this.huntOrBeHunted = huntOrBeHunted;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (huntOrBeHunted.getArena().getGameState().equals(GameState.LIVE)){
                Arena arena = huntOrBeHunted.getArena();
                if (arena.getGame().getHiders().contains(player.getUniqueId())){
                    if (!arena.getGame().getHiders().contains(event.getDamager().getUniqueId())){
                        arena.getGame().changeTeam(player);
                    }
                } else if (arena.getGame().getSeekers().contains(player.getUniqueId())){
                    event.setCancelled(true);
                } else {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }
    }
}
