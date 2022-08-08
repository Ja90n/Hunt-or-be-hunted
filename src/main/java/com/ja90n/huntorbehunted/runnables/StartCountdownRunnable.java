package com.ja90n.huntorbehunted.runnables;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import com.ja90n.huntorbehunted.GameState;
import com.ja90n.huntorbehunted.instances.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class StartCountdownRunnable extends BukkitRunnable {

    private HuntOrBeHunted huntOrBeHunted;
    private int countdownTime;
    private Arena arena;

    public StartCountdownRunnable(HuntOrBeHunted huntOrBeHunted, Arena arena){
        this.huntOrBeHunted = huntOrBeHunted;
        this.arena = arena;
        this.countdownTime = huntOrBeHunted.getConfigManager().getCountdownTime();

    }

    public void start(){
        arena.setGameState(GameState.COUNTDOWN);
        runTaskTimer(huntOrBeHunted,0,20);
    }

    @Override
    public void run() {
        if (countdownTime == 0){
            arena.startGame();
            cancel();
        }
        if (countdownTime <= 10 || countdownTime % 15 == 0){
            arena.sendMessage(ChatColor.BLUE + "Game will start in " + ChatColor.WHITE + countdownTime + ChatColor.BLUE + " second" + (countdownTime == 1 ? "" : "s") + ".");
        }
        arena.sendTitle(ChatColor.BLUE + "Game will start in " + ChatColor.WHITE + countdownTime + ChatColor.BLUE + " second" + (countdownTime == 1 ? "" : "s") + "."," ");
        countdownTime--;
    }
}