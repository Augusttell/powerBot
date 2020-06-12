package CombinationRuneCrafter;

import Common.BotRandom;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.Item;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class ReturnToBank extends Common.Task{
    BotRandom botRandom = new BotRandom(ctx);

    int fullRingID = 2552;
    int necklaceID = 5521;
    int ringSwitch;

    List<Integer> ringID = Arrays.asList(2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566);
    public static final Tile[]  bankTile = {new Tile(2441, 3083, 0 )};

    public ReturnToBank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        // If steam runes produced, activate
        return (!ctx.bank.present());
    }

    @Override
    public void execute() {
        // Randomly check stats
        botRandom.ranInvSkillSwitch(1.9, 3000, 100);

        System.out.println("Started returning to bank");

        // If a ring of dueling is in inventory, equip
        if(!ctx.inventory.select().id(fullRingID).isEmpty()) {
            ctx.inventory.select().id(fullRingID).poll().interact("Wear");
        }

        // Random behaviors
        botRandom.randCamera(0,100,2,-2,5,-5, 1.6);

        Item currentNecklace;
        Item currentRing;
        if(!ctx.bank.present()) {
            if(ctx.game.tab() != Game.Tab.EQUIPMENT) {
                ctx.game.tab(Game.Tab.EQUIPMENT);
            }
            currentRing = ctx.equipment.itemAt(Equipment.Slot.RING);
            currentNecklace = ctx.equipment.itemAt(Equipment.Slot.NECK);
            currentRing.interact("Castle Wars");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.bank.present();
                }
            }, 300, 10);

        }
        System.out.println("printing rings");

        currentRing = ctx.equipment.itemAt(Equipment.Slot.RING);
        currentNecklace = ctx.equipment.itemAt(Equipment.Slot.NECK);

        if(ringID.get(7)==currentRing.id()) {
            ringSwitch = 2;
        } else if(-1 ==currentRing.id()) {
            ringSwitch = 1;
        } else {
            ringSwitch = 0;
        }

            // Rand missclick
        botRandom.randMissClickDev(1.8,true,2,0,1,0);
        botRandom.randSleep(250000,  5000,  2.4);

        // If cant see bank
        if(!ctx.bank.inViewport()) {
            ctx.camera.turnTo(ctx.bank.nearest());
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.bank.inViewport();
                }
            }, 800, 10);
        }

        // Rand missclick
        //botRandom.randMissClickDev(1.8,true,2,0,1,0);

        System.out.println("Banking");
        ctx.bank.open();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.bank.opened();
            }
        }, 400, 10);

        // Long sleep
        //botRandom.randSleep(250000,  5000,  2.1);

        System.out.println(ringSwitch);

        // Withdraw ring of dueling if needed, empty or 1 charge left
        if(ringSwitch != 0 ) {
            ctx.bank.withdraw(fullRingID, 1);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                        return !ctx.inventory.select().id(fullRingID).isEmpty();
                }
            }, 100, 10);

            Item ring = ctx.inventory.select().id(fullRingID).poll();

            // If one charge left, do not equip instead equip later.
            if(ringSwitch == 1) {
                ring.interact("Wear");
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.equipment.itemAt(Equipment.Slot.RING).id() == fullRingID;
                    }
                }, 300, 10);
            }

            botRandom.randMissClickLastPress(1.67,true,2,0,1,0);

            System.out.println("Ring of dueling withdrawn and equipped");

        }

        // Withdraw Necklace
        if(ctx.bank.opened() && necklaceID != currentNecklace.id()) {
            ctx.bank.withdraw(necklaceID, 1);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.inventory.select().id(necklaceID).isEmpty();
                }
            }, 300, 10);

            Item necklace = ctx.inventory.select().id(necklaceID).poll();
            necklace.interact("Wear");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.inventory.select().id(necklaceID).isEmpty();
                }
            }, 300, 10);

            // Random missclick
            botRandom.randMissClickLastPress(1.67,true,2,0,1,0);

            botRandom.randSleep(250000,  5000,  2.4);


            System.out.println("Necklace withdrawn and equipped");

        }
    }
}
