package test.Multi_Thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyExecutorService {
    private ExecutorService es;
    private final int size;

    public MyExecutorService(int size) {
        this.size = size;
        this.es = Executors.newFixedThreadPool(size);
    }

    public void check_terminated(){
        if(es.isTerminated() || es.isShutdown()){
            es = Executors.newFixedThreadPool(size);
        }
    }

    public void execute_tasks(int sample_size , Task task){
        for (int i = 0; i < sample_size; i++) {
            es.execute(task);
        }
        es.shutdown();
    }

    public void await_termination() {
        try {
            es.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(){
        es.close();
    }
}
