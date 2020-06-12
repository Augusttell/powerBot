package BlastFurnace;

import Common.BotRandom;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;
import Common.Walker;
import org.powerbot.script.rt4.GameObject;

public class CollectBars extends Common.Task{
    Walker walker = new Walker(ctx);
    BotRandom botRandom = new BotRandom(ctx);



    public CollectBars(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return false; //Only coal bag and no ores
    }

    @Override
    public void execute() {
        final Tile[] barCollectionTile = {botRandom.randTile(Constants.collection.x(), Constants.collection.y(), 0,
                2, 0, 0, 0 , 0, 0)};
        final Tile[] bankTile = {botRandom.randTile(Constants.bank.x(), Constants.bank.y(), 0,
                2, 0, 0, 0 , 0, 0)};

        // Walk to bar collection spot
        walker.walkPath(barCollectionTile);

        // collect
        GameObject furnace = ctx.objects.select().name("Mysterious ruins").poll();
        furnace.interact("Action");

        // walk to bank
        walker.walkPath(bankTile);

        // open bank
        ctx.bank.open();

        // Deposit all bars
        ctx.bank.deposit(Constants.mithBar, Bank.Amount.ALL);

    }
}
