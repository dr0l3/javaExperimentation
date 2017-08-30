import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.runAsync;

/**
 * Created by g50848 on 11/11/2016.
 */
public class completeablefuture {
    public static void main(String[] args) {
        int scale = 3;

        CompletableFuture<Void> wait5 = runAsync(() -> waitForXSeconds(8 * scale));
        CompletableFuture<Void> wait4 = runAsync(() -> waitForXSeconds(7 * scale));
        CompletableFuture<Void> wait2 = runAsync(() -> waitForXSeconds(5 * scale));
        CompletableFuture<Void> wait = runAsync(() -> waitForXSeconds(2 * scale));
        CompletableFuture<Void> wait3 = runAsync(() -> waitForXSeconds(1 * scale));

        System.out.println("Starting");
        long time = System.currentTimeMillis();
        CompletableFuture<Void> all = allOf(wait, wait2, wait3, wait4, wait5);

        all.join();
        long time2 = System.currentTimeMillis();
        all.thenRun(() -> System.out.println("ending. Total time: " + (time2 - time)));

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        long time3 = System.currentTimeMillis();
        Future<?> future5 = executorService.submit(() -> waitForXSeconds(8 * scale));
        Future<?> future4 = executorService.submit(() -> waitForXSeconds(7 * scale));
        Future<?> future2 = executorService.submit(() -> waitForXSeconds(5 * scale));
        Future<?> future = executorService.submit(() -> waitForXSeconds(2 * scale));
        Future<?> future3 = executorService.submit(() -> waitForXSeconds(1 * scale));

        List<Future<?>> list = Arrays.asList(future,future2,future3, future4, future5);
        list.forEach(fut -> {
            try {
                fut.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        long time4 = System.currentTimeMillis();
        System.out.println("ending. Total time: " + (time4 - time3));


        wait = runAsync(() -> waitForXSeconds(2 * scale), executorService);
        wait2 = runAsync(() -> waitForXSeconds(5 * scale), executorService);
        wait3 = runAsync(() -> waitForXSeconds(1 * scale), executorService);
        wait4 = runAsync(() -> waitForXSeconds(7 * scale), executorService);
        wait5 = runAsync(() -> waitForXSeconds(8 * scale), executorService);

        System.out.println("Starting");
        long time5 = System.currentTimeMillis();
        all = allOf(wait, wait2, wait3, wait4, wait5);

        all.join();
        long time6 = System.currentTimeMillis();
        all.thenRun(() -> System.out.println("ending. Total time: " + (time6 - time5)));

        executorService.shutdown();

    }

    public static Void waitForXSecondsV2(int x){
        try {
            Thread.sleep(x * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void waitForXSeconds(int x) {
        try {
            Thread.sleep(x * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
