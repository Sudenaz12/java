package RentACarsystem;

public abstract class Vehicle implements IRentable,IMaintainable {
    private final int year;
    private final String model;
    private final String vehicleID;
    private  final String brand;//marka
    private double dailyRentalPrice;
    private boolean isAvailable;
    private boolean inService;
    public Vehicle(int year,String model,String vehicleID,String brand,double dailyRentalPrice){
             this.year=year;
             this.model=model;
             this.vehicleID=vehicleID;
             this.brand=brand;
             this.dailyRentalPrice=dailyRentalPrice;
             this.isAvailable = true;  // Yeni araç müsaittir.
            this.inService = false;
    }
    public int getYear(){
        return year;
    }
    public String getModel(){
        return model;
    }
    public String getVehicleID(){
        return vehicleID;
    }
    public String getBrand(){
        return brand;
    }
    public double getDailyRentalPrice(){
        return dailyRentalPrice;
    }
    public void setDailyRentalPrice(double dailyRentalPrice){
        if(dailyRentalPrice>0){
            this.dailyRentalPrice=dailyRentalPrice;
        }else {
            System.out.println("Hata: Günlük fiyat negatif olamaz!");
        }
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    public boolean isService(){
        return inService;
    }
    public void setService(boolean  inService) {
        this.inService = inService;
    }

    public abstract double calculateRentalPrice(int days);

    @Override
    public void rent(Customer customer,int days){
        if(isAvailable () && !inService){
            double price = calculateRentalPrice(days);
            System.out.println("--- KİRALAMA İŞLEMİ ---");
            System.out.println("Müşteri: " + customer.getName());
            System.out.println("Araç: " + getBrand() + " " + getModel());
            System.out.println("Toplam Tutar: " + price + " TL");

            setAvailable(false);
            customer.addVehicle(this); // 'this' o anki araç nesnesidir
            
            System.out.println("İşlem Başarılı!\n");
        }
        else{
                System.out.println("araç kiralamaya uygun değil");
        }
    }
    
    @Override
    public void returnVehicle() {
        System.out.println("Araç başarıyla teslim alındı. Kontroller yapılıyor...");
        setAvailable(true); // Tekrar müsait
    }

    @Override
    public void performMaintenance() {
        System.out.println(getBrand() + " - " + getModel() + " servise gönderildi.");
        this.inService = true;
        setAvailable(false); // Servisteki araç kiralanamaz
    }

    @Override
    public boolean isUnderMaintenance() {
        return inService;
    }
    // Servisten dönüş metodu da ekleyelim (Interface'de yoktu ama sınıfa lazım)
    public void completeMaintenance() {
        this.inService = false;
        setAvailable(true);
        System.out.println("Bakım tamamlandı. Araç tekrar sahalarda!");
    }
}