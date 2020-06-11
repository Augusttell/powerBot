package BridBot.task;

import BridBot.BridBot;
import BridBot.task.actiontasker.ActionTasker;
import BridBot.task.backgroundtasker.BackgroundTasker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class TaskScheduler implements Runnable {


    /*
        Execution queue sorted by priority. Used for ActionTaskers. Only one ActionTasker can be executing at any
        one given time since the ActionTasker will access inputs to the interface and we can only have one task
        controlling the inputs at any given time.
     */
    private static PriorityQueue<ActionTasker> execution_queue;

    /*
        Unique set of ActionTaskers
     */
    private static HashSet<ActionTasker> actiontasker_pool;

    /*
        Unique set of BackgroundTaskers
     */
    private static HashSet<BackgroundTasker> background_pool;

    /*
        List of all the ActionTaskers' threads
     */
    private static ArrayList<Thread> actiontasker_threads;

    /*
        List of all the BackgroundTasker threads
     */
    private static ArrayList<Thread> background_threads;


    public TaskScheduler()
    {
        background_threads = new ArrayList<Thread>();
        actiontasker_threads = new ArrayList<Thread>();

        actiontasker_pool = new HashSet<ActionTasker>();
        background_pool = new HashSet<BackgroundTasker>();

        execution_queue = new PriorityQueue<ActionTasker>(new Comparator<ActionTasker>() {
            @Override
            public int compare(ActionTasker o1, ActionTasker o2) {

                if (o1 == o2)
                {
                    return 0;
                }

                if (o1 == null)
                {
                    return 1;
                }

                if (o2 == null)
                {
                    return -1;
                }

                boolean exec1 = o1.isExecutionRequired();
                boolean exec2 = o2.isExecutionRequired();

                int prio1 = o1.getPriority();
                int prio2 = o2.getPriority();

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
                        System.out.println(o1.getClass() + " is greater prio than " + o2.getClass());
                        return -1;
                    }

                    if (prio1 > prio2)
                    {
                        System.out.println(o1.getClass() + " is less prio than " + o2.getClass());
                        return 1;
                    }
                }

                return 0;
            }


        });
    }

    public void stop()
    {
        stopBackgroundThreads();
        stopActionThreads();
    }

    public void stopBackgroundThreads()
    {
        for (Thread t : background_threads)
        {
            t.interrupt();
            try
            {
                t.join();
            }
            catch (InterruptedException e)
            {

            }
        }
    }

    public void stopActionThreads()
    {
        for (Thread t : actiontasker_threads)
        {
            t.interrupt();
            try
            {
                t.join();
            }
            catch (InterruptedException e)
            {

            }
        }
    }

    /*

        Takes all the BackgroundTaskers and ActionTaskers and puts them into threads and starts the threads.
        All tasks must be added to the TaskScheduler before calling spawn() as anything added afterwards is not
        taken into consideration or spawned.

     */
    public void spawn()
    {
        spawnBackgroundThreads();
        spawnActionThreads();
    }

    public void spawnBackgroundThreads()
    {
        for (BackgroundTasker b : background_pool)
        {
            Thread thread = new Thread(b);
            background_threads.add(thread);
            thread.start();
            System.out.println("Spawned BackgroundTasker: " + b.getClass());
        }
    }

    public void spawnActionThreads()
    {
        for (ActionTasker t : actiontasker_pool)
        {
            Thread thread = new Thread(t);
            actiontasker_threads.add(thread);
            thread.start();
            System.out.println("Spawned ActionTasker: " + t.getClass());
        }
    }

    /*
        Does not spawn it. spawn() is to be only called once, and if a task is not inside the TaskScheduler when spawn()
        is called then the task will never be executed. May change later.
     */
    public void addBackgroundTasker(BackgroundTasker t)
    {
        background_pool.add(t);
    }

    public void addActionTasker(ActionTasker t)
    {
        actiontasker_pool.add(t);
    }

    public static void addToExecutionQueue(ActionTasker t)
    {
        // Get current task prio and preempt it.
//        ActionTasker current = execution_queue.peek();
//        if (current.getPriority() > t.getPriority())
//        {
//            // Stop the thread, clear the queue. What does interrupt() do?
//        }

        execution_queue.add(t);
    }

    public ActionTasker getTask()
    {
        if (execution_queue != null)
        {
            if (execution_queue.size() >= 1)
            {
                return execution_queue.peek();
            }
        }

        return null;
    }

    public ActionTasker peekPriorityTask()
    {
        return execution_queue.peek();
    }

    public static boolean contains(ActionTasker t)
    {
        return execution_queue.contains(t);
    }

    public void run()
    {


        while (!BridBot.stopped)
        {
            ActionTasker t = getTask();
            if (t != null)
            {
                t.execute();

                if (!t.isComplete())
                {
                    System.out.println("Executing task for " + t.getClass());
                    continue;
                }
                else
                {
                    System.out.println("Completed task");
                    execution_queue.remove(t);
                }
            }
        }
    }

    public PriorityQueue<ActionTasker> getQueue()
    {
        return execution_queue;
    }
}