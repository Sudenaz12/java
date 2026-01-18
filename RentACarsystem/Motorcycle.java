 package RentACarsystem;

public class Motorcycle extends Vehicle {
    private final int engineCapacity; // cc cinsinden
    private final boolean hasTopCase;
    private final boolean isSafetyGearIncluded;

    public Motorcycle(int engineCapacity, boolean hasTopCase, boolean isSafetyGearIncluded,
                      int year, String model, String vehicleID, String brand, double dailyRentalPrice) {
        
         
        super(year, model, vehicleID, brand, dailyRentalPrice);
        
        this.engineCapacity = engineCapacity;
        this.hasTopCase = hasTopCase;
        this.isSafetyGearIncluded = isSafetyGearIncluded;
    }

    public int getEngineCapacity() { return engineCapacity; }
    public boolean isHasTopCase() { return hasTopCase; }
    public boolean isSafetyGearIncluded() { return isSafetyGearIncluded; }

    @Override
    public double calculateRentalPrice(int days) {
        double total = super.getDailyRentalPrice() * days;
        
        // MANTIK: Eğer koruyucu ekipman (kask, mont) istenmişse günlük 150 TL ekle.
        if (isSafetyGearIncluded) {
            total += (150 * days);
        }
        return total;
    }
}