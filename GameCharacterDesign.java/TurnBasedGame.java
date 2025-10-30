import java.util.Random;
import java.util.Scanner;

public class TurnBasedGame {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Random rnd = new Random();

        GameCharacter hero =new GameCharacter();
        GameCharacter enemy = new GameCharacter();
    
//başlangıç eşyaları
// Başlangıç eşyaları
        hero.addWeapon(new Weapon(20, 40));   // damage, durability
        hero.addWeapon(new Weapon(12, 60));
        hero.addArmor(new Armor(15, 50));     // defense, durability
        hero.addArmor(new Armor(8, 80));

        enemy.addWeapon(new Weapon(18, 50));
        enemy.addArmor(new Armor(10, 60));

        System.out.println("=== Small Turn-Based Demo ===");
        System.out.println("You and an enemy appear. Fight until one dies!");

        boolean heroTurn = true;

        while (hero.isAlive() && enemy.isAlive()) {
            if (heroTurn) {
                System.out.println("\n--- YOUR TURN ---");
                System.out.println("Your health: " + hero.getHealth());
                System.out.println("Enemy health: " + enemy.getHealth());
                System.out.println();
                System.out.println("Your inventory:");
                hero.checkInventory();
                System.out.println();

                System.out.println("Choose action:");
                System.out.println("1) Attack");
                System.out.println("2) Repair weapon (repair first non-broken weapon)");
                System.out.println("3) Repair armor (repair first non-broken armor)");
                System.out.println("4) Pass");
                System.out.print("Action number: ");
                int action = -1;
                try { action = Integer.parseInt(sc.nextLine().trim()); } catch(Exception e){ action = -1; }

                switch (action) {
                    case 1:
                        System.out.print("Enter weapon index to use: ");
                        int widx = Integer.parseInt(sc.nextLine().trim());
                        hero.attack(enemy, widx);
                        break;
                    case 2:
                        // basit: ilk kırık veya düşük durability var ise repair et
                        boolean repairedWeapon = repairFirstWeaponIfNeeded(hero);
                        if (!repairedWeapon) System.out.println("No weapon to repair or none broken.");
                        break;
                    case 3:
                        boolean repairedArmor = repairFirstArmorIfNeeded(hero);
                        if (!repairedArmor) System.out.println("No armor to repair or none broken.");
                        break;
                    default:
                        System.out.println("You skip your turn.");
                        break;
                }
            } else {
                System.out.println("\n--- ENEMY TURN ---");
                // Basit AI: %70 saldır, %30 repair
                int choice = rnd.nextInt(100);
                if (choice < 70) {
                    // Saldırı: seçilebilir en uygun silah (ilk usable)
                    int widx = firstUsableWeaponIndex(enemy);
                    if (widx >= 0) {
                        System.out.println("Enemy attacks with weapon slot " + widx);
                        enemy.attack(hero, widx);
                    } else {
                        System.out.println("Enemy has no usable weapon and passes.");
                    }
                } else {
                    boolean repW = repairFirstWeaponIfNeeded(enemy);
                    boolean repA = repairFirstArmorIfNeeded(enemy);
                    if (!repW && !repA) System.out.println("Enemy tried to repair but had nothing to repair.");
                }
            }

            // Turlar arasında kısa durum
            System.out.println("\n--- Status ---");
            System.out.println("You: " + hero.getHealth() + " HP | Enemy: " + enemy.getHealth() + " HP");

            heroTurn = !heroTurn;

            // küçük delay yerine bekleme için enter isteyebiliriz (opsiyonel)
            // System.out.println("Press Enter to continue..."); sc.nextLine();
        }

        System.out.println("\n=== BATTLE ENDED ===");
        if (hero.isAlive()) System.out.println("You won!");
        else System.out.println("You lost...");

        sc.close();
    }

    // Yardımcı: ilk usable weapon index, yoksa -1
    private static int firstUsableWeaponIndex(GameCharacter gc) {
        for (int i = 0; i < gc.getInventoryWeaponCount(); i++) {
            Weapon w = gc.getInventoryWeaponAt(i);
            if (w != null && !w.isBroken()) return i;
        }
        return -1;
    }

    // Yardımcı: ilk usable armor index, yoksa -1
    private static int firstUsableArmorIndex(GameCharacter gc){
        for (int i = 0; i < gc.getInventoryArmorCount(); i++) {
            Armor a = gc.getInventoryArmorAt(i);
            if (a != null && !a.isBroken()) return i;
        }
        return -1;
    }

    // Basit repair: eğer kırık veya durability düşükse repair et (calls repair(amount))
    private static boolean repairFirstWeaponIfNeeded(GameCharacter gc) {
        for (int i = 0; i < gc.getInventoryWeaponCount(); i++) {
            Weapon w = gc.getInventoryWeaponAt(i);
            if (w != null && w.isBroken()) {
                System.out.println("Repairing weapon at slot " + i);
                w.repair(30); // 30 puan ekle
                return true;
            }
        }
        return false;
    }

    private static boolean repairFirstArmorIfNeeded(GameCharacter gc) {
        for (int i = 0; i < gc.getInventoryArmorCount(); i++) {
            Armor a = gc.getInventoryArmorAt(i);
            if (a != null && a.isBroken()) {
                System.out.println("Repairing armor at slot " + i);
                a.repair(30);
                return true;
            }
        }
        return false;
    }
}
