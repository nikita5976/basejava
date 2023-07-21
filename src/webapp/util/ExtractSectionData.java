package webapp.util;

import webapp.model.AbstractSection;
import webapp.model.ListSection;
import webapp.model.SectionType;

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
        }
        //case EXPERIENCE -> null;
        //case EDUCATION -> null;

        return text;
    }
}
