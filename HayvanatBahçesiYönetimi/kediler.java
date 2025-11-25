package HayvanatBahçesiYönetimi;

public class kediler extends Hayvan {

    public kediler(double weight,String türName,int age){
          super( weight ,türName,age);
    }
    @Override
    public double getDosage(){
        return getWeight()*0.26 +getAge()*0.5;
    }
    @Override
    public String getFeedSchedule(){
        return "Öğlen Çiğ Et";
    }
}
