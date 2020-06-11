package tutorial;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext; // Import OSRSA client/engine selected this is chosen for initialization


import java.awt.*;
import java.util.ArrayList;



@Script.Manifest(name = "GoblinKiller", description = "Tutorial")

public class GoblinKiller extends PollingScript<ClientContext> implements PaintListener
{



    ArrayList<Task> tasks = new ArrayList<Task>();
    int exp=0;
    int gold=0;
    ExperienceTracker experienceTracker = new ExperienceTracker(ctx);
    //SProfitTracker sProfitTracker = new SProfitTracker();
    // Create object of class fight to be used in poll loop
    // Guess you could initiate everything in this yes?
    @Override
    public void start() {
        super.start();
        System.out.println("Script started");
        Fight fight = new Fight(ctx); // Create fight object
        Looting looting = new Looting(ctx); // Create loot object

        tasks.add(fight); // Add fight object to task list
        tasks.add(looting); // Add object to task list

        experienceTracker.startAll(); // Start tracking all skills

    }


    // Main loop of program
    // Go through task list
    @Override
    public void poll() {
        //experienceTracker.startAll(true);
        for(Task T : tasks ){
            if(T.activate()) {
                T.execute();
            }
        }
        exp = experienceTracker.gainedXPPerHour(Skill.ATTACK);

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


    // Graphics UI
    @Override
    public void repaint(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0, 180));
        graphics.fillRect(0, 0, 350, 100); // create new box, begin at 0,0 100 across 150 down

        graphics.setColor(new Color(255,255,255));
        graphics.drawRect(0, 0, 350, 100); // Make outline
        graphics.drawString("Goblin Killer: exp gained: " + exp, 20, 20);
    }

}


