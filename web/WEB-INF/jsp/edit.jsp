<%@ page import="webapp.model.ContactType" %>
<%@ page import="webapp.model.SectionType" %>
<%@ page import="webapp.model.ListSection" %>
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
        <c:if test="${resume.getSection(SectionType.PERSONAL)!=null}">
            <h3><%=SectionType.PERSONAL.getTitle()%></h3>
            <textarea name="PERSONAL" cols=75 rows=5><%=resume.getSection(SectionType.PERSONAL)%></textarea>
        </c:if>
        <c:if test="${resume.getSection(SectionType.PERSONAL)==null}">
            <h3><%=SectionType.PERSONAL.getTitle()%></h3>
            <textarea name="PERSONAL" cols=75 rows=5 placeholder="добавте личные качества"></textarea>
        </c:if>
         <c:if test="${resume.getSection(SectionType.OBJECTIVE)!=null}">
        <h3><%=SectionType.OBJECTIVE.getTitle()%></h3>
        <textarea name="OBJECTIVE" cols=75 rows=5><%=resume.getSection(SectionType.OBJECTIVE)%></textarea>
         </c:if>
        <c:if test="${resume.getSection(SectionType.OBJECTIVE)==null}">
            <h3><%=SectionType.OBJECTIVE.getTitle()%></h3>
            <textarea name="OBJECTIVE" cols=75 rows=5 placeholder="добавте текущую позицию"></textarea>
        </c:if>
        <br/>
        <c:if test="${resume.getSection(SectionType.ACHIEVEMENT)!=null}">
        <h3><%=SectionType.ACHIEVEMENT.getTitle()%></h3>
        <textarea name='arrayAchievement' cols=75
                  rows=25 ><%=String.join("\n \n",
                ((ListSection) resume.getSection(SectionType.ACHIEVEMENT)).getSectionData())%></textarea>
        </c:if>
        <c:if test="${resume.getSection(SectionType.ACHIEVEMENT)==null}">
            <h3><%=SectionType.ACHIEVEMENT.getTitle()%></h3>
            <textarea name='arrayAchievement' cols=75 rows=10 placeholder="добавте достижения"></textarea>
        </c:if>
        <br/>
        <br/>
        <c:if test="${resume.getSection(SectionType.QUALIFICATIONS)!=null}">
        <h3><%=SectionType.QUALIFICATIONS.getTitle()%></h3>
        <textarea name='arrayQualification' cols=75
                  rows=25 ><%=String.join("\n \n",
                ((ListSection) resume.getSection(SectionType.QUALIFICATIONS)).getSectionData())%></textarea>
        </c:if>
        <c:if test="${resume.getSection(SectionType.QUALIFICATIONS)==null}">
            <h3><%=SectionType.QUALIFICATIONS.getTitle()%></h3>
           <textarea name='arrayQualification' cols=75 rows=10 placeholder="добавте квалификацию"></textarea>
        </c:if>
        <br/>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
