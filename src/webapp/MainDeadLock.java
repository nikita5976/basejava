package webapp;

public class MainDeadLock {
    static final Object mdl1 =  new Object() {
        String a = "объект 1";
        @Override
        public String toString(){
            return a;
        }
    };
    static final Object mdl2 = new Object(){
        String b = "объект 2";
        @Override
        public String toString(){
            return b;
        }
    };

    public static void main(String[] args) {

      System.out.println("в одном потоке "+mdl1);
      System.out.println("в одном потоке "+mdl2);

       Runnable oneR = () -> {
           synchronized (mdl1){
               System.out.println("два потока и deadlock"+mdl2);
               Thread.yield();
               synchronized (mdl2) {
                   System.out.println(mdl2);
               }
           }
       };

       Runnable twoR = ()-> {
           synchronized (mdl2){
               System.out.println("два потока и deadlock"+mdl1);
               Thread.yield();
               synchronized (mdl1) {
                   System.out.println(mdl1);
               }
           }
       };
       new Thread(oneR).start();
       new Thread(twoR).start();
    }
}
