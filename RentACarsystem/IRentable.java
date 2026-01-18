 package RentACarsystem;

public interface IRentable {
    //kiralar
    void rent(Customer customer, int days); 
    
    // Aracı iade alır
    void returnVehicle();
}