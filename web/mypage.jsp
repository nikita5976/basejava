<%@ page import="webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="webapp.storage.SqlStorage" %>
<%@ page import="webapp.Config" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11.06.2023
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>test_page_jsp</title>
</head>
<body>
<h2>Hello ${name}</h2>
<p>Test <br/> ${test}</p>
<p> sessionID ${sessionID}</p>
<p> <%
    List<Resume> listAllResumes = new ArrayList<>();
    listAllResumes = new SqlStorage(Config.get().getDbUrl(),Config.get().getDbUser(),Config.get().getDbPassword()).getAllSorted();
    for(Resume r: listAllResumes){
        System.out.println("uuid ="+ r.getUuid());
    }
%> </p>
<p>Today <%= new java.util.Date() %>
</p>
</body>
</html>
