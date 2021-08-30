package Beans;

import java.io.*;

/**
 * Class of the config
 */
public class Config implements Serializable {
    //we will need serialVersionUID = to indicate a specific class serialization
    //link to data : https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    /**
     * Serial version attribute - final static
     */
    private static final long serialVersionUID = 6529685098267757690L;
    /**
     * sql connection attribute - string
     */
    private String sqlConnectionString;
    /**
     * Create if not exist attribute - boolean
     */
    private boolean createIfNotExists;
    /**
     * use time zone - boolean
     */
    private boolean useTimeZone;
    /**
     * server time zone - string
     */
    private String serverTimeZone;
    /**
     * user name - string
     */
    private String userName;
    /**
     * User password - string
     */
    private String userPassword;
    /**
     * DB name - string
     */
    private String DBName;

    /**
     * Default constructor
     */
    public Config() {
    }

    /**
     * Constructor full args
     *
     * @param sqlConnectionString connection string
     * @param createIfNotExists   boolean if it's exist or not
     * @param useTimeZone         use time zone
     * @param serverTimeZone      server time zone
     * @param userName            user name
     * @param userPassword        user password
     * @param DBName              data base name
     */
    @SuppressWarnings("unused")
    public Config(String sqlConnectionString, boolean createIfNotExists, boolean useTimeZone, String serverTimeZone, String userName, String userPassword, String DBName) {
        this.sqlConnectionString = sqlConnectionString;
        this.createIfNotExists = createIfNotExists;
        this.useTimeZone = useTimeZone;
        this.serverTimeZone = serverTimeZone;
        this.userName = userName;
        this.userPassword = userPassword;
        this.DBName = DBName;
    }

    /**
     * method to save all our configuration into a file
     *
     */
    public void saveConfig() {
        try {
            //we declare the file itself, where we are going to write data....
            FileOutputStream fileOut = new FileOutputStream("Groopon.config");
            //we will write an object , since we don't know which class type we will be using
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            //write the file on current instance
            out.writeObject(this);
            //close the output stream
            out.close();
            //close the file
            fileOut.close();
            //tell all is ok
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to read our configuration from the file we have created
     *
     * @return the configuration
     */
    public static Config readConfig() {
        Config returnResult = new Config();
        //choose the file that we will read from
        try {
            //point to the file that we will be reading from
            FileInputStream fileIn = new FileInputStream("Groopon.config");
            //create an object from the file
            ObjectInputStream in = new ObjectInputStream(fileIn);
            //get the data as config file (by the fields that we used)
            returnResult = (Config) in.readObject();
            //close inputStream
            in.close();
            //close the file
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return returnResult;
    }

    /**
     * Method to get the sql connection string
     * @return string - sql connection
     */
    public String getSqlConnectionString() {
        return sqlConnectionString;
    }

    /**
     * Method to set the sql connection string
     * @param sqlConnectionString string - new sql connection string
     */
    public void setSqlConnectionString(String sqlConnectionString) {
        this.sqlConnectionString = sqlConnectionString;
    }

    /**
     * Method to know if config created or not
     * @return boolean - true if yes - false if not
     */
    public boolean isCreateIfNotExists() {
        return createIfNotExists;
    }

    /**
     * Method to set the create status
     * @param createIfNotExists boolean - true/false
     */
    public void setCreateIfNotExists(boolean createIfNotExists) {
        this.createIfNotExists = createIfNotExists;
    }

    /**
     * Method that return boolean answer if it's use time zone
     * @return boolean
     */
    public boolean isUseTimeZone() {
        return useTimeZone;
    }

    /**
     * Method to set the boolean status of use time zone
     * @param useTimeZone boolean - true/false
     */
    public void setUseTimeZone(boolean useTimeZone) {
        this.useTimeZone = useTimeZone;
    }

    /**
     * Method to get the server of the time zone
     * @return string - server of the time zone
     */
    public String getServerTimeZone() {
        return serverTimeZone;
    }

    /**
     * Method to set the server time zone
     * @param serverTimeZone string - new server time zone
     */
    public void setServerTimeZone(String serverTimeZone) {
        this.serverTimeZone = serverTimeZone;
    }

    /**
     * Method to get user name
     * @return string - user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Method to set the user name
     * @param userName string - new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Method to get user password
     * @return string - user password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Method to set the user password
     * @param userPassword string - new user password
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * Method to get DB name
     * @return string - name of the DB
     */
    public String getDBName() {
        return DBName;
    }

    /**
     * Method to set the DB name
     * @param DBName string - new DB name
     */
    public void setDBName(String DBName) {
        this.DBName = DBName;
    }

    /**
     *
     * @return to string of the class
     */
    @Override
    public String toString() {
        return "Config{" +
                "sqlConnectionString='" + sqlConnectionString + '\'' +
                ", createIfNotExists=" + createIfNotExists +
                ", useTimeZone=" + useTimeZone +
                ", serverTimeZone='" + serverTimeZone + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", DBName='" + DBName + '\'' +
                '}';
    }
}
