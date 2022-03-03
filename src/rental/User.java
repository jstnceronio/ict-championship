package rental;

public class User {

    private int userId;
    private String firstName;
    private String lastName;
    private String address;
    private String zip;
    private String city;
    private String telephone;
    private String mail;
    private String creditCard;
    private String password;

    public User(int userId, String firstName, String lastName, String address, String zip, String city, String telephone, String mail, String creditcard, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.telephone = telephone;
        this.mail = mail;
        this.creditCard = creditcard;
        this.password = password;
    }

    public String toString() {
        return "User: " + firstName + " " + lastName + " " + address + " " + zip + " " + city + " " + telephone + " " + mail + " " +  creditCard + " " +  password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
