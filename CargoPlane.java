package flightManagementSystem;

public class CargoPlane extends Plane {
   private  double CargoPlaneCapasityKg;

   public CargoPlane(String ucakID, String model,double CargoPlaneCapasityKg){
      super( ucakID,model);
      this.CargoPlaneCapasityKg=CargoPlaneCapasityKg;
   }

   public void yukBosalt(){
       System.out.println("Kargo kapaklari açildi, yük boşaltiliyor...");
   }


    @Override
    public int getGerekliPilotSayisi(){
         return 3;
//kargo uçağı 3 pilota ihtiyaç var
    }

    @Override
    public void preFlightCheck(){
        System.out.println(getUcakID() + " Kargo Uçaği: Yük sabitleme kilitleri kontrol ediliyor (" + CargoPlaneCapasityKg + "kg kapasite).");
    }
    
}
