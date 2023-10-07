<%@ page import="webapp.model.ContactType" %>
<%@ page import="webapp.model.SectionType" %>
<%@ page import="webapp.model.ListSection" %>
<%@ page import="webapp.util.DateUtil" %>
<%@ page import="webapp.model.CompanySection" %>
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
        <h3><%=SectionType.EXPERIENCE.getTitle()%></h3>
        <dl>
            <dt>Название учереждения:</dt>
            <dd><input type="text" name='companyName' size=100 placeholder="добавте название организации" value></dd>
        </dl>
        <dl>
            <dt>Сайт учереждения:</dt>
            <dd><input type="text" name='companyWebsite' size=100 placeholder="добавте website"></dd>
        </dl>
        <br>
        <button name="nextCompany"  value="save" type="submit">продолжить</button>
        <c:if test="${resume.getSection(SectionType.EXPERIENCE)!=null}">
        <c:forEach var="Company" items="<%=((CompanySection) resume.getSection(SectionType.EXPERIENCE)).getSectionData()%>"
                   varStatus="counter">
            <dl>
                <dt>Название учереждения:</dt>
                <dd><input type="text" name='companyName' size=100 value="${Company.name}"></dd>
            </dl>
            <dl>
                <dt>Сайт учереждения:</dt>
                <dd><input type="text" name='companyWebsite' size=100 value="${Company.website}"></dd>
                </dd>
            </dl>
            <br>
            <div style="margin-left: 30px">
                <dl>
                    <dt>Начальная дата:</dt>
                    <dd>
                        <input type="text" name="EXPERIENCEstartDate" size=10
                               value placeholder="MM/yyyy">
                    </dd>
                </dl>
                <dl>
                    <dt>Конечная дата:</dt>
                    <dd>
                        <input type="text" name="EXPERIENCEendDate" size=10
                               value placeholder="MM/yyyy">
                </dl>
                <dl>
                    <dt>Должность:</dt>
                    <dd><input type="text" name='EXPERIENCEtitle' size=75
                               value placeholder="наименование должнсти">
                </dl>
                <dl>
                    <dt>Описание:</dt>
                    <dd><textarea name="EXPERIENCEdescription" rows=5
                                  cols=75></textarea></dd>
                </dl>
                <br>
                <button name="nextCompany"  value="save" type="submit">продолжить</button>
                <c:forEach var="period" items="${Company.period}">
                    <jsp:useBean id="period" type="webapp.model.Company.Period"/>
                    <dl>
                        <dt>Начальная дата:</dt>
                        <dd>
                            <input type="text" name="EXPERIENCE${counter.index}startDate" size=10
                                   value="<%=DateUtil.format(period.getDateStart())%>" placeholder="MM/yyyy">
                        </dd>
                    </dl>
                    <dl>
                        <dt>Конечная дата:</dt>
                        <dd>
                            <input type="text" name="EXPERIENCE${counter.index}endDate" size=10
                                   value="<%=DateUtil.format(period.getDateEnd())%>" placeholder="MM/yyyy">
                    </dl>
                    <dl>
                        <dt>Должность:</dt>
                        <dd><input type="text" name='EXPERIENCE${counter.index}title' size=75
                                   value="${period.title}">
                    </dl>
                    <dl>
                        <dt>Описание:</dt>
                        <dd><textarea name="EXPERIENCE${counter.index}description" rows=5
                                      cols=75 >${period.description}</textarea></dd>
                    </dl>
                </c:forEach>
            </div>
        </c:forEach>
        </c:if>
        <br>
        <br>
        <br>
        <br/>
        <h3><%=SectionType.EDUCATION.getTitle()%></h3>
        <dl>
            <dt>Название учереждения:</dt>
            <dd><input type="text" name='schoolName' size=100 placeholder="добавте название учебного заведения" value></dd>
        </dl>
        <dl>
            <dt>Сайт учереждения:</dt>
            <dd><input type="text" name='schoolWebsite' size=100 placeholder="добавте website"></dd>
        </dl>
        <br>
        <button name="nextCompany"  value="save" type="submit">продолжить</button>
        <c:if test="${resume.getSection(SectionType.EDUCATION)!=null}">
            <c:forEach var="Company" items="<%=((CompanySection) resume.getSection(SectionType.EDUCATION)).getSectionData()%>"
                       varStatus="counter">
                <dl>
                    <dt>Название учереждения:</dt>
                    <dd><input type="text" name='schoolName' size=100 value="${Company.name}"></dd>
                </dl>
                <dl>
                    <dt>Сайт учереждения:</dt>
                    <dd><input type="text" name='schoolWebsite' size=100 value="${Company.website}"></dd>
                    </dd>
                </dl>
                <br>
                <div style="margin-left: 30px">
                    <dl>
                        <dt>Начальная дата:</dt>
                        <dd>
                            <input type="text" name="EDUCATIONstartDate" size=10
                                   value placeholder="MM/yyyy">
                        </dd>
                    </dl>
                    <dl>
                        <dt>Конечная дата:</dt>
                        <dd>
                            <input type="text" name="EDUCATIONendDate" size=10
                                   value placeholder="MM/yyyy">
                    </dl>
                    <dl>
                        <dt>Курс:</dt>
                        <dd><input type="text" name='EDUCATIONtitle' size=75
                                   value placeholder="наименование курса">
                    </dl>
                    <br>
                    <button name="nextCompany"  value="save" type="submit">продолжить</button>
                    <c:forEach var="period2" items="${Company.period}">
                        <jsp:useBean id="period2" type="webapp.model.Company.Period"/>
                        <dl>
                            <dt>Начальная дата:</dt>
                            <dd>
                                <input type="text" name="EDUCATION${counter.index}startDate" size=10
                                       value="<%=DateUtil.format(period2.getDateStart())%>" placeholder="MM/yyyy">
                            </dd>
                        </dl>
                        <dl>
                            <dt>Конечная дата:</dt>
                            <dd>
                                <input type="text" name="EDUCATION${counter.index}endDate" size=10
                                       value="<%=DateUtil.format(period2.getDateEnd())%>" placeholder="MM/yyyy">
                        </dl>
                        <dl>
                            <dt>Курс:</dt>
                            <dd><input type="text" name='EDUCATION${counter.index}title' size=75
                                       value="${period2.title}">
                        </dl>
                    </c:forEach>
                </div>
            </c:forEach>
        </c:if>
        <br>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
