package CombinationRuneCrafter;

import Common.BotRandom;
import Common.Walker;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

import java.util.concurrent.Callable;

public class WalkToAltar extends Common.Task{
    Walker walker = new Walker(ctx);
    BotRandom botRandom = new BotRandom(ctx);

    int airTalisman = 1438;
    int airRunes = 556;
    int pureEss = 7936;
    int smokeRune = 4697;

    public WalkToAltar(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        // If all needed crafting items are present
        return !ctx.inventory.select().id(airTalisman).isEmpty() &&
                !ctx.inventory.select().id(pureEss).isEmpty() &&
                !ctx.inventory.select().id(airRunes).isEmpty();
    }
    @Override
    public void execute() {
        //TODO turn this in to a switch
        // Initated here due to randomness
        final Tile[] ruinsTile = {botRandom.randTile(3308,3245,0, 2, 0, 1, 0 , 0, 0)};
        final Tile[] altarTile = {botRandom.randTile(2585  , 4840, 0, 2, 0, 0, 0 , 0, 0)};

        System.out.println("Started walking to altar");

        // Random camera behavior
        botRandom.randCamera(0,100,2,-2,5,-5, 1.6);

        // TP to duel arena if not there
        if((Math.abs(ctx.players.local().tile().x() - ruinsTile[0].x()) >= 50) &&
                (Math.abs(ctx.players.local().tile().x()-altarTile[0].tile().x()) >= 5)) {
            ctx.game.tab(Game.Tab.EQUIPMENT);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.game.tab() == Game.Tab.EQUIPMENT;
                }
            }, 50, 100);

            ctx.equipment.itemAt(Equipment.Slot.RING).interact("Duel Arena");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.bank.present();
                }
            }, 5, 100);
        }

        // Random camera
        botRandom.ranInvSkillSwitch(1.9, 3000, 100);
        botRandom.randCamera(0,100,2,-2,5,-5, 1.2);

        // check energy and toggle if above
        int energyLevel = ctx.movement.energyLevel();
        if(energyLevel >= 60) {
            ctx.movement.running(true);
        }

        // Path to portal
        walker.walkPath(ruinsTile);
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.objects.select().name("Mysterious ruins").poll().inViewport();
            }
        }, 50, 4000);

        GameObject ruins = ctx.objects.select().name("Mysterious ruins").poll();
        // Click portal if viewable
        ruins.interact("Enter");
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                    return (Math.abs(altarTile[0].tile().x()-ctx.players.local().tile().x()) <=15);
                }
                }, 80, 1000);

        System.out.println("Entering ruins");

        // Move towards altar
        walker.walkPath(altarTile);
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return //!ctx.objects.select().name("Altar").poll().inViewport();
                Math.abs(altarTile[0].tile().x()-ctx.players.local().tile().x()) <=3;
            }
        }, 20, 4000);


        GameObject altar = ctx.objects.select().name("Altar").poll();

        // Use talisman on altar / craft runes
        if(ctx.game.tab() != Game.Tab.INVENTORY) {
            ctx.game.tab(Game.Tab.INVENTORY);
        }

        Item airTali = ctx.inventory.select().id(airTalisman).poll();

        while(altar.inViewport() && ctx.inventory.select().id(smokeRune).isEmpty()) {
            airTali.interact("Use");
            altar.interact("Use");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.inventory.select().id(smokeRune).isEmpty();
                }
            }, 60, 100);
        }

        System.out.println("Finished crafting runes");

        }
}






