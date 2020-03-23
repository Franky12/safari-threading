package problems;

class Counter implements Runnable {
  int i = 0;
  @Override
  public void run() {
    for (; i < 10_000; i++) {
      System.out.println(Thread.currentThread().getName() + " i is " + i);
    }
  }
}
public class Sharing {
  public static void main(String[] args) {
    Counter c = new Counter();
    Thread t1 = new Thread(c);
//    t1.setDaemon(true);
    t1.start();
//    c.run();
    Thread t2 = new Thread(c);
    t2.start();
    System.out.println(Thread.currentThread().getName() + " exiting");
  }
}
