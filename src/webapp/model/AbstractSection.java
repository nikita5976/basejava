package webapp.model;

public abstract class AbstractSection<IN, OUT> {


    abstract void setSectionData(IN data);

    abstract OUT getSectionData();

    abstract IN getCompany(String name, String website);

}
