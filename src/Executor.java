import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by g50848 on 28/03/2017.
 */
public class Executor {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(Executor::doWork);
        executorService.submit(Executor::doOtherWork);
    }

    public static void doWork(){
        while (true){
            try {
                Thread.sleep(2000);
                System.out.println("hehehheh");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void doOtherWork(){
        while(true){
            try{
                Thread.sleep(2000);
                System.out.println("THis is not funny!");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
