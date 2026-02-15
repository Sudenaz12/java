 import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

// ===================== VERİ MODELLERİ =====================
class Doz {
    int saat, dakika;
    String etiket;
    boolean tok;

    Doz(int s, int d, String etiket, boolean tok) {
        this.saat = s;
        this.dakika = d;
        this.etiket = etiket;
        this.tok = tok;
    }

    String zamanString() {
        return String.format("%02d:%02d", saat, dakika);
    }
}

class Ilac {
    String ad;
    int stok;
    LocalDateTime baslamaZamani;
    List<Doz> dozlar = new ArrayList<>();
    boolean aktif = true;
    int jenerasyon = 0;

    Ilac(String ad, int stok, LocalDateTime baslamaZamani) {
        this.ad = ad.trim().toLowerCase();
        this.stok = stok;
        this.baslamaZamani = baslamaZamani;
    }

    int gunlukDozSayisi() {
        return dozlar.size();
    }

    LocalDate tahminiBitisTarihi() {
        if (stok <= 0 || gunlukDozSayisi() == 0) return LocalDate.now();//bugünün tarihini verir 
        int kalanGun = (int) Math.ceil((double) stok / gunlukDozSayisi());//yukarı yuvarlar
        return LocalDate.now().plusDays(kalanGun);//şuanki güne hesapladığımız günü ekler
        
    }

    long gunKadarKaldı() {
        return Duration.between(LocalDate.now().atStartOfDay(), tahminiBitisTarihi().atStartOfDay()).toDays();
    }
}

// ===================== HATIRLATMA =====================
enum HatirlatmaTipi { ON, ANA }

class Hatirlatma implements Comparable<Hatirlatma> {
    LocalDateTime zaman;
    Ilac ilac;
    int dozIndeks;
    HatirlatmaTipi tip;
    int jenerasyon;

    Hatirlatma(LocalDateTime zaman, Ilac ilac, int dozIndeks, HatirlatmaTipi tip, int jenerasyon) {
        this.zaman = zaman;
        this.ilac = ilac;
        this.dozIndeks = dozIndeks;
        this.tip = tip;
        this.jenerasyon = jenerasyon;
    }

    @Override
    public int compareTo(Hatirlatma o) {
        return this.zaman.compareTo(o.zaman);
    }
}

// ===================== İLAÇ LİSTESİ (LinkedList + HashMap) =====================
class IlacListesi {
    LinkedList<Ilac> liste = new LinkedList<>();//tüm ilacları eklenme sırasına göre tutar
    HashMap<String, Ilac> map = new HashMap<>(); // Ad üzerinden anında hızlı erişim

    Ilac bul(String ad) {
        return map.get(ad.toLowerCase());
    }

    void ekleVeyaStokArtir(String ad, int stok, LocalDateTime baslama) {
        Ilac var = bul(ad);
        if (var != null) {
            var.stok += stok;
            System.out.printf("'%s' zaten var. Stok +%d → %d%n", var.ad.toUpperCase(), stok, var.stok);
        } else {
            Ilac yeni = new Ilac(ad, stok, baslama);
            liste.add(yeni);
            map.put(yeni.ad, yeni);
            System.out.printf("'%s' başarıyla eklendi!%n", ad.toUpperCase());
        }
    }

    void sil(Ilac i) {
        liste.remove(i);
        map.remove(i.ad);
        i.aktif = false;
        System.out.printf("'%s' listeden kaldırıldı.%n", i.ad.toUpperCase());
    }

    List<Ilac> tumIlaclar() {
        List<Ilac> aktifIlaclar = new ArrayList<>();
        for (Ilac i : liste) if (i.aktif) aktifIlaclar.add(i);
        return aktifIlaclar;
    }
}

// ===================== HATIRLATMA SİSTEMİ =====================
class HatirlatmaSistemi {
    private static final boolean SIMULASYON_MODU = true;
    private static final Duration ON_HATIRLATMA_DELTA = SIMULASYON_MODU ? Duration.ofMinutes(2) : Duration.ofHours(2);
    private static final int PLANLANAN_GUN = 30;

