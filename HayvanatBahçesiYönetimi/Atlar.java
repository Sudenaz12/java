package HayvanatBahçesiYönetimi;

public class Atlar  extends Hayvan{
    public Atlar(double weight,String türName ,int age){
        super(weight, türName, age);
    }
    @Override
    public double getDosage(){
        return getWeight() * 0.10;
    }
    @Override
    public String getFeedSchedule() {
        return "Sabah 06:00 ve Akşam 18:00 (Saman)";
    }
}
