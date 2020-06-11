package CombinationRuneCrafter;

import org.powerbot.script.rt4.ClientAccessor; // Is a class that will allow us to Access Client methods
import org.powerbot.script.rt4.ClientContext;

public abstract class Task extends ClientAccessor {

    public Task(ClientContext ctx) {
        super(ctx);
    }

    public abstract boolean activate(); // If
    public abstract void execute(); // Do

}