package beans;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Coupon> coupons = new ArrayList<>();

    public Customer() {
    }

    public Customer(String firstName, String lastname, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastname;
        this.email = email;
        this.password = password;
    }

    public Customer(int id, String firstName, String lastname, String email, String password) {
        this(firstName, lastname, email, password);
        this.id = id;
    }

    //    public Customer(String firstName, String lastname, String email, String password, ArrayList<Coupon> coupons) {
//        this.firstName = firstName;
//        this.lastname = lastname;
//        this.email = email;
//        this.password = password;
//        this.coupons = coupons;
//    }
//
//    public Customer(int id, String firstName, String lastname, String email, String password, ArrayList<Coupon> coupons) {
//        this(firstName, lastname, email, password, coupons);
//        this.id = id;
//    }

    public Customer(Customer customer) {
        this.id = customer.id;
        this.firstName = customer.firstName;
        this.lastName = customer.lastName;
        this.email = customer.email;
        this.password = customer.password;
        this.coupons = customer.coupons;
    }

    public int getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }

}
