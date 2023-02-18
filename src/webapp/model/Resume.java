package webapp.model;

import java.util.*;

public class Resume {
    private String uuid;
    private String fullName;
    private final ObjectivePersonal objective = new ObjectivePersonal();
    private final ObjectivePersonal personal = new ObjectivePersonal();
    private final AchievementQualifications achievement = new AchievementQualifications();
    private final AchievementQualifications qualifications = new AchievementQualifications();
    private final ExperienceEducation experience = new ExperienceEducation();
    private final ExperienceEducation education = new ExperienceEducation();

    public Resume() {

    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return fullName + " uuid " + uuid;
    }

    public String[] getSection(SectionType section) {
        return switch (section) {
            case OBJECTIVE -> objective.getOp();
            case PERSONAL -> personal.getOp();
            case ACHIEVEMENT -> achievement.getAq();
            case QUALIFICATIONS -> qualifications.getAq();
            case EDUCATION -> education.getEe();
            case EXPERIENCE -> experience.getEe();
        };
    }

    public void setSection(SectionType section, String[] data) {
        switch (section) {
            case OBJECTIVE:
                objective.setOp(data);
                break;
            case PERSONAL:
                personal.setOp(data);
                break;
            case ACHIEVEMENT:
                achievement.setAq(data);
            case QUALIFICATIONS:
                qualifications.setAq(data);
            case EDUCATION:
                education.setEe(data);
            case EXPERIENCE:
                experience.setEe(data);
        }
    }

    private final static class ObjectivePersonal {
        private final StringBuilder op = new StringBuilder();

        private void setOp(String[] inData) {
            op.append(inData[0]);
        }

        private String[] getOp() {
            String[] a = {op.toString()};
            return a;
        }
    }

    private final static class AchievementQualifications {
        private final LinkedList<String> aq = new LinkedList<>();

        private void setAq(String[] inData) {
            aq.addLast(inData[0]);
        }

        private String[] getAq() {
            String ach = aq.pollFirst();
            aq.addLast(ach);
            String[] a = {ach};
            return a;
        }
    }

    private final static class ExperienceEducation {

        private final LinkedList<String[]> ee = new LinkedList<>();

        private void setEe(String[] data) {
            ee.addLast(data);
        }

        private String[] getEe() {
            String[] data = ee.pollFirst();
            ee.addLast(data);
            return data;
        }
    }
}
