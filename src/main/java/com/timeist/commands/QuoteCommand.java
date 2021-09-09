package com.timeist.commands;

import com.timeist.PlayerData;
import com.timeist.Util;
import javax.annotation.Nonnull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuoteCommand implements CommandExecutor {

    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (Util.checkPlayer(sender)) {
            return true;
        } else if (Util.checkArgs(sender, args, 1, true)) {
            return true;
        } else {
            String quote = String.join(" ", args);
            if (quote.length() > 180) {
                Util.sendMessage(sender, "&cYour quote is too long. Are you trying to compensate for something?");
                return true;
            } else {
                Player player = (Player)sender;
                PlayerData playerData = Util.getPlayerData(player.getUniqueId());
                playerData.setQuote(quote);
                playerData.save();
                Util.sendMessage(player, "&dYour quote has been changed successfully.");
                return true;
            }
        }
    }
}