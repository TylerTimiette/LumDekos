package com.timeist.commands;

import com.timeist.PlayerData;
import com.timeist.Util;
import javax.annotation.Nonnull;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArrowCommand implements CommandExecutor {
    public ArrowCommand() {
    }

    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (Util.checkPlayer(sender)) {
            return true;
        } else if (Util.checkArgs(sender, args, 1, false)) {
            return true;
        } else {
            Player player;
            PlayerData playerData;
            if (!args[0].matches("^&[0-9a-fA-F]$")) {
                if (!args[0].contains("#")) {
                    Util.sendMessage(sender, "&cThat is not a valid chat format.");
                } else if (args[0].length() == 7) {
                    player = (Player)sender;
                    playerData = Util.getPlayerData(player.getUniqueId());
                    playerData.setMarker(args[0]);
                    playerData.save();
                    Util.sendMessage(player, "Arrow color changed to " + ChatColor.translateAlternateColorCodes('&', ChatColor.of(args[0]) + "&l>"));
                }

                return true;
            } else {
                player = (Player)sender;
                playerData = Util.getPlayerData(player.getUniqueId());
                playerData.setMarker(args[0]);
                playerData.save();
                Util.sendMessage(player, "Arrow color changed to " + args[0] + "&l>");
                return true;
            }
        }
    }
}
