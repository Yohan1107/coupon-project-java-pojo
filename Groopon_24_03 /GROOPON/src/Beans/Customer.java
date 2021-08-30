package Beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of the customer bean
 */
public class Customer {

    private int id;
    private String firstName;
    private String lstName;
    private String email;
    private String password;
    private List<Coupon> coupons;

    /**
     * Constructor full args
     *
     * @param id        customer id
     * @param email     customer email
     * @param password  customer password
     * @param firstName customer first name
     * @param lstName   customer last name
     */
    public Customer(int id, String email, String password, String firstName, String lstName) {
        this.id = id;
        this.firstName = firstName;
        this.lstName = lstName;
        this.email = email;
        this.password = password;
        this.coupons = new ArrayList<>();
    }

    /**
     * Constructor without id (id- Auto-Increment)
     * @param email customer email
     * @param password customer email
     * @param firstName customer first name
     * @param lstName customer last name
     */
    public Customer(String email, String password, String firstName, String lstName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lstName = lstName;
        this.coupons = new ArrayList<>();
    }

    /**
     * Default constructor
     */
    public Customer() {
    }

    /**
     * Method to get the id of the customer
     * @return int - customer id
     */
    public int getId() {
        return id;
    }

    /**
     * Method to get the first name of the customer
     * @return string - customer first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Method to set the customer first name
     * @param firstName string - new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Method to get the last name of the customer
     * @return string - customer last name
     */
    public String getLstName() {
        return lstName;
    }

    /**
     * Method to set the customer last name
     * @param lstName string - new customer last name
     */
    public void setLstName(String lstName) {
        this.lstName = lstName;
    }

    /**
     * Method to get the email of the customer
     * @return string - customer email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to set the customer email
     * @param email string - new customer email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method to get the customer password
     * @return string - customer password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method to set the customer password
     * @param password string - new customer password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method to get the list of the customer coupons
     * @return list - coupons list
     */
    public List<Coupon> getCoupons() {
        return coupons;
    }

    /**
     * Method to set the customer coupon list
     * @param coupons list - new coupons list
     */
    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }


    /**
     *
     * @return to string of the class
     */
    @Override
    public String toString() {
        return "Customer" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lstName='" + lstName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ",\nMy coupons:\n" + coupons +
                '\n';
    }
}
