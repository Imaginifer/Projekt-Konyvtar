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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author imaginifer
 */
public class DataBaseLister {

    String url = "jdbc:mysql://localhost:3306/library";
    Connection conn;

    public DataBaseLister() {
        try {
            this.conn = DriverManager.getConnection(url, "root", "");
        } catch (SQLException e) {
            System.out.println("Hiba!" + e);
        }
    }

    public void printListOfCustomersAndTheirRenteds() {
        ArrayList<Integer> customerIds = new ArrayList<>();
        try {
            String getCustomerIds = "SELECT customer_id FROM library.customer";
            PreparedStatement getCustomerIdsStmt = conn.prepareStatement(getCustomerIds);

            ResultSet getCustomerIdResults = getCustomerIdsStmt.executeQuery();
            int customerId;
            if (getCustomerIdResults.next()) {
                while (getCustomerIdResults.next()) {
                    customerId = getCustomerIdResults.getInt("customer_id");
                    customerIds.add(customerId);
                }
            } else {
                System.out.println("Még nincs kölcsönző!");
                return;
            }

            for (Integer c : customerIds) {
                printListOfRentedsByOneCustomer(c);
            }

        } catch (SQLException e) {
            System.out.println("Hiba!" + e);
        }
    }

    public void printOneCustomerAndHisActiveRentalsByCustomerId(int customerId) {

        try {
            String getCustomerName = "SELECT name FROM library.customer WHERE customer_id = ?";
            PreparedStatement getCustomerNameStmt = conn.prepareStatement(getCustomerName);
            getCustomerNameStmt.setInt(1, customerId);

            ResultSet getCustomerNameResults = getCustomerNameStmt.executeQuery();
            String customerName;
            if (getCustomerNameResults.next()) {
                customerName = getCustomerNameResults.getString("name");
                System.out.println("A kölcsönző neve: " + customerName);
            } else {
                System.out.println("Nincs ilyen kölcsönző!");
                return;
            }

            String getActiveCustomerRentals = "SELECT * FROM book WHERE book_id IN\n"
                    + "(SELECT book_id FROM inventory WHERE inventory_id IN\n"
                    + " (SELECT inventory_id FROM library.rental WHERE customer_id = ? AND return_date IS NULL))";
            PreparedStatement getActiveCustomerRentalsStmt = conn.prepareStatement(getActiveCustomerRentals);
            getActiveCustomerRentalsStmt.setInt(1, customerId);

            ResultSet getActiveCustomerRentalResults = getActiveCustomerRentalsStmt.executeQuery();

            String bookName;
            ArrayList<String> bookNameList = new ArrayList<>();
            while (getActiveCustomerRentalResults.next()) {
                bookName = getActiveCustomerRentalResults.getString("title");
                bookNameList.add(bookName);
            }
            if (bookNameList.isEmpty()) {
                System.out.println("Nincs aktív bérlése!");
                return;
            } else {
                for (String string : bookNameList) {
                    System.out.println("Aktív kölcsönzés:  " + string);
                }
            }

        } catch (SQLException e) {
            System.out.println("Hiba!" + e);
        }
    }

