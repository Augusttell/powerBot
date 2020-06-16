package PayDirtMiner;

import Common.BotRandom;
import Common.Task;
import Common.Walker;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

public class WalkToBank extends Task {
    BotRandom botRandom = new BotRandom(ctx);
    Walker walker = new Walker(ctx);


    public WalkToBank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() == 28 &&
                ctx.objects.select().id(Constants.payDirtOre).isEmpty();
    }

    @Override
    public void execute() {
        System.out.println("Started walking to bank");
        // Initate randomness tiles
        final Tile[] oreTile1 = {botRandom.randTile(Constants.oreTile1.x(), Constants.oreTile1.y(), 0,
                1, 0, 1, 0 , 0, 0)};
        final Tile[] oreTile2 = {botRandom.randTile(Constants.oreTile2.x(), Constants.oreTile2.y(), 0,
                1, 0, 1, 0 , 0, 0)};
        final Tile[] hopTile = {botRandom.randTile(Constants.hopTile.x(), Constants.hopTile.y(), 0,
                2, 0, 2, 0 , 0, 0)};
        final Tile[] bagTile = {botRandom.randTile(Constants.bagTile.x(), Constants.bagTile.y(), 0,
                1, 0, 1, 0 , 0, 0)};
        final Tile[] bankTile = {botRandom.randTile(Constants.bankTile.x(), Constants.bankTile.y(), 0,
                1, 0, 1, 0 , 0, 0)};


        // Walk to rockfall
//        if(oreTile1[0].matrix(ctx).reachable()) {
//            ctx.movement.newTilePath(oreTile1).traverse();
//            Condition.wait(() -> !ctx.players.local().inMotion());
//            botRandom.randCamera(0, 100, 10, -10, 10, -10, 2.1);
//        }
        if((Math.abs(oreTile1[0].tile().x()-ctx.players.local().tile().x()) >= 8)) {
            walker.walkPath(oreTile1);
            Condition.wait(() -> !ctx.players.local().inMotion());
        }
        // If there mine them
        GameObject rockfall1 = ctx.objects.select().at(new Tile(3731, 5683, 0)).name("Rockfall").poll();
        if(!oreTile2[0].matrix(ctx).reachable()
                //!Constants.betweenRocks.matrix(ctx).reachable()
        ) {
        if(rockfall1.valid()
                //rockfall1.id() == 26680
        ) {
            rockfall1.interact("Mine");
            Condition.wait(() -> ctx.players.local().animation() !=6746 && !ctx.players.local().inMotion());

            //GameObject finalRockfall = rockfall1;


            System.out.println("Rockfall 2 mined");
            //rockfall1 = ctx.objects.select().at(new Tile(3731, 5683, 0)).name("Rockfall").poll();

        }
        //}


        // If there mine them
        GameObject rockfall2 = ctx.objects.select().at(new Tile(3733, 5680, 0)).name("Rockfall").poll();

      //  if(Constants.betweenRocks.matrix(ctx).reachable()) {
            if (rockfall2.valid()
                   // rockfall2.id() == 26679
        ) {
                rockfall2.interact("Mine");
                Condition.wait(() -> ctx.players.local().animation() !=6746 && !ctx.players.local().inMotion());

                // GameObject finalRockfall2 = rockfall2;

                System.out.println("Rockfall 2 mined");
               //rockfall2 = ctx.objects.select().at(new Tile(3733, 5680, 0)).name("Rockfall").poll();
            }
        }


        // Walk out
        //if(oreTile2[0].matrix(ctx).reachable())
        else {
            walker.walkPath(oreTile2);
            Condition.wait(() -> !ctx.players.local().inMotion());
            System.out.println("Walked to oretile1");
            botRandom.randCamera(0,100,10,-10,10,-10, 2.1);


            // Walk to hopper
            ctx.movement.newTilePath(hopTile[0]).traverse();
            System.out.println("Walked to hop");
            botRandom.randCamera(0,100,10,-10,10,-10, 2.1);


            // Use hopper
            GameObject hopper = ctx.objects.select().name("Hopper").poll();
            while(hopper.inViewport() && ctx.inventory.select().count() == 28 && !ctx.inventory.select().id(Constants.payDirtOre).isEmpty()) {
                hopper.interact("Deposit");
                Condition.wait(() -> ctx.inventory.select().count() != 28, 500, 10);

            }

            System.out.println("Used hop");

            botRandom.randCamera(0,100,10,-10,10,-10, 2.1);

            // Walk to bag
            //ctx.movement.newTilePath(bagTile[0]).traverse();
            walker.walkPath(bagTile);
            Condition.wait(() -> !ctx.players.local().inMotion());
            //Condition.sleep(1300);
            botRandom.randSleep(3400,2600,0);
            System.out.println("walk to bag");

            botRandom.randCamera(0,100,10,-10,10,-10, 2.1);
            //botRandom.randSleep(2800,  900,  0);


            // Use bag
            GameObject sack1 = ctx.objects.select().name("Sack").poll();
            //GameObject sack2 = ctx.objects.select().name("Empty sack").poll();
            while(sack1.inViewport()) {
                //sack2.interact("Search");
                sack1.interact("Search");
                Condition.wait(() -> ctx.inventory.select().count() >= 1, 500, 100);

                sack1 = ctx.objects.select().name("Sack").poll();
                //sack2 = ctx.objects.select().name("Empty sack").poll();
                botRandom.randCamera(0,100,10,-10,10,-10, 2.1);

                while(ctx.inventory.select().count() != 0){
                    ctx.movement.newTilePath(bankTile).traverse();
                    System.out.println("Walked to bank");

                    botRandom.randCamera(0, 100, 10, -10, 10, -10, 2.1);

                    // Use bank
                    GameObject deposit = ctx.objects.select().id(Constants.depositBox).nearest().poll();
                    //ctx.bank.open();
                    deposit.interact("Deposit");
                    Condition.wait(() -> ctx.depositBox.opened(), 400, 10);


                   // Condition.wait(() -> ctx.bank.opened(), 400, 10);
                    botRandom.randSleep(250000, 5000, 2.6);
                    System.out.println("Banking");

                    // Deposit
                    ctx.depositBox.depositInventory();
                    Condition.wait(() -> ctx.inventory.select().count() == 0, 5, 100);
                    System.out.println("Banked");

                    ctx.depositBox.close();
                    Condition.wait(() -> !ctx.depositBox.opened(), 400, 10);
                }
            }

            System.out.println("used bag");

            botRandom.randCamera(0,100,10,-10,10,-10, 2.1);

            //sack2 = ctx.objects.select().name("Empty sack").poll();
        // Walk to bank
//            while(sack2.inViewport() && ctx.inventory.select().count() != 0){
//                    //ctx.inventory.select().count() >= 23 && ctx.inventory.select().id(Constants.payDirtOreVein).isEmpty()
//                ctx.movement.newTilePath(bankTile).traverse();
//                System.out.println("Walked to bank");
//
//                botRandom.randCamera(0, 100, 10, -10, 10, -10, 2.1);
//
//                // Use bank
//                ctx.bank.open();
//                Condition.wait(() -> ctx.bank.opened(), 400, 10);
//                botRandom.randSleep(250000, 5000, 2.6);
//                System.out.println("Banking");
//
//                // Deposit
//                ctx.bank.depositInventory();
//                Condition.wait(() -> ctx.inventory.select().count() == 0, 5, 100);
//                System.out.println("Banked");
//
//                ctx.bank.close();
//                Condition.wait(() -> !ctx.bank.opened(), 400, 10);
//
//            }

        botRandom.randSleep(250000,  5000,  2.6);

        }
    }
}