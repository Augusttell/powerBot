package Woodcutter;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import Common.BotRandom;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

public class CutTrees extends Common.Task{
    private int [] oakTreeIds = {10820};
    private int [] willowTreeIds = {10819};

    BotRandom botRandom = new BotRandom(ctx);


    public CutTrees(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() < 28 &&
                !ctx.objects.select().id(willowTreeIds).isEmpty() &&
                ctx.players.local().animation() == -1;
        // if trees nearby and inventory not full  and not actively chopping
    }

    @Override
    public void execute() {
        GameObject tree = ctx.objects.nearest().poll();
        System.out.println("Cutting trees");

         if(tree.inViewport()) {
             tree.interact("Chop");
             Condition.wait(new Callable<Boolean>() {
                 @Override
                 public Boolean call() throws Exception {
                     return !ctx.players.local().inMotion();
                 }
             }, 800, 10);

             botRandom.ranInvSkillSwitch(1.9, 30000, 1000);
             botRandom.randMissClickLastPress(1.67,true,2,0,1,0);
             botRandom.randCamera(0,100,2,-2,5,-5, 1.2);


         } else {
            ctx.movement.step(tree);
             Condition.wait(new Callable<Boolean>() {
                 @Override
                 public Boolean call() throws Exception {
                     return !ctx.players.local().inMotion();
                 }
             }, 500, 100);


            botRandom.randMissClickLastPress(1.67,true,2,0,1,0);
            ctx.camera.turnTo(tree);
            botRandom.randCamera(0,100,2,-2,5,-5, 1.2);

        }

        if(ctx.game.tab() != Game.Tab.INVENTORY) {
            ctx.game.tab(Game.Tab.INVENTORY);
        }

        botRandom.randSleep(250000,  5000,  2.1);

    }
}

