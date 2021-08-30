package DAO;

import Beans.Customer;
import Exceptions.GrooponSystemException;

import java.util.List;

/**
 * Interface - Abstract methods for customer CRUD
 */
public interface CustomerDAO {

    /**
     * Method to check if customer exist in the system
     *
     * @param email    string - email of the customer
     * @param password string - password of the customer
     * @return boolean - true if the customer is exist
     * @throws GrooponSystemException enable to throw exception
     */
    boolean isCustomerExist(String email, String password) throws GrooponSystemException;

    /**
     * Method to add customer in the system
     *
     * @param customer The customer we want to add to the system
     * @throws GrooponSystemException enable to throw exception
     */
    void addCustomer(Customer customer) throws GrooponSystemException;

    /**
     * Method to update customer
     *
     * @param customer The customer we want to update
     * @throws GrooponSystemException enable to throw exception
     */
    void updateCustomer(Customer customer) throws GrooponSystemException;

    /**
     * Method to delete customer from the system
     *
     * @param customerId int - id of the customer we want to remove
     * @return boolean - true if the customer was removed
     * @throws GrooponSystemException enable to throw exception
     */
    boolean deleteCustomer(int customerId) throws GrooponSystemException;

    /**
     * Method to get list of all the customers in the system
     *
     * @return list - list of customers
     * @throws GrooponSystemException enable to throw exception
     */
    List<Customer> getAllCustomers() throws GrooponSystemException;

    /**
     * Method to get one customer by id
     *
     * @param customerId int - id of the customer we want to get
     * @return customer object - the customer details
     * @throws GrooponSystemException enable to throw exception
     */
    Customer getOneCustomer(int customerId) throws GrooponSystemException;
}
