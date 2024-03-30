package ca.derpywashere;

import java.util.Arrays;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Override of JDA EventListener
 * Listens for messages sent in Discord guilds
 *
 * @author DerpyWasHere
 */
public class EventListener extends ListenerAdapter
{
    public EventListener() {}

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        String str = event.getMessage().getContentRaw();
        String lowercase_str = str.toLowerCase();
        String[] strings = lowercase_str.split(" ");

        if(checkMessage(strings))
            event.getChannel().sendMessage("https://derpywashere.s-ul.eu/KhHYXcyo").queue();
    }

    protected static Boolean checkMessage(String[] strings)
    {
        return Arrays.stream(strings).anyMatch(s -> s.equals("one") || s.equals("piece"));
    }
}