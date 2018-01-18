package fr.bretzel.quake.command.partial.player.killstreak;

import fr.bretzel.quake.util.PartialCommand;
import fr.bretzel.quake.PlayerInfo;
import fr.bretzel.quake.command.partial.player.ICommandPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class CommandPlayerRemoveKillStreak extends ICommandPlayer {

    private int i;

    public CommandPlayerRemoveKillStreak(CommandSender sender, Command command, Permission permission, String[] args, Player player, int i) {
        super(sender, command, permission, args, player);
        this.i = i;
    }

    @Override
    public PartialCommand execute() {
        PlayerInfo info = PlayerInfo.getPlayerInfo(getPlayer());
        info.removeKillStreak(i);
        getPlayer().sendMessage(getI18n("command.players.removekillsteak.valid").replace("%killstreak%", "" + i));
        getPlayer().setScoreboard(info.getPlayerScoreboard());
        return this;
    }
}