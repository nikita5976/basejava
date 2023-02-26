package webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Company {
    private final LocalDate dateStart;
    private final LocalDate dateEnd;
    private final String title;
    private final String description;
    Link link;

    Company(LocalDate dataStart, LocalDate dataEnd, String title, String description, String name, String website) {
        Objects.requireNonNull(dataStart, "dataStart must not be null");
        Objects.requireNonNull(dataEnd, "dataEnd must not be null");
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(name, "name must not be null");

        link = new Link(name, website);
        this.dateStart = dataStart;
        this.dateEnd = dataEnd;
        this.title = title;
        this.description = description;
    }

    public String getName() {
        return link.name;
    }

    public String getWebsite() {
        return link.website;
    }

    @Override
    public String toString() {
        return dateStart + " - " + dateEnd + " -  \n" + link.toString() + " -  \n" + title + " -  \n" + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return link.equals(company.link) && title.equals(company.title) && description.equals(company.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title + description) + link.hashCode();
    }


    public static class Link {
        private final String name;
        private final String website;

        public Link(String name, String website) {
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
}
