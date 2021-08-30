package DBDAO;

import Beans.Company;
import DAO.CompaniesDAO;
import DB.ConnectionPool;
import DB.DBUtils;
import Exceptions.GrooponSystemException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for company CRUD development methods
 */
public class CompaniesDBDAO implements CompaniesDAO {

    // SQL statements
    private static final String ADD_COMPANY = "INSERT INTO `Groopon`.Companies (`name`,`email`,`password`) VALUES (?,?,?);";
    private static final String UPDATE_COMPANY = "UPDATE `Groopon`.`Companies` SET name = ? , email = ? , password = ? WHERE id = ? ;";
    private static final String DELETE_COMPANY = "DELETE FROM `Groopon`.`Companies` WHERE (`id` = ?);";
    private static final String GET_ONE_COMPANY_BY_ID = "SELECT * FROM `Groopon`.`Companies` WHERE id = ? ;";
    private static final String GET_ALL_COMPANIES = "SELECT * FROM `Groopon`.`Companies`; ";
    private static final String CHECK_IF_EXIST = "SELECT * FROM `Groopon`.`Companies` WHERE `Companies`.`email` LIKE ? AND `Companies`.`password` LIKE ?";
    // Connection
    private final ConnectionPool connectionPool;
    private Connection connection;

    /**
     * Constructor no args - initialisation of connectionPool
     */
    public CompaniesDBDAO() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    /**
     * @param email    company email we want to check
     * @param password company password we want to check
     * @return true if the company exist
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public boolean isCompanyExist(String email, String password) throws GrooponSystemException {
        try {
            // get connection
            connection = connectionPool.getConnection();
            // create statement using connection object
            PreparedStatement statement = connection.prepareStatement(CHECK_IF_EXIST);
            // setting placeholder values
            statement.setString(1, email);
            statement.setString(2, password);
            // execute statement
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // if we get a result so return true
                return true;
            }
        } catch (InterruptedException | SQLException e) {
            throw new GrooponSystemException("Failed to check if company already exist !");
        } finally {
            // close the connection anyway
            connectionPool.returnConnection(connection);
        }
        return false;
    }

    /**
     * @param company get a company to add
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public void addCompany(Company company) throws GrooponSystemException {
        try {
            // Create collection for get attributes of the company we get for parameter
            Map<Integer, Object> parameters = new HashMap<>();
            // input the attributes into the map
            parameters.put(1, company.getName());
            parameters.put(2, company.getEmail());
            parameters.put(3, company.getPassword());
            // use the method from DB Util to insert the object in the database
            DBUtils.runSetQuery(ADD_COMPANY, parameters);
        } catch (Exception e) {
            throw new GrooponSystemException("Error, Create new company failed! ");
        } finally {
            //close the connection anyway
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * @param company get a company to update
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public void updateCompany(Company company) throws GrooponSystemException {
        // get connection to the data base
        try {
            // Create collection for get attributes of the company we get for parameter
            Map<Integer, Object> parameters = new HashMap<>();
            // insert into the map the attributes of the object
            parameters.put(1, company.getName());
            parameters.put(2, company.getEmail());
            parameters.put(3, company.getPassword());
            parameters.put(4, company.getId());
            //use the method to insert the object into the database
            DBUtils.runSetQuery(UPDATE_COMPANY, parameters);
        } catch (SQLException err) {
            throw new GrooponSystemException("Error! Update company failed! ");
        } finally {
            //close the connection anyway
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * @param companyId id of the company we want to delete from the system
     * @return true if the company was deleted
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public boolean deleteCompany(int companyId) throws GrooponSystemException {
        // get connection
        try {
            //get connection to the data base
            connection = connectionPool.getConnection();
            //check if we have company to delete
            if (getOneCompany(companyId)!= null) {
                // run sql statement
                PreparedStatement statement = connection.prepareStatement(DELETE_COMPANY);
                // set id
                statement.setInt(1, companyId);
                // execute delete statement
                statement.execute();
                return true;
            } else {
                throw new GrooponSystemException("ERROR! Delete company failed !");
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // close connections anyway
            connectionPool.returnConnection(connection);
        }
        return false;
    }

    /**
     * @param companyId id of the company we want to get
     * @return company object
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public Company getOneCompany(int companyId) throws GrooponSystemException {
        try {
            //get connection
            connection = connectionPool.getConnection();
            // Create SQL statement
            if (companyId > 0) {
                PreparedStatement statement = connection.prepareStatement(GET_ONE_COMPANY_BY_ID);
                // setting placeholder values
                statement.setInt(1, companyId);
                ResultSet resultSet = statement.executeQuery();
                // get the result from data base
                if (resultSet.next()) {
                    // create object whith parameters we gets from data base
                    return DBUtils.getCompanyObject(resultSet);
                }
            } else throw new GrooponSystemException("ERROR! No company for this ID!");
        } catch (InterruptedException | SQLException e) {
            throw new GrooponSystemException("ERROR! Cannot get one company from the system!");
        } finally {
            // close connection anyway
            connectionPool.returnConnection(connection);
        }
        return null;
    }

    /**
     * Method to get list of all the companies of the system
     *
     * @return list of companies
     * @throws GrooponSystemException enable to throw exception
     */
    @Override
    public List<Company> getAllCompanies() throws GrooponSystemException {
        //create array list that contains objects we get from the data base
        List<Company> companies = new ArrayList<>();
        try {
            // get connection
            connection = connectionPool.getConnection();
            // create statement using connection object
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMPANIES);
            //Execute statement
            ResultSet resultSet = statement.executeQuery();
            // as long as we have companies continue to get them
            while (resultSet.next()) {
                // add the next company to the list
                companies.add(DBUtils.getCompanyObject(resultSet));
            }
        } catch (InterruptedException | SQLException e) {
            throw new GrooponSystemException("Error, Getting all companies failed!");
        } finally {
            connectionPool.returnConnection(connection);
        }
        // return the list of the companies
        return companies;
    }


}
