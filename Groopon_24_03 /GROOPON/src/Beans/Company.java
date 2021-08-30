package Beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of the company bean
 */
public class Company {

    private int id;
    private String name;
    private String email;
    private String password;
    private List<Coupon> coupons;

    /**
     * Constructor full args
     *
     * @param id       the company id
     * @param name     the company name
     * @param email    the company email
     * @param password the company password
     */
    public Company(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = new ArrayList<>();
    }

    /**
     * Constructor without company id
     *
     * @param name     company name
     * @param email    company email
     * @param password company email
     */
    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = getId();
    }

    /**
     * Default constructor
     */
    @SuppressWarnings("unused")
    public Company() {
    }

    //getters and setters

    /**
     * Method to getting CompanyId
     * @return int - company id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Method to get company name
     * @return string - the company name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get company email
     * @return string - company email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to set company email
     * @param email string - new company email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method to get company password
     * @return string - company password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method to set the company password
     * @param password string - new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method to get the coupons of the company
     * @return list - list of the company coupons
     */
    public List<Coupon> getCoupons() {
        return coupons;
    }

    /**
     * Method to set the list of the company coupons
     * @param coupons list - new coupon list
     */
    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    /**
     * To string of the bean class - Company
     * @return String
     */
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ",\nMy coupons:\n" + coupons +
                '}'+'\n';
    }
}
