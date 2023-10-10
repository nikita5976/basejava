package webapp.model;

import com.google.gson.annotations.JsonAdapter;
import webapp.util.JsonLocalDateAdapter;
import webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static webapp.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    private  Link link;
    private final List<Period> period = new ArrayList<>();



    Company() {}

    Company(String name, String website) {
        Objects.requireNonNull(name, "name must not be null");
        link = new Link(name, website);
    }

    public void addPeriod(LocalDate dataStart, LocalDate dataEnd, String title, String description) {
        Objects.requireNonNull(dataStart, "dataStart must not be null");
        Objects.requireNonNull(dataEnd, "dataEnd must not be null");
        Objects.requireNonNull(title, "title must not be null");
        period.add(new Period(dataStart, dataEnd, title, description));
    }

    public String getName() {
        return link.getName();
    }

    public String getWebsite() {
        return link.getWebsite();
    }

    public List <Period> getPeriod () {
        return period;
    }

    public Company getCompany () {return this;}

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
        private String name;
        private String website;

        public Link () {}

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

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        @JsonAdapter(JsonLocalDateAdapter.class)
        private LocalDate dateStart;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        @JsonAdapter(JsonLocalDateAdapter.class)
        private LocalDate dateEnd;
        private String title;
        private String description;

        public Period () {}

        private Period(LocalDate dateStart, LocalDate dateEnd, String title, String description) {
            this.dateStart = dateStart;
            this.dateEnd = dateEnd;
            this.title = title;
            this.description = description;
        }

        // Пока не использую. Применить когда  будет понятно с вводом данных. назначение - ввод текущего места
        private Period(LocalDate dateStart, String title, String description) {
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
