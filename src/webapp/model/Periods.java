package webapp.model;

import java.util.Objects;

public class Periods {
    private final String dateStart;
    private final String dateEnd;

    Periods(String dateStart, String dateEnd) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        return getDateStart() + " - " + getDateStart();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Periods periods = (Periods) o;
        return dateStart.equals(periods.dateStart) && dateEnd.equals(periods.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateStart + dateEnd);
    }
}