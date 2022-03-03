package rental;

import java.util.Date;

public class Car {

    private int carId;
    private String description;
    private String year;
    private Location locationFk;
    private Category categoryFk;
    private boolean isRented;
    private int userFk;
    private Date pickUpDate;
    private Date dropOffDate;

    public Car(int carId, String description, String year, Location locationFk, Category categoryFk, boolean isRented, Date pickUpDate, Date dropOffDate) {
        this.carId = carId;
        this.description = description;
        this.year = year;
        this.locationFk = locationFk;
        this.categoryFk = categoryFk;
        this.isRented = isRented;
        this.pickUpDate = pickUpDate;
        this.dropOffDate = dropOffDate;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Location getLocationFk() {
        return locationFk;
    }

    public void setLocationFk(Location locationFk) {
        this.locationFk = locationFk;
    }

    public Category getCategoryFk() {
        return categoryFk;
    }

    public void setCategoryFk(Category categoryFk) {
        this.categoryFk = categoryFk;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public int getUserFk() {
        return userFk;
    }

    public void setUserFk(int userFk) {
        this.userFk = userFk;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Date getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(Date dropOffDate) {
        this.dropOffDate = dropOffDate;
    }
}
