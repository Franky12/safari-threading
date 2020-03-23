package problems;

class Stopper implements Runnable {
  public volatile boolean stop = false;
  public int x = 0;
  @Override
  public void run() {
    System.out.println("Stopper task starting...");
    while (!stop)
      ;
    System.out.println("Stopper task ending... x is " + x);
  }
}
public class Communication {
  public static void main(String[] args) throws Throwable {
    Stopper stopper = new Stopper();
    new Thread(stopper).start();
    Thread.sleep(1000);
    stopper.x = 100;
    stopper.stop = true;
    System.out.println("stop set to " + stopper.stop);
    System.out.println("Main exiting");
  }
}
