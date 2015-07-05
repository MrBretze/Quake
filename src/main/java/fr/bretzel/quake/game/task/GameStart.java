package fr.bretzel.quake.game.task;



import fr.bretzel.quake.*;
import fr.bretzel.quake.game.Game;
import fr.bretzel.quake.game.State;
import fr.bretzel.quake.game.event.GameStartEvent;
import fr.bretzel.quake.game.scoreboard.ScoreboardAPI;
import fr.bretzel.quake.inventory.BasicGun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.Random;
import java.util.UUID;

/**
 * Created by MrBretzel on 22/06/2015.
 */

public class GameStart extends GameTask {

    int minSecQuake = getGame().getSecLaunch();
    private Random random = new Random();
    private ScoreboardAPI scoreboardAPI = getGame().getScoreboardManager();

    public GameStart(JavaPlugin javaPlugin, long l, long l1, Game game) {
        super(javaPlugin, l, l1, game);
    }

    @Override
    public void run() {
        if (minSecQuake <= 5 || minSecQuake == 10) {
            getGame().broadcastMessage(ChatColor.BLUE + "The game start in: " + Util.getChatColorByInt(minSecQuake) + String.valueOf(minSecQuake));
            for(UUID id : getGame().getPlayerList()) {
                Player p = Bukkit.getPlayer(id);
                if(p!= null || p.isOnline()) {
                    p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 2.0F);
                }
            }
        }

        if (minSecQuake > 0) {
            minSecQuake--;
        }

        if (minSecQuake <= 0 && getGame().getState() == State.WAITING) {
            GameStartEvent event = new GameStartEvent(getGame());
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancelled()) {
                getGame().stop();
                cancel();
                return;
            }
            getGame().setState(State.STARTED);
            Quake.gameManager.signEvent.actualiseJoinSignForGame(getGame());
            scoreboardAPI.getObjective().unregister();
            scoreboardAPI.setObjective(scoreboardAPI.getScoreboard().registerNewObjective("quake", "dummy"));
            scoreboardAPI.getObjective().setDisplaySlot(DisplaySlot.SIDEBAR);
            for(UUID id : getGame().getPlayerList()) {
                Player p = Bukkit.getPlayer(id);
                if(p!= null || p.isOnline()) {
                    getGame().respawn(p);
                    PlayerInfo info = Quake.getPlayerInfo(p);
                    info.give(new BasicGun(info));
                    Chrono chrono = new Chrono();
                    chrono.start();
                    Quake.gameManager.getGameChrono().put(getGame(), chrono);
                    scoreboardAPI.getObjective().getScore(p.getName()).setScore(1);
                    scoreboardAPI.getObjective().getScore(p.getName()).setScore(0);
                }
            }
            cancel();
        }
    }
}
