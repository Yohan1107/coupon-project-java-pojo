package Threads;

import Beans.Coupon;
import Beans.Customer;
import DAO.CouponDAO;
import DBDAO.CouponDBDAO;
import Exceptions.GrooponSystemException;

import java.time.LocalDate;

/**
 *Daily Thread: Check and delete expired coupons
 */
public class CouponExpirationDailyJob implements Runnable {

    private final CouponDAO couponDAO;
    private boolean quit = false;


    /**
     * Constructor no args - initialise couponDAO
     */
    public CouponExpirationDailyJob() {
        this.couponDAO = new CouponDBDAO();
    }

    /**
     * Method to set quit attribute when the thread is end
     * @param quit the value corresponding
     */
    public void setQuit(boolean quit) {
        this.quit = quit;
    }

    /**
     * Thread - browse coupons and delete the coupons that end date is passed
     */
    @Override
    public void run() {
        // check if quit is already false
        while (!quit) {
            try {
                //browse all the coupons from the system
                for (Coupon item : couponDAO.getAllCoupons()) {
                    // check if the end date is passed
                    if (item.getEndDate().isBefore(LocalDate.now())) {
                        // if yes , remove this coupon from the customers that purchased it
                        for (Customer index : couponDAO.getCouponCustomersList(item.getId())) {
                            couponDAO.deleteCouponPurchase(index.getId(), item.getId());
                            System.out.println("Coupon " + item.getTitle() + " Was delete from purchased coupons of customer : " + index.getFirstName() + " the coupon ws expired...");
                        }
                        // remove this coupon from the system
                        couponDAO.deleteCoupon(item.getId());
                    }
                }
                try {
                    // go to sleep day
                    int TIME_SLEEP = 24 * 60 * 60 * 1_000;
                    Thread.sleep(TIME_SLEEP);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            } catch (GrooponSystemException e) {
                System.out.println(e.getMessage());
            }
            // update quit to true
            this.setQuit(true);
        }
    }
}
