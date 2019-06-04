/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.konyvtar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author imaginifer
 */
public class DataBaseLister {

    String url = "jdbc:mysql://localhost:3306/library";
    Connection conn;

    public DataBaseLister() throws SQLException {
        this.conn = DriverManager.getConnection(url, "root", "");
    }

    public void printListOfCustomersAndTheirRenteds() {

    }

    public void printOneCustomerAndHisActiveRentalsByCustomerId(int customerId) {

        try {
            String getCustomerName = "SELECT name FROM library.customer WHERE customer_id = ?";
            PreparedStatement getCustomerNameStmt = conn.prepareStatement(getCustomerName);
            getCustomerNameStmt.setString(1, "%" + customerId + "%");

            ResultSet getCustomerNameResults = getCustomerNameStmt.executeQuery();
            System.out.println("A keresett személy neve: " + getCustomerNameResults);

            String getActiveCustomerRentals = "SELECT * FROM book WHERE book_id IN\n"
                    + "(SELECT book_id FROM inventory WHERE inventory_id IN\n"
                    + " (SELECT inventory_id FROM library.rental WHERE customer_id = ? AND return_date = NULL))";
            PreparedStatement getActiveCustomerRentalsStmt = conn.prepareStatement(getActiveCustomerRentals);
            getActiveCustomerRentalsStmt.setString(1, "%" + customerId + "%");

            ResultSet getActiveCustomerRentalResults = getActiveCustomerRentalsStmt.executeQuery();

            String bookName;
            if (getActiveCustomerRentalResults.next()) {
                while (getActiveCustomerRentalResults.next()) {
                    bookName = getActiveCustomerRentalResults.getString("title");
                    System.out.println("Aktív bérlése:  " + bookName);
                }
            } else {
                System.out.println("Nincs aktív bérlése!");
                return;
            }

        } catch (SQLException e) {
            System.out.println("Hiba!" + e);
        }
    }

    public void printListOfRentsByInventoryId(int inventoryId) {
        try {
            String getBookName = "SELECT title FROM library.book WHERE inventory_id = ?";
            PreparedStatement getBookNameStmt = conn.prepareStatement(getBookName);
            getBookNameStmt.setString(1, "%" + inventoryId + "%");

            ResultSet getBookNameResults = getBookNameStmt.executeQuery();
            System.out.println("A keresett könyv címe: " + getBookNameResults);

            String getBookRentals = "SELECT * FROM library.rental WHERE inventory_id = ?";
            PreparedStatement getBookRentalsStmt = conn.prepareStatement(getBookRentals);
            getBookRentalsStmt.setString(1, "%" + inventoryId + "%");

            ResultSet getBookRentalResults = getBookRentalsStmt.executeQuery();

            int customerId;
            int rentalDate;
            int returnDate;
            if (getBookRentalResults.next()) {
                while (getBookRentalResults.next()) {
                    customerId = getBookRentalResults.getInt("customer_id");
                    System.out.println("Bérlő azonosítója:  " + customerId);
                    rentalDate = getBookRentalResults.getInt("rantal_date");
                    System.out.println("Bérlés kezdete: " + rentalDate);
                    returnDate = getBookRentalResults.getInt("return_date");
                    System.out.println("Bérlés vége: " + returnDate);
                }
            } else {
                System.out.println("Még nem bérelték ki ezt a  könyvet!");
                return;
            }

        } catch (SQLException e) {
            System.out.println("Hiba!" + e);
        }
    }

    public void printListOfRentsByBookId(int bookId) {
        try {
            String getBookName = "SELECT title FROM library.book WHERE book_id = ?";
            PreparedStatement getBookNameStmt = conn.prepareStatement(getBookName);
            getBookNameStmt.setString(1, "%" + bookId + "%");

            ResultSet getBookNameResults = getBookNameStmt.executeQuery();
            System.out.println("A keresett könyv címe: " + getBookNameResults);

            String getBookRentals = "SELECT * FROM library.rental WHERE inventory_id IN "
                    + "(SELECT inventory_id FROM library.inventory WHERE book_id = ?)";
            PreparedStatement getBookRentalsStmt = conn.prepareStatement(getBookRentals);
            getBookRentalsStmt.setString(1, "%" + bookId + "%");

            ResultSet getBookRentalResults = getBookRentalsStmt.executeQuery();

            int customerId;
            int rentalDate;
            int returnDate;
            if (getBookRentalResults.next()) {
                while (getBookRentalResults.next()) {
                    customerId = getBookRentalResults.getInt("customer_id");
                    System.out.println("Bérlő azonosítója:  " + customerId);
                    rentalDate = getBookRentalResults.getInt("rantal_date");
                    System.out.println("Bérlés kezdete: " + rentalDate);
                    returnDate = getBookRentalResults.getInt("return_date");
                    System.out.println("Bérlés vége: " + returnDate);
                }
            } else {
                System.out.println("Még nem bérelték ki ezt a  könyvet!");
                return;
            }

        } catch (SQLException e) {
            System.out.println("Hiba!" + e);
        }
    }

    public void printMostPopularBook() {

    }

    public void printAverageRentedTimeByBookId(int BookId) {

    }

    public void printListOfRentedsByOneCustomer(int customerId) {
        try {
            String getCustomerName = "SELECT name FROM library.customer WHERE customer_id = ?";
            PreparedStatement getCustomerNameStmt = conn.prepareStatement(getCustomerName);
            getCustomerNameStmt.setString(1, "%" + customerId + "%");

            ResultSet getCustomerNameResults = getCustomerNameStmt.executeQuery();
            System.out.println("A keresett személy neve: " + getCustomerNameResults);

            String getCustomerRentals = "SELECT * FROM book WHERE book_id IN\n"
                    + "(SELECT book_id FROM inventory WHERE inventory_id IN\n"
                    + " (SELECT inventory_id FROM library.rental WHERE customer_id = ?))";
            PreparedStatement getCustomerRentalsStmt = conn.prepareStatement(getCustomerRentals);
            getCustomerRentalsStmt.setString(1, "%" + customerId + "%");

            ResultSet getCustomerRentalResults = getCustomerRentalsStmt.executeQuery();

            String bookName;
            if (getCustomerRentalResults.next()) {
                while (getCustomerRentalResults.next()) {
                    bookName = getCustomerRentalResults.getString("title");
                    System.out.println("Bérelt könyve:  " + bookName);
                }
            } else {
                System.out.println("Még nem bérelt könyvet!");
                return;
            }

        } catch (SQLException e) {
            System.out.println("Hiba!" + e);
        }
    }

    public List findListOfInventoryIdsByTitle(String title) {

    }

    public int findRentalIdByInventoryIdAndCustomerId(int itemId, int customerId) {

    }

    public int findCustomerIdByCustomerName(String name) {

    }

}
