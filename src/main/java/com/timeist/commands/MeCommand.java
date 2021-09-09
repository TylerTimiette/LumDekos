package com.timeist.commands;

import com.timeist.Util;
import javax.annotation.Nonnull;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class MeCommand implements CommandExecutor {


    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (Util.checkArgs(sender, args, 1, true)) {
            return true;
        } else {
            String message = String.join(" ", args);
            message = Util.removeFormatting((Player)sender, message);
            if (sender instanceof ConsoleCommandSender) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&0(&d&lCONSOLE&0) &d&l> &r" + message));
                return true;
            } else {
                Player player = (Player)sender;
                message = ChatColor.translateAlternateColorCodes('&', "&d&l(&5ACTION&d&l) " + player.getDisplayName() + "&0: &d" + message);
                Util.sendChatMessage(player.getUniqueId(), new BaseComponent[]{new TextComponent(this.color(Util.translateHexColorCodes("#", message)))});
                return true;
            }
        }
    }
    private BaseComponent[] color(String text) {
        return TextComponent.fromLegacyText(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', text));
    }
}