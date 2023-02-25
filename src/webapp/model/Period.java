package webapp.model;

import java.util.Objects;

public class Period {
    private final String dateStart;
    private final String dateEnd;
    private final String title;
    private String description = "";

    Period(String dateStart, String dateEnd, String title) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.title = title;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getTitle(){
        return title;
    }

    @Override
    public String toString() {
        return getDateStart() + " - " + getDateStart() + "\n" + title + "\n" + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return title.equals(period.title)&& description.equals(period.description)
                &&dateStart.equals(period.dateStart) && dateEnd.equals(period.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title + description+ dateStart + dateEnd);
    }
}