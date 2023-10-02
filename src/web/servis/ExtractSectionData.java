package web.servis;

import webapp.model.*;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ExtractSectionData implements Serializable {

    public String extract(Map.Entry<SectionType, AbstractSection> section) {
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String text = "";
        switch (section.getKey()) {
            case PERSONAL, OBJECTIVE ->
                    text = "<h3>" + section.getKey().getTitle() + "</h3>" + "<br/>" + section.getValue().toString() + "<br/>";
            case ACHIEVEMENT, QUALIFICATIONS -> {
                text = "<h3>" + section.getKey().getTitle() + "</h3>" + "<ul>";
                List<String> sectionList = ((ListSection) section.getValue()).getSectionData();
                for (String data : sectionList) {
                    text = text + "<li>" + data + "</li>";
                }
                text = text + "</ul>";
            }
            case EXPERIENCE, EDUCATION -> {
                text = "<h3>" + section.getKey().getTitle() + "</h3>" + "<br/>";
                List<Company> companyList = ((CompanySection) section.getValue()).getSectionData();
                for (Company company : companyList) {
                    text = text +
                            "<h3> <a href=\"" + company.getWebsite() + "\">" + company.getName() + "</a></h3><br/>";
                    List<Company.Period> periodList = company.getPeriod();
                    for (Company.Period period : periodList) {
                        text = text + "<table>"+
                                "<tr><td>" +period.getDateStart().format(formatDate)+
                                " - </td><td> "+period.getDateEnd().format(formatDate)+
                                "</td><td><h4>" +period.getTitle()+ "</h4></td></tr>" +
                                "<tr><td> </td><td> </td><td>"+period.getDescription()+" </td></tr>" +
                                "</table>";
                    }
                }
            }
        }
        return text;
    }
}
