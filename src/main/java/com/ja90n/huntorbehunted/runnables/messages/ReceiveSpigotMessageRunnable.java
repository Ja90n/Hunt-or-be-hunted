package com.ja90n.huntorbehunted.runnables.messages;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import com.ja90n.huntorbehunted.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

public class ReceiveSpigotMessageRunnable extends BukkitRunnable {

    private HuntOrBeHunted huntOrBeHunted;
    private JedisPool pool;

    public ReceiveSpigotMessageRunnable(HuntOrBeHunted huntOrBeHunted){
        this.huntOrBeHunted = huntOrBeHunted;
        pool = huntOrBeHunted.getPool();
        runTaskTimer(huntOrBeHunted,0,1);
    }

    @Override
    public void run() {
        try (Jedis jedis = pool.getResource()) {
            if (!jedis.get("spigot:removeplayer:" + huntOrBeHunted.getArena().getName()).equals("")) {
                Player player = Bukkit.getPlayer(UUID.fromString(jedis.get("spigot:removeplayer:" + huntOrBeHunted.getArena().getName())));
                jedis.set("spigot:removeplayer:" + huntOrBeHunted.getArena().getName(),"");
                huntOrBeHunted.getArena().removePlayer(player);
                if (huntOrBeHunted.getArena().getGameState() == GameState.COUNTDOWN && huntOrBeHunted.getArena().getPlayers().size() < huntOrBeHunted.getConfigManager().getRequiredPlayers()){
                    huntOrBeHunted.getArena().sendMessage(ChatColor.RED + "There are not enough players, countdown stopped!");
                    huntOrBeHunted.getArena().stopGame();
                    return;
                }

                if (huntOrBeHunted.getArena().getGameState() == GameState.LIVE && huntOrBeHunted.getArena().getPlayers().size() < huntOrBeHunted.getConfigManager().getRequiredPlayers()){
                    huntOrBeHunted.getArena().sendMessage(ChatColor.RED + "The game has ended as to many players have left.");
                    huntOrBeHunted.getArena().stopGame();
                }
            }
        }
    }
}
