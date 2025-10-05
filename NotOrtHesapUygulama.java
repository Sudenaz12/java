import java.util.Scanner;
public class NotOrtHesapUygulama {
    public static void main(String[] args){
       Scanner input = new Scanner(System.in);
        double ortalama;
       System.out.println("öğrenci adini-soyadini giriniz:");
       String isim =input.nextLine();

       System.out.println("öğrenci quiz not:");
       float quiz=input.nextFloat();

       System.out.println("öğrenci vize not:");
       float vize=input.nextFloat();

       System.out.println("öğrenci vize2 notu: ");
       float vize2=input.nextFloat();

       System.out.println("------------------------------------");
       System.out.println("öğrenci adi " + isim);
       System.out.println("öğrenci quiz " + quiz);
       System.out.println("öğrenci vize " + vize);
       System.out.println("öğrenci vize2 " + vize2);

       ortalama =(quiz *0.2) +(vize *0.35)+ (vize2 *0.45);
       System.out.println( "öğrenci ortalama " + ortalama );
        String sonuç  = (ortalama >=50 ) ? "geçtiniz" :"Kaldiniz";
        System.out.println("öğrenci durum sonucu :" + sonuç);
//Scanner objesini close() metodu ile kapattım çünkü uzun süreli çalışmada bellek sızıntısı yapar.
    input.close();
    }
}
