package PayDirtMiner;

import Common.BotRandom;
import Common.Walker;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

public class WalkToOre extends Common.Task{
    BotRandom botRandom = new BotRandom(ctx);
    Walker walker = new Walker(ctx);

    public WalkToOre(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return !Constants.miningArea.contains(ctx.players.local()) && ctx.inventory.select().count() <28;
    }

    @Override
    public void execute() {
        System.out.println("Started walking to ores");
        final Tile[] hopBackTile = {botRandom.randTile(Constants.hopTile.x(), Constants.hopTile.y(), 0,
                1, 0, 1, 0 , 0, 0), botRandom.randTile(Constants.oreTile2.x(), Constants.oreTile2.y(), 0,
                1, 0, 3, 0 , 0, 0)};
        final Tile[] oreTileEnd = {botRandom.randTile(Constants.oreTileEnd.x(), Constants.oreTileEnd.y(), 0,
                4, 0, 0, 0 , 0, 0)};

        botRandom.ranInvSkillSwitch(2.3, 3000, 100);

        int energyLevel = ctx.movement.energyLevel();
        if(energyLevel >= 60) {
            ctx.movement.running(true);
        }


        if((Math.abs(hopBackTile[1].tile().x()-ctx.players.local().tile().x()) >= 6)) {
            // Walk to rockfall
            walker.walkPath(hopBackTile);
            Condition.wait(() -> !ctx.players.local().inMotion(), 1200, 10);

            botRandom.randCamera(0, 100, 10, -10, 10, -10, 2.1);

        }
        // If there mine them
        if(!oreTileEnd[0].matrix(ctx).reachable()) {
            GameObject rockfall2 = ctx.objects.select().at(new Tile(3733, 5680, 0)).name("Rockfall").poll();
            GameObject rockfall1 = ctx.objects.select().at(new Tile(3731, 5683, 0)).name("Rockfall").poll();

            // if(!Constants.betweenRocks.matrix(ctx).reachable() && hopBackTile[0].matrix(ctx).reachable()) {
            if(rockfall2.valid()
                    //rockfall2.id() == 26679
            ) {
                rockfall2.interact("Mine");
                Condition.wait(() -> ctx.players.local().animation() !=6746 && !ctx.players.local().inMotion());

                //GameObject finalRockfall = rockfall2;
                //Condition.sleep(1600);

                //rockfall2 = ctx.objects.select().at(new Tile(3733, 5680, 0)).name("Rockfall").poll();

            }
            System.out.println("Rockfall 2 mined");

       // }

        //if(Constants.betweenRocks.matrix(ctx).reachable() && !oreTileEnd[0].matrix(ctx).reachable()) {
            // If there mine them
            if (rockfall1.valid()
                    //rockfall1.id() == 26680
            ) {
                rockfall1.interact("Mine");
                Condition.wait(() -> ctx.players.local().animation() !=6746 && !ctx.players.local().inMotion());
                System.out.println(ctx.players.local().animation());

                //GameObject finalRockfall = rockfall1;
                //Condition.sleep(1000);

                System.out.println("Rockfall 1 mined");
                //rockfall1 = ctx.objects.select().at(new Tile(3731, 5683, 0)).name("Rockfall").poll();

            }
            botRandom.randCamera(0,100,10,-10,10,-10, 2.1);

            walker.walkPath(oreTileEnd);
            Condition.wait(() -> !ctx.players.local().inMotion(), 1200, 20);

       // }
    }


        // Walk to to mine
        else {
            walker.walkPath(oreTileEnd);
            //ctx.movement.newTilePath(oreTileEnd).traverse();
            Condition.wait(() -> !ctx.players.local().inMotion(), 1200, 20);
            botRandom.randCamera(0,100,10,-10,10,-10, 2.1);
            botRandom.randSleep(250000,  5000,  2.6);

        }
    }
}
