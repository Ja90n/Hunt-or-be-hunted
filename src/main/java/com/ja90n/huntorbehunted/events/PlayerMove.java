package com.ja90n.huntorbehunted.events;

import com.ja90n.huntorbehunted.GameState;
import com.ja90n.huntorbehunted.HuntOrBeHunted;
import com.ja90n.huntorbehunted.instances.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        } else if (huntOrBeHunted.getArena().getGameState().equals(GameState.LIVE)){
            for (Cuboid cuboid : huntOrBeHunted.getArena().getGame().getPowerUps().keySet()){
                if (cuboid.contains(event.getPlayer().getLocation())){
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*5,34));
                    huntOrBeHunted.getArena().getGame().getPowerUps().get(cuboid).remove();
                    huntOrBeHunted.getArena().getGame().getPowerUps().remove(cuboid);
                    break;
                }
            }
        }
    }
}
