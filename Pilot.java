package flightManagementSystem;

public class Pilot {
    private String name;
    private int yearOfExperience;

    public Pilot(String name, int yearOfExperience) {
        this.name = name;
        this.yearOfExperience =  yearOfExperience;
    }

    public String getName(){ return name; }
}
