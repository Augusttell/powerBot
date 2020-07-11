package BlastFurnace;

import Common.BotRandom;
import Common.Walker;
import com.sun.org.apache.bcel.internal.Const;
import org.powerbot.script.*;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.ClientContext;

import java.util.Timer;
import java.awt.*;
import java.util.concurrent.Callable;

import static BlastFurnace.Constants.*;

@Script.Manifest(name = "BlastFurnace", description = "Uses the blast furnace to create bars")


public class BlastFurnace extends PollingScript<ClientContext> implements PaintListener {
    BotRandom botRandom = new BotRandom(ctx);
    Walker walker = new Walker(ctx);
    Common.ExperienceTracker experienceTracker = new Common.ExperienceTracker( ctx);
    //Timer timer = new Timer();
    int rounds = 0;


    @Override
    public void start() {
        super.start();
        System.out.println("Script started");
       //ctx.input.speed(50+ctx.input.speed());

        experienceTracker.startAll(); // Start tracking all skills

    }
    @Override
    public void poll() {
        final Tile[] bankTileRand = {botRandom.randTile(Constants.bankTile.x(), Constants.bankTile.y(), 0,
                2, 0, 0, 0 , 0, 0)};


        switch (state()) {
            case withdrawOres:
                System.out.println("withdrawOres");

                // if bank is far away, walk there.
                if((Math.abs(Constants.bankTile.x()-ctx.players.local().tile().x()) >= 10)) {
                    walker.walkPath(bankTileRand);
                    Condition.wait(() -> !ctx.players.local().inMotion());
                    System.out.println("1");
                }

                //If bank isnt open, Open bank & withdraw
                if(!ctx.bank.opened()) {
                    ctx.bank.open();
                    Condition.wait(() -> ctx.bank.opened());
                    System.out.println("2");
                }
                if(!ctx.inventory.select().id(staminaPotions[0]).isEmpty()){ctx.bank.deposit(staminaPotions[0], Bank.Amount.ALL);}
                if(!ctx.inventory.select().id(staminaPotions[1]).isEmpty()){ctx.bank.deposit(staminaPotions[1], Bank.Amount.ALL);}
                if(!ctx.inventory.select().id(staminaPotions[2]).isEmpty()){ctx.bank.deposit(staminaPotions[2], Bank.Amount.ALL);}
                if(!ctx.inventory.select().id(staminaPotions[3]).isEmpty()){ctx.bank.deposit(staminaPotions[3], Bank.Amount.ALL);}
                if(!ctx.inventory.select().id(emptyVial).isEmpty()){ctx.bank.deposit(emptyVial, Bank.Amount.ALL);}

                System.out.println("3");
                ctx.bank.withdraw(coalOreID, 27);
                Condition.wait(() -> !ctx.inventory.select().id(coalOreID).isEmpty(),
                        botRandom.randInt(600,100), 4);
                //ctx.bank.close();
                ctx.input.send("{VK_ESCAPE}");
                Condition.wait(() -> !ctx.bank.opened(), botRandom.randInt(600,100), 2);

                if(ctx.game.tab() != Game.Tab.INVENTORY) {
                    ctx.game.tab(Game.Tab.INVENTORY);
                }
                ctx.inventory.select().id(coalBagID).poll().interact("Fill");
                  // Condition.wait(() -> ctx.inventory.select().id(Constants.coalOreID).isEmpty());

                ctx.bank.open();
                Condition.wait(() -> !ctx.bank.opened(), botRandom.randInt(600,100), 2);

                if(!ctx.inventory.select().id(coalOreID).isEmpty()) {
                    ctx.bank.deposit(coalOreID, Bank.Amount.ALL);
                }

                int energyLevel = ctx.movement.energyLevel();
                if(energyLevel <= 60) {
                    switch (stamState()) {
                        case pot1:
                            ctx.bank.withdraw(staminaPotions[0], Bank.Amount.ONE);
                            Condition.wait(() -> !ctx.inventory.select().id(staminaPotions[0]).isEmpty(),
                                    botRandom.randInt(500,200), 4);
                            ctx.inventory.select().id(staminaPotions[0]).poll().interact("Drink");
                            Condition.wait(() -> ctx.inventory.select().id(staminaPotions[0]).isEmpty(),
                                    botRandom.randInt(500,200), 4);
                            ctx.bank.deposit(staminaPotions[2], Bank.Amount.ALL);
                            ctx.bank.deposit(staminaPotions[1], Bank.Amount.ALL);
                            ctx.bank.deposit(staminaPotions[0], Bank.Amount.ALL);
                            ctx.bank.deposit(emptyVial, Bank.Amount.ALL);
                            Condition.wait(() -> ctx.inventory.id(emptyVial).isEmpty(), 50, 100);

                            break;

                        case pot2:
                            ctx.bank.withdraw(staminaPotions[1], Bank.Amount.ONE);
                            Condition.wait(() -> !ctx.inventory.select().id(staminaPotions[1]).isEmpty(),
                                    botRandom.randInt(500,200), 4);
                            ctx.inventory.select().id(staminaPotions[1]).poll().interact("Drink");
                            Condition.wait(() -> ctx.inventory.select().id(staminaPotions[1]).isEmpty(),
                                    botRandom.randInt(500,200), 4);
                            ctx.bank.deposit(staminaPotions[2], Bank.Amount.ALL);
                            ctx.bank.deposit(staminaPotions[1], Bank.Amount.ALL);
                            ctx.bank.deposit(staminaPotions[0], Bank.Amount.ALL);
                            ctx.bank.deposit(emptyVial, Bank.Amount.ALL);
                            Condition.wait(() -> ctx.inventory.id(staminaPotions[0]).isEmpty(), 50, 100);
                            break;

                        case pot3:
                            ctx.bank.withdraw(staminaPotions[2], Bank.Amount.ONE);
                            Condition.wait(() -> !ctx.inventory.select().id(staminaPotions[2]).isEmpty(),
                                    botRandom.randInt(500,200), 4);
                            ctx.inventory.select().id(staminaPotions[2]).poll().interact("Drink");
                            Condition.wait(() -> ctx.inventory.select().id(staminaPotions[2]).isEmpty(),
                                    botRandom.randInt(500,200), 4);
                            ctx.bank.deposit(staminaPotions[2], Bank.Amount.ALL);
                            ctx.bank.deposit(staminaPotions[1], Bank.Amount.ALL);
                            ctx.bank.deposit(staminaPotions[0], Bank.Amount.ALL);
                            ctx.bank.deposit(emptyVial, Bank.Amount.ALL);
                            Condition.wait(() -> ctx.inventory.id(staminaPotions[1]).isEmpty(), 50, 100);

                        case pot4:
                            ctx.bank.withdraw(staminaPotions[3], Bank.Amount.ONE);
                            Condition.wait(() -> !ctx.inventory.select().id(staminaPotions[3]).isEmpty(),
                                    botRandom.randInt(500,200), 4);
                            ctx.inventory.select().id(staminaPotions[3]).poll().interact("Drink");
                            Condition.wait(() -> ctx.inventory.select().id(staminaPotions[3]).isEmpty(),
                                    botRandom.randInt(500,200), 4);
                            ctx.bank.deposit(staminaPotions[2], Bank.Amount.ALL);
                            ctx.bank.deposit(staminaPotions[1], Bank.Amount.ALL);
                            ctx.bank.deposit(staminaPotions[0], Bank.Amount.ALL);
                            ctx.bank.deposit(emptyVial, Bank.Amount.ALL);
                            Condition.wait(() -> ctx.inventory.id(staminaPotions[2]).isEmpty(), 50, 100);

                            break;

                        case noPot:
                            break;
                    }
                }

                ctx.bank.withdraw(coalOreID, Bank.Amount.ALL);
                //Condition.wait(() -> !ctx.inventory.select().id(coalOreID).isEmpty(),
                //        botRandom.randInt(600,100), 4);

                ctx.input.send("{VK_ESCAPE}");
                //Condition.wait(() -> !ctx.bank.opened());

                break;

            case deliverOre:
               /* System.out.println("deliverOre");
                GameObject conveyerBelt = ctx.objects.select().id(conveyerBeltID).name("Conveyor belt").poll();
                conveyerBelt.bounds(-36,36,-192,-260,-44,28);
                Item coalBag = ctx.inventory.select().id(coalBagID).poll();

                // If conveyerbelt not viewable walk there
                if(!conveyerBelt.inViewport()) {
                    System.out.println("1");

                    walker.walkPath(bankTileRand);
                    Condition.wait(() -> !ctx.players.local().inMotion());

                }

                // If we have iron ore in bag, produce bars.
                if(!ctx.inventory.select().id(ironOreID).isEmpty() || !ctx.inventory.select().id(coalOreID).isEmpty() ) {
                    System.out.println("2");
                    ////conveyerBelt.click(true);
                    conveyerBelt.interact("Put-ore-on");
                    Condition.wait(() -> ctx.inventory.select().id(ironOreID).isEmpty(),
                            botRandom.randInt(4000,3000), 3);

                    ctx.input.send("{VK_SHIFT down}");
                    coalBag.click(true);
                    ctx.input.send("{VK_SHIFT up}");
                    //coalBag.interact("Empty");
                    Condition.wait(() -> ctx.inventory.select().id(coalOreID).isEmpty(),
                            botRandom.randInt(600,100), 2);

                    if(ctx.movement.running() == false)  {ctx.movement.running(true);}
                    conveyerBelt.click(true);
                    //conveyerBelt.interact("Put-ore-on");
                    Condition.wait(() -> ctx.inventory.select().id(coalOreID).isEmpty(),
                            botRandom.randInt(1200,800), 2);

                }*/

                System.out.println("deliverOre");
                GameObject conveyerBelt = ctx.objects.select().id(conveyerBeltID).name("Conveyor belt").poll();
                conveyerBelt.bounds(-36,36,-208,-260,-44,16);
                Item coalBag = ctx.inventory.select().id(coalBagID).poll();

                // If conveyerbelt not viewable walk there
                if(!conveyerBelt.inViewport()) {
                    System.out.println("1");

                    walker.walkPath(bankTileRand);
                    Condition.wait(() -> !ctx.players.local().inMotion());

                }

                // If we have iron ore in bag, produce bars.
                if(!ctx.inventory.select().id(adamOreID).isEmpty() || !ctx.inventory.select().id(coalOreID).isEmpty() ) {
                    System.out.println("2");
                    while(!ctx.inventory.select().id(adamOreID).isEmpty() ||
                            !ctx.inventory.select().id(coalOreID).isEmpty()) {

                        conveyerBelt.interact("Put-ore-on");
                        Condition.wait(() -> ctx.inventory.select().id(coalOreID).isEmpty(),
                                botRandom.randInt(7000, 6000), 2);


                        ctx.input.send("{VK_SHIFT down}");
                        coalBag.click(true);
                        ctx.input.send("{VK_SHIFT up}");

                        //conveyerBelt.click(true);
                        conveyerBelt.interact("Put-ore-on");
                        Condition.wait(() -> ctx.inventory.select().id(coalOreID).isEmpty(),
                                botRandom.randInt(600, 100), 3);

                        if (ctx.movement.running() == false) {
                            ctx.movement.running(true);
                        }
                    }

                    ctx.bank.open();
                    Condition.wait(() -> ctx.bank.opened(),
                            botRandom.randInt(800,600), 2);

                    ctx.bank.withdraw(coalOreID, 27);
                    Condition.wait(() -> !ctx.inventory.select().id(coalOreID).isEmpty(),
                            botRandom.randInt(600,100), 4);

                    ctx.input.send("{VK_ESCAPE}");
                    Condition.wait(() -> !ctx.bank.opened(), botRandom.randInt(600,100), 2);

                    if(ctx.game.tab() != Game.Tab.INVENTORY) {
                        ctx.game.tab(Game.Tab.INVENTORY);
                    }

                    ctx.inventory.select().id(coalBagID).poll().interact("Fill");

                    ctx.bank.open();
                    Condition.wait(() -> !ctx.bank.opened(), botRandom.randInt(600,100), 2);

                    if(!ctx.inventory.select().id(coalOreID).isEmpty()) {
                        ctx.bank.deposit(coalOreID, Bank.Amount.ALL);
                    }

                    energyLevel = ctx.movement.energyLevel();
                    if(energyLevel <= 60) {
                        switch (stamState()) {
                            case pot1:
                                ctx.bank.withdraw(staminaPotions[0], Bank.Amount.ONE);
                                Condition.wait(() -> !ctx.inventory.select().id(staminaPotions[0]).isEmpty(),
                                        botRandom.randInt(500,200), 4);
                                ctx.inventory.select().id(staminaPotions[0]).poll().interact("Drink");
                                Condition.wait(() -> ctx.inventory.select().id(staminaPotions[0]).isEmpty(),
                                        botRandom.randInt(500,200), 4);
                                ctx.bank.deposit(staminaPotions[2], Bank.Amount.ALL);
                                ctx.bank.deposit(staminaPotions[1], Bank.Amount.ALL);
                                ctx.bank.deposit(staminaPotions[0], Bank.Amount.ALL);
                                ctx.bank.deposit(emptyVial, Bank.Amount.ALL);
                                Condition.wait(() -> ctx.inventory.id(emptyVial).isEmpty(), 50, 100);

                                break;

                            case pot2:
                                ctx.bank.withdraw(staminaPotions[1], Bank.Amount.ONE);
                                Condition.wait(() -> !ctx.inventory.select().id(staminaPotions[1]).isEmpty(),
                                        botRandom.randInt(500,200), 4);
                                ctx.inventory.select().id(staminaPotions[1]).poll().interact("Drink");
                                Condition.wait(() -> ctx.inventory.select().id(staminaPotions[1]).isEmpty(),
                                        botRandom.randInt(500,200), 4);
                                ctx.bank.deposit(staminaPotions[2], Bank.Amount.ALL);
                                ctx.bank.deposit(staminaPotions[1], Bank.Amount.ALL);
                                ctx.bank.deposit(staminaPotions[0], Bank.Amount.ALL);
                                ctx.bank.deposit(emptyVial, Bank.Amount.ALL);
                                Condition.wait(() -> ctx.inventory.id(staminaPotions[0]).isEmpty(), 50, 100);
                                break;

                            case pot3:
                                ctx.bank.withdraw(staminaPotions[2], Bank.Amount.ONE);
                                Condition.wait(() -> !ctx.inventory.select().id(staminaPotions[2]).isEmpty(),
                                        botRandom.randInt(500,200), 4);
                                ctx.inventory.select().id(staminaPotions[2]).poll().interact("Drink");
                                Condition.wait(() -> ctx.inventory.select().id(staminaPotions[2]).isEmpty(),
                                        botRandom.randInt(500,200), 4);
                                ctx.bank.deposit(staminaPotions[2], Bank.Amount.ALL);
                                ctx.bank.deposit(staminaPotions[1], Bank.Amount.ALL);
                                ctx.bank.deposit(staminaPotions[0], Bank.Amount.ALL);
                                ctx.bank.deposit(emptyVial, Bank.Amount.ALL);
                                Condition.wait(() -> ctx.inventory.id(staminaPotions[1]).isEmpty(), 50, 100);

                            case pot4:
                                ctx.bank.withdraw(staminaPotions[3], Bank.Amount.ONE);
                                Condition.wait(() -> !ctx.inventory.select().id(staminaPotions[3]).isEmpty(),
                                        botRandom.randInt(500,200), 4);
                                ctx.inventory.select().id(staminaPotions[3]).poll().interact("Drink");
                                Condition.wait(() -> ctx.inventory.select().id(staminaPotions[3]).isEmpty(),
                                        botRandom.randInt(500,200), 4);
                                ctx.bank.deposit(staminaPotions[2], Bank.Amount.ALL);
                                ctx.bank.deposit(staminaPotions[1], Bank.Amount.ALL);
                                ctx.bank.deposit(staminaPotions[0], Bank.Amount.ALL);
                                ctx.bank.deposit(emptyVial, Bank.Amount.ALL);
                                Condition.wait(() -> ctx.inventory.id(staminaPotions[2]).isEmpty(), 50, 100);

                                break;

                            case noPot:
                                break;
                        }
                    }
                    ctx.bank.withdraw(adamOreID, Bank.Amount.ALL);

                    ctx.input.send("{VK_ESCAPE}");

                    while(!ctx.inventory.select().id(adamOreID).isEmpty() ||
                            !ctx.inventory.select().id(coalOreID).isEmpty()) {
                        conveyerBelt.interact("Put-ore-on");
                        Condition.wait(() -> ctx.inventory.select().id(adamOreID).isEmpty(),
                                botRandom.randInt(7000,6000), 2);

                            ctx.input.send("{VK_SHIFT down}");
                            coalBag.click(true);
                            ctx.input.send("{VK_SHIFT up}");


                        //conveyerBelt.click(true);
                        conveyerBelt.interact("Put-ore-on");
                        Condition.wait(() -> ctx.inventory.select().id(coalOreID).isEmpty(),
                                botRandom.randInt(600,100), 3);
                    }


                }

                break;


            case collectBars:
                System.out.println("collectBars");

                GameObject barDispenserFull = ctx.objects.select().name("Bar dispenser").poll();
                barDispenserFull.bounds(-108,-16,-132,-84,-40,40);

                // If we have iron ore in bag, produce bars.
                if(ctx.inventory.select().id(adamBarID).isEmpty()) {
                    System.out.println("1");

                    //walker.walkPath(Constants.barDispenserTile);
                    barDispenserTile[0].tile().matrix(ctx).click(true);
                    Condition.wait(() -> !ctx.players.local().inMotion());

                    barDispenserFull.click(true);
                    Condition.sleep(botRandom.randInt(900,600));
                    ctx.input.send(" ");
                    Condition.wait(() -> !ctx.inventory.select().id(adamBarID).isEmpty(),
                            botRandom.randInt(600,100), 2);

                    barDispenserFull = ctx.objects.select().id(barDispenserID).poll();



                }

                while(!ctx.inventory.select().id(adamBarID).isEmpty()) {
                    ctx.bank.open();
                    Condition.wait(() -> ctx.bank.opened(),
                            botRandom.randInt(800,600), 2);

                    ctx.bank.deposit(adamBarID, Bank.Amount.ALL);
                    Condition.wait(() -> ctx.inventory.select().id(adamBarID).isEmpty(),
                            botRandom.randInt(600,100), 3
                    );

//                    if(!ctx.inventory.select().id(steelBarID).isEmpty()) {
//                        ctx.bank.deposit(ironBarID, Bank.Amount.ALL);
//                    }

                    rounds =+1;

                }

                break;

            case withdrawCoalBag:
                System.out.println("withdrawCoalBag");

                // if bank is far away, walk there.
                if((Math.abs(Constants.bankTile.x()-ctx.players.local().tile().x()) >= 10)) {
                    walker.walkPath(bankTileRand);
                    Condition.wait(() -> !ctx.players.local().inMotion());

                }

                //If bank isnt open, Open bank
                if(!ctx.bank.opened()) {
                    ctx.bank.open();
                    Condition.wait(() -> ctx.bank.opened());
                }

                //If bank is open, withdraw coal bag
                if(ctx.bank.opened()) {
                    ctx.bank.withdraw(coalBagID, 1);
                    Condition.wait(() -> !ctx.inventory.select().id(coalBagID).isEmpty());
                }

                break;
            case error:
                System.out.println("Error");
                walker.walkPath(bankTileRand);
                Condition.wait(() -> !ctx.players.local().inMotion());

                break;

        }


    }
    private State state() {
        if(!ctx.inventory.select().id(coalBagID).isEmpty() &&
                ctx.inventory.select().id(adamBarID).isEmpty() &&
                //ctx.inventory.select().id(steelBarID).isEmpty() &&
                ctx.inventory.select().id(coalOreID).isEmpty() &&
                // ctx.inventory.select().id(ironOreID).isEmpty() &&
                ctx.inventory.select().id(adamOreID).isEmpty() &&
                (Math.abs(barDispenserTile[0].tile().x()-ctx.players.local().tile().x()) > 4)) {
            return State.withdrawOres;

        } else if(!ctx.inventory.select().id(coalBagID).isEmpty() &&
                ctx.inventory.select().id(adamBarID).isEmpty() &&
                //ctx.inventory.select().id(steelBarID).isEmpty() &&
                (!ctx.inventory.select().id(coalOreID).isEmpty() ||
                 // ctx.inventory.select().id(ironOreID).isEmpty() &&
                        !ctx.inventory.select().id(adamOreID).isEmpty()) &&
                (Math.abs(barDispenserTile[0].tile().x()-ctx.players.local().tile().x()) > 4)) {
            return State.deliverOre;

        } else if(!ctx.inventory.select().id(coalBagID).isEmpty() &&
                ctx.inventory.select().id(coalOreID).isEmpty() &&
                // ctx.inventory.select().id(ironOreID).isEmpty() &&
                ctx.inventory.select().id(adamOreID).isEmpty() &&
                (Math.abs(barDispenserTile[0].tile().x()-ctx.players.local().tile().x()) <= 4)) {
            return State.collectBars;

        } else if(ctx.inventory.select().id(coalBagID).isEmpty()){ // If no coal bag in inventory !
            return State.withdrawCoalBag;

        } else {
            return State.error;
        }
//        else if(2==3) { // If no coal bag in inventory !
//        return State.putMoneyInCoffer;
//
//        }
    }

