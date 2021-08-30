package DBDAO;

import Beans.Coupon;
import Beans.Customer;
import DAO.CouponDAO;
import DB.ConnectionPool;
import DB.DBUtils;
import Exceptions.GrooponSystemException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for coupon CRUD development methods
 */
public class CouponDBDAO implements CouponDAO {

    //SQL statements
    private static final String ADD_COUPON = "INSERT INTO `Groopon`.`Coupons` (`company_id`,`category_id`,`title`,`description`,`start_date`,`end_date`,`amount`,`price`,`image`) VALUES (?,?,?,?,?,?,?,?,?);";
    private static final String UPDATE_COUPON = "UPDATE `Groopon`.`Coupons` SET category_id = ?,title = ? , description = ? , start_date = ? , end_date = ? , amount = ? , price = ? , image = ? WHERE id = ?;";
    private static final String DELETE_COUPON = "DELETE FROM `Groopon`.`Coupons` WHERE (`id` = ?)";
    private static final String GET_ONE_COUPON = "SELECT * FROM `Groopon`.`Coupons` WHERE (`id` = ?);";
    private static final String GET_ALL_COUPONS = "SELECT * FROM `Groopon`.`Coupons`;";
    private static final String ADD_PURCHASE_COUPON = "INSERT INTO `Groopon`.`Customers_VS_Coupons`(`customer_id`, `coupon_id`) VALUES (?,?);";
    private static final String DELETE_PURCHASE_COUPON = "DELETE FROM `Groopon`.`Customers_VS_Coupons` WHERE `customer_id` = ? AND `coupon_id` = ?;";
    private static final String GET_CUSTOMER_COUPONS_LIST = "SELECT * FROM `Groopon`.`Coupons` INNER JOIN `Groopon`.`Customers_VS_Coupons` ON `Coupons`.`id` = `Customers_VS_Coupons`.`coupon_id` WHERE `customer_id` = ?;";
    private static final String GET_COUPON_CUSTOMERS_LIST = "SELECT * FROM `Groopon`.`Customers` INNER JOIN `Groopon`.`Customers_VS_Coupons` ON `Customers_VS_Coupons`.`customer_id` = `Customers`.`id` WHERE `Groopon`.`Customers_VS_Coupons`.`coupon_id` = ?";
    private static final String GET_COMPANY_COUPONS_LIST = "SELECT `id`, `company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image` FROM `Groopon`.`Coupons` WHERE `company_id` = ?";
    private static final String GET_COMPANY_COUPONS_UNDER_MAX_PRICE = "SELECT * FROM `Groopon`.`Coupons` WHERE company_id = ? AND `price` < ? ;";
    private static final String GET_COMPANY_COUPONS_BY_CATEGORY = "SELECT * FROM `Groopon`.`Coupons` WHERE company_id = ? AND category_id = ? ;";
    private static final String GET_CUSTOMER_COUPONS_UNDER_MAX_PRICE = "SELECT * FROM `Groopon`.`Coupons` INNER JOIN `Groopon`.`Customers_VS_Coupons` ON `Customers_VS_Coupons`.`coupon_id` = `Coupons`.`id` WHERE `Customers_VS_Coupons`.`customer_id` = ? AND `Coupons`.`price` < ?;";
    private static final String GET_CUSTOMER_COUPONS_BY_CATEGORY = "SELECT * FROM `Groopon`.`Coupons` INNER JOIN `Groopon`.`Customers_VS_Coupons` ON `Customers_VS_Coupons`.`coupon_id` = `Coupons`.`id` WHERE `Customers_VS_Coupons`.`customer_id` = ? AND `Coupons`.`category_id` = ?;";

    //attribute
    private final ConnectionPool connectionPool;
    private Connection connection;

