package DB;

import Beans.Config;

import java.sql.SQLException;

/**
 * Class for data base settings
 */
public class GrooponDataBaseManager {

    /**
     * connection string for connection to the mysql/mariadb server
     * create and drop data base - strings
     * create and drop tables - strings
     * insert values for categories - strings
     */
    public static String url = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE&useTimezone=TRUE&serverTimezone=GMT"; //Asia/Jerusalem
    /**
     * String username - root
     */
    public static String username = "root";
    /**
     * String password - ""
     */
    public static String password = "";
    private static String CREATE_DB = "CREATE DATABASE IF NOT EXISTS`Groopon`";
    private static String DROP_DB = "DROP DATABASE `Groopon`";
    private static final String CREATE_TABLE_COMPANY = "CREATE TABLE IF NOT EXISTS `Groopon`.`Companies` ( `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT , `name` VARCHAR(50) NOT NULL , `email` VARCHAR(70) NOT NULL , `password` VARCHAR(15) NOT NULL ) ;";
    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE IF NOT EXISTS `Groopon`.`Customers` ( `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY , `email` VARCHAR(70) NOT NULL , `password` VARCHAR(15) NOT NULL , `firstname` VARCHAR(30) NOT NULL , `lastname` VARCHAR(30) NOT NULL);";
    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE IF NOT EXISTS `Groopon`.`Categories` ( `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE , `name` VARCHAR(20) NOT NULL UNIQUE);";
    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE IF NOT EXISTS`Groopon`.`Coupons` ( `id` INT NOT NULL AUTO_INCREMENT , `company_id` INT NOT NULL , `category_id` INT NOT NULL , `title` VARCHAR(50) NOT NULL , `description` VARCHAR(2000) NOT NULL , `start_date` DATE NOT NULL , `end_date` DATE NOT NULL , `amount` INT NOT NULL , `price` DOUBLE NOT NULL , `image` VARCHAR(200) NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY (`company_id`) REFERENCES `Companies`(`id`),FOREIGN KEY (`category_id`) REFERENCES `Categories`(`id`))";
    private static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE IF NOT EXISTS `Groopon`.`Customers_VS_Coupons` ( `customer_id` INT NOT NULL , `coupon_id` INT NOT NULL , PRIMARY KEY (`customer_id`,`coupon_id`),FOREIGN KEY (`customer_id`) REFERENCES `Customers`(`id`),FOREIGN KEY (`coupon_id` ) REFERENCES `Coupons`(`id`)) ;";
    private static final String CATEGORY_FOOD = "INSERT INTO `Groopon`.`Categories`(`name`) VALUES (\"FOOD\") ON DUPLICATE KEY UPDATE name = \"FOOD\";";
    private static final String CATEGORY_ELECTRiCITY = "INSERT INTO `Groopon`.`Categories`(`name`) VALUES (\"ELECTRICITY\") ON DUPLICATE KEY UPDATE name = \"ELECTRICITY\";";
    private static final String CATEGORY_VACATION = "INSERT INTO `Groopon`.`Categories`(`name`) VALUES (\"VACATIONS\") ON DUPLICATE KEY UPDATE name = \"VACATIONS\";";
    private static final String CATEGORY_CLEANING_PRODUCTS = "INSERT INTO `Groopon`.`Categories`(`name`) VALUES (\"CLEANING_PRODUCTS\") ON DUPLICATE KEY UPDATE name = \"CLEANING_PRODUCTS\";";
    private static final String CATEGORY_SPORT_ACCESSORIES = "INSERT INTO `Groopon`.`Categories`(`name`) VALUES (\"SPORT_ACCESSORIES\") ON DUPLICATE KEY UPDATE name = \"SPORT_ACCESSORIES\";";
    private static final String CATEGORY_HOME = "INSERT INTO `Groopon`.`Categories`(`name`) VALUES (\"HOME\") ON DUPLICATE KEY UPDATE name = \"HOME\";";
    private static final String CATEGORY_CAR_ACCESSORIES = "INSERT INTO `Groopon`.`Categories`(`name`) VALUES (\"CAR_ACCESSORIES\") ON DUPLICATE KEY UPDATE name = \"CAR_ACCESSORIES\";";
    private static final String CATEGORY_KIDS = "INSERT INTO `Groopon`.`Categories`(`name`) VALUES (\"KIDS\") ON DUPLICATE KEY UPDATE name = \"KIDS\";";
    private static final String CATEGORY_PETS = "INSERT INTO `Groopon`.`Categories`(`name`) VALUES (\"PETS\") ON DUPLICATE KEY UPDATE name = \"PETS\";";
    private static final String DROP_TABLE_COMPANY = "DROP TABLE `Groopon`.`Companies`;";
    private static final String DROP_TABLE_CUSTOMERS = "DROP TABLE `Groopon`.`Customers`;";
    private static final String DROP_TABLE_CATEGORIES = "DROP TABLE `Groopon`.`Categories`;";
    private static final String DROP_TABLE_COUPONS = "DROP TABLE `Groopon`.`Coupons`;";
    private static final String DROP_TABLE_CUSTOMERS_VS_COUPONS = "DROP TABLE `Groopon`.`Coupons`;";

