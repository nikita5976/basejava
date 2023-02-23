import webapp.model.*;

import java.util.Map;

public class ResumeTestData {
    private final static Resume testResume = new Resume("Григорий Кислин");

    public static void main(String[] args) {
        fillingContactTestResume();
        filingPersonalTestResume();
        filingObjectiveTestResume();
        filingAchievementTestResume();
        filingQualificationsTestResume();
        filingExperienceTestResume();
        filingEducationTestResume();
        Map<SectionType, AbstractSection> sectionMap = testResume.getResumeSection();

        System.out.println(testResume.getFullName());
        System.out.println("----------------------------------------------");
        System.out.println("Контакты");
        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType.getTitle() + "  " + testResume.getContact(contactType));
        }

        for (SectionType type : SectionType.values()) {
            System.out.println("----------------------------------------------");
            System.out.println(type.getTitle());
            System.out.println(sectionMap.get(type).toString());
        }
    }

    // заполнение
    private static void fillingContactTestResume() {
        testResume.setContact(ContactType.TELEPHONE, "+7(921) 855-0482");
        testResume.setContact(ContactType.SKYPE, "skype:grigory.kislin");
        testResume.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        testResume.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        testResume.setContact(ContactType.GITHUB, "https://github.com/gkislin");
        testResume.setContact(ContactType.STACKOVRFLOW, "https://stackoverflow.com/users/548473");
        testResume.setContact(ContactType.HOMEPAGE, "http://gkislin.ru/");
    }

    private static void filingPersonalTestResume() {
        String personalData = "Аналитический склад ума, сильная логика, креативность, инициативность." +
                " Пурист кода и архитектуры.";
        testResume.setSectionPersonal(personalData);
    }

    private static void filingObjectiveTestResume() {
        String objectiveData = "Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям";
        testResume.setSectionObjective(objectiveData);
    }

    private static void filingAchievementTestResume() {
        String achievementData1 = "Организация команды и успешная реализация " +
                "Java проектов для сторонних заказчиков: приложения автопарк на " +
                "стеке Spring Cloud/микросервисы, система мониторинга показателей " +
                "спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, " +
                "многомодульный Spring Boot + Vaadin проект для комплексных DIY смет";
        testResume.setSectionAchievement(achievementData1);
        String achievementData2 = "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven." +
                " Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". " +
                "Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.";
        testResume.setSectionAchievement(achievementData2);
        String achievementData3 = "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.";
        testResume.setSectionAchievement(achievementData3);
        String achievementData4 = "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, " +
                "Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.";
        testResume.setSectionAchievement(achievementData4);
        String achievementData5 = "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, " +
                "Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.";
        testResume.setSectionAchievement(achievementData5);
        String achievementData6 = "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему " +
                "мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).";
        testResume.setSectionAchievement(achievementData6);
        String achievementData7 = "Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.";
        testResume.setSectionAchievement(achievementData7);
    }

    private static void filingQualificationsTestResume() {
        String qualificationsData1 = "EE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2";
        testResume.setSectionQualification(qualificationsData1);
        String qualificationsData2 = "Version control: Subversion, Git, Mercury, ClearCase, Perforce";
        testResume.setSectionQualification(qualificationsData2);
        String qualificationsData3 = "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB";
        testResume.setSectionQualification(qualificationsData3);
        String qualificationsData4 = "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy";
        testResume.setSectionQualification(qualificationsData4);
        String qualificationsData5 = "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts";
        testResume.setSectionQualification(qualificationsData5);
        String qualificationsData6 = "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot)," +
                " JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit," +
                " Selenium (htmlelements).";
        testResume.setSectionQualification(qualificationsData6);
        String qualificationsData7 = "Python: Django.";
        testResume.setSectionQualification(qualificationsData7);
        String qualificationsData8 = "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js";
        testResume.setSectionQualification(qualificationsData8);
        String qualificationsData9 = "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka";
        testResume.setSectionQualification(qualificationsData9);
        String qualificationsData10 = "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB," +
                " JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.";
        testResume.setSectionQualification(qualificationsData10);
        String qualificationsData11 = "Инструменты: Maven + plugin development, Gradle, настройка Ngnix";
        testResume.setSectionQualification(qualificationsData11);
        String qualificationsData12 = "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis," +
                " Bonita, pgBouncer";
        testResume.setSectionQualification(qualificationsData12);
        String qualificationsData13 = "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                "архитектурных шаблонов, UML, функционального программирования";
        testResume.setSectionQualification(qualificationsData13);
        String qualificationsData14 = "Родной русский, английский \"upper intermediate\"";
        testResume.setSectionQualification(qualificationsData14);
    }

    private static void filingExperienceTestResume() {
        testResume.setSectionExperience("10/2013", "Сейчас", "Java Online Projects", "http://javaops.ru/",
                "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");

        testResume.setSectionExperience("10/2014", "01/2016", "Wrike", "https://www.wrike.com/",
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1," +
                        " OAuth2, JWT SSO.");

        testResume.setSectionExperience("04/2012", "10/2014", "RIT Center", "",
                "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, " +
                        "ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. " +
                        "Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                        "сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера " +
                        "документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, " +
                        "Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");

        testResume.setSectionExperience("12/2010", "04/2012", "Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle)." +
                        " Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа " +
                        "результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");

        testResume.setSectionExperience("06/2008", "12/2010", "Yota", "https://www.yota.ru/",
                "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                        "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, " +
                        "статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");

        testResume.setSectionExperience("03/2007", "06/2008", "Enkata", "http://enkata.com/",
                "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного " +
                        "J2EE приложения (OLAP, Data mining).");

        testResume.setSectionExperience("01/2005", " 02/2007", "Siemens AG", "https://www.siemens.com/ru/ru/home.html",
                "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка " +
                        "ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");

        testResume.setSectionExperience("09/1997", "01/2005", "Alcatel", "http://www.alcatel.ru/",
                "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной " +
                        "станции Alcatel 1000 S12 (CHILL, ASM).");
    }

    private static void filingEducationTestResume() {

        testResume.setSectionEducation("03/2013", "05/2013", "Coursera", "https://www.coursera.org/course/progfun",
                "Functional Programming Principles in Scala' by Martin Odersky");

        testResume.setSectionEducation("03/2011", "04/2011", "Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.");

        testResume.setSectionEducation("01/2005", "04/2005", "Siemens AG", "http://www.siemens.ru/",
                "3 месяца обучения мобильным IN сетям (Берлин)");

        testResume.setSectionEducation("09/1997", "03/1998", "Alcatel", "http://www.alcatel.ru/",
                "6 месяцев обучения цифровым телефонным сетям (Москва)");

        testResume.setSectionEducation("09/1993", "07/1996", "Санкт-Петербургский национальный исследовательский " +
                        "университет информационных технологий, механики и оптики", "http://www.ifmo.ru/",
                "Аспирантура (программист С, С++)");

        testResume.setSectionEducation("09/1987", "07/1993", "Санкт-Петербургский национальный исследовательский \" +\n" +
                        " \"университет информационных технологий, механики и оптики", "http://www.ifmo.ru/",
                "Инженер (программист Fortran, C)");

        testResume.setSectionEducation("09/1984", "06/1987", "Заочная физико-техническая школа при МФТИ", "https://mipt.ru/",
                "Закончил с отличием");
    }
}
/*  заметка на память
System.out.println("----------------------------------------------");
        System.out.println(SectionType.QUALIFICATIONS.getTitle());
        ListSection qualificationsList = (ListSection) sectionMap.get(SectionType.QUALIFICATIONS);
        for (String qualification : qualificationsList.getSectionData()) {
            System.out.println(qualification);

System.out.println("----------------------------------------------");
        System.out.println(SectionType.EDUCATION.getTitle());
        CompanySection companySection2 = (CompanySection) sectionMap.get(SectionType.EDUCATION);
        for (CompanySection.Company a : companySection2.getSectionData()) {
            for (Periods p : a.getPeriods()) {
                System.out.println(p.getDateStart() + " - " + p.getDateEnd());
            }
            System.out.println(a.getName() + "  " + a.getWebsite());
            System.out.println(a.getPosition());
        }
 */