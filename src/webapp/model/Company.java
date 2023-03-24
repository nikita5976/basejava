package webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static webapp.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Link link;
    private final List<Period> period = new ArrayList<>();

    Company(String name, String website) {
        Objects.requireNonNull(name, "name must not be null");
        link = new Link(name, website);
    }

    public void setPeriod(LocalDate dataStart, LocalDate dataEnd, String title, String description) {
        Objects.requireNonNull(dataStart, "dataStart must not be null");
        Objects.requireNonNull(dataEnd, "dataEnd must not be null");
        Objects.requireNonNull(title, "title must not be null");
        period.add(new Period(dataStart, dataEnd, title, description));
    }

    public String getName() {
        return link.getName();
    }

    @Override
    public String toString() {
        return "\n" + link + "\n" + period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return link.equals(company.link) && period.equals(company.period);
    }

    @Override
    public int hashCode() {
        return period.hashCode() + link.hashCode();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Link implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String name;
        private final String website;

        private Link(String name, String website) {
            Objects.requireNonNull(name, "name must not be null");
            this.name = name;
            this.website = website;
        }


        public String getName() {
            return name;
        }

        public String getWebsite() {
            return website;
        }

        @Override
        public String toString() {
            return "Link(" + name + ',' + website + ')';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Link link = (Link) o;
            if (!name.equals(link.name)) return false;
            return website != null ? website.equals(link.website) : link.website == null;
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            // разобраться
            //result = 31 * result + (url != null ? url.hashCode() : 0);
            return result;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        private static final long serialVersionUID = 1L;
        private final LocalDate dateStart;
        private final LocalDate dateEnd;
        private final String title;
        private final String description;

        private Period(LocalDate dateStart, LocalDate dateEnd, String title, String description) {
            this.dateStart = dateStart;
            this.dateEnd = dateEnd;
            this.title = title;
            this.description = description;
        }

        // Пока не использую. Применить когда  будет понятно с вводом данных.
        private Period(LocalDate dateStart, String title, String description) {
            this.dateStart = dateStart;
            this.dateEnd = NOW;
            this.title = title;
            this.description = description;
        }

        @Override
        public String toString() {
            return dateStart + " - " + dateEnd + "\n" + title + "\n" + description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period period = (Period) o;
            return title.equals(period.title) && description.equals(period.description)
                    && dateStart.equals(period.dateStart) && dateEnd.equals(period.dateEnd);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title + description + dateStart + dateEnd);
        }
    }
}
