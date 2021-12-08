package com.timeist.commands;

import com.timeist.PlayerData;
import com.timeist.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class SpecialChatCommand implements CommandExecutor {

    //Pretty self-explanatory.
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!Util.checkPlayer(sender)) {
            PlayerData playerData;
            Player player = (Player) sender;
            playerData = Util.getPlayerData(player.getUniqueId());
            if (args.length == 1) {
                if(args[0].equalsIgnoreCase("rainbow") || args[0].equalsIgnoreCase("gay") || args[0].equalsIgnoreCase("lgbt") || args[0].equalsIgnoreCase("pride")) {

                    playerData.setColor("special_rainbow");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Util.rainbowifyText("Gotcha. Text set to rainbowify.")));

                }

                switch(args[0]) {
                    case "bi":
                    case "bisexual":
                    case "lilbitofeverything":
                        playerData.setColor("special_bi");
                        player.sendMessage(Util.translateHexColorCodes("#", Util.segmentColoredMessage("Gotcha. Text set to bisexual pride.", "bi")));
                        break;
                    case "lesbian":
                    case "girllovinggirl":
                    case "glg":
                    case "lesbo":
                    case "youbetterbe!":
                        playerData.setColor("special_lesbian");
                        player.sendMessage(Util.translateHexColorCodes("#", Util.segmentColoredMessage("Gotcha. Text set to lesbian pride.", "lesbian")));
                        break;
                    case "nb":
                    case "nonbinary":
                    case "nobinary":
                    case "anticomputer":
                        playerData.setColor("special_nb");
                        player.sendMessage(Util.translateHexColorCodes("#", Util.segmentColoredMessage("Gotcha. Text set to non-binary pride.", "nb")));
                        break;

                    case "trans":
                    case "afab":
                    case "amab":
                    case "transmasc":
                    case "transfem":
                    case "transgender":
                    case "transgener":
                    case "#transrights":
                    case "transrights":
                        playerData.setColor("special_trans");
                        player.sendMessage(Util.translateHexColorCodes("#", Util.segmentColoredMessage("Gotcha. Text set to transgender pride. Trans rights!", "trans")));
                        break;
                    case "ace":
                    case "asexual":
                    case "spades":
                    case "diamonds":
                    case "clubs":
                    case "hearts":
                        playerData.setColor("special_ace");
                        player.sendMessage(Util.translateHexColorCodes("#", Util.segmentColoredMessage("Gotcha. Text set to asexual pride.", "ace")));
                        break;
                    case "pan":
                    case "pansexual":
                    case "pan-sexual":
                    case "pots":
                        playerData.setColor("special_pan");
                        player.sendMessage(Util.translateHexColorCodes("#", Util.segmentColoredMessage("Gotcha. Text set to pansexual pride.", "pan")));
                        break;
                    case "lum":
                    case "tyler":
                    case "awesome":
                        if (args[0].length() == 7 && Util.validateHexCode(args[0])) {
                            playerData.setColor("#F6F89F");
                            sender.sendMessage(Util.translateHexColorCodes("#", "#F6F89FI got paid to make these new features. Your color code has been changed."));
                        }
                        break;
                }
                    playerData.save();

            } else
                sender.sendMessage("You have used this command incorrectly. Please specify a special keyword in order to use a special chat color.");


        }
        return true;
    }
}
