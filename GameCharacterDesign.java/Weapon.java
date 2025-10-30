public class Weapon {
    private int damage;
    private int durability;


//constructor 
   public Weapon(int damage,int durability){
      this.damage =damage;
      this.durability=durability;
    }
   
    public int  attack(){
        if(this.durability == 0 ){
            System.out.println("weapon is broke so it cant be used");
            return 0;
        }

        this.durability -= 5;

        if(this.durability <0){
            this.durability=0;
        }
    return this.damage;
    } 
    

    public void repair(int amount){
         this.durability += amount;
        if (this.durability > 100) {
            this.durability = 100;
        }
    }

    public boolean isBroken(){
        return (this.durability<=0);
    }
}