package com.timeist;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Util {
    private static String prefix = "";
    private static final Map<UUID, PlayerData> playerData = new HashMap();
    public static final char COLOR_CHAR = '§';

    //Init
    static void init() {
        prefix = color(TimeistsDecos.getInstance().getConfig().getString("prefix"));
    }


    //Colorizer.
    private static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }


    //A clean-up Util that some commands use.
    public static void sendMessage(CommandSender sender, String string) {
        sender.sendMessage(prefix + color(string));
    }

    //Used to check if the player has a place in the database.
    public static boolean hasPlayerData(UUID uuid) {
        return playerData.containsKey(uuid);
    }

    //Retrieves player data.
    public static PlayerData getPlayerData(UUID uuid) {
        return (PlayerData) playerData.get(uuid);
    }


    //Adds playerdata upon a player joining.
    public static void addPlayerData(UUID uuid) {
        playerData.put(uuid, TimeistsDecos.getInstance().getDatabase().getPlayerData(uuid));
    }


    //Deletes all player data from the hashmap. This is used so that when a player quits, the server does not keep excess data in RAM.
    public static void removePlayerData(UUID uuid) {
        playerData.remove(uuid);
    }


    //Checking if the sender is a player, a command block, or console.
    public static boolean checkPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, "This command can only be executed as a player.");
            return true;
        } else {
            return false;
        }
    }


    //This is a function Gadse made to clean up the code a little bit. Unfortunately, I am a rebel and forgot this function even existed.
    public static boolean checkArgs(CommandSender sender, String[] args, int length, boolean minimum) {
        if (minimum) {
            if (args.length < length) {
                sendMessage(sender, "You used an invalid amount of arguments. This command has to have at least " + length + ".");
                return true;
            } else {
                return false;
            }
        } else if (args.length != length) {
            sendMessage(sender, "You used an invalid amount of arguments. This command accepts only " + length + ".");
            return true;
        } else {
            return false;
        }
    }


    public static boolean checkPerms(CommandSender sender, String permission) {
        if (!sender.hasPermission(permission)) {
            sendMessage(sender, "&cI'm sorry, but you are missing the following permission: &6" + permission);
            return false;
        } else {
            return true;
        }
    }


    //This is the function we use to send /links, /me, and regular chat messages. It runs asynchronously as otherwise it would halt the server to send the messages. Which, believe it or not, is inefficient.
    public static void sendChatMessage(UUID player, BaseComponent... baseComponents) {
        Bukkit.getScheduler().runTaskAsynchronously(TimeistsDecos.getInstance(), () -> {
            Bukkit.getOnlinePlayers().forEach((target) -> {
                if (!getPlayerData(target.getUniqueId()).getIgnoredPlayers().contains(player.toString())) {
                    target.sendMessage(baseComponents);
                }

            });
        });
    }


    //Standard exception logger. This will fire every time a try-catch in this plugin generates an exception.
    static void logException(Exception exception, UUID uuid, String playerMessage, String consoleMessage) {
        TimeistsDecos plugin = TimeistsDecos.getInstance();
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            sendMessage(player, playerMessage);
        }

        plugin.getLogger().severe(consoleMessage);
        exception.printStackTrace();
    }


    public static String removeFormatting(Player player, String string) {
        return player.hasPermission("chat.formatting") ? string : string.replaceAll("&+[k-oK-O]+", "");
    }


    //Translating the codes in a nickname into something understandable by our TextComponents.
    public static String translateHexColorCodes(String startTag, String message) {
        Pattern hexPattern = Pattern.compile(startTag + "([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 32);

        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, "§x§" + group.charAt(0) + '§' + group.charAt(1) + '§' + group.charAt(2) + '§' + group.charAt(3) + '§' + group.charAt(4) + '§' + group.charAt(5));
        }

        return matcher.appendTail(buffer).toString();
    }


    //This code checks if you are using a valid hex code. If you use letters or characters outside of the allowed list, this function fails.
    public static boolean validateHexCode(String toValidate) {
        String regex = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
        Pattern p = Pattern.compile(regex);
        if (toValidate == null) {
            return false;
        } else {
            Matcher m = p.matcher(toValidate);
            return m.matches();
        }
    }


    //For /specialchat gay
    public static String rainbowifyText(String input) {
        StringBuilder output = new StringBuilder();
        //There'll only be 12 added characters in this loop and they are seen below.
        int currentChar = 1;
        //4, c, 6, e, 2, a, b, 3, 1, 9, d, 5
        for (int i = 0; i < input.length(); i++) {

            switch (currentChar) {
                case 11:
                    currentChar = 1;
                    break;
                case 1:
                    output.append("&4");
                    break;
                case 2:
                    output.append("&c");
                    break;
                case 3:
                    output.append("&6");
                    break;
                case 4:
                    output.append("&e");
                    break;
                case 5:
                    output.append("&a");
                    break;
                case 6:
                    output.append("&2");
                    break;
                case 7:
                    output.append("&b");
                    break;
                case 8:
                    output.append("&3");
                    break;
                case 9:
                    output.append("&5");
                    break;
                case 10:
                    output.append("&d");
                    break;
            }

            output.append(input.charAt(i));
            if(!(input.charAt(i) == ' '))
            currentChar++;
        }

        return output.toString();
    }


    public static String segmentColoredMessage(String input, String type) {
        int segments;
        int increment;
        StringBuilder output = new StringBuilder(input);



        switch(type) {
            case "bi":
                segments = 3;
                //#D8097E, #8C579C, #24468E
                //Every increment, the color changes. The +7 is to account for the hex codes that we just added
                Math.round(increment = (input.length() / segments));
                output.insert(0,"#D8097E");
                output.insert(increment + 7, "#8C579C");
                output.insert((increment * 2) + 14, "#24468E");
                break;


            case "lesbian":
                segments = 5;
                Math.round(increment = (input.length() / segments));

                output.insert(0, "#D62900");
                output.insert(increment + 7, "#FF9B55");
                output.insert((increment * 2) + 14, "#FFFFFF");
                output.insert((increment * 3) + 21, "#D461A6");
                output.insert((increment * 4) + 28, "#A50062");
                break;

            case "nb":
                segments = 4;
                Math.round(increment = (input.length() / segments));

                output.insert(0, "#FFF430");
                output.insert(increment + 7, "#FFFFFF");
                output.insert((increment * 2) + 14, "#9C59D1");
                output.insert((increment * 3) + 21, "#000000");

            case "trans":
                segments = 5;
                Math.round(increment = (input.length() / segments));
                output.insert(0, "#55CDFC");
                output.insert(increment + 7, "#F7A8B8");
                output.insert((increment * 2) + 14, "#FFFFFF");
                output.insert((increment * 3) + 21, "#F7A8B8");
                output.insert((increment * 4) + 28, "#55CDFC");

            case "ace":
                segments = 4;
                Math.round(increment = (input.length() / segments));

                output.insert(0, "#000000");
                output.insert(increment + 7, "#A3A3A3");
                output.insert((increment * 2) + 14, "#FFFFFF");
                output.insert((increment * 3) + 21, "#800080");

            case "pan":
                segments = 3;
                Math.round(increment = (input.length() / segments));
                output.insert(0,"#FF1B8D");
                output.insert(increment + 7, "#FFDA00");
                output.insert((increment * 2) + 14, "#1BB3FF");

        }
        return output.toString();
    }


}
