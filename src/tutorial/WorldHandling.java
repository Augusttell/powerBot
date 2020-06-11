package tutorial;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.World;

public class WorldHandling extends Task{


    @Override
    public boolean activate() {
        return false;
    }

    @Override
    public void execute() {

    }

    public void hopWorld(){
        ctx.worlds.open();
        ctx.worlds.select().types(World.Type.FREE);
        ctx.worlds.select().joinable();
        ctx.worlds.shuffle();
        ctx.worlds.peek().hop();
        Condition.sleep(2000);
    }

    public WorldHandling(ClientContext ctx) {
        super(ctx);
    }
}
