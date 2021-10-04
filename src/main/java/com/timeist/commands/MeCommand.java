package com.timeist.commands;

import com.timeist.DiscordWebhook;
import com.timeist.TimeistsDecos;
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

                //Makes actions appear on Discord.
                //Requested by Marrow
                String discordActionMessage = "**(**ACTION**)** " + message;
                Bukkit.getScheduler().runTaskAsynchronously(TimeistsDecos.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        DiscordWebhook hook = new DiscordWebhook();
                        //We're setting up the URL
                        hook.setUsername(player.getName());
                        hook.setDisplayname(net.md_5.bungee.api.ChatColor.stripColor(player.getDisplayName()).replaceAll("&[k-oK-O]", ""));
                        hook.setContent(net.md_5.bungee.api.ChatColor.stripColor(discordActionMessage.replaceAll("&[a-zA-Z0-9]","").replaceAll("@", "#")));
                        hook.setAvatarUrl("https://mc-heads.net/head/" + player.getUniqueId() + ".png");

                        try {
                            hook.execute();
                        } catch (Exception var10) {
                            var10.printStackTrace();
                            System.out.println("You borked it!");
                        }

                    }
                });
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