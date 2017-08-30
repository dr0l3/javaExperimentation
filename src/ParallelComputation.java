import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by g50848 on 19/06/2017.
 */
public class ParallelComputation {
    public static int times = 0;
    public static void main(String[] args) {
        run();
    }

    public static void run2(){
        CompletableFuture<List<String>> strings = CompletableFuture.supplyAsync(ParallelComputation::listOne);
        CompletableFuture<List<Integer>> ints = CompletableFuture.supplyAsync(ParallelComputation::listTwo);
        CompletableFuture<List<Boolean>> bools = CompletableFuture.supplyAsync(ParallelComputation::listThree);
        //cant zip more than two streams in java...
    }

    public static void run(){
        //this does what you expect it to do. Parallel computation with retry.
        long start = System.currentTimeMillis();
        CompletableFuture<String> one = CompletableFuture.supplyAsync(ParallelComputation::methodOne)
                .handle((s, t) ->s != null ? s : "Hello");
        CompletableFuture<String> two = CompletableFuture.supplyAsync(ParallelComputation::methodTwo)
                .handle((s, t) -> retryIfNull(s, t, 2,ParallelComputation::methodTwo));
        CompletableFuture<String> three = CompletableFuture.supplyAsync(ParallelComputation::methodThree)
                .handle((s,t) -> s != null ? s : "World");
        String res = Stream.of(one,two,three)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));
        long end = System.currentTimeMillis();
        System.out.println("Total time: " + (end-start));
        System.out.println(res);
    }

    public static <T> T retryIfNull(T origVal, Throwable maybeEx, int retries, Supplier<T> callBack){

        T val = origVal;
        while(retries > 0) {
            System.out.println("--Retrying");
            retries = retries-1;
            if (val == null) {
                System.out.println("--Value was null. Error was: " + maybeEx.getMessage());
                try {
                    val = callBack.get();
                } catch (Exception e){
                    val = null;
                }
            } else {
                System.out.println("--Value was good. Error value: " + maybeEx);
                return origVal;
            }
        }
        return null;
    }


    public static String methodOne() {
        try {
            Thread.sleep(1000);
            if(times < 10) {
                System.out.println("MethodOne: failing");
                throw new RuntimeException("MOG");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("MethodOne: succeeding");
        return "hello";
    }

    public static String methodTwo() {
        try {
            Thread.sleep(2000);
            if(times < 1) {
                System.out.println("MethodTwo: failing");
                times++;
                throw new RuntimeException("BAD");
            }
            System.out.println("MethodTwo: succeeding");
            return "fantastic";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String methodThree() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("MethodThree: succeeding");
        return "world";
    }

    public static List<String> listOne(){
        return Arrays.asList("hello", "world");
    }

    public static List<Integer> listTwo(){
        return Arrays.asList(1,23);
    }

    public static List<Boolean> listThree(){
        return Arrays.asList(false,true);
    }
}
