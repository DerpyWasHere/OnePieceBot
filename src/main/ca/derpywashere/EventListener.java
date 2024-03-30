package ca.derpywashere;

import java.util.Arrays;
import java.util.Random;

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

    private final String[] urlStrings = {
        "https://derpywashere.s-ul.eu/myzRbAeE",
        "https://derpywashere.s-ul.eu/pOwphPoN",
        "https://derpywashere.s-ul.eu/93Z4oK9R",
        "https://derpywashere.s-ul.eu/HRc69dj3",
        "https://derpywashere.s-ul.eu/gxQREb9L",
        "https://derpywashere.s-ul.eu/JZ9qkJPY",
        "https://derpywashere.s-ul.eu/KSeAWncL",
        "https://derpywashere.s-ul.eu/c1aWF098",
        "https://derpywashere.s-ul.eu/Vs6Kw8Hk",
        "https://derpywashere.s-ul.eu/99UWDifG",
        "https://derpywashere.s-ul.eu/O1uWfgK4",
        "https://derpywashere.s-ul.eu/KhHYXcyo",
        "https://derpywashere.s-ul.eu/RU7bGWsT",
        "https://derpywashere.s-ul.eu/ochgPS1Y",
        "https://derpywashere.s-ul.eu/vdnHMLFf"
    };

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        String str = event.getMessage().getContentRaw();
        String lowercase_str = str.toLowerCase();
        String[] strings = lowercase_str.split(" ");

        if(checkMessage(strings))
        {
            Random generator = new Random();
            int idx = generator.nextInt(urlStrings.length);

            event.getChannel().sendMessage(urlStrings[idx]).queue();
        }
    }

    protected static Boolean checkMessage(String[] strings)
    {
        return Arrays.stream(strings).anyMatch(s -> s.equals("one") || s.equals("piece"));
    }
}