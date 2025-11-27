package flightManagementSystem;

public abstract class Plane implements Bakim {
    private String ucakID;
    protected Durum durum;
    private String model;
    
 
    public Plane(String ucakID,String model){
        this.ucakID=ucakID;
        this.durum=Durum.AKTİF;//varsayılan durumu aktif yaptık
        this.model = model;
    }
 
    public abstract int getGerekliPilotSayisi();
//yolcu uçağı ve kargo uçağı için pilot sayısı farklı alt sınıflar seçip doldurur

// Abstract Metot: Her uçak tipinin hazırlığı farklıdır.
    public abstract void preFlightCheck();

    public String getUcakID(){
        return ucakID;
    }
    
    public String getModel() {
        return model;
    }

    @Override
    public  void bakimaAl(){
         this.durum=Durum.BAKİMDA;
         System.out.println(ucakID + " numarali uçak bakima alindi.");
    }
    @Override
    public void onarimiTamamla(){
        this.durum=Durum.AKTİF;
        System.out.println(ucakID + " onarimi bitti, uçuşa hazir.");
    }
    @Override
    public boolean servisteMi(){
        return this.durum != Durum.AKTİF;
    }

}
