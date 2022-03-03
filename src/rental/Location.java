package rental;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Location {

    private int locationId;
    private String city;
    private String address;
    private String zipCode;

    private StringProperty cityName = new SimpleStringProperty();

    public Location(int locationId, String city, String address, String zipCode) {
        this.locationId = locationId;
        this.city = city;
        this.address = address;
        this.zipCode = zipCode;
        cityName.set(city);
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return cityName.get();
    }
}