    public void printListOfRentsByInventoryId(int inventoryId) {
        try {
            String getBookName = "SELECT title FROM library.book WHERE inventory_id = ?";
            PreparedStatement getBookNameStmt = conn.prepareStatement(getBookName);
            getBookNameStmt.setInt(1, inventoryId);

            ResultSet getBookNameResults = getBookNameStmt.executeQuery();
            String bookName;
            if (getBookNameResults.next()) {
                bookName = getBookNameResults.getString("title");
                System.out.println("A könyv címe: " + bookName);
            } else {
                System.out.println("Nincs ilyen könyv!");
                return;
            }

            String getBookRentals = "SELECT * FROM library.rental WHERE inventory_id = ?";
            PreparedStatement getBookRentalsStmt = conn.prepareStatement(getBookRentals);
            getBookRentalsStmt.setInt(1, inventoryId);

            ResultSet getBookRentalResults = getBookRentalsStmt.executeQuery();

            int customerId;
            int rentalDate;
            int returnDate;
            if (getBookRentalResults.next()) {
                while (getBookRentalResults.next()) {
                    customerId = getBookRentalResults.getInt("customer_id");
                    System.out.println("Kölcsönző azonosítója:  " + customerId);
                    rentalDate = getBookRentalResults.getInt("rantal_date");
                    System.out.println("Kölcsönzés kezdete: " + rentalDate);
                    returnDate = getBookRentalResults.getInt("return_date");
                    System.out.println("Kölcsönzés vége: " + returnDate);
                }
            } else {
                System.out.println("Még nem kölcsönözték ki ezt a  könyvet!");
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
            getBookNameStmt.setInt(1, bookId);

            ResultSet getBookNameResults = getBookNameStmt.executeQuery();
            String bookName;
            if (getBookNameResults.next()) {
                bookName = getBookNameResults.getString("title");
                System.out.println("A könyv címe: " + bookName);
            } else {
                System.out.println("Nincs ilyen könyv!");
                return;
            }

            String getBookRentals = "SELECT * FROM library.rental WHERE inventory_id IN "
                    + "(SELECT inventory_id FROM library.inventory WHERE book_id = ?)";
            PreparedStatement getBookRentalsStmt = conn.prepareStatement(getBookRentals);
            getBookRentalsStmt.setInt(1, bookId);

            ResultSet getBookRentalResults = getBookRentalsStmt.executeQuery();

            int customerId;
            int rentalDate;
            int returnDate;
            List<Integer> test =new ArrayList<>();
            while (getBookRentalResults.next()) {
                customerId = getBookRentalResults.getInt("customer_id");
                test.add(customerId);
                System.out.println("Kölcsönző azonosítója:  " + customerId);
                rentalDate = getBookRentalResults.getInt("rental_date");
                System.out.println("Kölcsönzés kezdete: " + rentalDate);
                returnDate = getBookRentalResults.getInt("return_date");
                System.out.println("Kölcsönzés vége: " + returnDate);
            }
            if(test.isEmpty()){
                System.out.println("Még nem kölcsönözték ki ezt a  könyvet!");
                return;
            }
            

        } catch (SQLException e) {
            System.out.println("Hiba!" + e);
        }
    }

    public void printMostPopularBook() {
        
        try {
            String getUserSql = "SELECT title FROM library.book where book_id = (SELECT count(book_id) FROM rental join inventory on "
                    + "rental.inventory_id = inventory.inventory_id order by count(book_id) desc limit 1)";
            PreparedStatement getUserStmt = conn.prepareStatement(getUserSql);

            ResultSet getUserResults = getUserStmt.executeQuery();
            
            String mostPopBook;
            if (getUserResults.next()) {
                mostPopBook = getUserResults.getString("title");
                System.out.println("Most popular book: " + mostPopBook);
            } else {
                System.out.println("Error!!!");
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void printAverageRentedTimeByBookId(int BookId) {
        System.out.println("Ez a funkció jelenleg nem elérhető, menjen inkább fagyizni :) ");
    }

    public void printListOfRentedsByOneCustomer(int customerId) {
        try {
            String getCustomerName = "SELECT name FROM library.customer WHERE customer_id = ?";
            PreparedStatement getCustomerNameStmt = conn.prepareStatement(getCustomerName);
            getCustomerNameStmt.setInt(1, customerId);

            ResultSet getCustomerNameResults = getCustomerNameStmt.executeQuery();
            String customerName;
            if (getCustomerNameResults.next()) {
                customerName = getCustomerNameResults.getString("name");
                System.out.println("A kölcsönző neve: " + customerName);
            } else {
                System.out.println("Nincs ilyen kölcsönző!");
                return;
            }

            String getCustomerRentals = "SELECT * FROM book WHERE book_id IN\n"
                    + "(SELECT book_id FROM inventory WHERE inventory_id IN\n"
                    + " (SELECT inventory_id FROM library.rental WHERE customer_id = ?))";
            PreparedStatement getCustomerRentalsStmt = conn.prepareStatement(getCustomerRentals);
            getCustomerRentalsStmt.setInt(1, customerId);

            ResultSet getCustomerRentalResults = getCustomerRentalsStmt.executeQuery();

            String bookName;
            if (getCustomerRentalResults.next()) {
                while (getCustomerRentalResults.next()) {
                    bookName = getCustomerRentalResults.getString("title");
                    System.out.println("Kölcsönzött könyv neve:  " + bookName);
                }
            } else {
                System.out.println("Még nem kölcsönzött könyvet!");
                return;
            }

        } catch (SQLException e) {
            System.out.println("Hiba!" + e);
        }
    }

    public List<Integer> findListOfInventoryIdsByTitle(String title) {
        List<Integer> listOfInventoryIds = new ArrayList<>();

        try {
            String getInventoryId = "SELECT * FROM library.inventory WHERE book_id IN "
                    + "( SELECT book_id FROM library.book WHERE title = ?)";
            PreparedStatement getInventoryIdStmt = conn.prepareStatement(getInventoryId);
            getInventoryIdStmt.setString(1, title);

            ResultSet getInventoryIdResults = getInventoryIdStmt.executeQuery();

            int inventoryId;
            while (getInventoryIdResults.next()) {
                inventoryId = getInventoryIdResults.getInt("inventory_id");
                listOfInventoryIds.add(inventoryId);
            }
            if (listOfInventoryIds.isEmpty()) {

                System.out.println("Nincs ilyen könyv, vagy azonosító a raktárban!");
            }
            return listOfInventoryIds;

        } catch (SQLException e) {
            System.out.println("Hiba!" + e);
        }
        System.out.println("Nincs ilyen könyv, vagy azonosító a raktárban!");
        return listOfInventoryIds;
    }

    public int findRentalIdByInventoryIdAndCustomerId(int inventoryId, int customerId) {
        try {
            String getRentalId = "SELECT * FROM library.rental WHERE inventory_id = ? AND customer_id = ?";
            PreparedStatement getRentalIdStmt = conn.prepareStatement(getRentalId);
            getRentalIdStmt.setInt(1, inventoryId);
            getRentalIdStmt.setInt(2, customerId);

            ResultSet getRentalIdResults = getRentalIdStmt.executeQuery();

            int rentalId;
            if (getRentalIdResults.next()) {
                rentalId = getRentalIdResults.getInt("rental_id");
                return rentalId;
            } else {
                System.out.println("Valamelyik azonosító nem létezik!");
                return -1;
            }

        } catch (SQLException e) {
            System.out.println("Hiba!" + e);
        }
        System.out.println("Valamelyik azonosító nem létezik!");
        return -1;
    }

    public int findCustomerIdByCustomerName(String CustomerName) {
        try {
            String getCustomerId = "SELECT customer_id FROM library.customer WHERE name = ?";
            PreparedStatement getCustomerIdStmt = conn.prepareStatement(getCustomerId);
            getCustomerIdStmt.setString(1, CustomerName);

            ResultSet getCustomerIdResults = getCustomerIdStmt.executeQuery();

            int customerId;
            if (getCustomerIdResults.next()) {
                customerId = getCustomerIdResults.getInt("customer_id");
                return customerId;
            } else {
                System.out.println("Nincs ilyen ID/név!");
                return -1;
            }

        } catch (SQLException e) {
            System.out.println("Hiba!" + e);
        }
        System.out.println("Nincs ilyen ID/név!");
        return -1;
    }

    public int findBookId(String name) {

        try {
            String getUserSql = "SELECT book_id FROM book where title = ?";
            PreparedStatement getUserStmt = conn.prepareStatement(getUserSql);

            getUserStmt.setString(1, name);

            ResultSet getUserResults = getUserStmt.executeQuery();
            int bookId;
            if (getUserResults.next()) {
                bookId = getUserResults.getInt("book_id");
                return bookId;
            } else {
                System.out.println("No such book!");
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return -1;
    }

    public int findBookOut(String title, int customerId) {

        try {
            String getData = "SELECT inventory_id FROM rental where inventory_id = (SELECT inventory_id FROM inventory where book_id ="
                    + "(SELECT book_id FROM book where title = ?)) and customer_id = ? and return_date is null";
            PreparedStatement getDataStmt = conn.prepareStatement(getData);

            getDataStmt.setString(1, title);
            getDataStmt.setInt(2, customerId);

            ResultSet getDataResults = getDataStmt.executeQuery();

            int inventoryId;
            if (getDataResults.next()) {
                inventoryId = getDataResults.getInt("inventory_id");
                return inventoryId;
            } else {
                System.out.println("Error!!!");
                return -1;
            }
        } catch (SQLException e) {
            System.out.println("Hiba!" + e);
        }
        System.out.println("Error!!!");
        return -1;
    }
}