    /**
     * Method to get configuration
     */
    public static void getConfiguration() {
        Config config = Config.readConfig();
        //"jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE&useTimezone=TRUE&serverTimezone=Asia/Jerusalem"
        url = "jdbc:mysql://" + config.getSqlConnectionString() +
                "?createDatabaseIfNotExist" + (config.isCreateIfNotExists() ? "TRUE" : "FALSE") +
                "&useTimezone=" + (config.isUseTimeZone() ? "TRUE" : "FALSE") +
                "&serverTimezone=" + config.getServerTimeZone();
        username = config.getUserName();
        password = config.getUserPassword();
        CREATE_DB = "CREATE DATABASE " + config.getDBName();
        DROP_DB = "DROP DATABASE " + config.getDBName();
    }

    /**
     * Method to create our data base
     *
     * @throws SQLException enable to throw exception
     */
    public static void createDataBase() throws SQLException {
        DBUtils.runQuery(CREATE_DB);
    }

    /**
     * Method to drop our data base
     *
     * @throws SQLException enable to throw exception
     */
    @SuppressWarnings("unused")
    public static void dropDataBase() throws SQLException {
        DBUtils.runQuery(DROP_DB);
    }

    /**
     * Method to create companies table
     *
     * @throws SQLException enable to throw exception
     */
    public static void createTableCompany() throws SQLException {
        DBUtils.runQuery(CREATE_TABLE_COMPANY);
    }

    /**
     * Method to create customers table
     *
     * @throws SQLException enable to throw exception
     */
    public static void createTableCustomers() throws SQLException {
        DBUtils.runQuery(CREATE_TABLE_CUSTOMERS);
    }

    /**
     * Method to create coupons table
     *
     * @throws SQLException enable to throw exception
     */
    public static void createTableCoupons() throws SQLException {
        DBUtils.runQuery(CREATE_TABLE_COUPONS);
    }

    /**
     * Method to create categories table and insert into the categories we have
     *
     * @throws SQLException enable to throw exception
     */
    public static void createTableCategories() throws SQLException {
        DBUtils.runQuery(CREATE_TABLE_CATEGORIES);
        insertFoodCategory();
        insertVacationsCategory();
        insertElectricityCategory();
        insertCleaningProductsCategory();
        insertSportProductsCategory();
        insertHomeCategory();
        insertCarAccessoriesCategory();
        insertKidsCategory();
        insertPetsCategory();
    }

