package com.ja90n.huntorbehunted.runnables.messages;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.UUID;

public class SendVelocityMessageRunnable extends BukkitRunnable {

    private HuntOrBeHunted huntOrBeHunted;
    private ArrayList<UUID> playersToRemove;

    public SendVelocityMessageRunnable(HuntOrBeHunted huntOrBeHunted){
        this.huntOrBeHunted = huntOrBeHunted;
        playersToRemove = new ArrayList<>();
        runTaskTimer(huntOrBeHunted,0,1);
    }

    @Override
    public void run() {
        try (Jedis jedis1 = huntOrBeHunted.getPool().getResource()) {
            jedis1.set("spigot:playercount:" + huntOrBeHunted.getArena().getName()
                    , String.valueOf(huntOrBeHunted.getServer().getOnlinePlayers().size()));
            jedis1.set("spigot:gamestate:" + huntOrBeHunted.getArena().getName()
                    , String.valueOf(huntOrBeHunted.getArena().getGameState().toString()));

            if (jedis1.get("velocity:removeplayer:" + huntOrBeHunted.getArena().getName()).equals("")) {
                if (!playersToRemove.isEmpty()) {
                    jedis1.set("velocity:removeplayer:" + huntOrBeHunted.getArena().getName(), playersToRemove.get(0).toString());
                    playersToRemove.remove(0);
                }
            }
        }
    }

    public ArrayList<UUID> getPlayersToRemove() {
        return playersToRemove;
    }
}
