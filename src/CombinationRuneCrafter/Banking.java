package CombinationRuneCrafter;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;

public class Banking extends Task{
    int airTalisman = 1438;
    int airRunes = 556;
    int pureEss = 7936;
    int smokeRune = 4697;
    BotRandom botRandom = new BotRandom(ctx);

    public Banking(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        // If bank open and inv not full of runes, bank
        return ctx.bank.opened();
    }

    @Override
    public void execute() {

        if(ctx.bank.opened() && !ctx.inventory.select().id(smokeRune).isEmpty()) {
            System.out.println("Started Depositing runes");

            botRandom.randMissClickLastPress(1.67,true,2,0,1,0);

            ctx.bank.deposit(smokeRune, Bank.Amount.ALL);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.inventory.id(smokeRune).isEmpty();
                }
            }, 50, 100);

            // Random long sleep
            botRandom.randSleep(250000,  5000,  2.4);
            System.out.println("Runes deposited");
        }

        System.out.println("Started withdrawing runes and talisman");

        // Withdraw Air talismans
        if(ctx.inventory.select().id(airTalisman).isEmpty()) {
            ctx.bank.withdraw(airTalisman, 1);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.inventory.id(airTalisman).isEmpty();
                }
            }, 5, 100);

            System.out.println("Talisman withdrawn");

        }

        // Random short sleep
        botRandom.randSleep(250000,  5000,  2.4);

        if(ctx.inventory.select().id(airRunes).isEmpty()) {

            botRandom.randMissClickLastPress(1.67,true,2,0,1,0);

            ctx.bank.withdraw(airRunes, Bank.Amount.ALL);
            Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !ctx.inventory.id(airRunes).isEmpty();
            }
            }, 5, 100);
            System.out.println("Air runes withdrawn");
            botRandom.randSleep(250000,  5000,  2.4);

        }

        if(ctx.inventory.select().id(pureEss).isEmpty()) {

            botRandom.randMissClickLastPress(1.67,true,2,0,1,0);

            ctx.bank.withdraw(pureEss, Bank.Amount.ALL);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                return !ctx.inventory.id(pureEss).isEmpty();
            }
            }, 5, 100);
            System.out.println("Pure essence withdrawn");

            botRandom.randSleep(250000,  5000,  2.4);

        }

        System.out.println("Runecrafting items withdrawn");
        System.out.println("Closing Bank");

        ctx.bank.close();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !ctx.bank.opened();
            }
        }, 50, 100);

        // Random long sleep
        botRandom.randSleep(250000,  5000,  2.4);

    }
}