    IlacListesi ilacListesi = new IlacListesi();
    PriorityQueue<Hatirlatma> hatirlatmaKuyrugu = new PriorityQueue<>();
    BlockingQueue<Hatirlatma> onayKuyrugu = new LinkedBlockingQueue<>();
    private final Scanner scanner = new Scanner(System.in);
    private final ExecutorService zamanlayici = Executors.newSingleThreadExecutor();
    private final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    void zamanlayiciyiBaslat() {
        zamanlayici.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Hatirlatma h;
                    synchronized (hatirlatmaKuyrugu) {//
                        h = hatirlatmaKuyrugu.peek();
                        if (h == null) {
                            hatirlatmaKuyrugu.wait();//hatırlatma zamanına kadar uyutur
                            continue;
                        }
                        long millis = Duration.between(LocalDateTime.now(), h.zaman).toMillis();
                        if (millis > 0) {
                            hatirlatmaKuyrugu.wait(millis);
                            continue;
                        }
                        hatirlatmaKuyrugu.poll();//ilacın zamaı gelmişse listeden çıkarır
                    }//pool ile çekilen hatırlatmanın kontrolünü yapar
                    if (!h.ilac.aktif || h.jenerasyon != h.ilac.jenerasyon) continue;

                    if (h.tip == HatirlatmaTipi.ON) {
                        System.out.printf("[ÖN] %s %s%n", h.ilac.ad.toUpperCase(), h.ilac.dozlar.get(h.dozIndeks).etiket);
                    } else {
                        onayKuyrugu.put(h);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    void ilacIcinHatirlatmaPlanla(Ilac ilac) {
        for (int gun = 0; gun < PLANLANAN_GUN; gun++) {
            LocalDate tarih = ilac.baslamaZamani.toLocalDate().plusDays(gun);
            for (int i = 0; i < ilac.dozlar.size(); i++) {
                Doz d = ilac.dozlar.get(i);
                LocalDateTime zaman = tarih.atTime(d.saat, d.dakika);
                if (zaman.isBefore(LocalDateTime.now())) continue;

                Hatirlatma onHatirlatma = new Hatirlatma(zaman.minus(ON_HATIRLATMA_DELTA), ilac, i, HatirlatmaTipi.ON, ilac.jenerasyon);
                Hatirlatma anaHatirlatma = new Hatirlatma(zaman, ilac, i, HatirlatmaTipi.ANA, ilac.jenerasyon);

                synchronized (hatirlatmaKuyrugu) {// hatırlatma bitene kadar ekleme cıkarma yapmıyor
                    hatirlatmaKuyrugu.add(onHatirlatma);
                    hatirlatmaKuyrugu.add(anaHatirlatma);
                    hatirlatmaKuyrugu.notifyAll();
                }
            }
        }
    }

    void bekleyenOnaylariIsle() {
        Hatirlatma h;
        while ((h = onayKuyrugu.poll()) != null) {
            Doz d = h.ilac.dozlar.get(h.dozIndeks);
            System.out.printf("%nİLAÇ ZAMANI: %s%n", h.ilac.ad.toUpperCase());
            System.out.printf("Doz: %s  Saat: %s  (%s)%n", d.etiket, d.zamanString(), d.tok ? "TOK" : "AÇ");

            long gun = h.ilac.gunKadarKaldı();
            if (gun <= 7) System.out.printf("DİKKAT: Tahmini bitiş %s (%d gün kaldı)%n", h.ilac.tahminiBitisTarihi().format(DATE_FMT), gun);

            String cevap;
            while (true) {
                System.out.print("İlacı aldınız mı? (E/H): ");
                cevap = scanner.nextLine().trim();
                if (cevap.equalsIgnoreCase("E") || cevap.equalsIgnoreCase("H")) break;
            }

            if (cevap.equalsIgnoreCase("E")) {
                h.ilac.stok--;
                System.out.printf("Stok güncellendi → Kalan: %d%n", h.ilac.stok);
                if (h.ilac.stok <= 0) ilacListesi.sil(h.ilac);
            } else {
                System.out.println("İlaç alınmadı olarak kaydedildi.");
            }
        }
    }

    void yeniIlacEkle() {
        System.out.print("İlaç adı: ");
        String ad = scanner.nextLine().trim();
        if (ad.isEmpty()) { System.out.println("İsim boş olamaz."); return; }

        int stok = 0;
        while (stok <= 0) {
            System.out.print("Kutudaki hap sayısı: ");
            try { stok = Integer.parseInt(scanner.nextLine().trim()); } catch (Exception e) { stok = 0; }
            if (stok <= 0) System.out.println("Geçerli bir sayı girin.");
        }

        LocalDateTime baslama = null;
        DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        while (baslama == null) {
            System.out.print("Başlama tarihi ve saati (yyyy-MM-dd HH:mm): ");
            try { baslama = LocalDateTime.parse(scanner.nextLine().trim(), inputFmt); }
            catch (Exception e) { System.out.println("Hatalı format! Örnek: 2025-11-18 08:00"); }
        }

        System.out.print("Günde kaç doz? (1-3): ");
        int dozSayisi = 0;
        while (dozSayisi < 1 || dozSayisi > 3) {
            try { dozSayisi = Integer.parseInt(scanner.nextLine().trim()); } catch (Exception e) { dozSayisi = 0; }
        }

        Map<Integer, String> dozEtiketleri = Map.of(1, "tek doz", 2, "sabah", 3, "öğle", 4, "akşam");
        List<String> secilenEtiketler = new ArrayList<>();
        switch (dozSayisi) {
            case 1 -> secilenEtiketler.add(dozEtiketleri.get(1));
            case 2 -> { secilenEtiketler.add(dozEtiketleri.get(2)); secilenEtiketler.add(dozEtiketleri.get(4)); }
            case 3 -> { secilenEtiketler.add(dozEtiketleri.get(2)); secilenEtiketler.add(dozEtiketleri.get(3)); secilenEtiketler.add(dozEtiketleri.get(4)); }
        }

        Ilac ilac = ilacListesi.bul(ad);
        boolean yeni = false;
        if (ilac == null) {
            ilac = new Ilac(ad, stok, baslama);
            yeni = true;
        } else {
            ilac.stok += stok;
            System.out.printf("'%s' mevcut → stok +%d → %d%n", ad.toUpperCase(), stok, ilac.stok);
        }

        for (String e : secilenEtiketler) ilac.dozlar.add(sorDoz(e));
        if (yeni) ilacListesi.liste.add(ilac);
        ilacListesi.map.put(ilac.ad, ilac);

        ilacIcinHatirlatmaPlanla(ilac);
    }

    private Doz sorDoz(String etiket) {
        while (true) {
            System.out.print(etiket + " saati (HH:mm): ");
            try {
                LocalTime t = LocalTime.parse(scanner.nextLine().trim(), DateTimeFormatter.ofPattern("HH:mm"));
                System.out.print(etiket + " aç mı tok mu? (A/T): ");
                boolean tok = scanner.nextLine().trim().equalsIgnoreCase("T");
                return new Doz(t.getHour(), t.getMinute(), etiket, tok);
            } catch (Exception e) {
                System.out.println("Hatalı giriş! Örnek: 08:30");
            }
        }
    }

    void listele() {
        List<Ilac> liste = ilacListesi.tumIlaclar();
        if (liste.isEmpty()) { System.out.println("Hiç ilaç yok."); return; }

        System.out.println("\n=== MEVCUT İLAÇLAR ===");
        for (Ilac i : liste) {
            long gun = i.gunKadarKaldı();
            String uyari = gun <= 7 ? " BİTMEK ÜZERE!" : "";
            System.out.printf("• %s | Stok: %d | Bitiş: %s (%d gün)%s%n",
                    i.ad.toUpperCase(), i.stok, i.tahminiBitisTarihi().format(DATE_FMT), gun, uyari);
            for (Doz d : i.dozlar) System.out.printf("   → %s %s (%s)\n", d.etiket, d.zamanString(), d.tok ? "tok" : "aç");
        }
    }

    void sil() {
        System.out.print("Silinecek ilaç adı: ");
        String ad = scanner.nextLine().trim();
        Ilac i = ilacListesi.bul(ad);
        if (i == null) { System.out.println("İlaç bulunamadı."); return; }
        ilacListesi.sil(i);
    }

    void calistir() {
        zamanlayiciyiBaslat();
        System.out.println("Akıllı İlaç Hatırlatma Sistemi Başlatıldı!");
        System.out.println("SIMULASYON_MODU = " + SIMULASYON_MODU);

        while (true) {
            bekleyenOnaylariIsle();
            System.out.println("\n=== MENÜ ===");
            System.out.println("1) Yeni ilaç ekle");
            System.out.println("2) İlaçları listele");
            System.out.println("3) İlaç sil");
            System.out.println("4) Çıkış");
            System.out.print("Seçiminiz: ");

            String secim = scanner.nextLine().trim();
            switch (secim) {
                case "1" -> yeniIlacEkle();
                case "2" -> listele();
                case "3" -> sil();
                case "4" -> { System.out.println("Güle güle!"); zamanlayici.shutdownNow(); return; }
                default -> System.out.println("Geçersiz seçim.");
            }
        }
    }
}

// ===================== ANA =====================
public class ilaçhatırlatma{
    public static void main(String[] args) {
        new HatirlatmaSistemi().calistir();
    }
}
