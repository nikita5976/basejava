package web.servis;

import webapp.model.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ExtractSectionData implements Serializable {

    public String extract(Map.Entry<SectionType, AbstractSection> section) {
        String text = "";
        switch (section.getKey()) {
            case PERSONAL, OBJECTIVE ->
                    text = "<h3>" + section.getKey().getTitle() + "</h3>" + "<br/>" + section.getValue().toString() + "<br/>";
            case ACHIEVEMENT, QUALIFICATIONS -> {
                text= "<h3>"+section.getKey().getTitle()+"</h3>"+"<ul>";
                List<String> sectionList = ((ListSection) section.getValue()).getSectionData();
                for (String data : sectionList) {
                    text =text +"<li>"+ data + "</li>";
                }
                text = text + "</ul>";
            }
            case EXPERIENCE,EDUCATION -> {
                text= "<h3>"+section.getKey().getTitle()+"</h3>"+"<br/>"+
                        "<table>"+
                          "<tr>"+
                            "<th>Компания</th><th background: url(../img/email.png) no-repeat center left;>Website</th><th>должность</th><th>род занятий</th><th>период работы</th>"+
                          "</tr>";
                List<Company> companyList = ((CompanySection) section.getValue()).getSectionData();
                for (Company company: companyList){
                text = text+
                          "<tr>"+
                             "<td>...</td>"+
                          "</tr>";

                }
                text = text+"</table>";
            }
        }
        return text;
    }
}
