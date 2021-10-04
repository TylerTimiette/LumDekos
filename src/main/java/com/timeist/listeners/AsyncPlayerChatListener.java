package com.timeist.listeners;

import com.timeist.DiscordWebhook;
import com.timeist.PlayerData;
import com.timeist.TimeistsDecos;
import com.timeist.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class AsyncPlayerChatListener implements Listener {
    private final TimeistsDecos plugin;
    private final List<UUID> partyChat = new ArrayList();
    private final List<UUID> sctChat = new ArrayList();
    private List<UUID> marryChat = new ArrayList();

    public AsyncPlayerChatListener(TimeistsDecos plugin) {
        this.plugin = plugin;
    }

    //We need the highest priority as this plugin should be the absolute last to control the chat message being sent. Otherwise, chat messages would not come out as they should.
    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        //If the player is muted, they're in party chat, or they're in staff chat, we don't want to send the message to general chat. So we check if it's cancelled.
        if (event.isCancelled()) {
            if (this.partyChat.contains(player.getUniqueId())) {
                this.plugin.getLogger().info("(PARTY) " + player.getName() + ": " + event.getMessage());
            } else if (this.sctChat.contains(player.getUniqueId())) {
                this.plugin.getLogger().info("(SCT) " + player.getName() + ": " + event.getMessage());
            }
        } else {
            //We cancel the event on our own anyways in order to send our special message.
            event.setCancelled(true);
            PlayerData playerData = Util.getPlayerData(player.getUniqueId());
            Chat chat = TimeistsDecos.getChat();

            //StringBuilder is the main tool used for TextComponents due to it having the ability to add text on a whim.
            StringBuilder sb = new StringBuilder();
            sb.append(playerData.getPrefix());
            sb.append(chat.getPlayerPrefix((String)null, player));
            sb.append(playerData.getSuffix());
            //We reset all color after the player's suffix so that it doesn't mess up their chat name.
            sb.append("&r");
            if (sb.length() > 0) {
                sb.append(" ");
            }

            sb.append(player.getDisplayName());
            //Check if they are using hex codes in their marker.
            if (playerData.getMarker().contains("#")) {
                sb.append(ChatColor.translateAlternateColorCodes('&', ChatColor.of(playerData.getMarker()) + "&l >&r "));
            } else {
                sb.append(ChatColor.translateAlternateColorCodes('&', playerData.getMarker() + "&l >&r "));
            }

            //Messagecheck was just a placeholder name when I was bug-fixing, but I'm incredibly lazy and don't feel like renaming it now.
            String messagecheck = Util.removeFormatting(player, playerData.getColor() + event.getMessage());
            this.plugin.getLogger().info("[CHAT] " + player.getName() + " > " + event.getMessage());

            //1.5.4 changed this to be a URL taken from the config.

            //Without runTaskAsynchronously it needs to wait for Discord to respond before continuing

                    DiscordWebhook hook = new DiscordWebhook();
                    //We're setting up the URL
                    hook.setUsername(player.getName());
                    hook.setDisplayname(ChatColor.stripColor(player.getDisplayName()).replaceAll("&[k-oK-O]", ""));
                    hook.setContent(ChatColor.stripColor(event.getMessage().replaceAll("&[a-zA-Z0-9]","").replaceAll("@", "#")));
                    hook.setAvatarUrl("https://mc-heads.net/head/" + player.getUniqueId() + ".png");

                    try {
                        hook.execute();
                    } catch (Exception var10) {
                        var10.printStackTrace();
                        System.out.println("You borked it!");
                    }


            //Compiling our message.
            TextComponent precursor = new TextComponent(this.color(Util.translateHexColorCodes("#", sb.toString())));
            TextComponent message = new TextComponent(this.color(Util.translateHexColorCodes("#", messagecheck)));
            precursor.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, this.color("&4Name: &c" + player.getName() + "\n" + Util.translateHexColorCodes("#", playerData.getQuote()) + "\n&bClick to ignore this player!")));
            precursor.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "/ignore " + player.getName()));
            Util.sendChatMessage(player.getUniqueId(), new BaseComponent[]{precursor, message});
        }

    }
    //Converts chat colors from legacy to current-times.
    private BaseComponent[] color(String text) {
        return TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', text));
    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )

    //Function that checks if someone is toggling to staff chat or party chat.
    public void onCommand(PlayerCommandPreprocessEvent event) {
        UUID player = event.getPlayer().getUniqueId();
        String message = event.getMessage();
        if (message.equalsIgnoreCase("/party chat")) {
            if (this.partyChat.contains(player)) {
                this.partyChat.remove(player);
            } else {
                this.partyChat.add(player);
            }
        } else {
            if (message.contains("/mpm name")) {
                event.setCancelled(true);
            }

            if (message.contains("/marry chat toggle")) {
                if (this.marryChat.contains(player)) {
                    this.marryChat.remove(player);
                } else {
                    this.marryChat.add(player);
                }
            }

            if (message.equalsIgnoreCase("/sct")) {
                if (this.sctChat.contains(player)) {
                    this.sctChat.remove(player);
                } else {
                    this.sctChat.add(player);
                }
            }
        }

    }
}
