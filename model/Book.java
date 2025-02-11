import java.util.vector;
import java.sql.*;

public class BookCollection{
    private Vector <book> list_books;

    public BookCollection(){
        list_books = new Vector<>();

    }
    public void book_find_title(String title) throws SQLExeption{
        list_books.clear();
        String query = "SELECT * FROM Book WHERE bookTitle LIKE" + title;
        try(){
            Result = query.executeQuery();
        }

    }

    public void book_find_year(int year) throws SQLException{
        list_books.clear();
        String query = "SELECT * FROM Book WHERE pubYear <=" + year;
        try(){
            result = query.executeQuery();
        }
    }

    public void patron_age(int date) throws SQLException{
        list_books.clear();
        String query = "SELECT Name FROM Patron WHERE dateOfBirth <=" + date;
        try(){
            result = query.executeQuery();
        }
    }

    public void results_display(){
        if (list_books.isEmpty()){
            System.out.println("No books matching in the Database");

        }else{
            System.out.println(Result)
        }
    }
}

Given a year, print all book data for books that are published before that year.
5. Given a date, print all patron data for patrons that are younger than that date.
6. Given a zip, print all patron data for patrons that live at that zip