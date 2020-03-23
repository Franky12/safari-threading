package pool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

class MyWork implements Callable<String> {
  private static int nextId = 0;
  private int id = nextId++;

  @Override
  public String call() throws Exception {
    System.out.println("Job " + id + " started");
    Thread.sleep((int) (Math.random() * 2000) + 1000);
    System.out.println("Job " + id + " finished");
    return "Job " + id + " result";
  }
}

public class UseThreadPool {
  public static void main(String[] args) {
    ExecutorService es = Executors.newFixedThreadPool(2);
    List<Future<String>> jobs = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      jobs.add(es.submit(new MyWork()));
    }
    es.shutdown(); // takes no new jobs, finishes current ones
    while (jobs.size() > 0) {
      Iterator<Future<String>> iter = jobs.iterator();
      while (iter.hasNext()) {
        Future<String> fs = iter.next();
        if (fs.isDone()) {
          try {
            String result = fs.get();
            System.out.println("a job returned " + result);
            iter.remove();
          } catch (InterruptedException e) {
            e.printStackTrace();
          } catch (ExecutionException e) {
            e.printStackTrace();
          }
        }
      }
    }
    System.out.println("All jobs finished...");
  }
}
