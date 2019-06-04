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

            if (getActiveCustomerRentalResults.next()) {
                while (getActiveCustomerRentalResults.next()) {
                    String bookName = getActiveCustomerRentalResults.getString("title");
                    System.out.println("Aktív bérlései:  " + bookName);
                }
            } else {
                System.out.println("Nincs aktív bérlése!");
                return;
            }

        } catch (SQLException e) {
            System.out.println("Hiba!" + e);
        }
    }

    public void printListOfRentsByItemId(int itemId) {

    }

    public void printListOfRentsByBookId(int bookId) {

    }

    public void printMostPopularBook() {

    }

    public void printAverageRentedTimeByBookId(int BookId) {

    }

    public void printListOfRentedsByOneCustomer(int customerId) {

    }

    public List findListOfItemIdsByTitle(String title) {

    }

    public int findRentalIdByItemIdAndCustomerId(int itemId, int customerId) {

    }

    public int findCustomerIdByCustomerName(String name) {

    }

}
