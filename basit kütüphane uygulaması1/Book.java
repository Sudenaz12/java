public class Book {
    private  int bookID;
    private static int bookCount=0;
    private int year;
    private boolean isBorrowed;

    public Book(int year){
      bookCount++;
      this.bookID=bookCount;
      this.isBorrowed=false;
      this.year=year;
    }

     public boolean getIsBorrowed(){
        return  isBorrowed;//kitabın ödünç durumunu döndürür
    }
    public  int getBookID(){
        return this.bookID;
    }
    public int getYear(){
        return this.year;
    }
    
    public  void borrowBook(){//ödünç alma metodu
         isBorrowed = true;
        
    }
    public void returnBook(){
        isBorrowed=false;
    }
}