    /**
     * Constructor no args - we initialise the connectionPool
     */
    public CouponDBDAO() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    /**
     * @param coupon coupon we want to add to the system
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public void addCoupon(Coupon coupon) throws GrooponSystemException {
        try {
            // creating map to insert attributes of the coupon we need to add
            Map<Integer, Object> parameters = new HashMap<>();

            parameters.put(1, coupon.getCompanyId());
            parameters.put(2, coupon.getCategoryID());
            parameters.put(3, coupon.getTitle());
            parameters.put(4, coupon.getDescription());
            parameters.put(5, coupon.getStartDate());
            parameters.put(6, coupon.getEndDate());
            parameters.put(7, coupon.getAmount());
            parameters.put(8, coupon.getPrice());
            parameters.put(9, coupon.getImage());
            // use the method from db utils to insert the coupon to the database
            DBUtils.runSetQuery(ADD_COUPON, parameters);
        } catch (SQLException e) {
            throw new GrooponSystemException("ERROR! Added coupon failed!");
        } finally {
            // close connection anyway
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * @param coupon coupon we want to update
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public void updateCoupon(Coupon coupon) throws GrooponSystemException {
        try {
            // creating map to insert attributes of the coupon we need to update
            Map<Integer, Object> parameters = new HashMap<>();

            parameters.put(1, coupon.getCategoryID());
            parameters.put(2, coupon.getTitle());
            parameters.put(3, coupon.getDescription());
            parameters.put(4, coupon.getStartDate());
            parameters.put(5, coupon.getEndDate());
            parameters.put(6, coupon.getAmount());
            parameters.put(7, coupon.getPrice());
            parameters.put(8, coupon.getImage());
            parameters.put(9, coupon.getId());
            // use the methode from db utils to insert attributes of the update coupon
            DBUtils.runSetQuery(UPDATE_COUPON, parameters);
        } catch (SQLException e) {
            throw new GrooponSystemException("ERROR! Update failed!");
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * @param couponId id of the coupon we want to remove
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public void deleteCoupon(int couponId) throws GrooponSystemException {
        try {
            // creating map to insert attributes of the coupon we need to add
            Map<Integer, Object> parameters = new HashMap<>();
            parameters.put(1, couponId);
            //  use the methode from db utils to insert attributes of the coupon id
            DBUtils.runSetQuery(DELETE_COUPON, parameters);
        } catch (SQLException e) {
            throw new GrooponSystemException("ERROR! Delete coupon failed!");
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * @return list of all coupons of the system
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public List<Coupon> getAllCoupons() throws GrooponSystemException {
        // create list for save coupons from DB
        List<Coupon> coupons = new ArrayList<>();
        try {
            // get connection
            connection = connectionPool.getConnection();
            // run statement
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS);
            // get results from data base
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // add the next object to the coupon list
                coupons.add(DBUtils.getCouponObject(resultSet));
            }
        } catch (InterruptedException | SQLException e) {
            throw new GrooponSystemException("ERROR! Getting all coupons failed!");
        }finally {
            connectionPool.returnConnection(connection);
        }
        return coupons;
    }

    /**
     * @param couponId id of the coupon we want to get
     * @return coupon object from the data base
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public Coupon getOneCoupon(int couponId) throws GrooponSystemException {
        try {
            //get connection
            connection = connectionPool.getConnection();
            // run sql statement
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COUPON);
            statement.setInt(1, couponId);
            // get results from data base
            ResultSet resultSet = statement.executeQuery();
            // if we have a result - return it
            if (resultSet.next()) {
                return DBUtils.getCouponObject(resultSet);
            }
        } catch (InterruptedException | SQLException e) {
            throw new GrooponSystemException("ERROR! Cannot get one coupon from the system!");
        }finally {
            connectionPool.returnConnection(connection);
        }
        // if we have not result return null
        return null;
    }

    /**
     * @param customerId id of the customer that purchase the coupon
     * @param couponId   id of the purchase coupon
     * @return true if coupon was added
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public boolean addCouponPurchase(int customerId, int couponId) throws GrooponSystemException {
        try {
            // if amount = 0 or the date of the end is passed so cannot buy this coupon
            if (getOneCoupon(couponId).getAmount() == 0 ||
                    getOneCoupon(couponId).getEndDate().isBefore(LocalDate.now())) {
                throw new GrooponSystemException("ERROR! Cannot purchase this coupon!");
            } else {
                // check if this customer not already have this coupon - if yes, cannot buy it
                for (Customer item : getCouponCustomersList(couponId)) {
                    if (item.getId() == customerId) {
                        System.err.println("ERROR! You have already this coupon... Sorry you can buy only one time...");
                    }
                }
            }
            // create collection to input the parameters
            Map<Integer, Object> parameters = new HashMap<>();
            // input them
            parameters.put(1, customerId);
            parameters.put(2, couponId);
            // use the method of DB utils to run the statement
            DBUtils.runSetQuery(ADD_PURCHASE_COUPON, parameters);
            //update the amount of this coupon - (-1)
            Coupon coupon = getOneCoupon(couponId);
            coupon.setAmount(coupon.getAmount() - 1);
            updateCoupon(coupon);
            return true;

        } catch (SQLException err) {
            throw new GrooponSystemException("ERROR! Add new coupon purchase failed!");
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * @param customerId id of the customer that purchase the coupon
     * @param couponId   id of the purchased coupon
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public void deleteCouponPurchase(int customerId, int couponId) throws GrooponSystemException {
        try {
            // create collection to save our parameters
            Map<Integer, Object> parameters = new HashMap<>();
            // input them
            parameters.put(1, customerId);
            parameters.put(2, couponId);
            // use the db utils method to run the statement
            DBUtils.runSetQuery(DELETE_PURCHASE_COUPON, parameters);
        } catch (SQLException err) {
            throw new GrooponSystemException("ERROR! Delete purchase coupon failed! ");
        }finally {
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * @param customerId id of the customer
     * @return list of all coupons of this customer
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public List<Coupon> getCustomerCouponsList(int customerId) throws GrooponSystemException {
        // create list to get all the coupons from the database
        List<Coupon> coupons = new ArrayList<>();
        try {
            // get connection
            connection = connectionPool.getConnection();
            // run sql statement
            PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_COUPONS_LIST);
            statement.setInt(1, customerId);
            // get result from data base
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // add the next coupon to the list
                coupons.add(DBUtils.getCouponObject(resultSet));
            }
        } catch (InterruptedException | SQLException err) {
            throw new GrooponSystemException("ERROR! Cannot get customer coupon list!");
        }finally {
            connectionPool.returnConnection(connection);
        }
        // return the list with the coupons
        return coupons;
    }

    /**
     * @param couponId id of the coupon we want to know about
     * @return customer list of this coupon
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public List<Customer> getCouponCustomersList(int couponId) throws GrooponSystemException {
        // list for save customers from the data base
        List<Customer> customers = new ArrayList<>();
        try {
            // get connection
            connection = connectionPool.getConnection();
            // run sql statement
            PreparedStatement statement = connection.prepareStatement(GET_COUPON_CUSTOMERS_LIST);
            statement.setInt(1, couponId);
            // get results from database
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // add the next customer to the list
                Customer customer = DBUtils.getCustomer(resultSet);
                customers.add(customer);
            }
        } catch (InterruptedException | SQLException e) {
            throw new GrooponSystemException("ERROR! cannot get customers list!");
        }finally {
            connectionPool.returnConnection(connection);
        }
        // return the list with the customers
        return customers;
    }

    /**
     * @param companyId id of the company
     * @return this company coupons list
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public List<Coupon> getCompanyCouponsList(int companyId) throws GrooponSystemException {
        // get list to save company coupons
        List<Coupon> coupons = new ArrayList<>();
        try {
            // get connection
            connection = connectionPool.getConnection();
            // run sql statement
            PreparedStatement statement = connection.prepareStatement(GET_COMPANY_COUPONS_LIST);
            statement.setInt(1, companyId);
            // get results from the database
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // add the next coupon to the list
                coupons.add(DBUtils.getCouponObject(resultSet));
            }
        } catch (InterruptedException | SQLException e) {
            throw new GrooponSystemException("Error! cannot get coupon list!");

        }finally {
            connectionPool.returnConnection(connection);
        }
        // return the list of the company coupons
        return coupons;
    }

    /**
     * @param companyId id of the company
     * @param price     the max price
     * @return list of this company coupons under the max price
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public List<Coupon> getCompanyCouponsUnderPrice(int companyId, double price) throws GrooponSystemException {
        // create a list to save the coupons
        List<Coupon> coupons = new ArrayList<>();
        try {
            // get connection
            connection = connectionPool.getConnection();
            // run sql statement
            PreparedStatement statement = connection.prepareStatement(GET_COMPANY_COUPONS_UNDER_MAX_PRICE);
            statement.setInt(1, companyId);
            statement.setDouble(2, price);
            // get results
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // add the next coupon to the list
                coupons.add(DBUtils.getCouponObject(resultSet));
            }
        } catch (InterruptedException | SQLException e) {
            throw new GrooponSystemException("Error cannot get coupon list!");
        }finally {
            connectionPool.returnConnection(connection);
        }
        // return the coupon list
        return coupons;
    }

    /**
     * @param companyId  id of the company
     * @param categoryId id of the category
     * @return list of coupons by category
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public List<Coupon> getCompanyCouponsByCategory(int companyId, int categoryId) throws GrooponSystemException {
        // create list to save the company coupons by category
        List<Coupon> coupons = new ArrayList<>();
        try {
            // get connection
            connection = connectionPool.getConnection();
            // run sql statement
            PreparedStatement statement = connection.prepareStatement(GET_COMPANY_COUPONS_BY_CATEGORY);
            statement.setInt(1, companyId);
            statement.setInt(2, categoryId);
            // get results
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // add the next coupon to the list
                coupons.add(DBUtils.getCouponObject(resultSet));
            }
        } catch (InterruptedException | SQLException e) {
            throw new GrooponSystemException("Error! cannot get coupon list by category !");
        }finally {
            connectionPool.returnConnection(connection);
        }
        // return the list
        return coupons;
    }

    /**
     * @param customerId id of the customer
     * @param categoryId id of the category
     * @return list of customer coupons by category
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public List<Coupon> getCustomerCouponsByCategory(int customerId, int categoryId) throws GrooponSystemException {
        List<Coupon> coupons = new ArrayList<>();
        try {
            // get connection
            connection = connectionPool.getConnection();
            // run sql statement
            PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_COUPONS_BY_CATEGORY);
            statement.setInt(1, customerId);
            statement.setInt(2, categoryId);
            // get results
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                //add the next coupon to the list
                coupons.add(DBUtils.getCouponObject(resultSet));
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            connectionPool.returnConnection(connection);
        }
        // return the coupon list
        return coupons;
    }

    /**
     * @param customerId id of the customer
     * @param price      the max price
     * @return list of customer coupons under the max price
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public List<Coupon> getCustomerCouponsUnderPrice(int customerId, double price) throws GrooponSystemException {
        // create list to save coupons under max price from the database
        List<Coupon> coupons = new ArrayList<>();
        try {
            // get connection
            connection = connectionPool.getConnection();
            // run sql statement
            PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_COUPONS_UNDER_MAX_PRICE);
            statement.setInt(1, customerId);
            statement.setDouble(2, price);
            // get results from database
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // add the next coupon to the list
                coupons.add(DBUtils.getCouponObject(resultSet));
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            connectionPool.returnConnection(connection);
        }
        // return the list
        return coupons;
    }
}
