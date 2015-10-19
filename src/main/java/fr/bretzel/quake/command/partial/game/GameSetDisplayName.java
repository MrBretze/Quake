package fr.bretzel.quake.command.partial.game;

import fr.bretzel.commands.PartialCommand;
import fr.bretzel.quake.Quake;
import fr.bretzel.quake.game.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

/**
 * Created by MrBretzel on 18/10/2015.
 */
public class GameSetDisplayName extends IGame {

    public GameSetDisplayName(CommandSender sender, Command command, Permission permission, String[] args, Game game) {
        super(sender, command, permission, args, game);
    }

    @Override
    public PartialCommand execute() {
        if (getArgs().length > 3) {
            String name = getArgs()[3];
            getGame().setDisplayName(name);
            getSender().sendMessage(getI18("command.game.setdisplayname.valid").replace("%value%", name));
            Quake.gameManager.signEvent.actualiseJoinSignForGame(getGame());
            return this;
        } else {
            getSender().sendMessage(getI18("command.game.setdisplayname.usage"));
            return this;
        }
    }
}
