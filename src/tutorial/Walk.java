package tutorial;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class Walk extends Task {
    public Walk(ClientContext ctx) {
        super(ctx);
    }
    // public static final Tile[] path = {new Tile(3259, 3256, 0), new}

    @Override
    public boolean activate() {
        return false;
    }

    @Override
    public void execute() {

    }
}
