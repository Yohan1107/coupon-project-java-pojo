package Facades;

import DAO.CompaniesDAO;
import DAO.CouponDAO;
import DAO.CustomerDAO;
import Exceptions.GrooponSystemException;

/**
 * Abstract class Client facade - Basic facade for default user
 */
public abstract class ClientFacade {
    // attributes
    protected CompaniesDAO companiesDAO;
    protected CustomerDAO customerDAO;
    protected CouponDAO couponDAO;

    /**
     * Methode to check if the email and the password are correct
     * @param email of the user
     * @param password of the user
     * @return true if the email and the password are correct
     * @throws GrooponSystemException enable to throw exception
     */
    public abstract boolean login(String email, String password) throws GrooponSystemException;

}
