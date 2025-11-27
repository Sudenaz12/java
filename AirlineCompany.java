package flightManagementSystem;
import java.util.ArrayList;
import java.util.List;

public   class AirlineCompany {
    private int id;
    private String name;
//dizi yerine list kullan
    private List<Plane> planes;//hem yolcu hemde karho uçağını tutar
    private List<Pilot> pilots;

    public AirlineCompany(int id, String name){
        this.id = id;
        this.name = name;
        this.planes = new ArrayList<>();//boş bir liste oluşturduk
        this.pilots = new ArrayList<>();
    }
 
    public void AddPlane(Plane plane){//yeni bir plane nesnesi yazdık
       this.planes.add(plane);
       System.out.println(plane.getUcakID() + "filoya katildi");
    }

    public void addPilot(Pilot pilot){
        this.pilots.add(pilot);
    }
    public List<Plane> getPlanes() {
    return planes;
   }

//tüm filoyu görüntüle
public void showFleetStatus(){
    System.out.println("\n--- " + this.name + " Filo Durumu ---");
    for(Plane p :planes){
        // Hangi uçak tipi olursa olsun çalışır
            System.out.println("Uçak ID: " + p.getUcakID() +" | Durum: " + (p.servisteMi() ? "SERVİSTE/ARIZALI" : "UÇUŞA HAZIR"));
        
        // Eğer uçuş öncesi kontrol yap dersek, her biri kendi yöntemini çalıştırır
            if (!p.servisteMi()) {
                p.preFlightCheck();
            }
    }
}
    public int getUcakID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setId(int id){
        if(id > 0){
             this.id = id;
        }
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }
}
