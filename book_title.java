import java.util.vector;
import java.sql.*;

public class BookCollection{
    private Vector <book> list_books;

    public BookCollection(){
        list_books = new Vector<>();

    }
    public void book_find(String title) throws SQLExeption{
        list_books.clear();
        String query = "SELECT * FROM Book WHERE bookTitle LIKE" + title;
        try(){
            Result = query.executeQuery();
        }

    }
    Public void results_display(){
        if (list_books.isEmpty()){
            System.out.println("No books matching in the Database");

        }else{
            System.out.println(Result)
        }
    }
}