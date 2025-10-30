public class GameTest{
    public static void main(String[] args){
    GameCharacter  hero = new GameCharacter();
    GameCharacter enemy= new GameCharacter();
  
 // Silah ve zırh oluştur
 Weapon sword = new Weapon(20, 34);
 Weapon  axe = new Weapon(30, 60);
 Armor shield = new Armor(20 ,70);
 Armor helmet = new Armor(45,87);

 //envantere ekle
 hero.addArmor(shield);
 hero.addWeapon(sword);
 enemy.addArmor(helmet);
 enemy.addWeapon(axe);
 
 //envanteri kontrol et
 System.out.println("--Hero's inventrory");
 hero.checkInventory();
 System.out.println();

 System.out.println("--enemy's inventrory");
 enemy.checkInventory();
 System.out.println();

 //saldırı
 System.out.println("hero attacks enemy!");
 hero.attack(enemy, 0); // 0. silah ile saldır
 System.out.println();

 //envanter sonucu
 System.out.println("== INVENTORIES AFTER ATTACK ==");
 System.out.println("hero:");
 hero.checkInventory();

 System.out.println("enemy:");
 enemy.checkInventory();
   }  
}