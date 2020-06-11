package BridBot.task.actiontasker;

import BridBot.BridBot;
import BridBot.task.TaskScheduler;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;

/*
Also tasks which are 'BackgroundTasker', which don't need to have a priority and always keep running no matter what. They don't have an activate() or execute() loop.


Current tasks:
AttackPlayerTask -> ActionTasker
ConsumablesTask -> ActionTasker
LastAttackTimerTask -> BackgroundTasker
MovePlayerAroundTask -> ActionTasker
SetTargetTask -> BackgroundTasker
SwitchOffensiveGearTask -> ActionTasker
SwitchPrayerTask -> ActionTasker

The TaskScheduler can only execute one ActionTasker at a time, whereas a Background tasker can run in parallel to all other tasks respectively.

E.g a BridBot will have:

EventWatcherTasker which is a BackgroundTasker running all the time collecting/showing information for the system.

And it will also have tasks like eating food, moving, attacking, and praying but those are actiontasker tasks and only one can be done at a time. Though, to activate them correctly it needs up to date information collected from a backgroundtasker thread. The activate() functions of all threads will run in the backgroundtasker at all times, it's just the execute() functions of the ActionTaskers which can only be run once at a time.

 */
public abstract class ActionTasker extends ClientAccessor implements Runnable, Comparable<ActionTasker> {

    public boolean execution_required = false;
    public int PRIORITY = 10;
    public long timeout = -1;

    public ActionTasker(ClientContext ctx)
    {
        super(ctx);
    }

    @Override
    public int compareTo(ActionTasker t)
    {
        if (this == t)
        {
            return 0;
        }

        if (t == null)
        {
            return 1;
        }

        if (t == null)
        {
            return -1;
        }

        boolean exec1 = this.isExecutionRequired();
        boolean exec2 = t.isExecutionRequired();

        int prio1 = this.getPriority();
        int prio2 = t.getPriority();

        if (exec1 && !exec2)
        {
            return -1;
        }

        if (exec2 && !exec1)
        {
            return 1;
        }

        if (exec1 && exec2)
        {
            if (prio1 < prio2)
            {
                return -1;
            }

            if (prio1 > prio2)
            {
                return 1;
            }
        }

        return 0;
    }

    public void run()
    {
        while (!BridBot.stopped)
        {
            execution_required = this.activate();
            long wait_for = this.wait_for();
            long curr_time = System.currentTimeMillis();

            if (execution_required)
            {
                if (!TaskScheduler.contains(this))
                {
                    System.out.println("Added task");
                    TaskScheduler.addToExecutionQueue(this);
                }
                else
                {
                    System.out.println("Waiting until task is complete");
                }

                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return isComplete();
                    }
                });
            }

        }

    }

    public long wait_for()
    {
        return this.timeout;
    }

    public void setTimeout(long t)
    {
        this.timeout = t;
    }

    public boolean isExecutionRequired()
    {
        return this.execution_required;
    }

    public int getPriority()
    {
        return this.PRIORITY;
    }

    public void setPriority(int p)
    {
        this.PRIORITY = p;
    }

    public void setExecutionRequired(boolean b)
    {
        this.execution_required = false;
    }

    public abstract boolean activate();

    public abstract void execute();

    public abstract boolean isComplete();



}