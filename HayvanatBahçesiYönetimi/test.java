package HayvanatBahçesiYönetimi;

public class test {
    public static void main(String[] args) {
        
        // Farklı türlerden nesneler oluşturuyoruz
        // Dikkat: Referans tipi 'Hayvan' olabilir (Polimorfizm)
        Hayvan at1 = new Atlar(150.9,"Midilli", 5);
        Hayvan kaplan1 = new kediler(200,"Bengal Kaplani", 8);
        Hayvan fare1 = new kemirgenler(0.5,"Laboratuvar Faresi", 1);

        // Hepsini bir listeye atıp topluca işlem yapabiliriz
        Hayvan[] hayvanListesi = {at1, kaplan1, fare1};

        System.out.println("--- HAYVANAT BAHÇESİ SİSTEMİ ---\n");

        for (Hayvan h : hayvanListesi) {
            h.bilgileriGoster(); // Hayvan sınıfındaki ortak metot
            
            // Aşağıdaki metotlar her sınıf için farklı çalışacak (Override edildiği için)
            System.out.println("İlaç Dozu: " + h.getDosage() + " ml");
            System.out.println("Beslenme: " + h.getFeedSchedule());
            System.out.println("---------------------------------");
        }
    }
}
