package webapp.model;

import java.util.ArrayList;
import java.util.List;

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
        private String practice;
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
    }
}
