package RentACarsystem;

public interface IMaintainable {
    // Aracı servise gönderir
    void performMaintenance(); 
    
    // Bakımın bitip bitmediğini veya aracın bozuk olup olmadığını söyler
    boolean isUnderMaintenance();
}
