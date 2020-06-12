package BlastFurnace;

import Common.BotRandom;
import Common.Walker;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;

public class DeliverOre extends Common.Task{
    Walker walker = new Walker(ctx);
    BotRandom botRandom = new BotRandom(ctx);

    public DeliverOre(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return false; // Empty inventory
    }

    @Override
    public void execute() {
        final Tile[] barCollectionTile = {botRandom.randTile(Constants.collection.x(), Constants.collection.y(), 0,
                2, 0, 0, 0 , 0, 0)};
        final Tile[] furnaceTile = {botRandom.randTile(Constants.furnace.x(), Constants.furnace.y(), 0,
                2, 0, 0, 0 , 0, 0)};

        /// Trip 1
        // Withdraw ores
        ctx.bank.withdraw(Constants.coalOre, Bank.Amount.ALL);

        // fill bag
        // Walk to furnace
        walker.walkPath(furnaceTile);

        // use ores on furnace
        airTali.interact("Use");
        altar.interact("Use");

        // empty bag
        // use ores on furnace
        airTali.interact("Use");
        altar.interact("Use");

        // use on furnace
        // walk back to bank
        walker.walkPath(barCollectionTile);

        // open bank
        ctx.bank.open();

        /// Trip 2
        // Withdraw ores
        ctx.bank.withdraw(Constants.coalOre, 27);

        // fill bag
        // Withdraw ores
        ctx.bank.withdraw(Constants.mithOre, 27);

        // Walk to furnace
        walker.walkPath(furnaceTile);

        // use ores on furnace
        airTali.interact("Use");
        altar.interact("Use");

        // empty bag
        // use ores on furnace
        airTali.interact("Use");
        altar.interact("Use");

        // use on furnace
        // walk back to bank
        walker.walkPath(barCollectionTile);

        // open bank
        ctx.bank.open();

        /// Trip 3
        // Withdraw ores
        ctx.bank.withdraw(Constants.coalOre, 27);

        // fill bag
        // Withdraw ores
        ctx.bank.withdraw(Constants.mithOre, 27);

        // Walk to furnace
        walker.walkPath(furnaceTile);

        // use ores on furnace
        airTali.interact("Use");
        altar.interact("Use");

        // empty bag
        // use ores on furnace
        airTali.interact("Use");
        altar.interact("Use");

        // use on furnace


    }
}
