import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizeField {
    private static Pair<Long, String> someData;

    public static void main(String[] args) throws InterruptedException {
        someData = new Pair<>(System.currentTimeMillis(), UUID.randomUUID().toString());
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            tasks.add(SynchronizeField::doLotsOfStuff);
        }
        long start = System.currentTimeMillis();
        executorService.invokeAll(tasks);
        long total = System.currentTimeMillis() - start;
        System.out.println("------------------------");
        System.out.println("total time: " + total);
        System.out.println(someData);
        executorService.shutdown();
    }

    public static Integer doLotsOfStuff(){
        System.out.println(someData);
        someData = new Pair<>(System.currentTimeMillis(), UUID.randomUUID().toString());
        return 1;
    }
}
