package webapp.model;

import webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import static webapp.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period125 implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateStart;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateEnd;
    private String title;
    private String description;

    public Period125 () {}

    private Period125(LocalDate dateStart, LocalDate dateEnd, String title, String description) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.title = title;
        this.description = description;
    }

    // Пока не использую. Применить когда  будет понятно с вводом данных. назначение - ввод текущего места
    private Period125(LocalDate dateStart, String title, String description) {
        this.dateStart = dateStart;
        this.dateEnd = NOW;
        this.title = title;
        this.description = description;
    }

    public LocalDate getDateStart () {
        return dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public String getTitle () {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return dateStart + " - " + dateEnd + "\n" + title + "\n" + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period125 period = (Period125) o;
        return title.equals(period.title) && description.equals(period.description)
                && dateStart.equals(period.dateStart) && dateEnd.equals(period.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title + description + dateStart + dateEnd);
    }
}
