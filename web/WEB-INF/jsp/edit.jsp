<%@ page import="webapp.model.ContactType" %>
<%@ page import="webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input required type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h2>Контакты:</h2>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h2>Секции:</h2>
        <h3><%=SectionType.PERSONAL.getTitle()%></h3>
        <input type="text" name="PERSONAL" size=130 value="${resume.getSection(SectionType.PERSONAL)}"><br/>
        <h3><%=SectionType.OBJECTIVE.getTitle()%></h3>
        <input type="text" name="OBJECTIVE" size=130 value="${resume.getSection(SectionType.OBJECTIVE)}"><br/>
        <h3><%=SectionType.ACHIEVEMENT.getTitle()%></h3>
        <textarea rows=5 cols=100 name="ACHIEVEMENT" > ${resume.getSection(SectionType.ACHIEVEMENT)}"</textarea><br/>
        <input type="text" name="section" size=30 value="3"><br/>
        <hr>
        <button type="submit" >Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
