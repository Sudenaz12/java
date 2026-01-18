package RentACarsystem;
import java.util.ArrayList;
 

public class Customer {
    private final String id;
    private String name;
    private String phone;
    private ArrayList<Vehicle> rentedVehicles =new ArrayList<>();

    public Customer(String id,String name,String phone){
        this.id=id;
        this.name=name;
        this.phone=phone;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    
    public ArrayList<Vehicle> getRentedVehicles() {
        return rentedVehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        rentedVehicles.add(vehicle);
        System.out.println(vehicle.getBrand() + " aracı " + this.name + " kullanıcısının listesine eklendi.");
    }
/**
 * 
 * @param vehicle iade ettiğimiz zaman çalışır ,liseden çıkarır
 * .contains() javada hazır buunan bir arama motoru,listeyi baştan sona tarar
 */
    public void removeVehicle(Vehicle vehicle) {
        if (rentedVehicles.contains(vehicle)) {
            rentedVehicles.remove(vehicle);
            System.out.println(vehicle.getBrand() + " aracı listeden düşüldü.");
        } else {
            System.out.println("Bu araç zaten müşteride değil.");
        }
    }
}