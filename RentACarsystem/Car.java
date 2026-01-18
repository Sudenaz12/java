package RentACarsystem;

public class Car extends Vehicle {
    private final  int doorCount;      // 2 kapı, 4 kapı (Motorda kapı yok!)
    private  final String fuelType;    // Benzin, Dizel, Hibrit
    private  final String transmission;// Manuel, Otomatik
    private boolean isConvertible; // Üstü açık mı?

    public Car(int doorCount,String fuelType,String transmission,boolean isConvertible,int year,String model,String vehicleID,String brand,double dailyRentalPrice ){
        super(year, model, vehicleID, brand, dailyRentalPrice);
        this.doorCount=doorCount;
        this.fuelType=fuelType;
        this.transmission=transmission;
        this.isConvertible = isConvertible;
     }

    public int getDoorCount() { return doorCount; }
    public String getFuelType() { return fuelType; }
    public String getTransmission() { return transmission; }
    public boolean isConvertible() { return isConvertible; }
/**
 @param  
 araç üstü açıksa (Convertible), günlük fiyata %20 lüks vergisi ekle.
 */
    @Override
    public double calculateRentalPrice(int days){
        double basePrice=days*super.getDailyRentalPrice();
        if(isConvertible){
        return basePrice*1.20;
        }else{
            return basePrice;
        }
    
    }
}
