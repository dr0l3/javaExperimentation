import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by g50848 on 01/12/2016.
 */
public class ConcurrentIncrement {
    private static volatile int int_vol = 0;
    private static int value = 0;
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        long t_start = System.currentTimeMillis();
        long t_stop;
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(ConcurrentIncrement::increment_syn2);
        }

        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        t_stop = System.currentTimeMillis();
        System.out.println("Runtime: " + (t_stop- t_start));
        System.out.println(value);

        executorService = Executors.newFixedThreadPool(8);
        value = 0;
        t_start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(ConcurrentIncrement::increment_lock);
        }
        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        t_stop = System.currentTimeMillis();
        System.out.println("Runtime: " + (t_stop- t_start));
        System.out.println(value);

        executorService = Executors.newFixedThreadPool(8);
        value = 0;
        t_start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(ConcurrentIncrement::increment_syn);
        }

        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        t_stop = System.currentTimeMillis();
        System.out.println("Runtime: " + (t_stop- t_start));
        System.out.println(value);

        executorService = Executors.newFixedThreadPool(8);
        value = 0;
        t_start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(ConcurrentIncrement::increment_vol);
        }

        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        t_stop = System.currentTimeMillis();
        System.out.println("Runtime: " + (t_stop- t_start));
        System.out.println(value);

        executorService.shutdown();
    }

    public static synchronized void increment_syn2(){
        for (int i = 0; i < 1000000; i++) {
            value++;
        }
    }

    public static void increment_lock(){
        lock.lock();
        try{
            for (int i = 0; i < 1000000; i++) {
                value++;
            }
        } finally {
            lock.unlock();
        }
    }

    public static void increment_syn(){
        synchronized (ConcurrentIncrement.class) {
            for (int i = 0; i < 1000000; i++) {
                value++;
            }
        }
    }

    public static void increment_vol(){
        for (int i = 0; i < 1000000; i++) {
            value++;
        }
    }

}
