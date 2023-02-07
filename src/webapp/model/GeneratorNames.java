package webapp.model;

class GeneratorNames {
    private static int enumerator = -1;
    private static final String[] names = {
            "Иванов Пётр",
            "Петров Иван",
            "Петров Иван",
            "Петров Иван",
            "Сидоров Николай",
            "Николаев Сидр",
            "Савельев Алексей",
            "Алексеев Савелий",
            "Петров Николай",
            "Николаев Петр",
            "Иванов Алексей",
            "Алексеев Иван"
    };

    protected static String getFullName() {
        if (enumerator == 11) {
            enumerator = -1;
        }
        enumerator++;
        return names[enumerator];
    }
}
