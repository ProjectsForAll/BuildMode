package host.plas.buildmode.commands;

import host.plas.bou.commands.CommandContext;
import host.plas.bou.commands.SimplifiedCommand;
import host.plas.buildmode.BuildMode;
import host.plas.buildmode.config.messages.MessageContainer;
import host.plas.buildmode.config.messages.MessageString;
import host.plas.buildmode.data.PlayerData;
import host.plas.buildmode.data.PlayerManager;
import org.bukkit.entity.Player;

import java.util.Optional;

public class BuildCMD extends SimplifiedCommand {
    public static final String IDENTIFIER = "build";

    public BuildCMD() {
        super(IDENTIFIER, BuildMode.getInstance());
    }

    @Override
    public boolean command(CommandContext ctx) {
        if (ctx.isConsole()) {
            ctx.sendMessage("&cThis command can only be executed by players.");
            return true;
        }

        Optional<Player> player = ctx.getPlayer();
        if (player.isEmpty()) {
            ctx.sendMessage("&cFailed to get player.");
            return true;
        }
        Player p = player.get();
        PlayerData data = PlayerManager.getOrCreatePlayer(p);
        boolean current = data.isToggled();
        data.setToggled(! current);
        boolean newStatus = data.isToggled();

        MessageContainer container = BuildMode.getMainConfig().getMessageContainer();
        MessageString toggleMessage = container.getToggleString();
        if (! toggleMessage.isEmpty()) {
            MessageString enabled = container.getStatusEnabledString();
            MessageString disabled = container.getStatusDisabledString();

            String status = newStatus ? enabled.getString() : disabled.getString();

            String message = toggleMessage.getString().replace("%status%", status);
            ctx.sendMessage(message);
        }

        return true;
    }
}
