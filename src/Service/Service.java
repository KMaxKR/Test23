package Service;

import java.util.concurrent.*;

public class Service {
    private static int count = 0;
    ExecutorService service = null;

    public void method(){
        try {
            service = Executors.newSingleThreadExecutor();
            System.out.println("Begin ...");
            service.execute(() -> System.out.println("Starting ..."));
            service.execute(() -> {
                for (int i = 0; i <= 100; i++) {
                    System.out.println("Loading " + i + "%");
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        System.out.println(e.getMessage());
                    }
                }
            });
            service.execute(() -> System.out.println("Process End."));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            if (service != null) service.shutdown();
        }
    }



    public void method2(){
        try {
            service = Executors.newSingleThreadExecutor();
            Future<?> result = service.submit(() -> {
                for (int i = 0; i < 100; i++) {
                    Service.count++;
                    System.out.println(count);
                }
            });
            result.get(1, TimeUnit.SECONDS);
            System.out.println("Done");
        }catch (TimeoutException e){
            System.out.println("Not reached in time");
        }catch (ExecutionException e) {
            System.out.println("ExecutionException");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        } finally {
            if (service != null) service.shutdown();
        }
    }



    public void method3() throws ExecutionException, InterruptedException {
        try {
            service = Executors.newSingleThreadExecutor();
            Future<Integer> result = service.submit(() -> 40 + 11);
            System.out.println(result.get());
        }finally {
            if(service != null) service.shutdown();
        }
        if (service != null){
            service.awaitTermination(1, TimeUnit.SECONDS);
            if(service.isTerminated()){
                System.out.println("All task are done");
            }else {
                System.out.println("At least one task is still running ...");
            }
        }
    }



    ScheduledExecutorService serviceSchedule = Executors.newSingleThreadScheduledExecutor();
    public void method4() throws ExecutionException, InterruptedException {
        try{
            Runnable task1 = () -> System.out.println("Still");
            Callable<String> task2 = () -> "Working";
            Future<?> result1 = serviceSchedule.schedule(task1, 5, TimeUnit.SECONDS);
            Future<?> result2 = serviceSchedule.schedule(task2, 10, TimeUnit.SECONDS);
            System.out.println(result2.get());
        }finally {
            if (service != null) serviceSchedule.shutdown();
        }
        if (service != null){
            service.awaitTermination(1, TimeUnit.SECONDS);
            if(service.isTerminated()){
                System.out.println("All task are done");
            }else {
                System.out.println("At least one task is still running ...");
            }
        }
    }

}
