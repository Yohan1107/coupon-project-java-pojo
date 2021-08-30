package Facades;

import Beans.Coupon;
import Beans.Customer;
import DBDAO.CouponDBDAO;
import DBDAO.CustomerDBDAO;
import Exceptions.GrooponSystemException;

import java.util.List;

/**
 * Customer facade - Allow the customer to use his skills
 */
public class CustomerFacade extends ClientFacade {

    private int customerId;

    /**
     * Constructor no args - initialise couponDAO and customerDAO
     */
    public CustomerFacade() {
        this.couponDAO = new CouponDBDAO();
        this.customerDAO = new CustomerDBDAO();
    }

    /**
     * Method to set customerId by the customer who do login
     *
     * @param customerId int - new customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Method to check if the email and the password are correct
     *
     * @param email    of the user
     * @param password of the user
     * @return true if the email and the password are correct
     */
    @Override
    public boolean login(String email, String password) {
        try {
            //check if customer exist in the system
            if (customerDAO.isCustomerExist(email, password)) {
                // find this customer in the system
                for (Customer item : customerDAO.getAllCustomers()) {
                    if (item.getEmail().equals(email) && item.getPassword().equals(password)) {
                        // set the customer id of this class with the id of the customer we get
                        this.setCustomerId(item.getId());
                        return true;
                    }
                }
            }
        } catch (Exception err){
            System.err.println(err.getMessage());
        }
        return false;
    }

    /**
     * Method that allow the customer to get one coupon from the system
     *
     * @param couponId id of the coupon
     * @return coupon object
     */
    public Coupon getOneCoupon(int couponId) {
        try {
            return couponDAO.getOneCoupon(couponId);
        }catch (Exception err){
            System.err.println(err.getMessage());
        }
        return null;
    }

    /**
     * Method that allow customer to purchase coupon
     *
     * @param coupon coupon that the customer wants to purchase
     * @return true if the coupon was purchased
     */
    public boolean purchaseCoupon(Coupon coupon) {
        try{
            return couponDAO.addCouponPurchase(customerId, coupon.getId());
        }catch (Exception err){
            System.err.println(err.getMessage());
        }
        return false;
    }

    /**
     * Method that allow customer to get his coupons
     *
     * @return list of the customer coupons
     */
    public List<Coupon> getCustomerCoupons() {
        try{
            return couponDAO.getCustomerCouponsList(customerId);
        }catch (Exception err){
            System.err.println(err.getMessage());
        }
        return null;
    }

    /**
     * Method that allow customer to get all his coupons by category
     *
     * @param categoryId category id of the coupons customer wants to get
     * @return list of coupons by category
     */
    public List<Coupon> getCustomerCouponsByCategory(int categoryId){
        try {
            return couponDAO.getCustomerCouponsByCategory(customerId, categoryId);
        }catch (Exception err){
            System.err.println(err.getMessage());
        }
        return null;
    }

    /**
     * Method that allow customer to get all his coupons by max price
     *
     * @param price the max price
     * @return list of coupons by max price
     * @throws GrooponSystemException enable to throw exception
     */
    public List<Coupon> getCustomerCouponsUnderPrice(double price) throws GrooponSystemException {
        try{
            return couponDAO.getCustomerCouponsUnderPrice(customerId, price);
        }catch (Exception err){
            System.err.println(err.getMessage());
        }
        return null;
    }

    /**
     * Method that allow customer to get all his details
     *
     * @return customer object
     * @throws GrooponSystemException enable to throw exception
     */
    public Customer getCustomer() throws GrooponSystemException {
        try {
            Customer customer = customerDAO.getOneCustomer(this.customerId);
            customer.setCoupons(couponDAO.getCustomerCouponsList(this.customerId));
            return customer;
        }catch (Exception err){
            System.err.println(err.getMessage());
        }
        return null;
    }

}
