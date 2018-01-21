package cn.irving.zhao.util.remote.mina.server.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public final class TimerUtil {

    private static final Timer TIMER = new Timer();

    public static void schedule(Runnable task, long delay) {
        TIMER.schedule(new TimerUtilTask(task), delay);
    }

    public static void schedule(Runnable task, Date time) {
        TIMER.schedule(new TimerUtilTask(task), time);
    }

    public static void schedule(Runnable task, long delay, long period) {
        TIMER.schedule(new TimerUtilTask(task), delay, period);
    }

    public static void schedule(Runnable task, Date firstTime, long period) {
        TIMER.schedule(new TimerUtilTask(task), firstTime, period);
    }

    public static void scheduleAtFixedRate(Runnable task, long delay, long period) {
        TIMER.scheduleAtFixedRate(new TimerUtilTask(task), delay, period);
    }

    public static void scheduleAtFixedRate(Runnable task, Date firstTime, long period) {
        TIMER.scheduleAtFixedRate(new TimerUtilTask(task), firstTime, period);
    }

    public static void schedule(TimerTask task, long delay) {
        TIMER.schedule(task, delay);
    }

    public static void schedule(TimerTask task, Date time) {
        TIMER.schedule(task, time);
    }

    public static void schedule(TimerTask task, long delay, long period) {
        TIMER.schedule(task, delay, period);
    }

    public static void schedule(TimerTask task, Date firstTime, long period) {
        TIMER.schedule(task, firstTime, period);
    }

    public static void scheduleAtFixedRate(TimerTask task, long delay, long period) {
        TIMER.scheduleAtFixedRate(task, delay, period);
    }

    public static void scheduleAtFixedRate(TimerTask task, Date firstTime, long period) {
        TIMER.scheduleAtFixedRate(task, firstTime, period);
    }

    private static class TimerUtilTask extends TimerTask {

        public TimerUtilTask(Runnable executor) {
            this.executor = executor;
        }

        private Runnable executor;

        @Override
        public void run() {
            executor.run();
        }
    }
}
