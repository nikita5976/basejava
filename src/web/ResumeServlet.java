package web;

import webapp.Config;
import webapp.exception.NotExistStorageException;
import webapp.model.ContactType;
import webapp.model.ListSection;
import webapp.model.Resume;
import webapp.model.SectionType;
import webapp.storage.SqlStorage;
import webapp.storage.Storage;

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
        String fullName = request.getParameter("fullName");
        fullName = fullName.trim();
        if (fullName.equals("")) {
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
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        String personal = request.getParameter("PERSONAL");
        if (personal != null && personal.trim().length() != 0) {
            r.setSectionPersonal(personal.trim());
        } else {
            r.removeTextSection(SectionType.PERSONAL);
        }

        String objective = request.getParameter("OBJECTIVE");
        if (objective != null && objective.trim().length() != 0) {
            r.setSectionObjective(objective.trim());
        } else {
            r.removeTextSection(SectionType.OBJECTIVE);
        }


        String[] listAchievement = request.getParameterValues("arrayAchievement");
        r.removeTextSection(SectionType.ACHIEVEMENT);
        for (String stringAchievement : listAchievement) {
            if (stringAchievement != null && stringAchievement.trim().length() != 0) {
                r.setSectionAchievement(stringAchievement.trim());
            }
        }

        String [] listQualification = request.getParameterValues("arrayQualification");
        r.removeTextSection(SectionType.QUALIFICATIONS);
        for (String stringQualification : listQualification) {
            if (stringQualification != null && stringQualification.trim().length() != 0){
                r.setSectionQualification(stringQualification.trim());
            }
        }


        if (!first) {
            storage.update(r);
        } else {
            storage.save(r);
        }
        response.sendRedirect("resume");
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
}
