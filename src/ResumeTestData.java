import webapp.model.Resume;
import webapp.model.SectionType;

import java.util.Arrays;

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

        System.out.println(testResume.getFullName());
        System.out.println("----------------------------------------------");
        System.out.println("Контакты");
        String[] nameContact = testResume.getNamesContacts();
        for (String name: nameContact){
            System.out.println(name +"  "+testResume.getContact(name));
        }
        System.out.println("----------------------------------------------");
        printSection(SectionType.OBJECTIVE);
        printSection(SectionType.PERSONAL);
        printSection(SectionType.ACHIEVEMENT);
        printSection(SectionType.QUALIFICATIONS);
        printSection(SectionType.EXPERIENCE);
        printSection(SectionType.EDUCATION);
    }

    private static void printSection(SectionType type){
        int size = testResume.getSectionSize(type);
        System.out.println(type.getTitle());
        for (int i=0; i< size; i++ ){
            System.out.println(Arrays.toString(testResume.getSection(type)));
            System.out.println();
        }
        System.out.println("----------------------------------------------");
    }

    private static void fillingContactTestResume() {
        testResume.setContact("Тел.: ", "+7(921) 855-0482");
        testResume.setContact("Skype: ", "skype:grigory.kislin");
        testResume.setContact("Почта: ", "gkislin@yandex.ru");
        testResume.setContact("Профиль LinkedIn", "https://www.linkedin.com/in/gkislin");
        testResume.setContact("Профиль GitHub", "https://github.com/gkislin");
        testResume.setContact("Профиль Stackoverflow", "https://stackoverflow.com/users/548473");
        testResume.setContact("Домашняя страница", "http://gkislin.ru/");
    }

    private static void filingPersonalTestResume() {
        String[] personalData = {"Аналитический склад ума, сильная логика, креативность, инициативность." +
                " Пурист кода и архитектуры."};
        testResume.setSection(SectionType.PERSONAL, personalData );
    }

    private static void filingObjectiveTestResume (){
        String[] objectiveData = {"Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям"};
        testResume.setSection(SectionType.OBJECTIVE, objectiveData);
    }

    private static void filingAchievementTestResume(){
        String[] achievementData1 = {"Организация команды и успешная реализация " +
                "Java проектов для сторонних заказчиков: приложения автопарк на " +
                "стеке Spring Cloud/микросервисы, система мониторинга показателей " +
                "спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, " +
                "многомодульный Spring Boot + Vaadin проект для комплексных DIY смет"};
        testResume.setSection(SectionType.ACHIEVEMENT, achievementData1);
        String[] achievementData2 = {"С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven." +
                " Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". " +
                "Организация онлайн стажировок и ведение проектов. Более 3500 выпускников."};
        testResume.setSection(SectionType.ACHIEVEMENT, achievementData2);
        String[] achievementData3 = {"Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk."};
        testResume.setSection(SectionType.ACHIEVEMENT, achievementData3);
        String[] achievementData4 = {"Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, " +
                "Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."};
        testResume.setSection(SectionType.ACHIEVEMENT, achievementData4);
        String[] achievementData5 = {"Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, " +
                "Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга."};
        testResume.setSection(SectionType.ACHIEVEMENT, achievementData5);
        String[] achievementData6 = {"Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему " +
                "мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django)."};
        testResume.setSection(SectionType.ACHIEVEMENT, achievementData6);
        String[] achievementData7 = {"Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."};
        testResume.setSection(SectionType.ACHIEVEMENT, achievementData7);
    }

    private static void filingQualificationsTestResume(){
        String[] qualificationsData1 = {"EE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2"};
        testResume.setSection(SectionType.QUALIFICATIONS, qualificationsData1);
        String[] qualificationsData2 = {"Version control: Subversion, Git, Mercury, ClearCase, Perforce"};
        testResume.setSection(SectionType.QUALIFICATIONS, qualificationsData2);
        String[] qualificationsData3 = {"DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB"};
        testResume.setSection(SectionType.QUALIFICATIONS, qualificationsData3);
        String[] qualificationsData4 = {"Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy"};
        testResume.setSection(SectionType.QUALIFICATIONS, qualificationsData4);
        String[] qualificationsData5 = {"XML/XSD/XSLT, SQL, C/C++, Unix shell scripts"};
        testResume.setSection(SectionType.QUALIFICATIONS, qualificationsData5);
        String[] qualificationsData6 = {"Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot)," +
                " JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit," +
                " Selenium (htmlelements)."};
        testResume.setSection(SectionType.QUALIFICATIONS, qualificationsData6);
        String[] qualificationsData7 = {"Python: Django."};
        testResume.setSection(SectionType.QUALIFICATIONS, qualificationsData7);
        String[] qualificationsData8 = {"JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js"};
        testResume.setSection(SectionType.QUALIFICATIONS, qualificationsData8);
        String[] qualificationsData9 = {"Scala: SBT, Play2, Specs2, Anorm, Spray, Akka"};
        testResume.setSection(SectionType.QUALIFICATIONS, qualificationsData9);
        String[] qualificationsData10 = {"Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB," +
                " JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT."};
        testResume.setSection(SectionType.QUALIFICATIONS, qualificationsData10);
        String[] qualificationsData11 = {"Инструменты: Maven + plugin development, Gradle, настройка Ngnix"};
        testResume.setSection(SectionType.QUALIFICATIONS, qualificationsData11);
        String[] qualificationsData12 = {"администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis," +
                " Bonita, pgBouncer"};
        testResume.setSection(SectionType.QUALIFICATIONS, qualificationsData12);
        String[] qualificationsData13 = {"Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                "архитектурных шаблонов, UML, функционального программирования"};
        testResume.setSection(SectionType.QUALIFICATIONS, qualificationsData13);
        String[] qualificationsData14 = {"Родной русский, английский \"upper intermediate\""};
        testResume.setSection(SectionType.QUALIFICATIONS, qualificationsData14);
    }

    private static void filingExperienceTestResume(){
    String[] experienceData1 = {"10/2013 - Сейчас", "Java Online Projects", "http://javaops.ru/",
            "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."};
    testResume.setSection(SectionType.EXPERIENCE, experienceData1);
        String[] experienceData2 = {"10/2014 - 01/2016", "Wrike", "https://www.wrike.com/",
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1," +
                " OAuth2, JWT SSO."};
        testResume.setSection(SectionType.EXPERIENCE, experienceData2);
        String[] experienceData3 = {"04/2012 - 10/2014", "RIT Center", "",
                "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, " +
                "ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. " +
                "Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                "сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера " +
                "документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, " +
                "Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"};
        testResume.setSection(SectionType.EXPERIENCE, experienceData3);
        String[] experienceData4 = {"12/2010 - 04/2012", "Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle)." +
                " Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа " +
                "результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."};
        testResume.setSection(SectionType.EXPERIENCE, experienceData4);
        String[] experienceData5 = {"06/2008 - 12/2010", "Yota", "https://www.yota.ru/",
                "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, " +
                "статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"};
        testResume.setSection(SectionType.EXPERIENCE, experienceData5);
        String[] experienceData6 = {"03/2007 - 06/2008", "Enkata", "http://enkata.com/",
                "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного " +
                "J2EE приложения (OLAP, Data mining)."};
        testResume.setSection(SectionType.EXPERIENCE, experienceData6);
        String[] experienceData7 = {"01/2005 - 02/2007", "Siemens AG", "https://www.siemens.com/ru/ru/home.html",
                "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка " +
                "ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."};
        testResume.setSection(SectionType.EXPERIENCE, experienceData7);
        String[] experienceData8 = {"09/1997 - 01/2005", "Alcatel", "http://www.alcatel.ru/",
                "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной " +
                "станции Alcatel 1000 S12 (CHILL, ASM)."};
        testResume.setSection(SectionType.EXPERIENCE, experienceData8);
    }

    private static void filingEducationTestResume(){
        String[] educationData1 = {"03/2013 - 05/2013", "Coursera", "https://www.coursera.org/course/progfun",
        "Functional Programming Principles in Scala' by Martin Odersky"};
        testResume.setSection(SectionType.EDUCATION, educationData1);
        String[] educationData2 = {"03/2011 - 04/2011", "Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML."};
        testResume.setSection(SectionType.EDUCATION, educationData2);
        String[] educationData3 = {"01/2005 - 04/2005", "Siemens AG", "http://www.siemens.ru/",
                "3 месяца обучения мобильным IN сетям (Берлин)"};
        testResume.setSection(SectionType.EDUCATION, educationData3);
        String[] educationData4 = {"09/1997 - 03/1998", "Alcatel", "http://www.alcatel.ru/",
                "6 месяцев обучения цифровым телефонным сетям (Москва)"};
        testResume.setSection(SectionType.EDUCATION, educationData4);
        String[] educationData5 = {"09/1993 - 07/1996", "Санкт-Петербургский национальный исследовательский " +
                "университет информационных технологий, механики и оптики", "http://www.ifmo.ru/",
                "Аспирантура (программист С, С++)"};
        testResume.setSection(SectionType.EDUCATION, educationData5);
        String[] educationData6 = {"09/1987 - 07/1993", "Санкт-Петербургский национальный исследовательский \" +\n" +
                "                \"университет информационных технологий, механики и оптики", "http://www.ifmo.ru/",
                "Инженер (программист Fortran, C)"};
        testResume.setSection(SectionType.EDUCATION, educationData6);
        String[] educationData7 = {"09/1984 - 06/1987", "Заочная физико-техническая школа при МФТИ", "https://mipt.ru/",
                "Закончил с отличием"};
        testResume.setSection(SectionType.EDUCATION, educationData7);
    }
}
