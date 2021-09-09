package com.timeist.listeners;

import com.timeist.PlayerData;
import com.timeist.Util;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {


    //Making sure the server has the up-to-date info on a player.
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UUID player = event.getPlayer().getUniqueId();
        if (!Util.hasPlayerData(player)) {
            Util.addPlayerData(player);
        }

        PlayerData playerData = Util.getPlayerData(player);
        String nickname = playerData.getNickname().length() > 0 ? ChatColor.translateAlternateColorCodes('&', playerData.getNickname()) : event.getPlayer().getName();
        event.getPlayer().setDisplayName(nickname);
    }
}
