package webapp;

public class LazySingleton {

    volatile private static LazySingleton INSTANCE;

    private LazySingleton() {
    }

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
//        if (INSTANCE == null) {
//            synchronized (webapp.LazySingleton.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new webapp.LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
    }
}
