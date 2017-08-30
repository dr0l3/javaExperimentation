import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ExecutorsWithMultipleThreads {
    private static int threadPoolSize = 2;
    private static int waitBeforeResume = 1000;
    private static int numberOfFutures = 100;
    private static int futureTimeToComplete = 500;
    private static boolean useYield = true;

    private static int boundOnRandomNumbers = 1000;

    public static void main(String[] args) {

        /** Scenario: Run two threads each creating x futures each running for y milliseconds.
         * Run the futures in parallel and see how long it takes.
         *
         * Theoretical best case: Total time should be y + slight overhead
         *
         * Theoretical worst case: Purely sequential, total time is x * y
         *
         * Actual finding: total time = ~x*y/2
         *
         * Used parameters was all combiations of the below
         *  - ThreadPoolSize 2 and 10
         *  - NumberOfFutures 5,10,100
         *  - FutureTimeToComplete 500
         *
         *  VERY IMPORTANT NOTE:
         *  The above only counts for thread.yield. If thread.sleep is used the second wrapper is starved!
         *
         *  It appears that the UNIREST win-client is sleep-based
         *
         */


        ScheduledExecutorService executor1 = new ScheduledThreadPoolExecutor(threadPoolSize);
        try{
            executor1.submit(() -> ExecutorsWithMultipleThreads.wrapper("DOG"));
            executor1.submit(() -> ExecutorsWithMultipleThreads.wrapper("CAT"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void printYes(){
        while (true){
            System.out.println("yes");
            try {
                Thread.sleep(waitBeforeResume);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void wrapper(String id){
        while(true){
            long start = System.currentTimeMillis();
            int res = letsOfStuff();
            long end = System.currentTimeMillis();
            String print = id +" " + res + " Took time -> " + (end-start);
            System.out.println(print);
            try {
                Thread.sleep(waitBeforeResume);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static int letsOfStuff(){
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfFutures; i++) {
            futures.add(quiteSomeTime());
        }
        return futures.stream()
                .map(CompletableFuture::join)
                .reduce(0, (a,b) -> a +b);
    }

    public static CompletableFuture<Integer> quiteSomeTime(){
        return CompletableFuture.supplyAsync(() -> {
            Random r = new Random();
            if(useYield) {
                long start = System.currentTimeMillis();
                long current = start;
                long end = start + futureTimeToComplete;
                while(current < end){
                    current = System.currentTimeMillis();
                    Thread.yield();
                }
            } else {
                try {
                    Thread.sleep(futureTimeToComplete);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return r.nextInt(boundOnRandomNumbers);
        });

    }

    public static void printNo(){
        while (true){
            System.out.println("No");
            try {
                Thread.sleep(waitBeforeResume);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
