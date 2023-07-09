package web;

import webapp.Config;
import webapp.model.ContactType;
import webapp.model.Resume;
import webapp.storage.SqlStorage;
import webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ResumeServletOld extends HttpServlet {
    private Storage storage;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //storage = Config.get().getStorage();
        storage = new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword());
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        Writer writer = response.getWriter();
        List<Resume> listAllResumes;

        String fullName;
        if (uuid != null) {
            try {
                Resume resume = storage.get(uuid);
                fullName = resume.getFullName();
            } catch (webapp.exception.NotExistStorageException e) {
                fullName = "Нет такого резюме";
            }


            writer.write("uuid - " + uuid + "\n" +
                    "full name - " + fullName);
        } else {
            listAllResumes = storage.getAllSorted();
            writer.write(
                    "<html>\n" +
                            "<head>\n" +
                            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                            "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                            "    <title>Список всех резюме</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "<p1> OLD </p1>\n" +
                            "<section>\n" +
                            "<table border=\"1\" cellpadding=\"8\" cellspacing=\"0\">\n" +
                            "    <tr>\n" +
                            "        <th>Имя</th>\n" +
                            "        <th>Email</th>\n" +
                            "    </tr>\n");
            for (Resume resume : listAllResumes) {
                writer.write( "<tr>\n" +
                        "     <td><a href=\"resumeold?uuid=" + resume.getUuid() + "\">" + resume.getFullName() + "</a></td>\n" +
                        "     <td>" + resume.getContact(ContactType.EMAIL) + "</td>\n" +
                        "</tr>\n");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
