package BridBot.task.backgroundtasker;

import BridBot.BridBot;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

/*

     Background taskers take no actions on the main game client, they just observe and collect information for the rest of the system
     and they run in a different thread. They also collect/provide information used for paints etc.

 */
public abstract class BackgroundTasker extends ClientAccessor implements Runnable {

    public BackgroundTasker(ClientContext ctx)
    {
        super(ctx);
    }

    public void run()
    {
        while (!BridBot.stopped)
        {
            collectInfo();
        }
    }

    public abstract void collectInfo();
}