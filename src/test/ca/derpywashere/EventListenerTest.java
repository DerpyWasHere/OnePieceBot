package ca.derpywashere;

import dev.coly.jdat.JDATesting;
import org.junit.Test;
import org.junit.Assert;

import ca.derpywashere.EventListener;


public class EventListenerTest
{
    @Test
    public void validateString1()
    {
        try
        {
            String res = JDATesting.testMessageReceivedEvent(new EventListener(), "the one piece is real").getContentRaw();
            Assert.assertNotNull(res);
        }
        catch(InterruptedException e)
        {
            Assert.fail();
        }
    }

    @Test
    public void validateString2()
    {
        try
        {
            String res = JDATesting.testMessageReceivedEvent(new EventListener(), "one").getContentRaw();
            Assert.assertNotNull(res);
        }
        catch(InterruptedException e)
        {
            Assert.fail();
        }
    }

    @Test
    public void validateString3()
    {
        try
        {
            String res = JDATesting.testMessageReceivedEvent(new EventListener(), "pIeCe").getContentRaw();
            Assert.assertNotNull(res);
        }
        catch(InterruptedException e)
        {
            Assert.fail();
        }
    }

    @Test
    public void validateString4()
    {
        String input = "bones pieced together";
        Assert.assertFalse(EventListener.checkMessage(input.split(" ")));
    }
}

