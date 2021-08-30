package Login;


import Exceptions.GrooponSystemException;
import Facades.AdminFacade;
import Facades.ClientFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;

/**
 * Login manager class - manage our different possibilities of login
 */
public class LoginManager {

    private static LoginManager instance = null;

    /**
     * Constructor no args
     */
    private LoginManager() {
    }

    /**
     * Method to get instance of this class - Login manager is a singleton class
     *
     * @return instance of loginManager
     */
    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    /**
     * Method to get the user who wants to login and check if password and email are correct
     *
     * @param email      of the user
     * @param password   of the user
     * @param clientType of the user
     * @return the right facade
     * @throws GrooponSystemException enable to throw exception
     */
    public ClientFacade login(String email, String password, ClientType clientType) throws GrooponSystemException {
        switch (clientType) {
            case ADMINISTRATOR:
                // check email and password according to admin facade
                if (new AdminFacade().login(email, password)) {
                    // return instance of admin facade
                    return new AdminFacade();
                } else {
                    throw new GrooponSystemException("ERROR! Wrong Email or password !");
                }
            case COMPANY:
                // check email and password according company facade
                ClientFacade companyFacade = new CompanyFacade();
                if (companyFacade.login(email, password)) {
                    // return instance of company facade
                    return companyFacade;
                } else throw new GrooponSystemException("ERROR! Wrong Email or password !");

            case CUSTOMER:
                //check email and password according customer facade
                ClientFacade customerFacade = new CustomerFacade();
                if (customerFacade.login(email, password)) {
                    // return instance of customer facade
                    return customerFacade;
                } else throw new GrooponSystemException("ERROR!Wrong Email or password !");

            default:
                return null;
        }
    }


}