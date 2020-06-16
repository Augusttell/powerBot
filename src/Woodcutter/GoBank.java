package Woodcutter;

import Common.Walker;
import Common.BotRandom;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;

public class GoBank extends Common.Task{
    Walker walker = new Walker(ctx);
    BotRandom botRandom = new BotRandom(ctx);


    // Goes and bank and then returns
    public GoBank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() == 28;
    }

    @Override
    public void execute() {
        int energyLevel = ctx.movement.energyLevel();
        final Tile[]  bank = {new Tile(2584, 4840, 0),
                botRandom.randTile(2719, 3499, 0, 1, 0, 1, 0 , 0, 0),
                botRandom.randTile(2725, 3492, 0, 1, 0, 1, 0 , 0, 0)};

        final Tile[] trees  = {new Tile(2584, 4840, 0),
                botRandom.randTile(2719, 3499, 0, 1, 0, 1, 0 , 0, 0),
                botRandom.randTile(2714, 3509, 0, 1, 0, 1, 0 , 0, 0)};

        System.out.println("Inventory full going to bank");

        if(energyLevel >= 60) {
            ctx.movement.running(true);
        }

        botRandom.randCamera(0,100,2,-2,5,-5, 1.2);

        walker.walkPath(bank);
        Condition.wait(() -> ctx.players.local().inMotion(),500, 100);

        ctx.bank.open();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !ctx.bank.opened();
            }
        }, 100, 10);

        ctx.bank.depositInventory();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.inventory.select().count() == 0;
            }
        }, 100, 10);

        botRandom.randSleep(250000,  5000,  2.1);
        botRandom.randCamera(0,100,2,-2,5,-5, 1.2);

        System.out.println("Finished banking returning to trees");

        walker.walkPath(trees);
        Condition.wait(() -> ctx.players.local().inMotion(),100, 100);

        botRandom.randCamera(0,100,2,-2,5,-5, 1.2);

        System.out.println("Returned to trees");

    }
}
