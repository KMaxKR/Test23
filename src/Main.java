import Service.Service;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Service service = new Service();

        service.method();

        service.method2();

        service.method3();

        service.method4();
    }
}