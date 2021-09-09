package com.timeist.commands;


import com.timeist.TimeistsDecos;
import com.timeist.Util;
import javax.annotation.Nonnull;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrefixCommand implements CommandExecutor {

    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (Util.checkPlayer(sender)) {
            return true;
        } else if (Util.checkArgs(sender, args, 1, false)) {
            return true;
        } else if (args[0].matches(".*&[LlMmNnOo].*") && !sender.hasPermission("chat.formatting")) {
            Util.sendMessage(sender, "&cInvalid formatting character detected. Please use only color characters. Your brackets will be made bold by the plugin.");
            return true;
        } else {
            Player player = (Player)sender;
            Chat chat = TimeistsDecos.getChat();
            String oldPrefix = ChatColor.stripColor(Util.translateHexColorCodes("#", ChatColor.translateAlternateColorCodes('&', chat.getPlayerPrefix((String)null, player))));
            String newPrefix = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', args[0]));
            System.out.println(oldPrefix);
            if (!oldPrefix.equals(ChatColor.stripColor(Util.translateHexColorCodes("#", newPrefix)))) {
                Util.sendMessage(sender, "Please use the exact prefix as before. You're only allowed to additionally specify new colors.");
                return true;
            } else {
                chat.setPlayerPrefix((String)null, player, Util.translateHexColorCodes("#", args[0]));
                Util.sendMessage(sender, "&aYour prefix has been set to " + Util.translateHexColorCodes("#", args[0]));
                return true;
            }
        }
    }
}