    private enum State {
        withdrawOres, deliverOre, collectBars, withdrawCoalBag, error //noPot//, putMoneyInCoffer

    }

    private enum StamState {
        pot1, pot2, pot3, pot4, noPot

    }
    private StamState stamState() {
        if(!ctx.bank.select().id(staminaPotions[0]).isEmpty()) {
            return StamState.pot1;
        } else if(!ctx.bank.select().id(staminaPotions[1]).isEmpty()
                //&&ctx.bank.select().id(staminaPotions[0]).isEmpty()
        //
        ) {
            return StamState.pot2;
        } else if(!ctx.bank.select().id(staminaPotions[2]).isEmpty()
   // &&
             //   ctx.bank.select().id(staminaPotions[0]).isEmpty() &&
             //   ctx.bank.select().id(staminaPotions[1]).isEmpty()
    ) {
            return StamState.pot3;
        } else if(!ctx.bank.select().id(staminaPotions[3]).isEmpty()
            //    && ctx.bank.select().id(staminaPotions[0]).isEmpty() &&
               // ctx.bank.select().id(staminaPotions[1]).isEmpty() &&
               // ctx.bank.select().id(staminaPotions[2]).isEmpty()
        ) {
            return StamState.pot4;
        } else {
            return StamState.noPot;
        }
    }

    // Graphics UI
    @Override
    public void repaint(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0, 180));
        graphics.fillRect(0, 0, 350, 100); // create new box, begin at 0,0 100 across 150 down

        graphics.setColor(new Color(255,255,255));
        graphics.drawRect(0, 0, 350, 100); // Make outline
        graphics.drawString("Smithing exp gained: ", 20, 20);
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
