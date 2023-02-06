package webapp.model;

public class GeneratorNames {
    private static int enumerator = -1;
    private final String[] names = {
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

    protected String getFullName() {
        if (enumerator == 11) {
            enumerator = -1;
        }
        enumerator++;
        return names[enumerator];
    }
}
