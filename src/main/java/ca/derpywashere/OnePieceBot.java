package ca.derpywashere;

/**
 * Entry point for the bot
 * @author DerpyWashere
 */
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
public class OnePieceBot
{
    public static void main(String[] args)
    {
        try
        {
            JDABuilder builder = JDABuilder.createDefault(args[0]);
            builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
            JDA jda = builder.build();
            jda.addEventListener(new EventListener(jda));
            jda.awaitReady();
        }
        catch(InterruptedException interruptedEx)
        {
            interruptedEx.printStackTrace();
        }
    }
}
