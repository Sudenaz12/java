package HayvanatBahçesiYönetimi;

public class kemirgenler extends Hayvan {
    public kemirgenler(double weight,String türName,int age){
        super (weight,türName,age);

    }
    @Override
    public double getDosage(){
        return 2.5;
    }
    @Override
    public String getFeedSchedule() {
        return "Gece 00:00 (Tohum ve Sebze)";
    }
}
