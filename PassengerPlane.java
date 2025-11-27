package flightManagementSystem;

public class PassengerPlane extends Plane{
    private int PassengerCapacity;

    public PassengerPlane(String ucakID, String model,int PassengerCapacity){
         super(ucakID, model);
         this.PassengerCapacity=PassengerCapacity;
    }

    public void anonsYap(){
        System.out.println("Kaptan Konuşuyor: Sayın yolcularımız kemerlerinizi bağlayın.");
    }


    @Override
    public int getGerekliPilotSayisi(){
        return 2;
    }
//yolcu uçağı için en az 2 pilt gerek    
    @Override
    public void preFlightCheck(){
       System.out.println(getUcakID() + " Yolcu Uçağı: Kabin basıncı ve " + PassengerCapacity + " koltuk kontrol ediliyor.");
    }
}
