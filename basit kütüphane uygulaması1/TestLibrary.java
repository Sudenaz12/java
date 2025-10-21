public class TestLibrary {
     public static void main(String[]args){
//10 kitap kapasiteli yeni kütüphane yaptık,library bizim nesnemiz,Library class
         Library library = new Library(10);
//kitap nesnesini oluşturduk Book dizisi içine  neww ile
         Book book1 =new Book(2020);
         Book book2 = new Book(2021);
         Book book3 = new Book(2022);
         Book book4 = new Book(2024);

//kitapları kütüphaneye ekle,yeni kütüphanemiz library'e ekle
   System.out.println("\n ---Kitaplar kütüphaneye ekleniyor ---");
       library.addBook(book1);
       library.addBook(book2);
       library.addBook(book3);
       library.addBook(book4);
//şu anda olan kitapları listele
    library.listAvailableBooks();
//borrowBook ile kitabı ödünç almayı dene
  System.out.println("bazi kitaplar ödünç aliniyor...");
    library.borrowBook(book4);
    library.borrowBook(book3);

//ödünç alınıp alınmadığının kontrolünü Library sınıfının içindeki borrowBook yapıyor
   System.out.println("error: bu kitap önceden alinmiş");
   library.borrowBook(book4);

//kitap almadan sonra kitaplar listele
   library.listAvailableBooks();

//kitabı iade et
   library.returnBook(book4);
   library.returnBook(book1);

//kitap ödünç alınmamışsa iade edemezsin,kontrol et
   library.borrowBook(book1);
   

//son listeyi listele
   library.listAvailableBooks();

//olan kitabı tekrar ekle
   library.addBook(book1);

//kütüphanede olmayan kitabı ödünç al,ilk önce yeni bir kitap nesnesi oluştur ama kütüphaneye eklme
  Book book5 = new Book(2019);
  library.borrowBook(book5);
 

     }
}
