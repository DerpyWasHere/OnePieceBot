package ca.derpywashere;

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
    public EventListener(JDA jda) {}

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        String str = event.getMessage().getContentRaw();
        String lowercase_str = str.toLowerCase();
        if(lowercase_str.contains("one") || lowercase_str.contains("piece"))
        {
            event.getChannel().sendMessage("https://derpywashere.s-ul.eu/KhHYXcyo").queue();
        }
    }
}