package com.timeist.listeners;

import com.timeist.Util;
import java.util.UUID;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    //Removes player data from the HashMap.
    @EventHandler
    public void onJoin(PlayerQuitEvent event) {
        UUID player = event.getPlayer().getUniqueId();
        if (Util.hasPlayerData(player)) {
            Util.getPlayerData(player).save();
            Util.removePlayerData(player);
        }

    }
}