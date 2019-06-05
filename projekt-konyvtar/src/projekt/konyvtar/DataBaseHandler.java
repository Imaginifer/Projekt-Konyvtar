package projekt.konyvtar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseHandler {

    Connection conn;

    public DataBaseHandler() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "");
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void addBook(String a, String t) {
        try {
            String insertBookSql = "insert into library.book(author, title) values(?, ?)";
            PreparedStatement insertBookStmt = conn.prepareStatement(insertBookSql, PreparedStatement.RETURN_GENERATED_KEYS);
            insertBookStmt.setString(1, a);
            insertBookStmt.setString(2, t);
            insertBookStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void removeBook(int id) {
        try {
            String delBookSql = "DELETE FROM library.book WHERE book_id = ?";
            PreparedStatement delBookStmt = conn.prepareStatement(delBookSql, PreparedStatement.RETURN_GENERATED_KEYS);
            delBookStmt.setInt(1, id);
            delBookStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void addItem(int bookId) {
        try {
            String insertBookSql = "insert into inventory (book_id) select book_id from book where book_id = ?";
            PreparedStatement insertBookStmt = conn.prepareStatement(insertBookSql, PreparedStatement.RETURN_GENERATED_KEYS);
            insertBookStmt.setInt(1, bookId);
            insertBookStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void scrapItem(int itemId) {
        try {
            String deleteBookSql = "delete from inventory where inventory_id = ?";
            PreparedStatement deleteBookStmt = conn.prepareStatement(deleteBookSql, PreparedStatement.RETURN_GENERATED_KEYS);
            deleteBookStmt.setInt(1, itemId);
            deleteBookStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void addCustomer(String name) {
        try {
            String addCustomerSql = "insert into customer (name) value (?)";
            PreparedStatement addCustomerStmt = conn.prepareStatement(addCustomerSql, PreparedStatement.RETURN_GENERATED_KEYS);
            addCustomerStmt.setString(1, name);
            addCustomerStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void setCustomerStatus(int customerId, boolean status) {
        if (status) {
            try {
                String customerSatusSql = "update customer set active = 1 where customer_id = ?";
                PreparedStatement customerSatusStmt = conn.prepareStatement(customerSatusSql, PreparedStatement.RETURN_GENERATED_KEYS);
                customerSatusStmt.setInt(1, customerId);
                customerSatusStmt.executeUpdate();
            } catch (SQLException ex) {
                ex.getMessage();
            }
        } else {
            try {
                String customerSatusSql = "update customer set active = null where customer_id = ?";
                PreparedStatement customerSatusStmt = conn.prepareStatement(customerSatusSql, PreparedStatement.RETURN_GENERATED_KEYS);
                customerSatusStmt.setInt(1, customerId);
                customerSatusStmt.executeUpdate();
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }
    }

    public void rentABook(int customerId, int inventoryId) {
        try {
            String rentABookSql = "insert into rental (customer_id, inventory_id, rental_date, return_date) values (?, ?, now(), null)";
            PreparedStatement rentABookStmt = conn.prepareStatement(rentABookSql, PreparedStatement.RETURN_GENERATED_KEYS);
            rentABookStmt.setInt(1, customerId);
            rentABookStmt.setInt(2, inventoryId);
            
            rentABookStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void returnBook(int inventoryId) {
        try {
            String returnBookSql = "update rental set return_date = now() where inventory_id = ?";
            PreparedStatement returnBookStmt = conn.prepareStatement(returnBookSql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            returnBookStmt.setInt(1, inventoryId);
            returnBookStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
}
