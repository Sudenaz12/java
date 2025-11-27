 package flightManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<AirlineCompany> sirketler = new ArrayList<>(); // Birden fazla şirketi burada tutacağız

        System.out.println("--- HAVAYOLU YÖNETİM SİSTEMİNE HOŞGELDİNİZ ---");

        while (true) {
            System.out.println("\n1. Yeni Şirket Ekle");
            System.out.println("2. Mevcut Şirkete Uçak Ekle");
            System.out.println("3. Tüm Şirketleri ve Detaylı Aksiyonları Listele (CASTING TEST)");
            System.out.println("4. Çıkış");
            System.out.print("Seçiminiz: ");
            int secim = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme (Java Scanner sorunu için)

            if (secim == 1) {
                // --- ŞİRKET EKLEME ---
                System.out.print("Şirket ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Şirket Adı: ");
                String ad = scanner.nextLine();

                AirlineCompany yeniSirket = new AirlineCompany(id, ad);
                sirketler.add(yeniSirket);
                System.out.println(ad + " sisteme eklendi.");

            } else if (secim == 2) {
                // --- UÇAK EKLEME ---
                if (sirketler.isEmpty()) {
                    System.out.println("Önce şirket eklemelisiniz!");
                    continue;
                }

                // Hangi şirkete eklenecek?
                System.out.println("Hangi şirkete uçak eklenecek?");
                for (int i = 0; i < sirketler.size(); i++) {
                    System.out.println(i + ". " + sirketler.get(i).getName());
                }
                int sirketIndex = scanner.nextInt();
                scanner.nextLine();

                if (sirketIndex < 0 || sirketIndex >= sirketler.size()) {
                    System.out.println("Geçersiz seçim.");
                    continue;
                }

                AirlineCompany secilenSirket = sirketler.get(sirketIndex);

                System.out.print("Uçak Tipi (1: Yolcu, 2: Kargo): ");
                int tip = scanner.nextInt();
                scanner.nextLine();
                
                System.out.print("Uçak ID (Kuyruk No): ");
                String ucakId = scanner.nextLine();
                System.out.print("Model: ");
                String model = scanner.nextLine();

                // POLYMORPHISM: Referans tipi 'Plane', nesne tipi alt sınıflar
                Plane yeniUcak = null;

                if (tip == 1) {
                    System.out.print("Yolcu Kapasitesi: ");
                    int kap = scanner.nextInt();
                    yeniUcak = new PassengerPlane(ucakId, model, kap);
                } else if (tip == 2) {
                    System.out.print("Yük Kapasitesi (kg): ");
                    double yuk = scanner.nextDouble();
                    yeniUcak = new CargoPlane(ucakId, model, yuk);
                }

                if (yeniUcak != null) {
                    secilenSirket.AddPlane(yeniUcak);
                }

            } else if (secim == 3) {
                // --- CASTING VE POLYMORPHISM GÖSTERİSİ ---
                
                for (AirlineCompany sirket : sirketler) {
                    System.out.println("\n### Şirket: " + sirket.getName() + " ###");
                    
                    // Şirketin uçak listesine erişmemiz lazım (Getter eklediğini varsayıyorum)
                    // AirlineCompany sınıfına: public List<Plane> getPlanes() { return planes; } eklemelisin.
                    
                    for (Plane p : sirket.getPlanes()) {
                        String durumMesaji = p.servisteMi() ? " [BAKIMDA/ARIZALI X]" : " [UÇUŞA HAZIR V]";
                        System.out.println("- Uçak: " + p.getUcakID() + durumMesaji);
                        
                        // 1. Polymorphism (Herkesin ortak metodu)
                        p.preFlightCheck(); 

                        // 2. Casting (Özel yetenekleri kullanma)
                        // 'p' değişkeni Plane tipinde olduğu için anonsYap() veya yukBosalt() göremez.
                        // Görmesi için "Sen aslında bir Yolcu Uçağısın" dememiz (Cast etmemiz) lazım.
                        
                        if (p instanceof PassengerPlane) {
                            // Downcasting: Plane -> YolcuUcagi
                            PassengerPlane yolcuU = (PassengerPlane) p; 
                            yolcuU.anonsYap();
                        } 
                        else if (p instanceof  CargoPlane) {
                            // Downcasting: Plane -> KargoUcagi
                            CargoPlane kargoU = (CargoPlane) p;
                            kargoU.yukBosalt();
                        }
                        System.out.println("----------------");
                    }
                }

            } else if (secim == 4) {
                System.out.println("Sistemden çıkılıyor...");
                break;
            }
        }
        scanner.close();
    }
}