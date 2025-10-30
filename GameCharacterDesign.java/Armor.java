public class Armor {
    private int defense;//savunma
    private int durability;//dayanıklılık

 
   public Armor( int defense,int durability){
    this.defense=defense;
    this.durability=durability;
   } 
   
   public int defend(){
       if(isBroken()){//this.durability<=0
          System.out.println("zirh kirik, savunma yapilamadi.");
          return 0;
        }
        this.durability -= 10;
//0 altına düşerse sabitle
        if(this.durability < 0){
            this.durability=0;
        }
        return this.defense;
   }


  public  void repair (int amount){
     this.durability += amount;//1 defa ekler
      if(this.durability>100){
          this.durability = 100;//limit aşarsa 100 sbtle
      }
  } 
//nesnenin kendi dayanıklılığını kontrol etmeli bu yüzden parametreye gerek yok
//değerlere this ile ulaştık çünkü constractor'ın metodu ile karışmaması için
  public boolean isBroken(){
      if(this.durability >0){
          return false;
       }
      else{
          return true;
       }
  }

}