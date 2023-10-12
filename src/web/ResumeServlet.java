package web;

import webapp.Config;
import webapp.exception.NotExistStorageException;
import webapp.model.*;
import webapp.storage.SqlStorage;
import webapp.storage.Storage;
import webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //storage = Config.get().getStorage();
        storage = new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword());

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = parsingHTML(request.getParameter("fullName"));
        if (HtmlUtil.isEmpty(fullName)) {
            response.sendRedirect("resume");
            return;
        }
        boolean first = false;
        Resume r;
        try {
            r = storage.get(uuid);
        } catch (NotExistStorageException exception) {
            first = true;
            r = new Resume(uuid, fullName);
        }
        r.setFullName(fullName);

        for (ContactType type : ContactType.values()) {
            String value = parsingHTML(request.getParameter(type.name()));
            if (HtmlUtil.isEmpty(value)) {
                r.getContacts().remove(type);
            } else {
                r.setContact(type, value);
            }
        }

        String personal = parsingHTML(request.getParameter("PERSONAL"));
        if (HtmlUtil.isEmpty(personal)) {
            r.removeSection(SectionType.PERSONAL);
        } else {
            r.setSectionPersonal(personal);
        }

        String objective = parsingHTML(request.getParameter("OBJECTIVE"));
        if (HtmlUtil.isEmpty(objective)) {
            r.removeSection(SectionType.OBJECTIVE);
        } else {
            r.setSectionObjective(objective);
        }

        String[] Achievement = request.getParameterValues("arrayAchievement");
        r.removeSection(SectionType.ACHIEVEMENT);
        String stringAchievement = Achievement[0];
        if (HtmlUtil.isEmpty(stringAchievement)) {
        } else {
            String[] listAchievement = stringAchievement.split("\n");
            for (String achievement : listAchievement) {
                if (HtmlUtil.isEmpty(achievement)) {
                } else {
                    r.setSectionAchievement(parsingHTML(achievement));
                }
            }
        }

        String[] Qualification = request.getParameterValues("arrayQualification");
        r.removeSection(SectionType.QUALIFICATIONS);
        String stringQualification = Qualification[0];
        if (HtmlUtil.isEmpty(stringQualification)) {
        } else {
            String[] listQualification = stringQualification.split("\n");
            for (String qualification : listQualification) {
                if (HtmlUtil.isEmpty(qualification)) {
                } else {
                    r.setSectionQualification(parsingHTML(qualification));
                }
            }
        }

        String[] newExperienceName = request.getParameterValues("newEXPERIENCEcompanyName");
        String[] newExperienceWebsite = request.getParameterValues("newEXPERIENCEcompanyWebsite");
        String[] newExperienceStartDateM = request.getParameterValues("newEXPERIENCEstartDateM");
        String[] newExperienceStartDateY = request.getParameterValues("newEXPERIENCEstartDateY");
        String[] newExperienceEndDateM = request.getParameterValues("newEXPERIENCEendDateM");
        String[] newExperienceEndDateY = request.getParameterValues("newEXPERIENCEendDateY");
        String[] newExperienceTitle = request.getParameterValues("newEXPERIENCEtitle");
        String[] newExperienceDescription = request.getParameterValues("newEXPERIENCEdescription");
        r.removeSection(SectionType.EXPERIENCE);
        if (HtmlUtil.isEmpty(newExperienceName[0])) {
        } else {
            if (isNumeric(newExperienceStartDateM[0]) & isNumeric(newExperienceStartDateY[0])
                    & isNumeric(newExperienceEndDateM[0]) & isNumeric(newExperienceEndDateY[0])) {
                r.setSectionExperience(Integer.parseInt(parsingHTML(newExperienceStartDateM[0])),
                        Integer.parseInt(parsingHTML(newExperienceStartDateY[0])),
                        Integer.parseInt(parsingHTML(newExperienceEndDateM[0])),
                        Integer.parseInt(parsingHTML(newExperienceEndDateY[0])),
                        parsingHTML(newExperienceName[0]),
                        parsingHTML(newExperienceWebsite[0]),
                        parsingHTML(newExperienceTitle[0]),
                        parsingHTML(newExperienceDescription[0]));
            }
        }
        String experienceId = request.getParameter("experienceId");
        if (experienceId != null) {
            int iEx = Integer.parseInt(experienceId);
            for (int i = 0; i <= iEx; i++) {
                String[] experienceName = request.getParameterValues("EXPERIENCEcompany" + i + "Name");
                String[] experienceWebsite = request.getParameterValues("EXPERIENCEcompany" + i + "Website");
                String[] experienceStartDateM = request.getParameterValues("EXPERIENCE" + i + "startDateM");
                String[] experienceStartDateY = request.getParameterValues("EXPERIENCE" + i + "startDateY");
                String[] experienceEndDateM = request.getParameterValues("EXPERIENCE" + i + "endDateM");
                String[] experienceEndDateY = request.getParameterValues("EXPERIENCE" + i + "endDateY");
                String[] experienceTitle = request.getParameterValues("EXPERIENCE" + i + "title");
                String[] experienceDescription = request.getParameterValues("EXPERIENCE" + i + "description");
                for (int j = 0; j <= (experienceTitle.length - 1); j++) {
                    if (!HtmlUtil.isEmpty(experienceName[0]) & isNumeric(experienceStartDateM[j]) & isNumeric(experienceStartDateY[j])
                            & isNumeric(experienceEndDateM[j]) & isNumeric(experienceEndDateY[j]) & !HtmlUtil.isEmpty(experienceTitle[j])) {
                        r.setSectionExperience(Integer.parseInt(parsingHTML(experienceStartDateM[j])),
                                Integer.parseInt(parsingHTML(experienceStartDateY[j])),
                                Integer.parseInt(parsingHTML(experienceEndDateM[j])),
                                Integer.parseInt(parsingHTML(experienceEndDateY[j])),
                                parsingHTML(experienceName[0]),
                                parsingHTML(experienceWebsite[0]),
                                parsingHTML(experienceTitle[j]),
                                parsingHTML(experienceDescription[j]));
                    }
                }
            }
        }

        String[] newEducationName = request.getParameterValues("newEDUCATIONcompanyName");
        String[] newEducationWebsite = request.getParameterValues("newEDUCATIONcompanyWebsite");
        String[] newEducationStartDateM = request.getParameterValues("newEDUCATIONstartDateM");
        String[] newEducationStartDateY = request.getParameterValues("newEDUCATIONstartDateY");
        String[] newEducationEndDateM = request.getParameterValues("newEDUCATIONendDateM");
        String[] newEducationEndDateY = request.getParameterValues("newEDUCATIONendDateY");
        String[] newEducationTitle = request.getParameterValues("newEDUCATIONtitle");
        r.removeSection(SectionType.EDUCATION);
        if (HtmlUtil.isEmpty(newEducationName[0])) {
        } else {
            if (isNumeric(newEducationStartDateM[0]) & isNumeric(newEducationStartDateY[0])
                    & isNumeric(newEducationEndDateM[0]) & isNumeric(newEducationEndDateY[0])) {
                r.setSectionEducation(Integer.parseInt(parsingHTML(newEducationStartDateM[0])),
                        Integer.parseInt(parsingHTML(newEducationStartDateY[0])),
                        Integer.parseInt(parsingHTML(newEducationEndDateM[0])),
                        Integer.parseInt(parsingHTML(newEducationEndDateY[0])),
                        parsingHTML(newEducationName[0]),
                        parsingHTML(newEducationWebsite[0]),
                        parsingHTML(newEducationTitle[0]));
            }
        }
        String educationId = request.getParameter("educationId");
        if (educationId != null) {
            int iEd = Integer.parseInt(educationId);
            for (int i = 0; i <= iEd; i++) {
                String[] educationName = request.getParameterValues("EDUCATIONcompany" + i + "Name");
                String[] educationWebsite = request.getParameterValues("EDUCATIONcompany" + i + "Website");
                String[] educationStartDateM = request.getParameterValues("EDUCATION" + i + "startDateM");
                String[] educationStartDateY = request.getParameterValues("EDUCATION" + i + "startDateY");
                String[] educationEndDateM = request.getParameterValues("EDUCATION" + i + "endDateM");
                String[] educationEndDateY = request.getParameterValues("EDUCATION" + i + "endDateY");
                String[] educationTitle = request.getParameterValues("EDUCATION" + i + "title");
                for (int j = 0; j <= (educationTitle.length - 1); j++) {
                    if (!HtmlUtil.isEmpty(educationName[0]) & isNumeric(educationStartDateM[j]) & isNumeric(educationStartDateY[j])
                            & isNumeric(educationEndDateM[j]) & isNumeric(educationEndDateY[j]) & !HtmlUtil.isEmpty(educationTitle[j])) {
                        r.setSectionEducation(Integer.parseInt(parsingHTML(educationStartDateM[j])),
                                Integer.parseInt(parsingHTML(educationStartDateY[j])),
                                Integer.parseInt(parsingHTML(educationEndDateM[j])),
                                Integer.parseInt(parsingHTML(educationEndDateY[j])),
                                parsingHTML(educationName[0]),
                                parsingHTML(educationWebsite[0]),
                                parsingHTML(educationTitle[j]));
                    }
                }
            }
        }


        if (!first) {
            storage.update(r);
        } else {
            storage.save(r);
        }
        String[] flag = request.getParameterValues("nextCompany");
        if (flag[0].equals("edit")) {
            response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
        } else {
            response.sendRedirect("resume");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;

        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "edit" -> {
                r = storage.get(uuid);
                List<String> achievement;
                List<String> qualification;
                ListSection achievementSection = r.getSection(SectionType.ACHIEVEMENT);
                ListSection qualificationSection = r.getSection(SectionType.QUALIFICATIONS);
                if (achievementSection == null) {
                    achievement = new ArrayList<>();
                } else {
                    achievement = achievementSection.getSectionData();
                }
                request.setAttribute("achievement", achievement);
                if (qualificationSection == null) {
                    qualification = new ArrayList<>();
                } else {
                    qualification = qualificationSection.getSectionData();
                }
                request.setAttribute("qualification", qualification);
            }
            case "view" -> r = storage.get(uuid);
            case "new" -> {
                r = new Resume("  ");
                List<String> achievement = new ArrayList<>();
                List<String> qualification = new ArrayList<>();
                request.setAttribute("achievement", achievement);
                request.setAttribute("qualification", qualification);
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    protected String parsingHTML(String text) {
        return text.replaceAll("(<.*?>)|(&.*?;)", " ").replaceAll("\\s{2,}", " ").trim();
    }

    private static boolean isNumeric(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
