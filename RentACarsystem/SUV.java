 package RentACarsystem;

public class SUV extends Vehicle {
    private final boolean isFourWheelDrive; // 4x4 mü?
    private final boolean hasOffRoadMode;   // Arazi modu var mı?

    // Constructor: Hem kendi özelliklerini hem de Vehicle'ın özelliklerini alır
    public SUV(boolean isFourWheelDrive, boolean hasOffRoadMode, 
               int year, String model, String vehicleID, String brand, double dailyRentalPrice) {
        super(year, model, vehicleID, brand, dailyRentalPrice);
        
        this.isFourWheelDrive = isFourWheelDrive;
        this.hasOffRoadMode = hasOffRoadMode;
    }

    public boolean isFourWheelDrive() {
        return isFourWheelDrive;
    }

    public boolean hasOffRoadMode() {
        return hasOffRoadMode;
    }

    @Override
    public double calculateRentalPrice(int days) {
        double totalPrice = super.getDailyRentalPrice() * days;

        // MANTIK: Eğer araç 4x4 ise, motoru daha çok yıpranır ve lastikleri pahalıdır.
        // Bu yüzden 4x4 araçlar için günlük ekstra 250 TL "Arazi Hizmet Bedeli" ekleyelim.
        if (isFourWheelDrive) {
            totalPrice += (250 * days);
        }

        return totalPrice;
    }
}