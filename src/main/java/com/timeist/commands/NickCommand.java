package com.timeist.commands;

import com.timeist.PlayerData;
import com.timeist.TimeistsDecos;
import com.timeist.Util;
import javax.annotation.Nonnull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor {
    public NickCommand() {
    }

    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String commandLabel, @Nonnull String[] args) {
        if (Util.checkPlayer(sender)) {
            return true;
        } else if (Util.checkArgs(sender, args, 1, true)) {
            return true;
        } else if (args[0].equalsIgnoreCase("setInt")) {
            if (!Util.checkPerms(sender, "mn.setInt")) {
                return true;
            } else {
                try {
                    int maxLength = Integer.parseInt(args[1]);
                    if (maxLength <= 0) {
                        Util.sendMessage(sender, "WHAT THE FUCK ARE YOU EVEN TRYING TO DO YOU MADMAN");
                        return true;
                    }

                    TimeistsDecos.getInstance().getConfig().set("maxLength", maxLength);
                    Util.sendMessage(sender, "&aThe maximum amount of characters for nicknames has been set to &f" + args[1] + "&a.");
                    TimeistsDecos.getInstance().saveConfig();
                } catch (NumberFormatException var9) {
                    Util.sendMessage(sender, args[0] + "&c is not a valid number. Did you go to school?");
                }

                return true;
            }
        } else {
            String nickname = String.join(" ", args);
            if (nickname.contains("@")) {
                Util.sendMessage(sender, "No. Just no. Why would you do this? What is wrong with you?");
                return true;
            } else {
                int limit = TimeistsDecos.getInstance().getConfig().getInt("maxLength");
                if (nickname.length() > limit) {
                    Util.sendMessage(sender, "&cYour nickname is longer than the limit of &f" + limit + "&c.");
                    return true;
                } else {
                    Player player = (Player)sender;
                    PlayerData playerData = Util.getPlayerData(player.getUniqueId());
                    playerData.setNickname(Util.translateHexColorCodes("#", nickname));
                    playerData.save();
                    player.setDisplayName(Util.translateHexColorCodes("#", nickname));
                    Util.sendMessage(sender, "&aYour nickname has been set to " + Util.translateHexColorCodes("#", nickname));
                    return true;
                }
            }
        }
    }
}