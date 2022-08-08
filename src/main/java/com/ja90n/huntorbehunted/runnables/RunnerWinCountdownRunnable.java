package com.ja90n.huntorbehunted.runnables;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import com.ja90n.huntorbehunted.instances.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class RunnerWinCountdownRunnable extends BukkitRunnable {

    private HuntOrBeHunted huntOrBeHunted;
    private Arena arena;
    private int countdownTime;

    public RunnerWinCountdownRunnable(HuntOrBeHunted huntOrBeHunted, Arena arena){
        this.huntOrBeHunted = huntOrBeHunted;
        this.arena = arena;
    }

    public void start(){
        countdownTime = huntOrBeHunted.getConfigManager().getMatchTime();
        runTaskTimer(huntOrBeHunted,0,20);
    }

    @Override
    public void run() {
        for (UUID uuid : arena.getPlayers()){
            Player player = Bukkit.getPlayer(uuid);
            if (countdownTime >= 60){
                int minutes = countdownTime/60;
                int seconds = countdownTime - (60*minutes);
                if (seconds <= 9){
                    player.getScoreboard().getTeam("TimeToWin").setSuffix(ChatColor.WHITE.toString() + minutes + ":0" + seconds);
                } else {
                    player.getScoreboard().getTeam("TimeToWin").setSuffix(ChatColor.WHITE.toString() + minutes + ":" + seconds);
                }
            } else {
                if (countdownTime <= 9){
                    player.getScoreboard().getTeam("TimeToWin").setSuffix(ChatColor.WHITE.toString() + "00:0" + countdownTime);
                } else {
                    player.getScoreboard().getTeam("TimeToWin").setSuffix(ChatColor.WHITE.toString() + "00:" + countdownTime);
                }
            }
        }
        if (countdownTime == 0){
            arena.sendMessage(ChatColor.BLUE + "Runners have won the game!");
            arena.sendTitle(ChatColor.BLUE + "Runners have won the game!",ChatColor.GRAY + "Thank you for playing!");
            arena.stopGame();
            cancel();
        }
        if (countdownTime % 60 == 0){
            arena.sendMessage(ChatColor.BLUE + "Runners will win in " + countdownTime/60 + " minute" + (countdownTime/60 == 1 ? "" : "s") + ".");
        }
        if (countdownTime == 30){
            arena.sendMessage(ChatColor.BLUE + "Runners will win in 30 seconds");
        }
        if (countdownTime <= 10){
            arena.sendMessage(ChatColor.BLUE + "Runners will win in " + countdownTime + " second" + (countdownTime == 1 ? "" : "s") + ".");
        }
        countdownTime--;
    }
}
