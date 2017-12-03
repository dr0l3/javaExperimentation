import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ExecutorsWithMultipleThreads {
    private static int threadPoolSize = 16;
    private static int waitBeforeResume = 1000;
    private static int numberOfFutures = 100;
    private static int futureTimeToComplete = 500;
    private static boolean useYield = false;
    private static boolean printDebug = false;
    private static boolean useCompletableFuture = false;
    private static boolean skipWrapper = true;

    private static int boundOnRandomNumbers = 1000;
    private static ScheduledExecutorService executor1 = new ScheduledThreadPoolExecutor(threadPoolSize);

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
         * Used parameters was all combinations of the below
         *  - ThreadPoolSize 2 and 10
         *  - NumberOfFutures 5,10,100
         *  - FutureTimeToComplete 500
         *
         *  VERY IMPORTANT NOTE:
         *  The above only counts for thread.yield. If thread.sleep is used one wrapper is starved!
         *  However the first thread seems to work normally. Example output:
         *
         *  ThreadPoolSize 2, NumberOfFutures 100, FutureToComplete 500
         *  DOG 50703 Took time -> 22614
         *  CAT 48744 Took time -> 37530
         *  CAT 48195 Took time -> 24197
         *  DOG 43835 Took time -> 60193
         *  CAT 48819 Took time -> 22125
         *  CAT 52988 Took time -> 20595
         *  CAT 51988 Took time -> 22640
         *  DOG 54716 Took time -> 67044
         *  CAT 49177 Took time -> 22343
         *
         *  ThreadPoolSize is irrelevant, example output.
         *
         *  ThreadPoolSize 16, NumberOfFutures 100, FutureToComplete 500
         *  DOG 50814 Took time -> 23155
         *  CAT 50569 Took time -> 37042
         *  CAT 48561 Took time -> 24186
         *  DOG 47170 Took time -> 60197
         *  CAT 52967 Took time -> 22654
         *  CAT 49995 Took time -> 20592
         *  CAT 54613 Took time -> 23095
         *
         *  Cant replicate the problem with NumberOfFutures 10.
         *  Starts to creep in at NumberOfFutures 20.
         *
         *  The problem persists with a purely CompletableFuture based implementation.
         *  Even if you submit each new orchestrator request to the threadpool.
         *  In fact it gets worse. Everything is slower, much slower.
         *
         *  It appears that the UNIREST win-client is sleep-based
         *
         */


        if(!useCompletableFuture){
            if(skipWrapper){
                executor1.scheduleAtFixedRate(() -> {
                    try {
                        long start = System.currentTimeMillis();
                        int res = orchestrate("DOG");
                        long end = System.currentTimeMillis();
                        String print = "DOG" +" " + res + " Took time -> " + (end-start);
                        System.out.println(print);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
                }, 0, waitBeforeResume, TimeUnit.MILLISECONDS);

                executor1.scheduleAtFixedRate(() -> {
                    try {
                        long start = System.currentTimeMillis();
                        int res = orchestrate("CAT");
                        long end = System.currentTimeMillis();
                        String print = "CAT" +" " + res + " Took time -> " + (end-start);
                        System.out.println(print);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
                }, 0, waitBeforeResume, TimeUnit.MILLISECONDS);
            } else {
                try{
                    executor1.submit(() -> {
                        try {
                            ExecutorsWithMultipleThreads.wrapper("DOG");
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    executor1.submit(() -> {
                        try {
                            ExecutorsWithMultipleThreads.wrapper("CAT");
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            try{
                executor1.submit(() -> ExecutorsWithMultipleThreads.futureWrapper("DOG"));
                executor1.submit(() -> ExecutorsWithMultipleThreads.futureWrapper("CAT"));
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public static void wrapper(String id) throws ExecutionException, InterruptedException {
        while(true){
            long start = System.currentTimeMillis();
            int res = orchestrate(id);
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

    public static int orchestrate(String id) throws ExecutionException, InterruptedException {
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfFutures; i++) {
            futures.add(quiteSomeTime(id));
        }

        return executor1.submit(() -> futures.stream()
                .map(CompletableFuture::join)
                .reduce(0, (a,b) -> a +b)).get();
    }

    public static void futureWrapper(String id){
        while(true){
            long start = System.currentTimeMillis();
            executor1.submit(() -> futureOrchestrator(id)
                    .thenApply(v -> {
                        long end = System.currentTimeMillis();
                        String print = id +" " + v + " Took time -> " + (end-start);
                        System.out.println(print);
                        return v;
                    }));

            try {
                Thread.sleep(waitBeforeResume);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static CompletableFuture<Integer> futureOrchestrator(String id){
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfFutures; i++) {
            futures.add(quiteSomeTime(id));
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .thenApply(v -> futures.stream()
                .map(fut -> fut.join())
                .reduce(0, (a,b) -> a+b));
    }

    public static CompletableFuture<Integer> quiteSomeTime(String id){
        return CompletableFuture.supplyAsync(() -> {
            Random r = new Random();
            if(printDebug) System.out.println("quiteSomeTimeBeforeWait -> " + id);
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
//                    System.out.println("ThreadID -> " + Thread.currentThread());
                    Thread.sleep(futureTimeToComplete);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(printDebug)System.out.println("QuiteSomeTimeAfterWait -> " + id);

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
