package ca.derpywashere;

// URI/URL imports
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

// Utility imports
import java.util.Arrays;
import java.util.Random;

// SQL imports
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

// JDA Event imports
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
    private Statement stmt;
    private final Random generator;

    // Default strings if DB does not work
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

    public EventListener(String user, String pwd, String ip) {

        generator = new Random();
        final String jdbcString = String.format("jdbc:mysql://%s/OnePieceBot", ip);
        try {
            Connection con = DriverManager.getConnection(jdbcString, user, pwd);
            stmt = con.createStatement();
        }
        catch(SQLException e)
        {
            // DB errored in constructor
            // Explicitly opaque, does not send error message, only prints.
            System.out.println("DB Error:");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        // Parse and tokenize message
        String str = event.getMessage().getContentRaw();
        String lowercase_str = str.toLowerCase();
        String[] strings = lowercase_str.split(" ");
        String[] tokens = str.split(" ");

        // Check for "one" and or "piece"
        if(checkMessage(strings))
        {
            String url = null;
            int idx = 0;
            try
            {
                // Grab size of table
                if(stmt == null)
                    throw new StatementNullException();
                ResultSet querySize = stmt.executeQuery("SELECT COUNT(id) FROM image_urls");
                if(querySize.next())
                {
                    int size = querySize.getInt(1);
                    idx = generator.nextInt(size);
                }
                // Grab the 'random' entry from the table
                ResultSet queryRand = stmt.executeQuery(String.format("SELECT * FROM image_urls WHERE id=%d", idx));
                if(queryRand.next())
                {
                    url = queryRand.getString("url");
                    event.getChannel().sendMessage(url).queue();
                }
            }
            catch(SQLException | StatementNullException e)
            {
                // DB errored
                event.getChannel().sendMessage("Error in retrieving URL:").queue();
                event.getChannel().sendMessage(e.getMessage()).queue();

                // Default if DB errors
                url = urlStrings[generator.nextInt(urlStrings.length)];
                event.getChannel().sendMessage(url).queue();
            }
        }

        // Add url to DB
        if(tokens[0].equals("!addUrl"))
        {
            try
            {
                // Validates that the URL is valid
                URI validateUri = new URI(tokens[1]);
                URL validate = validateUri.toURL();

                int queryAdd = 0;
                while(queryAdd == 0)
                {
                    queryAdd = stmt.executeUpdate(String.format("INSERT INTO image_urls (url) VALUES ('%s')", validate));
                }

                event.getChannel().sendMessage(String.format("URL '%s' was added to the database", tokens[1])).queue();
            }
            // Catch malformed URLs
            catch(URISyntaxException | MalformedURLException | IllegalArgumentException e)
            {
                event.getChannel().sendMessage(String.format("URL '%s' is malformed.\n", tokens[1])).queue();
            }
            // Catch DB errors
            catch(SQLException e)
            {
                // DB errored
                event.getChannel().sendMessage("Error in adding URL:").queue();
                event.getChannel().sendMessage(e.getMessage()).queue();
            }
        }

        if(tokens[0].equals("!deleteUrl")) {
            try {
                // Validates that the URL is valid
                URI validateUri = new URI(tokens[1]);
                URL validate = validateUri.toURL();

                // Grab size of table
                ResultSet querySize = stmt.executeQuery("SELECT COUNT(id) FROM image_urls");
                querySize.next();
                int size = querySize.getInt(1);

                // Check if URL exists in DB
                ResultSet queryCheck = stmt.executeQuery(String.format("SELECT * FROM image_urls WHERE url = '%s'", tokens[1]));
                if (!queryCheck.isBeforeFirst() && queryCheck.getRow() == 0) {
                    throw new URLNotFoundException();
                }

                // Perform deletion
                int queryDelete = 0;
                while (queryDelete == 0) {
                    queryDelete = stmt.executeUpdate(String.format("DELETE FROM image_urls WHERE url = '%s'", validate));
                }

                // Alter auto increment such that there exists no gap between ids
                stmt.executeUpdate(String.format("ALTER TABLE image_urls AUTO_INCREMENT = %d", size - 1));

                event.getChannel().sendMessage(String.format("URL '%s' was deleted from the database", tokens[1])).queue();
            }
            // Catch malformed URLs
            catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
                event.getChannel().sendMessage(String.format("URL '%s' is malformed.\n", tokens[1])).queue();
            }
            // Catch DB errors
            catch (SQLException e) {
                // DB errored
                event.getChannel().sendMessage("Error in deleting URL:").queue();
                event.getChannel().sendMessage(e.getMessage()).queue();
            }
            // URL doesn't exist, show error message
            catch (URLNotFoundException e) {
                event.getChannel().sendMessage(String.format("URL '%s' was not found in the database", tokens[1])).queue();
            }
        }
    }

    protected static Boolean checkMessage(String[] strings)
    {
        return Arrays.stream(strings).anyMatch(s -> s.equals("one") || s.equals("piece"));
    }

    private static class URLNotFoundException extends RuntimeException {}
    private static class StatementNullException extends NullPointerException {
        @Override
        public String getMessage() {
            return "SQL Statement object is null";
        }
    }
}