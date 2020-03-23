package queue;

public class MyQueue<E> {
  private static final int CAPACITY = 10;
  private E[] data = (E[])(new Object[CAPACITY]);
  private int count = 0;

  public void put(E e) throws InterruptedException {
    synchronized(this) {
//      while (count >= CAPACITY)
//        ;
      while (count >= CAPACITY)
        this.wait(); // releases CPU, and key (temporarily)
      data[count] = e;
      count++;
      this.notifyAll(); // works, but unscalable
    }
  }

  public E take() throws InterruptedException {
    synchronized (this) {
//      while (count <= 0)
//        ;
      while (count <= 0)
        this.wait();
      E item = data[0];
      System.arraycopy(data, 1, data, 0, --count);
      this.notifyAll();
      return item;
    }
  }

  public static void main(String[] args) {
    MyQueue<Integer> mqi = new MyQueue<>();
    Runnable producer = () -> {
      for (int i = 0; i < 100; i++) {
        try {
          mqi.put((i != 50) ? i : -1);
          Thread.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      System.out.println("Producer completed");
    };
    Runnable consumer = () -> {
      for(int i = 0; i < 100; i++) {
        try {
          if (i != mqi.take()) {
            System.out.println("ERROR!!!!");
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      System.out.println("Consumer finished...");
    };

    new Thread(producer).start();
//    new Thread(consumer).start();
    System.out.println("Main exiting...");
  }
}
