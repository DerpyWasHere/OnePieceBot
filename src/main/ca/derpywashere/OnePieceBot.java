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
        init(args[0], args[1], args[2], args[3]);
    }

    public static void init(String key, String db_user, String db_password, String db_ip)
    {
        try
        {
            JDABuilder builder = JDABuilder.createDefault(key);
            builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
            JDA jda = builder.build();
            jda.addEventListener(new EventListener(db_user, db_password, db_ip));
            jda.awaitReady();
        }
        catch(InterruptedException interruptedEx)
        {
            interruptedEx.printStackTrace();
        }
    }
}
