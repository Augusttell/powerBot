package BlastFurnace;

import Common.BotRandom;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import Common.Walker;

public class steelBars extends Common.Task{
    Walker walker = new Walker(ctx);
    BotRandom botRandom = new BotRandom(ctx);



    public steelBars(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return false; //Only coal bag and no ores
    }

    @Override
    public void execute() {
//        final Tile[] bankTile = {botRandom.randTile(Constants.bankTile.x(), Constants.bankTile.y(), 0,
//                2, 0, 0, 0 , 0, 0)};
//        final Tile[] conveyerBeltTile = {botRandom.randTile(Constants.conveyerBeltTile.x(), Constants.conveyerBeltTile.y(), 0,
//                2, 0, 0, 0 , 0, 0)};
//        final Tile[] barDispenserTile = {botRandom.randTile(Constants.barDispenserTile.x(), Constants.barDispenserTile.y(), 0,
//                2, 0, 0, 0 , 0, 0)};
//
//        // if not at bank
//        ctx.bank.open();
//        if(ctx.inventory.select().id(Constants.coalBagID).isEmpty()) {
//            ctx.bank.withdraw(Constants.coalBagID, 1);
//        }
//        // 1 ) Place coal and/or ore on the Conveyor Belt.
//        // 2 ) Cool down smelted bars with a bucket of water or just collect the bars with the ice gloves equipped.
//        // Collect the bars from the bar dispenser.
//
//        switch (state()) {
//            case bank:
//                break;
//
//            case deliverOre:
//                break;
//
//        }
//
//
//
//
//        private State state() {
//            if(i==1) {
//                return State.bank;
//
//            } else {
//                return State.deliverOre;
//            }
//        }
//        private enum State {
//            withdrawOres, deliverOre, collectBars
//
//        }
    }
}
