class Q {
  int n;
  synchronized int get() {
    try{
      wait();
    }catch(InterruptedException e){
      System.out.println("interrupedException caught");
    }
    System.out.println("Got: " + n);
    notify();
    return n;
  }
  
  synchronized void put(int n) {
    try{
      wait();
    }catch(InterruptedException e){
      System.out.println("interrupedExceptionCaught");
    }
    this.n = n;
    System.out.println("Put: " + n);
    notify();
  }
}
class Producer implements Runnable {
  Q q;
  Producer(Q q) {
    this.q = q;
    new Thread(this, "Producer").start();
  }
  public void run() {
    int i = 0;
    while(i<100) {
      q.put(i++);
    }
  }
}
class Consumer implements Runnable {
  Q q;
  Consumer(Q q) {
    this.q = q;
    new Thread(this, "Consumer").start();
  }
  public void run() {
    int i=0;
    while(i<100) {
      q.get();
       i++;
    }
  }
}
class PC_wrong {
  public static void main(String args[]) {
    Q q = new Q();
    new Producer(q);
    new Consumer(q);
  }
}