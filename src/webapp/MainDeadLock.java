package webapp;

public class MainDeadLock {
    static final MainDeadLock mdl = new MainDeadLock();
    static final MainDeadLock.TestObject mdl1 = mdl.new TestObject("объект 1");
    static final MainDeadLock.TestObject mdl2 = mdl.new TestObject("объект 2");

    public static void main(String[] args) {

        System.out.println("в одном потоке " + mdl1 + " & " + mdl2);

        Runnable oneR = () -> {
            synchronized (mdl1) {
                System.out.println("два потока и deadlock" + mdl2);
                synchronized (mdl2) {
                    System.out.println("stop usage " + mdl2);
                }
            }
        };

        Runnable twoR = () -> {
            synchronized (mdl2) {
                System.out.println("два потока и deadlock" + mdl1);
                synchronized (mdl1) {
                    System.out.println("stop usage " + mdl1);
                }
            }
        };
        new Thread(oneR).start();
        new Thread(twoR).start();
    }

    private  class TestObject {
        String a;
        TestObject (String a) {
            this.a = a;
        }

        @Override
        public String toString(){
            return a;
        }
    }

}
