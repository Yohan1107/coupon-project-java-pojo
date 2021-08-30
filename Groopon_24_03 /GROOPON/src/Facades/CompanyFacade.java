package Facades;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponDBDAO;
import Exceptions.GrooponSystemException;

import java.util.List;

/**
 * Company facade - Allow the company to use his skills
 */
public class CompanyFacade extends ClientFacade {

    private int companyId;

    /**
     * Constructor no args - initialise companiesDAO and couponDAO
     */
    public CompanyFacade() {
        this.companiesDAO = new CompaniesDBDAO();
        this.couponDAO = new CouponDBDAO();
    }

    /**
     * Method to get the current companyId whose do login
     *
     * @return int - company id
     */
    public int getCompanyId() {
        return companyId;
    }

    /**
     * Method to set companyId by the company who do login
     *
     * @param companyId int - new company id
     */
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    /**
     * Method to check if the email and password are true
     *
     * @param email    of the user
     * @param password of the user
     * @return true if email and password are true
     */
    @Override
    public boolean login(String email, String password) {
        try {
            // check if the company exist
            if (companiesDAO.isCompanyExist(email, password)) {
                //get all companies from the system
                for (Company item : companiesDAO.getAllCompanies()) {
                    //check witch company from the system try to login
                    if (item.getEmail().equals(email) && item.getPassword().equals(password)) {
                        // set the company id of this class with the id of the company that connected
                        this.setCompanyId(item.getId());
                        return true;
                    }
                }
            }
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }
        return false;
    }

    /**
     * Method that allow company to add coupon
     *
     * @param coupon that he wants to add
     */
    public void addCoupon(Coupon coupon) {
        try {
            // get companies from the data base
            List<Coupon> coupons = couponDAO.getCompanyCouponsList(companyId);
            for (Coupon item : coupons) {
                // check if the title is not already used for another coupon
                if (coupon.getTitle().equalsIgnoreCase(item.getTitle())) {
                    throw new GrooponSystemException("ERROR! Cannot add two coupons with same title! ");
                }
            }
            // add the coupon
            couponDAO.addCoupon(coupon);
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }
    }

    /**
     * Method that allow company to update coupon
     * @return true if coupon was updated
     * @param coupon coupon that he wants to update
     */
    public boolean updateCoupon(Coupon coupon) {
        try {
            // check if the coupon exist
            if (couponDAO.getOneCoupon(coupon.getId()) != null) {
                // update this coupon
                couponDAO.updateCoupon(coupon);
                return true;
            }
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }
        return false;
    }

    /**
     * Method that allow the company to remove coupon
     *
     * @param couponId id of the coupon he wants to remove
     */
    public void deleteCoupon(int couponId) {
        try {
            // get the list of the customer that purchase this coupon
            List<Customer> customers = couponDAO.getCouponCustomersList(couponId);
            for (Customer item : customers) {
                //remove from them this coupon
                couponDAO.deleteCouponPurchase(item.getId(), couponId);
            }
            // remove from the system this coupon
            couponDAO.deleteCoupon(couponId);
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }
    }

    /**
     * Method that allow the company to show all his coupons
     *
     * @return list of his coupons
     */
    public List<Coupon> getCompanyCoupons() {
        try {
            // get the list of the company coupons
            return couponDAO.getCompanyCouponsList(companyId);
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }
        return null;
    }

    /**
     * Method that allow the company to show all his coupons by category
     *
     * @param categoryId category he wants to get coupons
     * @return all his coupons for this category
     */
    public List<Coupon> getCompanyCouponsByCategory(int categoryId)  {
        try{
            return couponDAO.getCompanyCouponsByCategory(companyId, categoryId);
        }catch (Exception err){
            System.err.println(err.getMessage());
        }
        return null;
    }

    /**
     * Method that allow the company to show all his coupons by max price
     *
     * @param price the max price
     * @return list of coupons under the max price
     */
    public List<Coupon> getCompanyCouponsUnderPrice(double price) {
        try{
            return couponDAO.getCompanyCouponsUnderPrice(companyId, price);
        }catch (Exception err){
            System.err.println(err.getMessage());
        }
        return null;
    }

    /**
     * Method that allow the company to show his details
     *
     * @return details of this company
     */
    public Company getCompany() {
        try {
            Company company = companiesDAO.getOneCompany(this.companyId);
            company.setCoupons(couponDAO.getCompanyCouponsList(this.companyId));
            return company;
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }
        return null;
    }
}
