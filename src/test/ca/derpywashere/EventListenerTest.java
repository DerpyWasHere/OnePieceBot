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
        Assert.assertTrue(EventListener.checkMessage("the one piece is real".split(" ")));
    }

    @Test
    public void validateString2()
    {
        Assert.assertTrue(EventListener.checkMessage("one".split(" ")));
    }

    @Test
    public void validateString3()
    {
        Assert.assertTrue(EventListener.checkMessage("pIeCe".toLowerCase().split(" ")));
    }

    @Test
    public void validateString4()
    {
        String input = "bones pieced together";
        Assert.assertFalse(EventListener.checkMessage(input.split(" ")));
    }
}

