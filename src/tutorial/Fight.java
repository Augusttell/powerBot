package tutorial;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Npc;
import java.lang.Math;


public class Fight extends Task{
    int[] goblinIDs = {3032, 3029, 3033, 3034, 3033};

    public Fight(ClientContext ctx) {
        super(ctx);
    }


    // condition for fighting / activate fighting
    @Override
    public boolean activate() {

        double healthPercent = (double) ctx.skills.level(Constants.SKILLS_HITPOINTS)/ (double) ctx.skills.realLevel(Constants.SKILLS_HITPOINTS);

        return ctx.players.local().healthBarVisible() == false &&
                healthPercent >= 0.35 &&
                !ctx.players.local().interacting().valid();
    }

    // If condition holds do the following
    @Override
    public void execute() {
        Filter<Npc> filter = new Filter<Npc>(){ // This filter works by effectively going through our list,
            // and doing something we code for every goblin
            @Override // This filter checks if goblin is fighting some1
            public boolean accept(Npc npc) {
                boolean fighting = npc.healthBarVisible();

                return !fighting;
            }
        };

        Npc goblin = ctx.npcs.select().id(goblinIDs).nearest().select(filter).poll(); // Select is used for getting all
        // refresehed targets, poll is basicailly a put, fetch from list

        int random_int_camera = (int) Math.random() * (20 - 5 + 1) + 20;
        if(goblin.inViewport()){ // If we can see goblin
            int random_int_delay = (int) Math.random() * (3500 - 1500 + 1) + 3500;
            ctx.camera.turnTo(goblin, random_int_camera);
            goblin.interact("Attack"); // attack it
            Condition.sleep(random_int_delay); // add a random delay to tricky evil boys

        } else {
            ctx.camera.turnTo(goblin, random_int_camera);

        }

    }
}
