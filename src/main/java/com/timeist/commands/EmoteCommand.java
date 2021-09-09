package com.timeist.commands;

import com.timeist.PlayerData;
import com.timeist.Util;
import javax.annotation.Nonnull;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EmoteCommand implements CommandExecutor {
    //You're not allowed to have fun.
    private final char[] openingBrackets = new char[]{'[', '(', '{', '<'};
    private final char[] closingBrackets = new char[]{']', ')', '}', '>'};


    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        //Console can't run this.
        if (Util.checkPlayer(sender)) {
            return true;
        }
        //Clears emote if there is no prefix or suffix specified.
        else if (Util.checkArgs(sender, args, 2, false)) {
            Player player = (Player)sender;
            PlayerData playerData = Util.getPlayerData(player.getUniqueId());
            sender.sendMessage("\nIf you are looking to clear your /emote, it has been automatically cleared.");
            playerData.setPrefix("");
            playerData.setSuffix("");
            return true;
            //If the first argument is neither prefix or suffix, the command informs on how to properly use it.
        } else if (!args[0].equalsIgnoreCase("prefix") && !args[0].equalsIgnoreCase("suffix")) {

            Util.sendMessage(sender, "&cThe first argument has to be either prefix or suffix.");
            return true;
        } else {

            //Fun.
            boolean prefix = args[0].equalsIgnoreCase("prefix");
            String display = args[1];
            if (display.matches(".*&[LlMmNnOo].*")) {
                Util.sendMessage(sender, "&cInvalid formatting character detected. Please use only color characters.");
                return true;
            } else if (prefix && !StringUtils.containsAny(display, this.openingBrackets)) {
                Util.sendMessage(sender, "&cYou are modifying your prefix. Please use a valid bracket. Valid are: " + new String(this.openingBrackets));
                return true;

            } else if (!prefix && !StringUtils.containsAny(display, this.closingBrackets)) {
                Util.sendMessage(sender, "&cYou are modifying your suffix. Please use a valid bracket. Valid are: " + new String(this.closingBrackets));
                return true;
            } else {
                display = ChatColor.translateAlternateColorCodes('&', display);
                if (ChatColor.stripColor(display).length() > 2) {
                    Util.sendMessage(sender, "&cYou entered more than two characters besides chat formatting characters. Please don't. You rascal. Data storage is not free.");
                    return true;
                } else {
                    Player player = (Player)sender;
                    PlayerData playerData = Util.getPlayerData(player.getUniqueId());
                    if (prefix) {
                        playerData.setPrefix(args[1]);
                    } else {
                        playerData.setSuffix(args[1]);
                    }

                    playerData.save();
                    Util.sendMessage(player, "&4" + args[0] + " &chas been set to &r" + display);
                    return true;
                }
            }
        }
    }
}
