package local.kiririnyo.stream;

public class StreamParamBean {

    private String name;
    private int schoolYear;

    public StreamParamBean(String name, int age) {
        this.name = name;
        this.schoolYear = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }
}
