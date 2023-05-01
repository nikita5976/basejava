package webapp;

public class MainDeadLock {
    static final MainDeadLock mdl = new MainDeadLock();
    static final MainDeadLock.TestObject mdl1 = mdl.new TestObject("объект 1");
    static final MainDeadLock.TestObject mdl2 = mdl.new TestObject("объект 2");

    public static void main(String[] args) {

        System.out.println("в одном потоке " + mdl1 + " & " + mdl2);

        new Thread(mdl.new DeadLock(mdl1, mdl2)).start();
        new Thread(mdl.new DeadLock(mdl2, mdl1)).start();
    }

    private class DeadLock implements Runnable {
        final MainDeadLock.TestObject mdl1;
        final MainDeadLock.TestObject mdl2;

        DeadLock(MainDeadLock.TestObject mdl1, MainDeadLock.TestObject mdl2) {
            this.mdl1 = mdl1;
            this.mdl2 = mdl2;
        }

        @Override
        public void run() {
            synchronized (mdl1) {
                System.out.println("два потока и deadlock" + mdl1);
                synchronized (mdl2) {
                    System.out.println("stop usage " + mdl1);
                }
            }
        }
    }

    private class TestObject {
        String a;

        TestObject(String a) {
            this.a = a;
        }

        @Override
        public String toString() {
            return a;
        }
    }


}
