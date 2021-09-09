package com.timeist.commands;

import com.timeist.TimeistsDecos;
import com.timeist.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

public class WebhookCommand implements @Nullable CommandExecutor {

    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            if(args.length != 1)
                sender.sendMessage("This command only requires one argument, and it is the Discord webhook URL you would like to change to.");
           else {
               //They have entered (presumably) a URL.
                try {
                    new URL(args[0]);
                } catch (MalformedURLException var12) {
                    sender.sendMessage("This is not a valid URL.");
                    return true;
                }

                //This'll be a valid webhook if it meets the criteria.
                if(args[0].contains("discord.com/api/webhooks")) {
                    TimeistsDecos.getInstance().getConfig().set("webhook", args[0]);
                    TimeistsDecos.getInstance().saveConfig();
                    sender.sendMessage("Set webhook address to " + args[0]);
                    TimeistsDecos.getInstance().url = args[0];
                } else
                    sender.sendMessage("This is not a valid Discord Webhook.");
            }
            return true;

        } else {
            sender.sendMessage("Hello! You are trying to modify the webhook URL for Lum's Dekos. Unfortunately, due to security concerns, only CONSOLE can run this command. Thanks!");
            return true;
        }
    }
}
