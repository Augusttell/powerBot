package PayDirtMiner;

import CombinationRuneCrafter.Banking;
import CombinationRuneCrafter.ReturnToBank;
import CombinationRuneCrafter.WalkToAltar;
import Common.Skill;
import Common.Task;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.util.ArrayList;

@Script.Manifest(name = "PayDirtMiner", description = "Mines pay dirt")
//TODO add menus if below level 72
public class PayDirtMiner extends PollingScript implements PaintListener {
    ArrayList<Task> tasks = new ArrayList<>();
    int exp=0;
    Common.ExperienceTracker experienceTracker = new Common.ExperienceTracker((ClientContext) ctx);

    @Override
    public void start() {
        super.start();
        System.out.println("Script started");
        //System.out.println(ctx.input.speed());

        ctx.input.speed(50+ctx.input.speed());
        System.out.println(15+ctx.input.speed());

        WalkToOre walkToOre = new WalkToOre((ClientContext) ctx);
        MineOre mineOre = new MineOre((ClientContext) ctx);
        WalkToBank walkToBank = new WalkToBank((ClientContext) ctx);
        FixWheel fixWheel = new FixWheel((ClientContext) ctx);

        experienceTracker.startAll(); // Start tracking all skills

        tasks.add(walkToOre);
        tasks.add(mineOre);
        tasks.add(walkToBank);
        tasks.add(fixWheel);

    }
    @Override
    public void poll() {
        for(Common.Task T : tasks ){
            if(T.activate()) {
                T.execute();
            }
            exp = experienceTracker.gainedXPPerHour(Skill.MINING);
        }

    }

    // Graphics UI
    @Override
    public void repaint(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0, 180));
        graphics.fillRect(0, 0, 350, 100); // create new box, begin at 0,0 100 across 150 down

        graphics.setColor(new Color(255,255,255));
        graphics.drawRect(0, 0, 350, 100); // Make outline
        graphics.drawString("Mining exp gained: " + exp, 20, 20);
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
