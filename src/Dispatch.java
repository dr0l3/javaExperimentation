import com.sun.xml.internal.ws.util.CompletedFuture;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Dispatch {
    private static int threadPoolSize = 8;
    private static int sleepTime = 1000;
    private static int pollFrequency = 1000;
    private static Random r = new Random();
    private static ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(threadPoolSize);

    public static void main(String[] args) {
        executor.scheduleAtFixedRate(Dispatch::pollAndDispatchAsync, 0, pollFrequency, TimeUnit.MILLISECONDS);
    }

    public static void pollAndDispatch(){
        for (int i = 0; i < 10; i++) {
            final String id = String.valueOf(i);
            final int resp = pollSingle(id);

            if(resp != -1){
                executor.submit(() -> {
                    try {
                        doLotsOfWork(id, resp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    public static void pollAndDispatchAsync(){
        long start = System.currentTimeMillis();
        List<CompletableFuture> futs = new ArrayList();
        for (int i = 0; i < 10; i++) {
            final String id = String.valueOf(i);
            final int resp = pollSingle(id);

            if(resp != -1){
                futs.add(doLotsOfAsyncWork(id,resp));
            }
        }
        CompletableFuture.allOf(futs.toArray(new CompletableFuture[futs.size()])).join();
        long end = System.currentTimeMillis();
        System.out.println("total time -> " + (end-start));
    }

    public static int pollSingle(String id){
        if(r.nextInt(10) >8){
            return 9;
        } else {
            return -1;
        }
    }

    public static void doLotsOfWork(String id, int message) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread.sleep(sleepTime);
        long end = System.currentTimeMillis();
        long duration = end-start;
        if(r.nextInt(100) > 90) {
            System.out.println("THROWING EXCEPTIONS");
            throw new RuntimeException("ZOMG");
        }
        System.out.println("Lots of work finished for id="+id + " total time -> " + duration);
    }

    public static CompletableFuture<Void> doLotsOfAsyncWork(String id, int message){
        return CompletableFuture.runAsync(() -> {
            try {
                System.out.println("    Starting work for id: " + id);
                long start = System.currentTimeMillis();
                Thread.sleep(sleepTime);
                System.out.println("    Ending work for id: " + id);
                long end = System.currentTimeMillis();
                long duration = end-start;
                System.out.println("Lots of work finished for id="+id + " total time -> " + duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
