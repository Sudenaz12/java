 package RentACarsystem;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ARAÇ KİRALAMA SİSTEMİ BAŞLATILIYOR ===\n");

        // 1. Müşteri Oluştur
        Customer musteri = new Customer("1", "Sudenaz", "555-1234");

        // 2. Araçları Oluştur
        Vehicle jip = new SUV(true, true, 2025, "Land Cruiser", "34SU100", "Toyota", 4000);
        Vehicle motor = new Motorcycle(1000, true, true, 2024, "Goldwing", "35MT200", "Honda", 2000);

        // 3. Kiralama Testi
        jip.rent(musteri, 3); // 3 gün
        
        // 4. Bakım Testi
        motor.performMaintenance(); // Motoru servise sokalım
        motor.rent(musteri, 1);     // HATA vermeli çünkü serviste!

        // 5. Müşterinin elindekileri görelim
        System.out.println("\n--- Müşterideki Araçlar ---");
        for(Vehicle v : musteri.getRentedVehicles()){
            System.out.println(v.getBrand() + " " + v.getModel());
        }
    }
}