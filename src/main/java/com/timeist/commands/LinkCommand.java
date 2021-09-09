package com.timeist.commands;

import com.timeist.PlayerData;
import com.timeist.TimeistsDecos;
import com.timeist.Util;
import java.net.MalformedURLException;
import java.net.URL;
import javax.annotation.Nonnull;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LinkCommand implements CommandExecutor {


    //This command is used for when you're sharing links, as you can't click on a chat message to open the link as normal due to how this plugin works.
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (Util.checkArgs(sender, args, 1, false)) {
            return true;
        } else {
            try {
                new URL(args[0]);
            } catch (MalformedURLException var12) {
                //This is how we detect if you're using a valid URL.
                Util.sendMessage(sender, "This was not a recognized URL. There might be more information in the console.");
                var12.printStackTrace();
                return true;
            }
            //We check if this sender is the console.
            if (!(sender instanceof Player)) {
                TextComponent textComponent = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&0(&d&lCONSOLE&0) &7&l> &b&l{LINK}"));
                textComponent.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&bClick to visit the attached link&0: &3" + args[0]))).create()));
                textComponent.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, args[0]));
                Bukkit.broadcast(textComponent);
                return true;

                //Otherwise...
            } else {
                Player player = (Player)sender;
                PlayerData playerData = Util.getPlayerData(player.getUniqueId());
                Chat chat = TimeistsDecos.getChat();

                StringBuilder sb = new StringBuilder();
                sb.append(chat.getPlayerPrefix((String)null, player));
                sb.append(" ");
                sb.append(player.getDisplayName());
                sb.append(" ");
                sb.append(chat.getPlayerSuffix(player));
                sb.append("&7&l> &b&l{LINK}");
                String text = ChatColor.translateAlternateColorCodes('&', sb.toString());
                sb.setLength(0);
                sb.append("&4Name&0: &c");
                sb.append(player.getName());

                sb.append("\n&r");
                sb.append(Util.translateHexColorCodes("#", playerData.getQuote()));
                sb.append("\n&bClick to visit the attached link&8: &b(&3");
                sb.append(args[0]);
                sb.append("&b)");
                String hoverText = ChatColor.translateAlternateColorCodes('&', sb.toString());

                TextComponent textComponent = new TextComponent(this.color(Util.translateHexColorCodes("#", text)));
                textComponent.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, this.color("&4Name: &c" + player.getName() + "\n" + Util.translateHexColorCodes("#", hoverText))));
                textComponent.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, args[0]));
                Util.sendChatMessage(player.getUniqueId(), new BaseComponent[]{textComponent});
                return true;
            }
        }
    }

    private BaseComponent[] color(String text) {
        return TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', text));
    }


}
