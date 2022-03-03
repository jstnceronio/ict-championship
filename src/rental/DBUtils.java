package rental;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class DBUtils {

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental", "root", "root");
    }

    public static void registerUser(User user) {
        try {
            Connection connection = getConnection();
            PreparedStatement psInsert = connection.prepareStatement(
                    "INSERT INTO users (email, password, firstName, lastName, zipCode, city, telephone, creditCardNumber, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            psInsert.setString(1, user.getMail());
            psInsert.setString(2, user.getPassword());
            psInsert.setString(3, user.getFirstName());
            psInsert.setString(4, user.getLastName());
            psInsert.setString(5, user.getZip());
            psInsert.setString(6, user.getCity());
            psInsert.setString(7, user.getTelephone());
            psInsert.setString(8, user.getCreditCard());
            psInsert.setString(9, user.getAddress());
            psInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User isUser(String email, String password) {
        try {
            Connection connection = getConnection();
            PreparedStatement psSelect = connection.prepareStatement(
                    "SELECT * FROM users where email = ? and password = ?"
            );
            psSelect.setString(1, email);
            psSelect.setString(2, password);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                User existingUser = new User(
                        rs.getInt("userId"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("address"), rs.getString("zipCode"),
                        rs.getString("city"), rs.getString("telephone"), rs.getString("email"), rs.getString("creditCardNumber"),
                        rs.getString("password"));
                return existingUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ObservableList<Location> getLocationList() {
        ObservableList<Location> locationList = FXCollections.observableArrayList();
        try {
            Connection connection = getConnection();
            PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM locations");
            ResultSet rs = psSelect.executeQuery();
            Location location;
            while (rs.next()) {
                location = new Location(
                        rs.getInt("locationId"),
                        rs.getString("city"),
                        rs.getString("address"),
                        rs.getString("zipCode")
                );
                locationList.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locationList;
    }

    public static ObservableList<Category> getCategoryList() {
        ObservableList<Category> categoryList = FXCollections.observableArrayList();
        try {
            Connection connection = getConnection();
            PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM categories");
            ResultSet rs = psSelect.executeQuery();
            Category category;
            while (rs.next()) {
                category = new Category(
                        rs.getInt("categoryId"),
                        rs.getString("categoryName"),
                        rs.getDouble("categoryPrize")
                );
                categoryList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public static ObservableList<Car> getCarList() {
        ObservableList<Car> carList = FXCollections.observableArrayList();
        try {
            Connection connection = getConnection();
            PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM cars");
            ResultSet rs = psSelect.executeQuery();
            Car car;
            while (rs.next()) {
                Location location = getLocationFromId(rs.getInt("locationFk"));
                Category category = getCategoryFromId(rs.getInt("categoryFk"));
                car = new Car(
                        rs.getInt("carId"),
                        rs.getString("description"),
                        rs.getString("year"),
                        location,
                        category,
                        rs.getBoolean("isRented"),
                        rs.getDate("pickUpDate"),
                        rs.getDate("dropOffDate")
                );
                carList.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carList;
    }

    public static Location getLocationFromId(int id) {
        try {
            Connection connection = getConnection();
            PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM locations WHERE locationId = ?");
            psSelect.setInt(1, id);
            ResultSet rs = psSelect.executeQuery();
            Location location = null;
            if (rs.next()) {
                location = new Location(
                        rs.getInt("locationId"),
                        rs.getString("city"),
                        rs.getString("address"),
                        rs.getString("zipcode")
                );
            }
            return location;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // smth went wrong :(
    }

    public static Category getCategoryFromId(int id) {
        try {
            Connection connection = getConnection();
            PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM categories WHERE categoryId = ?");
            psSelect.setInt(1, id);
            ResultSet rs = psSelect.executeQuery();
            Category category = null;
            if (rs.next()) {
                category = new Category(
                        rs.getInt("categoryId"),
                        rs.getString("categoryName"),
                        rs.getDouble("categoryPrize")
                );
            }
            return category;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // smth went wrong :(
    }

    public static ObservableList<Car> getMostPopularCars() {
        ObservableList<Car> popularCars = FXCollections.observableArrayList();
        try {
            Connection connection = getConnection();
            PreparedStatement psSelect = connection.prepareStatement("SELECT * FROM cars  WHERE isRented = 1 GROUP BY description ORDER BY COUNT(description) DESC");
            ResultSet rs = psSelect.executeQuery();
            Car car;
            while (rs.next()) {
                Location location = getLocationFromId(rs.getInt("locationFk"));
                Category category = getCategoryFromId(rs.getInt("categoryFk"));
                car = new Car(
                        rs.getInt("carId"),
                        rs.getString("description"),
                        rs.getString("year"),
                        location,
                        category,
                        rs.getBoolean("isRented"),
                        rs.getDate("pickUpDate"),
                        rs.getDate("dropOffDate")
                );
                popularCars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return popularCars;
    }

    public static void rentCar(Car carToRent, User customer) {
        try {
            Connection connection = getConnection();
            PreparedStatement psUpdate = connection.prepareStatement(
                    "UPDATE cars SET pickUpDate = ?, dropOffDate = ?, userFk = ?, isRented = 1 WHERE carId = ?"
            );
            psUpdate.setDate(1, new Date(carToRent.getPickUpDate().getTime()));
            psUpdate.setDate(2, new Date(carToRent.getDropOffDate().getTime()));
            psUpdate.setInt(3, customer.getUserId());
            psUpdate.setInt(4, carToRent.getCarId());
            psUpdate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
