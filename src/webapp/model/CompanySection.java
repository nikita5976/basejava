package webapp.model;

import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection {
    private static final long  serialVersionUID = 1L;
    private final List<Company> companySection;

    protected CompanySection(List<Company> companySection) {
        this.companySection = companySection;
    }

    public List<Company> getSectionData() {
        return companySection;
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
