package PayDirtMiner;

import Common.BotRandom;
import com.sun.org.apache.bcel.internal.Const;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

public class MineOre extends Common.Task{
    BotRandom botRandom = new BotRandom(ctx);

    public MineOre(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() < 28 &&
                Constants.miningArea.contains(ctx.players.local());
    }

    @Override
    public void execute() {
        GameObject ore = ctx.objects.select().id(Constants.payDirtOreVein).within(Constants.miningArea).nearest().poll();

        if(ore.inViewport() &&
                ctx.players.local().animation() !=6752 &&
                !ctx.players.local().inMotion() &&
                ctx.players.local().animation()==-1) {
            botRandom.randTurnTo(ore, 1.5, 3);

            ore.interact("Mine");
            Condition.wait(() -> (ctx.players.local().animation()==-1 ||
                    ctx.players.local().animation()==6752 ||
                    !ctx.players.local().inMotion()), 200, 20);

            botRandom.ranInvSkillSwitch(2.3, 3000, 100);
            botRandom.randMissClickLastPress(2.3,true,2,0,1,0);
            botRandom.randCamera(0,100,2,-2,5,-5, 1.2);

            if(ctx.game.tab() != Game.Tab.INVENTORY) {
                ctx.game.tab(Game.Tab.INVENTORY);
            }

            botRandom.randSleep(250000,  5000,  2.8);

        }

    }
}
