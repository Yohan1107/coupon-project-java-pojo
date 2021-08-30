package DB;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import Exceptions.GrooponSystemException;

import java.sql.*;
import java.time.LocalDate;
import java.util.Map;

/**
 * DBUtils class - Help us to manage our work in front the database
 */
public class DBUtils {

    /**
     * Attributes
     */
    public static Connection connection;
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();


    /**
     * Method to execute a statement for the data base
     *
     * @param sql String of the sql statement
     * @throws SQLException enable to throw exception
     */
    public static void runQuery(String sql) throws SQLException {
        Connection connection = null;
        try {
            //taking a connection from connection pool
            connection = connectionPool.getConnection();
            //run the sql command
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * Method to execute statement from java to sql data base
     *
     * @param sql        String of the sql statement
     * @param parameters list of the parameters we need to insert
     * @throws SQLException           enable to throw exception
     * @throws GrooponSystemException enable to throw exception
     */
    public static void runSetQuery(String sql, Map<Integer, Object> parameters) throws SQLException, GrooponSystemException {
        try {
            // get connection
            connection = connectionPool.getConnection();
            // get the sql statement
            PreparedStatement statement = connection.prepareStatement(sql);
            // check all the values and cast them by type
            parameters.forEach((key, value) -> {
                try {
                    if (value instanceof Integer) {
                        statement.setInt(key, (int) value);
                    } else if (value instanceof Double) {
                        statement.setDouble(key, (double) value);
                    } else if (value instanceof Float) {
                        statement.setFloat(key, (float) value);
                    } else if (value instanceof String) {
                        statement.setString(key, (String) value);
                    } else if (value instanceof LocalDate) {
                        statement.setDate(key, Date.valueOf((LocalDate) value));
                    } else if (value instanceof Boolean) {
                        statement.setBoolean(key, (boolean) value);
                    }
                } catch (SQLException err) {
                    System.out.println(err.getMessage());
                }
            });
            // execute the statement with all the parameters
            statement.executeUpdate();
        } catch (InterruptedException e) {
            throw new GrooponSystemException("Error! Connection interrupted!");
        }
    }

    /**
     * Method to get company object from the data base
     *
     * @param resultSet to get parameters from the data base
     * @return company object
     * @throws GrooponSystemException enable to throw exception
     */
    public static Company getCompanyObject(ResultSet resultSet) throws GrooponSystemException {
        try {
            // return an object with all parameters from the data base
            return new Company(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        } catch (SQLException err) {
            throw new GrooponSystemException("ERROR! Getting company failed!");
        }
    }

    /**
     * Method to get a coupon object from the data base
     *
     * @param resultSet to get parameters of the coupon
     * @return coupon object
     * @throws GrooponSystemException enable to throw exception
     */
    public static Coupon getCouponObject(ResultSet resultSet) throws GrooponSystemException {
        try {
            // get coupon object from the data base with all his attributes
            return new Coupon(resultSet.getInt(1),
                    resultSet.getInt(2),
                    getCategoryName(resultSet.getInt(3)),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6).toLocalDate(),
                    resultSet.getDate(7).toLocalDate(),
                    resultSet.getInt(8),
                    resultSet.getDouble(9),
                    resultSet.getString(10));
        } catch (SQLException err) {
            throw new GrooponSystemException("ERROR! Getting Coupon failed!");
        }
    }

    /**
     * Method to get a customer object from the data base
     *
     * @param resultSet to get parameters of the coupon
     * @return coupon  object
     * @throws GrooponSystemException enable to throw exception
     */
    public static Customer getCustomer(ResultSet resultSet) throws GrooponSystemException {
        try {
            // get from the data base customer object with all attributes
            return new Customer(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
        } catch (SQLException err) {
            throw new GrooponSystemException("ERROR! Getting Customer failed!");
        }

    }

    /**
     * Method to get the category by his id
     *
     * @param categoryId id of the category we want to get
     * @return category object
     */
    // methode to get category from the data base for coupon
    public static Category getCategoryName(int categoryId) {
        return Category.values()[categoryId - 1];
    }


}
