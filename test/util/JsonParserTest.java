package util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import webapp.model.CompanySection;
import webapp.model.Resume;
import webapp.model.SectionType;
import webapp.util.JsonParser;

class JsonParserTest {

    protected static final String UUID_1 = "uuid1";
    protected static final String FULL_NAME_1 = "Иванов Пётр";
    protected static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, FULL_NAME_1);
    protected static final CompanySection EDUCATION = RESUME_1.getSection(SectionType.EDUCATION);
    protected static final CompanySection EXPERIENCE = RESUME_1.getSection(SectionType.EXPERIENCE);

    @Test
    public void testResume()  {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assertions.assertEquals(RESUME_1, resume);
    }

    @Test
    void write() {
        String jsonEducation = JsonParser.write(EDUCATION, CompanySection.class);
        String jsonExperience = JsonParser.write(EXPERIENCE, CompanySection.class);
        System.out.println("Education -> \n"+jsonEducation);
        System.out.println("Experience -> \n"+ jsonExperience);
        CompanySection education2 = JsonParser.read(jsonEducation, CompanySection.class);
        CompanySection experience2 = JsonParser.read(jsonExperience, CompanySection.class);
        Assertions.assertEquals(EDUCATION, education2);
        Assertions.assertEquals(EXPERIENCE, experience2);
    }
}
