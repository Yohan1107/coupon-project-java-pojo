package DBDAO;

import Beans.Customer;
import DAO.CustomerDAO;
import DB.ConnectionPool;
import DB.DBUtils;
import Exceptions.GrooponSystemException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for customer CRUD development methods
 */
public class CustomerDBDAO implements CustomerDAO {

    //Statements
    private static final String ADD_CUSTOMER = "INSERT INTO `Groopon`.`Customers`(`email`, `password`, `firstname`, `lastname`) VALUES (?,?,?,?);";
    private static final String UPDATE_CUSTOMER = "UPDATE `Groopon`.Customers SET firstName = ? , lastName = ?, email = ? , password = ? WHERE id = ?;";
    private static final String DELETE_CUSTOMER = "DELETE FROM `Groopon`.Customers WHERE (`id` = ?);";
    private static final String GET_ALL_CUSTOMERS = "SELECT * FROM `Groopon`.`Customers`; ";
    private static final String GET_ONE_CUSTOMER = "SELECT * FROM `Groopon`.`Customers` WHERE (`id` = ?);";
    private static final String IS_CUSTOMER_EXIST = "SELECT * FROM `Groopon`.`Customers` WHERE `Customers`.`email` LIKE ? AND `Customers`.`password` LIKE ?";

    private final ConnectionPool connectionPool;
    private Connection connection;

    /**
     * Constructor no args - initialise the connection
     */
    public CustomerDBDAO() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    /**
     * @param email    email of the customer
     * @param password password of the customer
     * @return true if the customer exist
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public boolean isCustomerExist(String email, String password) throws GrooponSystemException {
        try {
            // get connection
            connection = ConnectionPool.getInstance().getConnection();
            // run SQL statement
            PreparedStatement statement = connection.prepareStatement(IS_CUSTOMER_EXIST);
            //insert the params to the statement
            statement.setString(1, email);
            statement.setString(2, password);
            // get results from data base
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // if we have a result return true
                return true;
            }
        } catch (InterruptedException | SQLException e) {
            throw new GrooponSystemException("ERROR! System ERROR!");
        } finally {
            connectionPool.returnConnection(connection);
        }
        // if we have not result or exception return false
        return false;
    }

    /**
     * @param customer The customer we want to add to the system
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public void addCustomer(Customer customer) throws GrooponSystemException {
        try {
            // create map to save parameters of the customer
            Map<Integer, Object> parameters = new HashMap<>();
            // insert to the map the parameters
            parameters.put(1, customer.getEmail());
            parameters.put(2, customer.getPassword());
            parameters.put(3, customer.getFirstName());
            parameters.put(4, customer.getLstName());
            // use the method from db utils to run the statement
            DBUtils.runSetQuery(ADD_CUSTOMER, parameters);
        } catch (SQLException err) {
            throw new GrooponSystemException("ERROR! Adding " + customer.getFirstName() + " failed !");
        } finally {
            //close the connection
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * @param customer The customer we want to update
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public void updateCustomer(Customer customer) throws GrooponSystemException {
        try {
            //Create collection to save the customer params
            Map<Integer, Object> parameters = new HashMap<>();
            // insert them
            parameters.put(1, customer.getFirstName());
            parameters.put(2, customer.getLstName());
            parameters.put(3, customer.getEmail());
            parameters.put(4, customer.getPassword());
            parameters.put(5, customer.getId());
            // use the db utils methode to run the statement
            DBUtils.runSetQuery(UPDATE_CUSTOMER, parameters);
        } catch (SQLException err) {
            throw new GrooponSystemException("ERROR! Cannot update customer!");
        } finally {
            // return the connection anyway
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * @param customerId id of the customer we want to remove
     * @return true if the customer was removed
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public boolean deleteCustomer(int customerId) throws GrooponSystemException {
        try {
            if (getOneCustomer(customerId) != null) {
                // create map to save the params
                Map<Integer, Object> parameters = new HashMap<>();
                parameters.put(1, customerId);
                //use the methode to run the statement with the params we give
                DBUtils.runSetQuery(DELETE_CUSTOMER, parameters);
                return true;
            } else throw new GrooponSystemException("ERROR! Cannot delete customer because he's not exist!");
        } catch (SQLException e) {
            throw new GrooponSystemException("ERROR! Delete customer failed! ");
        } finally {
            // return the connection anyway
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * @param customerId id of the customer we want to get
     * @return customer object from the database
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public Customer getOneCustomer(int customerId) throws GrooponSystemException {
        try {
            // get connection
            connection = connectionPool.getConnection();
            //run sql statement
            PreparedStatement statement = connection.prepareStatement(GET_ONE_CUSTOMER);
            statement.setInt(1, customerId);
            //get results from database
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // if we have a result return it
                return DBUtils.getCustomer(resultSet);
            }
        } catch (InterruptedException | SQLException e) {
            throw new GrooponSystemException("ERROR! Cannot get one customer!");
        } finally {
            // return the connection anyway
            connectionPool.returnConnection(connection);
        }
        // if we have not results return null
        return null;
    }

    /**
     * @return list of all the customers in the system
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public List<Customer> getAllCustomers() throws GrooponSystemException {
        // create a list to save all customers from the data base
        List<Customer> customers = new ArrayList<>();
        try {
            // get connection
            connection = ConnectionPool.getInstance().getConnection();
            // run SQL statement
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMERS);
            // build result set to get information
            ResultSet resultSet = statement.executeQuery();
            //get customers
            while (resultSet.next()) {
                // add the next customer to the list
                customers.add(DBUtils.getCustomer(resultSet));
            }
        } catch (InterruptedException | SQLException e) {
            throw new GrooponSystemException("ERROR! Cannot get customers list !");
        } finally {
            // return connection
            ConnectionPool.getInstance().returnConnection(connection);
        }
        // return customers list
        return customers;
    }
}
