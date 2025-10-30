public class Inventory {
     Weapon[] weapons;
     Armor[] armors;
     int weaponCount;
     int armorCount;

    public Inventory(){
        this.weapons = new Weapon[3];
        this.armors = new Armor[2];
        this.weaponCount=0;
        this.armorCount=0;
    }
//diziye silah ekleme metodu
    public void addWeapon(Weapon weapon){
        if(weaponCount >=3){
            System.out.println("Weapon inventory full !!");
            return ;//metot burada biter ,ekleme olmaz
        }
//weaponCount' indeksine yeni silahı ekle
//weaponCount=0 ise, weapons[0]'a ekle.
        this.weapons[weaponCount] = weapon;
        this.weaponCount++;
    }

    public void  addArmor(Armor armor){
        if(armorCount>=2){
            System.out.println("Armor inventory full.!");
            return;
        }
        this.armors[armorCount] = armor;
        this.armorCount++;
    }
//useWeapon ve useArmor amacı  bir eşyayı güvenli bir şekilde alıp vermektir. 
//Programın çökmesine neden olabilecek birkaç durumu kontrol etmeleri gereki
//metodun temel amacı: "Envanterin içinden, istenen bir silahı GÜVENLİ bir şekilde alıp, onu isteyen GameCharacter'a GERİ VERMEK."   
    public  Weapon useWeapon(int index){
        if(index < 0 || index >= weapons.length){//weapons.length burada 3
             System.out.println("Hata: Geçersiz silah indeksi.");
             return null; // Nesne bulunamadı, 'null' döndürerek işlemi bitir
        }

       // O indeksteki yuva boş (null) mu?
        if (weapons[index] == null) {
            System.out.println("Hata: Bu yuvada silah yok.");
            return null; // Nesne bulunamadı, 'null' döndür.
        }

        if (weapons[index].isBroken()) {
            System.out.println("Hata: Silah kirik ve kullanilamaz.");
            return null;
        }

return weapons[index];
    }


    public Armor useArmor(int index){
        if (index < 0 || index >= armors.length) {
            System.out.println("Hata: Geçersiz zirh indeksi.");
            return null;
        }
        //boş yuva kontrolü
        if (armors[index] == null) {
            System.out.println("Hata: Bu yuvada zirh yok.");
            return null;
        }
        //kırık zırh kontrolü
        if (armors[index].isBroken()) {
            System.out.println("Hata: Zirh kirik ve kullanilamaz.");
            return null;
        }
        return this.armors[index];
    }
    


}
