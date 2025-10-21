public class Library {
    private Book[] books; // Book nesnelerini tutan dizi
    private int totalBooks; // O anki kitap sayısı

    public Library(int kapasite) {
        books = new Book[kapasite];
        this.totalBooks = 0; // Kütüphanede ilk başta kitap yok
    }

    public void addBook(Book book) {
        if (totalBooks >= books.length) {
            System.out.println("Kütüphane dolu, kitap eklenemiyor.");
            return;
        }
        for (int i = 0; i < totalBooks; i++) {
            if (books[i].getBookID() == book.getBookID()) {
                System.out.println("Hata: Bu kitap (ID: " + book.getBookID() + ") zaten kütüphanede mevcut.");
                return;
            }
        }
        books[totalBooks] = book;
        totalBooks++;
        System.out.println("Kitap başarıyla eklendi ID: " + book.getBookID());
    }

    public void borrowBook(Book book) {
        if (book == null) {
            System.out.println("Geçersiz kitap nesnesi.");
            return;
        }

        Book libraryBook = findBook(book.getBookID());

        // DÜZELTME 1: Kitabın kütüphanede olup olmadığını kontrol et
        if (libraryBook == null) {
            System.out.println("Hata: " + book.getBookID() + " ID'li kitap kütüphanede bulunamadı.");
            return;
        }

        // DÜZELTME 2: Kitabın zaten ödünç alınıp alınmadığını kontrol et
        if (libraryBook.getIsBorrowed()) {
            System.out.println("Hata: Bu kitap (ID: " + libraryBook.getBookID() + ") zaten ödünç alınmış.");
            return; // DÜZELTME: Hata mesajından sonra metottan çıkmak için 'return' eklendi.
        }
        
        // Yukarıdaki kontrollerden geçerse kitabı ödünç al
        libraryBook.borrowBook();
        System.out.println(libraryBook.getBookID() + " ID'li kitap ödünç alındı.");
    }

    public void returnBook(Book book) {
        if (book == null) {
            System.out.println("Geçersiz kitap nesnesi.");
            return;
        }
        
        Book libraryBook = findBook(book.getBookID());

        // DÜZELTME 3 (ÖNEMLİ): NullPointerException hatasını önlemek için
        // 'libraryBook' nesnesinin 'null' olup olmadığını, 'getIsBorrowed()' metodunu
        // çağırmadan ÖNCE kontrol etmeliyiz.
        if (libraryBook == null) {
            System.out.println("Hata: İade edilmek istenen " + book.getBookID() + " ID'li kitap kütüphanede bulunamadı.");
            return; // Metottan çık
        }

        // DÜZELTME 4: Kitabın ödünç durumunu kontrol et
        if (!libraryBook.getIsBorrowed()) {
            // Mesaj netleştirildi.
            System.out.println("Hata: " + libraryBook.getBookID() + " ID'li kitap zaten kütüphanede (ödünç alınmamış).");
            return;
        }
        
        // Kitabı iade et
        libraryBook.returnBook();
        System.out.println("Kitap (ID: " + libraryBook.getBookID() + ") başarıyla iade edildi.");
    }

    public void listAvailableBooks() {
        boolean foundAvailable = false; // Mevcut kitap var mı diye kontrol
        System.out.println("\n--- Mevcut Kitapların Listesi ---");
        for (int i = 0; i < totalBooks; i++) {
            if (!books[i].getIsBorrowed()) {
                System.out.println("Book ID: " + books[i].getBookID() + ", Yıl: " + books[i].getYear());
                foundAvailable = true; // Kitap bulundu
            }
        }
        // Döngü bittikten sonra hiç kitap bulamadıysak
        if (!foundAvailable) {
            System.out.println("Kütüphanede mevcut (ödünç alınabilir) kitap bulunmamaktadır.");
        }
        System.out.println("---------------------------------");
    }

    public Book findBook(int bookID) {
        for (int i = 0; i < totalBooks; i++) {
            if (books[i].getBookID() == bookID) {
                return books[i];
            }
        }
        return null; // Kitap bulunmazsa
    }
}
