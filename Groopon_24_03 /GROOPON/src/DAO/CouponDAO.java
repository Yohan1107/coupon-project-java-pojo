package DAO;

import Beans.Coupon;
import Beans.Customer;
import Exceptions.GrooponSystemException;

import java.util.List;

/**
 * Interface - abstract methods for the coupon CRUD
 */
public interface CouponDAO {

    /**
     * Method to add a coupon to the system
     *
     * @param coupon coupon we want to add to the system
     * @throws GrooponSystemException enable to throw exception
     */
    void addCoupon(Coupon coupon) throws GrooponSystemException;


    /**
     * Method to update coupon from the system
     *
     * @param coupon coupon we want to update
     * @throws GrooponSystemException enable to throw exception
     */
    void updateCoupon(Coupon coupon) throws GrooponSystemException;

    /**
     * Method to remove coupon from the system
     *
     * @param couponId int - id of the coupon we want to remove
     * @throws GrooponSystemException enable to throw exception
     */
    void deleteCoupon(int couponId) throws GrooponSystemException;


    /**
     * Method to get all the coupons of the system
     *
     * @return list - list of coupons
     * @throws GrooponSystemException enable to throw exception
     */
    List<Coupon> getAllCoupons() throws GrooponSystemException;


    /**
     * Method to get details of one coupon
     *
     * @param couponId id of the coupon we want to get
     * @return Coupon object - coupon details
     * @throws GrooponSystemException enable to throw exception
     */
    Coupon getOneCoupon(int couponId) throws GrooponSystemException;

    /**
     * Method to add a purchase coupon
     *
     * @param customerId id of the customer that purchase the coupon
     * @param couponId   id of the purchase coupon
     * @return boolean - true if the coupon was purchased
     * @throws GrooponSystemException enable to throw exception
     */
    boolean addCouponPurchase(int customerId, int couponId) throws GrooponSystemException;

    /**
     * Method to remove purchased coupon
     *
     * @param customerId int - id of the customer that purchase the coupon
     * @param couponId   int - id of the purchased coupon
     * @throws GrooponSystemException enable to throw exception
     */
    void deleteCouponPurchase(int customerId, int couponId) throws GrooponSystemException;

    /**
     * Method to get list of all coupons that customer purchased
     *
     * @param customerId int - id of the customer
     * @return list - coupon list
     * @throws GrooponSystemException enable to throw exception
     */
    List<Coupon> getCustomerCouponsList(int customerId) throws GrooponSystemException;

    /**
     * Method to get list of all customers that purchase this coupon
     *
     * @param couponId int - id of the coupon we want to know about
     * @return list - list of all the customers that purchase this coupon
     * @throws GrooponSystemException enable to throw exception
     */
    List<Customer> getCouponCustomersList(int couponId) throws GrooponSystemException;

    /**
     * Method to get list of all coupons of a company
     *
     * @param companyId int - id of the company
     * @return list - coupon list of this company
     * @throws GrooponSystemException enable to throw exception
     */
    List<Coupon> getCompanyCouponsList(int companyId) throws GrooponSystemException;

    /**
     * Method to get list of company coupons under price
     *
     * @param companyId int - id of the company
     * @param price     double -   the max price
     * @return list - list of the coupons under this price
     * @throws GrooponSystemException enable to throw exception
     */
    List<Coupon> getCompanyCouponsUnderPrice(int companyId, double price) throws GrooponSystemException;

    /**
     * Method to get list of company coupons by category
     *
     * @param companyId  int - id of the company
     * @param categoryId int - id of the category
     * @return list - list of coupons for this category
     * @throws GrooponSystemException enable to throw exception
     */
    List<Coupon> getCompanyCouponsByCategory(int companyId, int categoryId) throws GrooponSystemException;

    /**
     * Method to get list of customer coupons by category
     *
     * @param customerId int - id of the customer
     * @param categoryId int - id of the category
     * @return list - list of coupons for this category
     * @throws GrooponSystemException enable to throw exception
     */
    List<Coupon> getCustomerCouponsByCategory(int customerId, int categoryId) throws GrooponSystemException;

    /**
     * Method to get list of customer coupons under price
     *
     * @param customerId int - id of the customer
     * @param price      double - the max price
     * @return list - list of the coupons under this price
     * @throws GrooponSystemException enable to throw exception
     */
    List<Coupon> getCustomerCouponsUnderPrice(int customerId, double price) throws GrooponSystemException;


}
