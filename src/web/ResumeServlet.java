package web;

import webapp.Config;
import webapp.exception.NotExistStorageException;
import webapp.model.ContactType;
import webapp.model.ListSection;
import webapp.model.Resume;
import webapp.model.SectionType;
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
            r.removeTextSection(SectionType.PERSONAL);
        } else {
            r.setSectionPersonal(personal);
        }

        String objective = parsingHTML(request.getParameter("OBJECTIVE"));
        if (HtmlUtil.isEmpty(objective)) {
            r.removeTextSection(SectionType.OBJECTIVE);
        } else {
            r.setSectionObjective(objective);
        }

        String[] listAchievement = request.getParameterValues("arrayAchievement");
        r.removeTextSection(SectionType.ACHIEVEMENT);
        for (String stringAchievement : listAchievement) {
            if (HtmlUtil.isEmpty(stringAchievement)) {}
            else {
                r.setSectionAchievement(parsingHTML(stringAchievement));
            }
        }

        String[] listQualification = request.getParameterValues("arrayQualification");
        r.removeTextSection(SectionType.QUALIFICATIONS);
        for (String stringQualification : listQualification) {
            if (HtmlUtil.isEmpty(stringQualification)) {}
            else {
                r.setSectionQualification(parsingHTML(stringQualification));
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

    protected String parsingHTML(String text) {
        return text.replaceAll("(<.*?>)|(&.*?;)", " ").replaceAll("\\s{2,}", " ").trim();
    }
}
