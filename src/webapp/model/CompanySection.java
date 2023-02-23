package webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection<CompanySection.Company, ArrayList<CompanySection.Company>> {
    private final List<Company> companySection = new ArrayList<>();

    @Override
    public void setSectionData(CompanySection.Company companySection) {
        this.companySection.add(companySection);
    }

    @Override
    public ArrayList<Company> getSectionData() {
        return new ArrayList<>(companySection);
    }

    @Override
    Company getCompany(String name, String website) {
        return new Company(name, website);
    }

    public class Company {
        private final String name;
        private final String website;
        private String position;
        private String practice = "";
        List<Periods> periods = new ArrayList<>();

        Company(String name, String website) {
            this.name = name;
            this.website = website;
        }

        void setPosition(String position) {
            this.position = position;
        }

        void setPractice(String practice) {
            this.practice = practice;
        }

        void setPeriod(String dataStart, String dataStop) {
            Periods periodsClass = new Periods(dataStart, dataStop);
            periods.add(periodsClass);
        }

        public String getName() {
            return name;
        }

        public String getWebsite() {
            return website;
        }

        public String getPosition() {
            return position;
        }

        public String getPractice() {
            return practice;
        }

        public List<Periods> getPeriods() {
            return new ArrayList<>(periods);
        }

        @Override
        public String toString() {
            return periods.toString() + " -  \n" + name + "  " + website + "\n" + position + "\n" + practice;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Company company = (Company) o;
            return name.equals(company.name) && website.equals(company.website) && position.equals(company.position)
                    && practice.equals(company.practice);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name + website + position + practice);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Company company : companySection) {
            sb.append(company.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection companySectionThis = (CompanySection) o;
        return companySection.equals(companySectionThis.companySection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companySection.hashCode());
    }
}
