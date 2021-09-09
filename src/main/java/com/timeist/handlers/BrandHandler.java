package com.timeist.handlers;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.timeist.TimeistsDecos;
import javax.annotation.Nonnull;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BrandHandler implements PluginMessageListener {

    //Just a fun thing Gadse showed me.
    public void onPluginMessageReceived(String channel, @Nonnull Player player, @Nonnull byte[] bytes) {
        if (channel.equals("minecraft:brand")) {
            ByteArrayDataInput input = ByteStreams.newDataInput(bytes);
            TimeistsDecos.getInstance().getLogger().info(player.getName() + " logged in using " + input.readLine());
        }

    }
}