package PayDirtMiner;

import Common.BotRandom;
import Common.Walker;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

public class FixWheel extends Common.Task{
    BotRandom botRandom = new BotRandom(ctx);
    Walker walker = new Walker(ctx);


    public FixWheel(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.objects.select().at(Constants.wheel1).name("Broken strut").poll().id() == 26670 &&
                ctx.objects.select().at(Constants.wheel2).name("Broken strut").poll().id() == 26670 &&
                ctx.inventory.select().count() == 0 &&
                !Constants.miningArea.contains(ctx.players.local()) &&
                (Math.abs(Constants.bankTile.tile().x()-ctx.players.local().tile().x()) <= 12);
    }

    @Override
    public void execute() {
        final Tile[] wheelTiles = {botRandom.randTile(Constants.wheelTiles.x(), Constants.wheelTiles.y(), 0,
                0, 0, 2, 0 , 0, 0)};
        final Tile[] bankTile = {botRandom.randTile(Constants.bankTile.x(), Constants.bankTile.y(), 0,
                1, 0, 1, 0 , 0, 0)};

        while(ctx.inventory.id(2347).isEmpty()) {
            walker.walkPath(bankTile);
            ctx.bank.open();
            Condition.wait(() -> ctx.bank.opened(), 400, 10);
            ctx.bank.withdraw(2347, Bank.Amount.ONE);
            Condition.wait(() -> !ctx.inventory.id(2347).isEmpty(), 5, 100);

        }

            //ctx.movement.newTilePath(wheelTiles[0]).traverse();
            walker.walkPath(wheelTiles);
            Condition.wait(() -> !ctx.players.local().inMotion(), 1200, 20);

            GameObject wheel1 = ctx.objects.select().at(Constants.wheel1).name("Broken strut").poll();

            while(wheel1.id() == 26670) {
                wheel1.interact("Hammer");
                GameObject finalwheel1 = wheel1;
                Condition.wait(() -> finalwheel1.id() != 26670, 500, 10);

                wheel1 = ctx.objects.select().at(Constants.wheel1).name("Broken strut").poll();

            }

            System.out.println("Fixed wheel 1");

            GameObject wheel2 = ctx.objects.select().at(Constants.wheel2).name("Broken strut").poll();
            while(wheel2.id() == 26670) {
                wheel2.interact("Hammer");
                GameObject finalwheel2 = wheel2;
                Condition.wait(() -> finalwheel2.id() != 26670, 500, 10);

                wheel2 = ctx.objects.select().at(Constants.wheel2).name("Broken strut").poll();
            }

            System.out.println("Fixed wheel 2");

            //ctx.movement.newTilePath(bankTile).traverse();
            walker.walkPath(bankTile);
            Condition.wait(() -> !ctx.players.local().inMotion(), 1200, 20);

            // Use bank
            ctx.bank.open();
            Condition.wait(() -> ctx.bank.opened(), 400, 10);
            botRandom.randSleep(250000, 5000, 2.6);
            System.out.println("Banking");

            // Deposit
            ctx.bank.depositInventory();
            Condition.wait(() -> ctx.inventory.select().count() == 0, 5, 100);
            System.out.println("Banked");

            ctx.bank.close();
            Condition.wait(() -> !ctx.bank.opened(), 400, 10);

    }
}
