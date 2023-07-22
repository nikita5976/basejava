<%@ page import="webapp.model.ContactType" %>
<%@ page import="webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="webapp.model.Resume" scope="request"/>
    <jsp:useBean id="achievement" type="java.util.List" scope="request"/>
    <jsp:useBean id="qualification" type="java.util.List" scope="request"/>

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

        <h3><%=SectionType.PERSONAL.getTitle()%>
        </h3>
        <input type="text" name="PERSONAL" size=130 value="${resume.getSection(SectionType.PERSONAL)}"><br/>

        <h3><%=SectionType.OBJECTIVE.getTitle()%>
        </h3>
        <input type="text" name="OBJECTIVE" size=130 value="${resume.getSection(SectionType.OBJECTIVE)}"><br/>

        <h3><%=SectionType.ACHIEVEMENT.getTitle()%></h3>
        <c:forEach var="stringAchievement" items="<%=achievement%>">
            <dl>
                <dd><textarea name="arrayAchievement" rows="4" cols="120"> ${stringAchievement}</textarea></dd>
            </dl>
        </c:forEach>
        <input type="text" name="arrayAchievement" size="130" placeholder="Дополните Ваши достижения">
        <br/>
        <br/>

        <h3><%=SectionType.QUALIFICATIONS.getTitle()%></h3>
        <c:forEach var="stringQualification" items="<%=qualification%>">
            <dl>
                <dd><textarea  name="arrayQualification" rows="4" cols="120"> ${stringQualification} </textarea></dd>
            </dl>
        </c:forEach>
        <input type="text" name="arrayQualification" size="130" placeholder="Дополните сведения о Вашей квалификации">
        <br/>
        <br/>


        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
