package com.timeist;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class DiscordWebhook {
    private String content;
    private String username;
    private String displayname;
    private String avatarUrl;


    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    //Executing the request to the webhook. The result will appear in #game-chat.
    public void execute() throws IOException {
        if (this.content == null) {
            throw new IllegalArgumentException("I don't know how this managed to happen, but the message sent has no content.");
        } else {

            //Setting up the request.
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("content", this.content);
            jsonObject.addProperty("username", this.username);
            jsonObject.addProperty("avatar_url", this.avatarUrl);

            //Directing the request.
            if((!TimeistsDecos.url.equalsIgnoreCase("changeme")) && TimeistsDecos.url.contains("discord.com/api/webhooks")) {
                URL url = new URL(TimeistsDecos.url);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.addRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("User-Agent", "Timmy can go fuck his user agent lol");
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                //Finally, sending the request and then disconnecting.
                OutputStream stream = connection.getOutputStream();
                stream.write(jsonObject.toString().getBytes());
                stream.flush();
                stream.close();
                connection.getInputStream().close();
                connection.disconnect();
                //Error stuff.
            } else {
                System.out.println("Hello! You have not set the Discord Webhook URL in the config! You can do this with /webhook (URL)! \nThis command must be run by CONSOLE.");
            }
        }
    }
}
