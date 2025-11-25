package HayvanatBahçesiYönetimi;

public abstract class Hayvan {
    private double weight;
    private String türName;
    private int age;

    public Hayvan(double weight,String türName,int age){
        this.age=age;
        this.türName=türName;
        this.weight=weight;
    }
    public double getWeight(){
        return weight;
    }
    public void setTürName(String türName){
        this.türName=türName;
    }
    public void setWeight(double weight){
        if(weight >0){
            this.weight = weight;
        }else {
            System.out.println("Hata: weight negatif olamaz!");
        }
    }
    public int getAge(){
        return age;
    }
    public void setage(int age){
        if(age>=0){
            this.age=age;
        }
    }
     
    public abstract double  getDosage();//ilaç dozası
    public abstract  String   getFeedSchedule();//yemleme zamanı
   
    // Ortak bir metot (Her hayvanın ismini yazdırır)
    public void bilgileriGoster() {
        System.out.println("Tür: " + türName + " | weight: " + weight + "kg | Yaş: " + age);
    }
}
