package com.ja90n.huntorbehunted.instances;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import com.ja90n.huntorbehunted.GameState;
import com.ja90n.huntorbehunted.runnables.StartCountdownRunnable;
import com.ja90n.huntorbehunted.utils.SetScoreboard;
import com.ja90n.huntorbehunted.utils.WinCheckUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private HuntOrBeHunted huntOrBeHunted;
    private String name;
    private List<UUID> players;
    private Game game;
    private GameState gameState;
    private StartCountdownRunnable startCountdownRunnable;

    public Arena(HuntOrBeHunted huntOrBeHunted, String name) {
        this.huntOrBeHunted = huntOrBeHunted;
        this.name = name;
        this.game = new Game(huntOrBeHunted,this);
        this.gameState = GameState.RECRUITING;
        this.startCountdownRunnable = new StartCountdownRunnable(huntOrBeHunted,this);

        players = new ArrayList<>();
    }

    public void startGame(){ game.start();}

    public void stopGame(){
        if (gameState.equals(GameState.LIVE)){
            Location location = huntOrBeHunted.getConfigManager().getSpawn();
            ArrayList<Player> players1 = new ArrayList<>();
            for (UUID uuid : players){
                Player player = Bukkit.getPlayer(uuid);
                player.teleport(location);
                player.setGameMode(GameMode.ADVENTURE);
                player.getInventory().clear();
                player.getEnderChest().clear();
                player.setInvisible(false);
                player.setInvulnerable(false);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.removePotionEffect(PotionEffectType.SPEED);
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                player.removePotionEffect(PotionEffectType.WEAKNESS);
                players1.add(player);
            }
            players.clear();
            for (Player player : players1){
                huntOrBeHunted.getSendVelocityMessageRunnable().getPlayersToRemove().add(player.getUniqueId());
                new SetScoreboard(player, huntOrBeHunted);
            }
            players1.clear();
            game.ResetRunnerWinCountdownRunnable();
        }
        sendTitle(" ", " ");
        gameState = GameState.RECRUITING;
        try {
            startCountdownRunnable.cancel();
            startCountdownRunnable = new StartCountdownRunnable(huntOrBeHunted,this);
        } catch (IllegalStateException exception){}
        game = new Game(huntOrBeHunted,this);
    }

    // Player management
    public void addPlayer(Player player){
        players.add(player.getUniqueId());

        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        player.teleport(huntOrBeHunted.getConfigManager().getSpawn());

        if (gameState.equals(GameState.RECRUITING) && players.size() >= huntOrBeHunted.getConfigManager().getRequiredPlayers()){
            startCountdownRunnable.start();
        }
    }

    public void removePlayer(Player player){
        try {
            players.remove(player.getUniqueId());
            player.getInventory().clear();
            player.getEnderChest().clear();
            player.setInvisible(false);
            player.setInvulnerable(false);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.removePotionEffect(PotionEffectType.SPEED);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            player.setGameMode(GameMode.ADVENTURE);
            player.teleport(huntOrBeHunted.getConfigManager().getSpawn());
            player.sendTitle(" ", " ");

            new SetScoreboard(player, huntOrBeHunted);
        } catch (NullPointerException e){}

        game.getTeams().remove(player.getUniqueId());

        if (gameState == GameState.LIVE){
            new WinCheckUtil(this);
        }

        if (gameState == GameState.COUNTDOWN && players.size() < huntOrBeHunted.getConfigManager().getRequiredPlayers()){
            sendMessage(ChatColor.RED + "There are not enough players, countdown stopped!");
            stopGame();
            return;
        }

        if (gameState == GameState.LIVE && players.size() < huntOrBeHunted.getConfigManager().getRequiredPlayers()){
            sendMessage(ChatColor.RED + "The game has ended as to many players have left.");
            stopGame();
        }
    }

    public void sendMessage(String message){
        for (UUID uuid : getPlayers()){
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendTitle(String title1, String title2){
        for (UUID uuid : getPlayers()){
            Bukkit.getPlayer(uuid).sendTitle(title1,title2);
        }
    }


    // Getters
    public List<UUID> getPlayers() {
        return players;
    }

    public Game getGame() {
        return game;
    }

    public GameState getGameState() {
        return gameState;
    }

    public String getName() {return name;}

    public World getWorld(){ return huntOrBeHunted.getConfigManager().getSeekerSpawn().getWorld();}

    // Setters
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
