public class GameCharacter {
    private int health ;
    private boolean isAlive ;
    private Inventory inventory;

     public GameCharacter(){
        this.health=100;
        this.isAlive=true;
//Karakter için YENİ, BOŞ bir envanter oluştur.
//Inventory constructor'ı otomatik olarak dizileri oluşturup sayaçları sıfırlayacak.
        this.inventory = new Inventory();
     }
   
//private olduğu için sadece bu sınıf içinden erişilir
   private  void setIsAlive(boolean isAlive){
        this.isAlive = isAlive;
   }

   public void addWeapon(Weapon w) {
        this.inventory.addWeapon(w);
    }

    public void addArmor(Armor a) {
        this.inventory.addArmor(a);
    }



   public void  attack(GameCharacter opponent, int weaponIndex){
       if(!this.isAlive){
         System.out.println("Dead characters can't attack.");
         return;
       }
       if(!opponent.isAlive){
          System.out.println("rakip hayatta değil");
          return;
       }
   //envanterden silah almayı deneme
       Weapon weaponToUse = this.inventory.useWeapon(weaponIndex);

      // 2. Silah geçerliyse (null değilse)
        if (weaponToUse != null) {
            // 3. Silahı kullan (bu, silahın attack() metodunu çağırır ve dayanıklılığını düşürür)
            int damageDealt = weaponToUse.attack();

            if (damageDealt > 0) {
                System.out.println("Saldiri yapildi! Hasar: " + damageDealt);
                // 4. Rakibin savunma yapmasını sağla (Basitlik için 0. zırhı kullandığını varsayıyoruz)
                opponent.defend(damageDealt, 0); 
            }
        } else {
            System.out.println("Saldiri başarisiz! Silah kullanilamadi.");
        }

   }
     
   public  void defend(int damage, int armorIndex){
        if(!this.isAlive){
           return;//rakip ölü
       }
       // 1. Envanterden zırhı almayı dene
        Armor armorToUse = this.inventory.useArmor(armorIndex);
        int reducedDamage = damage;

        if (armorToUse != null) {
            // armorToUse.defend() returns defense value and reduces durability
            int armorDefense = armorToUse.defend();
            reducedDamage = Math.max(0, damage - armorDefense);
            System.out.println("Used armor. Incoming damage " + damage + " reduced by " + armorDefense + " -> " + reducedDamage);
        } else {
            System.out.println("No usable armor. Took full damage: " + damage);
            reducedDamage = damage;
        }
        
        this.health -= reducedDamage;
        System.out.println("Health now: " + this.health);

        if(this.health <= 0){
            this.health=0;
            setIsAlive( false);
            System.out.println("Character died!");
        }
   }
 
 
   public void checkInventory(){
      System.out.println("--- Weapons ---");
      for(int i=0;i<this.inventory.weaponCount;i++){
        Weapon w = this.inventory.weapons[i]; 
        if(w==null){
             System.out.println(i + ": (empty)");
        }else{
            String status=w.isBroken() ? "BROKEN":"USABLE";
            System.out.println(i + ": Weapon (status: " + status + ")");
        }
      }
       if (this.inventory.weaponCount == 0) {
            System.out.println("(No weapons)");
        }

        System.out.println("--- Armors ---");
        for (int i = 0; i < this.inventory.armorCount; i++) {
            Armor a = this.inventory.armors[i];
            if (a == null) {
                System.out.println(i + ": (empty)");
            } else {
                String status = a.isBroken() ? "BROKEN" : "USABLE";
                System.out.println(i + ": Armor (status: " + status + ")");
            }
        }
        if (this.inventory.armorCount == 0) {
            System.out.println("(No armors)");
        }
    
   }

   public int getHealth() {
        return this.health;
    }

   public boolean isAlive() {
        return this.isAlive;
    }

   public int getInventoryWeaponCount() { 
        return this.inventory.weaponCount; 
    }

    public int getInventoryArmorCount()  { 
        return this.inventory.armorCount; 
    }

    public Weapon getInventoryWeaponAt(int i) { 
        return this.inventory.weapons[i]; 
    }

    public Armor getInventoryArmorAt(int i) { 
        return this.inventory.armors[i]; 
    }
    
}
// this.inventory → GameCharacter’ın envanterine (Inventory nesnesine) gider.
//weapons[i] → Inventory içindeki weapons dizisinin i. elemanını alır.
//Weapon w = ... → o elemanı bir Weapon değişkenine atar (daha kolay erişmek için).

