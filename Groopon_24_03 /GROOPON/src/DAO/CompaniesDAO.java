package DAO;

import Beans.Company;
import Exceptions.GrooponSystemException;

import java.util.List;

/**
 * Interface - abstract methods for the company CRUD
 */
public interface CompaniesDAO {

    /**
     * Method to check if company is exist in the system
     *
     * @param email    company email we want to check
     * @param password company password we want to check
     * @return boolean - true if the company exist
     * @throws GrooponSystemException enable to throw exception
     */
    boolean isCompanyExist(String email, String password) throws GrooponSystemException;


    /**
     * Method to add company to the system
     *
     * @param company get a company to add
     * @throws GrooponSystemException enable to throw exception
     */
    void addCompany(Company company) throws GrooponSystemException;

    /**
     * Method to update a company
     *
     * @param company get a company to update
     * @throws GrooponSystemException enable to throw exception
     */
    void updateCompany(Company company) throws GrooponSystemException;

    /**
     * Method to delete company from the system
     *
     * @param companyId id of the company we want to delete from the system
     * @return true if the company wes deleted
     * @throws GrooponSystemException enable to throw exception
     */
    boolean deleteCompany(int companyId) throws GrooponSystemException;

    /**
     * Method to get details of a company
     *
     * @param companyId id of the company we want to get
     * @return Company - all detail of the company with this id
     * @throws GrooponSystemException enable to throw exception
     */
    Company getOneCompany(int companyId) throws GrooponSystemException;

    /**
     * Method to get all the companies in the system
     *
     * @return list - list of all the companies
     * @throws GrooponSystemException enable to throw exception
     */
    List<Company> getAllCompanies() throws GrooponSystemException;


}
