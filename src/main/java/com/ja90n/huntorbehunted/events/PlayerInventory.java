package com.ja90n.huntorbehunted.events;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class PlayerInventory implements Listener {

    private HuntOrBeHunted huntOrBeHunted;

    public PlayerInventory(HuntOrBeHunted huntOrBeHunted){
        this.huntOrBeHunted = huntOrBeHunted;
    }

    @EventHandler
    public void onClick (InventoryClickEvent event){
        if (event.getWhoClicked() instanceof Player){
            if (event.getSlotType() == InventoryType.SlotType.ARMOR){
                event.setCancelled(true);
            }
        }
    }
}
