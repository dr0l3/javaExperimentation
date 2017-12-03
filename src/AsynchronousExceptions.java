import java.util.Random;
import java.util.concurrent.*;

public class AsynchronousExceptions {
    private static ScheduledExecutorService executorService;
    public static void main(String[] args) throws InterruptedException {
        executorService = new ScheduledThreadPoolExecutor(2);
//        classicFutures();
        for (int i = 0; i < 10; i++) {
            completableFutures();
        }
        executorService.awaitTermination(5,TimeUnit.SECONDS);
        executorService.shutdown();
    }

    public static void completableFutures(){
        CompletableFuture<?> fut = CompletableFuture.runAsync(AsynchronousExceptions::something,executorService).handle((res, err) -> {
            if(err != null){
                System.out.println(err.toString());
            } else {
                System.out.println("Success");
            }
            return 1;
        });
    }

    public static void classicFutures(){
        //cant attach callback so need to poll the future for stuff
        Future<?> future = executorService.submit(AsynchronousExceptions::something);
        try {
            future.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void something(){
        Random r = new Random();
        if(r.nextInt(10) < 5){
            return;
        }
        throw new RuntimeException("waaaa");
    }
}
