package com.timeist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {
    private final UUID uuid;
    private String prefix;
    private String nickname;
    private String suffix;
    private String marker;
    private String quote;
    private String color;
    private List<String> ignoredPlayers = new ArrayList();

    public PlayerData(UUID uuid, String prefix, String nickname, String suffix, String marker, String quote, String color, List<String> ignoredPlayers) {
        this.uuid = uuid;
        this.prefix = prefix;
        this.nickname = nickname;
        this.suffix = suffix;
        this.marker = marker;
        this.quote = quote;
        this.color = color;
        this.ignoredPlayers.addAll(ignoredPlayers);
    }

    //Just a general management class for player information.
    public UUID getUuid() {
        return this.uuid;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getMarker() {
        return this.marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getQuote() {
        return this.quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public List<String> getIgnoredPlayers() {
        return this.ignoredPlayers;
    }

    public void setIgnoredPlayers(List<String> ignoredPlayers) {
        this.ignoredPlayers = ignoredPlayers;
    }

    public void save() {
        TimeistsDecos.getInstance().getDatabase().setPlayerData(this);
    }
}
