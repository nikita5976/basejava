package web;

import webapp.Config;
import webapp.model.Resume;
import webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String dbUrl = Config.get().getDbUrl();
        final String dbUser = Config.get().getDbUser();
        final String dbPassword = Config.get().getDbPassword();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        // request.setAttribute("name", (name == null ? "Hello Resumes!" : "Hello " + name + '!'));
        //  request.setAttribute("test", "проба пера");
        //  request.setAttribute("sessionID", request.getSession().getId());

        //  request.getRequestDispatcher("\\mypage.jsp").forward(request, response);

        //response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');

        List<Resume> listAllResumes;
        SqlStorage sqlStorage = new SqlStorage(dbUrl, dbUser, dbPassword);
        String fullName;
        if (uuid != null) {
            try {
                Resume resume = sqlStorage.get(uuid);
                fullName = resume.getFullName();
            } catch (webapp.exception.NotExistStorageException e) {
                fullName = "Нет такого резюме";
            }


            response.getWriter().write("uuid - " + uuid + "\n" +
                    "full name - " + fullName);
        } else {
            listAllResumes = sqlStorage.getAllSorted();
            for (Resume r : listAllResumes) {
                response.getWriter().write("\n" + "uuid = " + r.getUuid());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
