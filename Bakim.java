package flightManagementSystem;
//uçağın durumu
enum Durum {
    AKTİF,BAKİMDA,ARİZALİ
}
public interface Bakim {
    public abstract void bakimaAl();
    public abstract void onarimiTamamla();
    public abstract boolean servisteMi();
    
}
