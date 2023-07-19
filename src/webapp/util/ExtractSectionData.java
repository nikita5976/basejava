package webapp.util;

import webapp.model.AbstractSection;
import webapp.model.SectionType;

import java.io.Serializable;
import java.util.Map;

public class ExtractSectionData implements Serializable {

    public String extract (Map.Entry<SectionType, AbstractSection> section) {
        String text = "";
        switch (section.getKey()) {
            case PERSONAL, OBJECTIVE -> text = section.getValue().toString();
            //case ACHIEVEMENT -> null;
            //case QUALIFICATIONS -> null;
            //case EXPERIENCE -> null;
            //case EDUCATION -> null;
        }
      return text;
    }
}
