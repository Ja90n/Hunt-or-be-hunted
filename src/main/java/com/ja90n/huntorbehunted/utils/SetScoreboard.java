package com.ja90n.huntorbehunted.utils;

import com.ja90n.huntorbehunted.GameState;
import com.ja90n.huntorbehunted.HuntOrBeHunted;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class SetScoreboard {
    public SetScoreboard(Player player, HuntOrBeHunted huntOrBeHunted){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        if (huntOrBeHunted.getArena().getGameState().equals(GameState.LIVE)){
            Objective objective = scoreboard.registerNewObjective("Scoreboard","dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Hunt or be hunted");

            Score website = objective.getScore(ChatColor.LIGHT_PURPLE + "play.ja90n.software");
            website.setScore(1);

            Score space2 = objective.getScore("  ");
            space2.setScore(2);

            Team timeUntilWin = scoreboard.registerNewTeam("TimeToWin");
            timeUntilWin.addEntry(ChatColor.YELLOW.toString());
            timeUntilWin.setPrefix(ChatColor.BLUE + "Time until the runners win: ");
            timeUntilWin.setSuffix(ChatColor.WHITE + "0:00");
            objective.getScore(ChatColor.YELLOW.toString()).setScore(3);

            Score space3 = objective.getScore("    ");
            space3.setScore(4);

            Team hidersRemaining = scoreboard.registerNewTeam("HidersRemaining");
            hidersRemaining.addEntry(ChatColor.DARK_RED.toString());
            hidersRemaining.setPrefix(ChatColor.GREEN + "Hiders remaining: ");
            hidersRemaining.setSuffix(ChatColor.WHITE.toString() + huntOrBeHunted.getArena().getGame().getHiders().size());
            objective.getScore(ChatColor.DARK_RED.toString()).setScore(5);

            Score space4 = objective.getScore("     ");
            space4.setScore(6);
        }
        player.setScoreboard(scoreboard);
    }
}
