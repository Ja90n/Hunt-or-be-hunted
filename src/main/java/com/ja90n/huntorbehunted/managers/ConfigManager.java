package com.ja90n.huntorbehunted.managers;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config;
    private HuntOrBeHunted catchMefYouCan;

    public ConfigManager(HuntOrBeHunted huntOrBeHunted){
        config = huntOrBeHunted.getConfig();
    }

    public Location getSeekerSpawn(){
        return new Location(
                Bukkit.getWorld(config.getString("arena.seeker-spawn.world")),
                config.getDouble("arena.seeker-spawn.x"),
                config.getDouble("arena.seeker-spawn.y"),
                config.getDouble("arena.seeker-spawn.z"),
                (float) config.getDouble("arena.seeker-spawn.yaw"),
                (float) config.getDouble("arena.seeker-spawn.pitch"));
    }

    public String getName(){
        return config.getString("arena.name");
    }

    public Location getSpawn(){
        return new Location(
                Bukkit.getWorld(config.getString("spawn.world")),
                config.getDouble("spawn.x"),
                config.getDouble("spawn.y"),
                config.getDouble("spawn.z"),
                (float) config.getDouble("spawn.yaw"),
                (float) config.getDouble("spawn.pitch"));
    }

    public int getRequiredPlayers(){
        return config.getInt("required-players");
    }
    public int getMatchTime(){return config.getInt("match-time");}
    public int getSeekerWaitTime(){
        return config.getInt("seeker-wait-time");
    }
    public int getCountdownTime(){
        return config.getInt("countdown-time");
    }
}
