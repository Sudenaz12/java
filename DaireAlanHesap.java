import java.util.Scanner;
public class DaireAlanHesap {
    public static void main(String[] args){
      Scanner input= new Scanner(System.in);
      System.out.println("dairenin yariçapini giriniz :");
      double yariçap =input.nextDouble();
      System.out.println("yariçap :" + yariçap);

//Alan Hesaplama (Math.PI ve Math.pow() kullanarak)
      double alan=Math.PI * Math.pow(yariçap,2);
      System.out.println("daire alani :" + alan);

      double cevre= 2 * Math.PI * yariçap;
      System.out.println("daire çevre : " + cevre);
input.close();

    }
}
