public class Book {
    String title;
    String author;
    int pages;
     //Main Constructor 
    public Book(String title, String author, int pages){
        this.title = title;
        this.author = author;
        this.pages = pages; 
    }
    public Book(String title, String author){
        this.title = title;
        this.author = author;
        this.pages =0;
    }

    public static void main(String[] args){
        Book redRissing = new Book("Red Rissing","perice Brown",368);
        Book PercyJackson = new Book("Percy Jackson", "Gwen", 289);
        Book Wee = new Book("Yeess", "noo");

        System.out.println(description(redRissing));
        System.out.println(description(PercyJackson));
        System.out.println(description(Wee));
    }
    public static String description(Book book){
        String description = book.title +" By "+ book.author + " "+ book.pages + " Pages";

        return description;
    }

    public boolean isLong(Book book){
        if(book.pages > 400){
            return true;
        }
        return false;
    }
}