    /**
     * Method to create table customers VS coupons
     *
     * @throws SQLException enable to throw exception
     */
    public static void createTableCustomers_VS_Coupons() throws SQLException {
        DBUtils.runQuery(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
    }

    /**
     * Method to drop company table
     *
     * @throws SQLException enable to throw exception
     */
    @SuppressWarnings("unused")
    public static void dropTableCompany() throws SQLException {
        DBUtils.runQuery(DROP_TABLE_COMPANY);
    }

    /**
     * Method to drop customers table
     *
     * @throws SQLException enable to throw exception
     */
    @SuppressWarnings("unused")
    public static void dropTableCustomer() throws SQLException {
        DBUtils.runQuery(DROP_TABLE_CUSTOMERS);
    }

    /**
     * Method to drop coupons table
     *
     * @throws SQLException enable to throw exception
     */
    @SuppressWarnings("unused")
    public static void dropTableCoupons() throws SQLException {
        DBUtils.runQuery(DROP_TABLE_COUPONS);
    }

    /**
     * Method to drop categories table
     *
     * @throws SQLException enable to throw exception
     */
    @SuppressWarnings("unused")
    public static void dropTableCategories() throws SQLException {
        DBUtils.runQuery(DROP_TABLE_CATEGORIES);
    }

    /**
     * Method to drop Customers_VS_Coupons table
     *
     * @throws SQLException enable to throw exception
     */
    @SuppressWarnings("unused")
    public static void dropTableCustomers_VS_Coupons() throws SQLException {
        DBUtils.runQuery(DROP_TABLE_CUSTOMERS_VS_COUPONS);
    }

    /**
     * Method to insert the food category into the categories table
     *
     * @throws SQLException enable to throw exception
     */
    public static void insertFoodCategory() throws SQLException {
        DBUtils.runQuery(CATEGORY_FOOD);
    }

    /**
     * Method to insert the electricity category into the categories table
     *
     * @throws SQLException enable to throw exception
     */
    public static void insertElectricityCategory() throws SQLException {
        DBUtils.runQuery(CATEGORY_ELECTRiCITY);
    }

    /**
     * Method to insert the food vacations into the categories table
     *
     * @throws SQLException enable to throw exception
     */
    public static void insertVacationsCategory() throws SQLException {
        DBUtils.runQuery(CATEGORY_VACATION);
    }

    /**
     * Method to insert the cleaning products category into the categories table
     *
     * @throws SQLException enable to throw exception
     */
    public static void insertCleaningProductsCategory() throws SQLException {
        DBUtils.runQuery(CATEGORY_CLEANING_PRODUCTS);
    }

    /**
     * Method to insert the sport accessories category into the categories table
     *
     * @throws SQLException enable to throw exception
     */
    public static void insertSportProductsCategory() throws SQLException {
        DBUtils.runQuery(CATEGORY_SPORT_ACCESSORIES);
    }

    /**
     * Method to insert the home category into the categories table
     *
     * @throws SQLException enable to throw exception
     */
    public static void insertHomeCategory() throws SQLException {
        DBUtils.runQuery(CATEGORY_HOME);
    }

    /**
     * Method to insert the car accessories category into the categories table
     *
     * @throws SQLException enable to throw exception
     */
    public static void insertCarAccessoriesCategory() throws SQLException {
        DBUtils.runQuery(CATEGORY_CAR_ACCESSORIES);
    }

    /**
     * Method to insert the food kids into the categories table
     *
     * @throws SQLException enable to throw exception
     */
    public static void insertKidsCategory() throws SQLException {
        DBUtils.runQuery(CATEGORY_KIDS);
    }

    /**
     * Method to insert the pets category into the categories table
     *
     * @throws SQLException enable to throw exception
     */
    public static void insertPetsCategory() throws SQLException {
        DBUtils.runQuery(CATEGORY_PETS);
    }

    /**
     * Method to create our full data base
     *
     * @throws SQLException enable to throw exception
     */
    public static void myGrooponDataBase() throws SQLException {
        createDataBase();
        createTableCategories();
        createTableCompany();
        createTableCoupons();
        createTableCustomers();
        createTableCustomers_VS_Coupons();

    }


}
