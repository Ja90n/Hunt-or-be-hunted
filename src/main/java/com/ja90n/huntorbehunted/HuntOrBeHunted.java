package com.ja90n.huntorbehunted;

import com.ja90n.huntorbehunted.events.*;
import com.ja90n.huntorbehunted.instances.Arena;
import com.ja90n.huntorbehunted.managers.ConfigManager;
import com.ja90n.huntorbehunted.runnables.messages.ReceiveSpigotMessageRunnable;
import com.ja90n.huntorbehunted.runnables.messages.SendVelocityMessageRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class HuntOrBeHunted extends JavaPlugin {

    private ConfigManager configManager;
    private Arena arena;
    private JedisPool pool;
    private ReceiveSpigotMessageRunnable receiveSpigotMessageRunnable;
    private SendVelocityMessageRunnable sendVelocityMessageRunnable;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        configManager = new ConfigManager(this);
        arena = new Arena(this,configManager.getName());

        JedisPoolConfig poolCfg = new JedisPoolConfig();
        poolCfg.setMaxTotal(3);

        pool = new JedisPool(poolCfg, "192.168.178.16", 6379, 500);

        try (Jedis jedis1 = pool.getResource()) {
            System.out.println("-------------------------------------------------");
            System.out.println(jedis1.get("spigot:gamestate:" + arena.getName()));
            System.out.println("spigot:gamestate:" + arena.getName());
        }


        //receiveSpigotMessageRunnable = new ReceiveSpigotMessageRunnable(this);
        sendVelocityMessageRunnable = new SendVelocityMessageRunnable(this);

        getServer().getPluginManager().registerEvents(new PlayerMove(this),this);
        getServer().getPluginManager().registerEvents(new EntityDamage(this),this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this),this);
        getServer().getPluginManager().registerEvents(new PlayerInventory(this),this);
        getServer().getPluginManager().registerEvents(new PlayerHunger(),this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player player : Bukkit.getOnlinePlayers()){
            arena.removePlayer(player);
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Arena getArena() {
        return arena;
    }

    public JedisPool getPool() {
        return pool;
    }

    public SendVelocityMessageRunnable getSendVelocityMessageRunnable() {
        return sendVelocityMessageRunnable;
    }
}
