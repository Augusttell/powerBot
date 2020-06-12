package CombinationRuneCrafter;

import Common.Skill;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.util.ArrayList;


@Script.Manifest(name = "CombinationRuneCrafter", description = "Make combination runes")


public class CombinationRuneCrafter extends PollingScript implements PaintListener {
    ArrayList<Common.Task> tasks = new ArrayList<>();
    int exp=0;
    Common.ExperienceTracker experienceTracker = new Common.ExperienceTracker((ClientContext) ctx);

    @Override
    public void start() {
        super.start();
        System.out.println("Script started");
        //System.out.println(ctx.input.speed());

        ctx.input.speed(25+ctx.input.speed());
        System.out.println(15+ctx.input.speed());
        Banking banking = new Banking((ClientContext) ctx);
        ReturnToBank returnToBank = new ReturnToBank((ClientContext) ctx);
        WalkToAltar walkToAlter = new WalkToAltar((ClientContext) ctx);
        experienceTracker.startAll(); // Start tracking all skills

        tasks.add(returnToBank);
        tasks.add(banking);
        tasks.add(walkToAlter);

    }
    @Override
    public void poll() {
        for(Common.Task T : tasks ){
            if(T.activate()) {
                T.execute();
            }
            exp = experienceTracker.gainedXPPerHour(Skill.RUNECRAFTING);
        }

    }

    // Graphics UI
    @Override
    public void repaint(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0, 180));
        graphics.fillRect(0, 0, 350, 100); // create new box, begin at 0,0 100 across 150 down

        graphics.setColor(new Color(255,255,255));
        graphics.drawRect(0, 0, 350, 100); // Make outline
        graphics.drawString("Runecrafting exp gained: " + exp, 20, 20);
    }

    // What happeneds when we suspend
    @Override
    public void suspend() {
        super.suspend();
        System.out.println("Script suspended");
    }

    // What happeneds when we resume
    @Override
    public void resume() {
        super.resume();
        System.out.println("Script resumed");
    }


    @Override
    public void stop() {
        super.stop();
        System.out.println("Script stopped");
        System.out.println(super.getTotalRuntime());
    }

}
