package com.timeist.commands;

import com.timeist.PlayerData;
import com.timeist.TimeistsDecos;
import com.timeist.Util;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nonnull;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IgnoreCommand implements CommandExecutor {


    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        //Blah blah.
        if (Util.checkPlayer(sender)) {
            return true;
        } else {

            Player player = (Player)sender;
            PlayerData playerData = Util.getPlayerData(player.getUniqueId());
            List<String> ignoredPlayers = playerData.getIgnoredPlayers();
            //Blahhh....
            if (args.length == 0) {
                if (ignoredPlayers.isEmpty()) {
                    Util.sendMessage(sender, "You have not ignored any players yet. You must have the patience of an angel.");
                    return true;
                } else {

                    //Async to avoid server slowdown while it grabs your list.
                    Bukkit.getScheduler().runTaskAsynchronously(TimeistsDecos.getInstance(), () -> {
                        Util.sendMessage(sender, "&eYou're currently ignoring &b" + ignoredPlayers.size() + " &eplayers.");

                        //Click to unignore list, etc.
                        ignoredPlayers.forEach((uuid) -> {
                            String targetName = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
                            TextComponent component = new TextComponent("&3- &b" + targetName);
                            component.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, (new ComponentBuilder("Click to un-ignore this player.")).color(ChatColor.AQUA).create()));
                            component.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "/ignore " + targetName));
                            sender.sendMessage(component);
                            System.out.println(String.join(", ", ignoredPlayers));
                        });
                    });
                    return true;
                }


            } else {
                Player target = Bukkit.getPlayer(args[0]);

                //Target is null if the player is not online.
                if (target == null) {
                    Util.sendMessage(sender, "&cThe player &f" + args[0] + " &ccould not be found.");
                    return true;

                    //You can't ignore staff.
                } else if (target.hasPermission("chat.unignorable")) {
                    Util.sendMessage(sender, "&cThis player can not be ignored.");
                    return true;

                    //Gadse made this. I think it's oretty funny.
                } else if (player.getUniqueId().equals(target.getUniqueId())) {
                    Util.sendMessage(sender, "&cWhy are you ignoring yourself? Are you okay? Do you need a hug? Have some hearts.");
                    player.getWorld().spawnParticle(Particle.HEART, player.getLocation(), 100, 1.0D, 1.0D, 1.0D, 0.0D);
                    return true;
                    //If all else does not stop this command, you are ignoring or unignoring another player and thus we can continue on.
                } else {
                    String targetUUID = target.getUniqueId().toString();
                    if (ignoredPlayers.contains(targetUUID)) {
                        ignoredPlayers.remove(targetUUID);
                    } else {
                        ignoredPlayers.add(targetUUID);
                    }

                    playerData.setIgnoredPlayers(ignoredPlayers);
                    playerData.save();
                    //Check a double-check to see if the player was ignored or unignored.
                    Util.sendMessage(sender, ignoredPlayers.contains(targetUUID) ? "&aThe player has been added to your ignore list." : "&aThe player has been removed from your ignore list.");
                    return true;
                }
            }
        }
    }
}
