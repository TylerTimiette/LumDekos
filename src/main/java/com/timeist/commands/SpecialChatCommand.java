package com.timeist.commands;

import com.timeist.PlayerData;
import com.timeist.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class SpecialChatCommand implements CommandExecutor {

    //Pretty self-explanatory.
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!Util.checkPlayer(sender)) {
            if (args.length == 1) {
                if(args[0].equalsIgnoreCase("rainbow") || args[0].equalsIgnoreCase("gay") || args[0].equalsIgnoreCase("lgbt") || args[0].equalsIgnoreCase("pride")) {
                    PlayerData playerData;
                    Player player = (Player) sender;
                    playerData = Util.getPlayerData(player.getUniqueId());
                    playerData.setColor("special:rainbow");
                    playerData.save();
                }




            } else
                sender.sendMessage("You have used this command incorrectly. Please specify a special keyword in order to use a special chat color.");


        }
        return true;
    }
}
