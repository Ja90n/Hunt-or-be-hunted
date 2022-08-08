package com.ja90n.huntorbehunted.instances;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import com.ja90n.huntorbehunted.GameState;
import com.ja90n.huntorbehunted.runnables.RunnerWinCountdownRunnable;
import com.ja90n.huntorbehunted.runnables.SeekerStartCountdownRunnable;
import com.ja90n.huntorbehunted.utils.SetScoreboard;
import com.ja90n.huntorbehunted.utils.SetupPlayerUtil;
import com.ja90n.huntorbehunted.utils.UnfreezeRunnable;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private HuntOrBeHunted huntOrBeHunted;
    private Arena arena;
    private HashMap<UUID,String> teams;
    private List<UUID> seekerCountdown;
    private RunnerWinCountdownRunnable runnerWinCountdownRunnable;
    private List<Location> spawnLocations;
    private List<Location> hiderSpawnLocations;

    public Game(HuntOrBeHunted huntOrBeHunted, Arena arena){
        this.huntOrBeHunted = huntOrBeHunted;
        this.arena = arena;
        this.runnerWinCountdownRunnable = new RunnerWinCountdownRunnable(huntOrBeHunted,arena);
        seekerCountdown = new ArrayList<>();
        teams = new HashMap<>();
        spawnLocations = new ArrayList<>();
        FileConfiguration configuration = huntOrBeHunted.getConfig();
        hiderSpawnLocations = spawnLocations;

        for (String str : huntOrBeHunted.getConfig().getConfigurationSection("arena.hider-spawns.").getKeys(false)){
            spawnLocations.add(new Location(
                    Bukkit.getWorld(configuration.getString("arena.hider-spawns." + str + ".world")),
                    configuration.getDouble("arena.hider-spawns." + str + ".x"),
                    configuration.getDouble("arena.hider-spawns." + str + ".y"),
                    configuration.getDouble("arena.hider-spawns." + str + ".z"),
                    (float) configuration.getDouble("arena.hider-spawns." + str + ".yaw"),
                    (float) configuration.getDouble("arena.hider-spawns." + str + ".pitch")
            ));
        }
    }

    public void start() {
        int random = ThreadLocalRandom.current().nextInt(0,arena.getPlayers().size());
        for (UUID uuid : arena.getPlayers()){
            Player player = Bukkit.getPlayer(uuid);
            player.removePotionEffect(PotionEffectType.SPEED);
            if (arena.getPlayers().indexOf(uuid) == random){
                teams.put(uuid,"seeker");
                new SetupPlayerUtil(player,"seeker");
                new SeekerStartCountdownRunnable(huntOrBeHunted,player,arena);
            } else {
                teams.put(uuid,"hider");
                new SetupPlayerUtil(player,"hider");
                int random2 = ThreadLocalRandom.current().nextInt(0,hiderSpawnLocations.size());
                player.teleport(hiderSpawnLocations.get(random2));
                hiderSpawnLocations.remove(random2);
                player.sendMessage(ChatColor.BLUE + "You are a runner!");
                player.sendMessage(ChatColor.BLUE + "Game has been started! You have " + huntOrBeHunted.getConfigManager().getSeekerWaitTime()  + " seconds to run away!");
                player.sendTitle(ChatColor.BLUE + "Game has been started!", ChatColor.GRAY + "You have " + huntOrBeHunted.getConfigManager().getSeekerWaitTime()  + " seconds to run away!");
            }
            player.setHealth(20);
            player.setGameMode(GameMode.ADVENTURE);
        }
        arena.getWorld().setTime(0);
        arena.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        arena.getWorld().setGameRule(GameRule.DO_MOB_SPAWNING, false);
        runnerWinCountdownRunnable.start();
        hiderSpawnLocations = spawnLocations;
        arena.setGameState(GameState.LIVE);

        for (UUID uuid : arena.getPlayers()){
            new SetScoreboard(Bukkit.getPlayer(uuid), huntOrBeHunted);
        }
    }

    public void toggleRunnableCheck(Player player, boolean bool){
        if (bool){
            seekerCountdown.add(player.getUniqueId());
        } else {
            seekerCountdown.remove(player.getUniqueId());
        }
    }

    public void changeTeam(Player player){
        player.sendMessage(ChatColor.RED + "You have been tagged!");
        player.sendMessage(ChatColor.GREEN + "You are now also a hunter!");
        player.sendTitle(ChatColor.RED + "You have been tagged!",ChatColor.GREEN + "You are now also infected!");
        arena.getGame().getSeekerCountdown().add(player.getUniqueId());
        teams.put(player.getUniqueId(),"seeker");
        if (getHiders().size() <= 0){
            arena.sendTitle(ChatColor.GREEN + "The hunters have won the game!",ChatColor.WHITE + "Thank you for playing!");
            arena.sendMessage(ChatColor.GREEN + "The hunters have won the game!");
            arena.stopGame();
            return;
        }
        new UnfreezeRunnable(player,huntOrBeHunted);
        new SetupPlayerUtil(player,"seeker");
    }

    public HashMap<UUID, String> getTeams() {
        return teams;
    }

    public List<UUID> getSeekerCountdown() {
        return seekerCountdown;
    }

    public List<UUID> getHiders() {
        List<UUID> hiders = new ArrayList<>();
        for (UUID uuid : teams.keySet()){
            if (teams.get(uuid).equals("hider")){
                hiders.add(uuid);
            }
        }
        return hiders;
    }

    public List<UUID> getSeekers() {
        List<UUID> hiders = new ArrayList<>();
        for (UUID uuid : teams.keySet()){
            if (teams.get(uuid).equals("seeker")){
                hiders.add(uuid);
            }
        }
        return hiders;
    }

    public List<UUID> getSpectators() {
        List<UUID> hiders = new ArrayList<>();
        for (UUID uuid : teams.keySet()){
            if (teams.get(uuid).equals("spectator")){
                hiders.add(uuid);
            }
        }
        return hiders;
    }


    public RunnerWinCountdownRunnable getRunnerWinCountdownRunnable() {
        return runnerWinCountdownRunnable;
    }

    public void ResetRunnerWinCountdownRunnable(){
        runnerWinCountdownRunnable.cancel();
        runnerWinCountdownRunnable = new RunnerWinCountdownRunnable(huntOrBeHunted,arena);
    }
}