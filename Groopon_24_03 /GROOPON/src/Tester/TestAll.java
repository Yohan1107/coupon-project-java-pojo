package Tester;

import Beans.*;
import DB.ConnectionPool;
import DB.GrooponDataBaseManager;
import Facades.AdminFacade;
import Facades.ClientFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;
import Login.ClientType;
import Login.LoginManager;
import Threads.CouponExpirationDailyJob;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Test all class -  test of all our project skills
 */
public class TestAll {

    /**
     * Constructor -
     * initialize our config and read it
     * initialize our data base - if not exist
     * start our thread for delete expired coupons
     * invoke the test method
     * close all the connections
     */
    public TestAll() {
        setDbConfig();
        getDbConfig();
        try {
            GrooponDataBaseManager.myGrooponDataBase();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        new Thread(new CouponExpirationDailyJob()).start();
        test();
        try {
            ConnectionPool.getInstance().closeAllConnection();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Test method - for test all the skills of the project
     */
    public static void test() {
        try {
            /*              ****************************************************************
                                ADMIN TEST -- EMAIL: admin@admin.com -- PASSWORD: admin
                            ****************************************************************                          */

            //Admin login to the system
            ClientFacade clientFacade = LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
            if (clientFacade instanceof AdminFacade) {
                System.out.println("**** ADMIN LOGIN ****");

                //Crate 5 companies

                ((AdminFacade) clientFacade).addCompany(new Company("Home Center", "aaa@gmail.com", "1111"));
                ((AdminFacade) clientFacade).addCompany(new Company("Ace", "bbb@gmail.com", "2222"));
                ((AdminFacade) clientFacade).addCompany(new Company("Dominoes pizza", "ccc@gmail.com", "3333"));
                ((AdminFacade) clientFacade).addCompany(new Company("Zoo Shop", "ddd@gmail.com", "4444"));
                ((AdminFacade) clientFacade).addCompany(new Company("Leonardo Club", "eee@gmail.com", "5555"));


                //Show all companies - Before updates
                System.out.println("Show all companies using getAllCompanies");
                List<Company> companies = ((AdminFacade) clientFacade).getAllCompanies();
                companies.forEach(System.out::println);
                System.out.println("*************************************************************************************************************************************\n");


                //Update company - (1,5)

                Company company = ((AdminFacade) clientFacade).getOneCompany(1);
                company.setEmail("HomeC@gmail.com");
                company.setPassword("HomeCenter1111");
                if (((AdminFacade) clientFacade).updateCompany(company)) {
                    System.out.println("Company home center was updated");
                }
                Company company1 = ((AdminFacade) clientFacade).getOneCompany(5);
                company1.setEmail("LEO12@gmail.com");
                if (((AdminFacade) clientFacade).updateCompany(company1)) {
                    System.out.println("Company leonardo club was updated");
                }
                System.out.println("*************************************************************************************************************************************\n");

                //Show one company - 2 after update(1,5)
                System.out.println("Showing companies home center and leonardo club after update using getOne method ");
                System.out.print(((AdminFacade) clientFacade).getOneCompany(1));
                System.out.print(((AdminFacade) clientFacade).getOneCompany(5));
                System.out.println();

                //Show one company - 2 without update
                System.out.println("Showing companies Ace and zoo shop using getOne method- No updates ");
                System.out.print(((AdminFacade) clientFacade).getOneCompany(2));
                System.out.print(((AdminFacade) clientFacade).getOneCompany(4));
                System.out.println("*************************************************************************************************************************************\n");

                //Delete company - (Company 3)

                if (((AdminFacade) clientFacade).deleteCompany(3)) {
                    System.out.println("Company deleted Successfully");
                }
                

                //Add 5 new customers
                ((AdminFacade) clientFacade).addCustomer(new Customer("Eliran@gmail.com", "1111", "Eliran", "Takiya"));
                ((AdminFacade) clientFacade).addCustomer(new Customer("Omer@gmail.com", "2222", "Omer", "Amsallem"));
                ((AdminFacade) clientFacade).addCustomer(new Customer("Yohan@gmail.com", "2222", "yohan", "olivier"));
                ((AdminFacade) clientFacade).addCustomer(new Customer("zeev@gmail.com", "2222", "zeev", "mindali"));
                ((AdminFacade) clientFacade).addCustomer(new Customer("Gucci@gmail.com", "2222", "gucci", "prada"));


                //Show all customers - Before updates
                System.out.println("Showing all customers - using getAllCustomer method\n");
                List<Customer> customers = ((AdminFacade) clientFacade).getAllCustomers();
                customers.forEach(System.out::println);
                System.out.println("*************************************************************************************************************************************\n");

                //Update 2 customers
                Customer customer = ((AdminFacade) clientFacade).getOneCustomer(4);
                customer.setFirstName("zeevik");
                customer.setLstName("The fox");
                if (((AdminFacade) clientFacade).updateCustomer(customer)) {
                    System.out.println("Customer zeev was updated ");
                }
                Customer customer1 = ((AdminFacade) clientFacade).getOneCustomer(2);
                customer1.setEmail("OmerAmss@gmail.com");
                customer1.setPassword("I'mInTheHouse");
                if (((AdminFacade) clientFacade).updateCustomer(customer1)) {
                    System.out.println("Customer Omer was updated");
                }


                //Show one Customer - The customers that get changes
                System.out.println("Show customers after changes - Using getOne method");
                System.out.println(((AdminFacade) clientFacade).getOneCustomer(4));
                System.out.println(((AdminFacade) clientFacade).getOneCustomer(2));
                System.out.println("*************************************************************************************************************************************\n");

                //Delete customer
                if (((AdminFacade) clientFacade).deleteCustomer(5)) {
                    System.out.println("Customer was deleted successfully");
                }
            }



            /*              *************************************************************
                              1 - COMPANY TEST -- EMAIL: bbb@gmail.com -- PASSWORD: 2222
                            **************************************************************                      */

            clientFacade = LoginManager.getInstance().login("bbb@gmail.com", "2222", ClientType.COMPANY);
            if (clientFacade instanceof CompanyFacade) {
                System.out.println("****** Company " + ((CompanyFacade) clientFacade).getCompany().getName() + " Was login ******");

                // Add 3 new coupons
                ((CompanyFacade) clientFacade).addCoupon(new Coupon(((CompanyFacade) clientFacade).getCompanyId(), Category.ELECTRICITY, "TV", "Give me your money, i'll give you anything", LocalDate.of(2021,5,24), LocalDate.of(2021, 6, 12), 10, 40_000, "jpg"));
                ((CompanyFacade) clientFacade).addCoupon(new Coupon(((CompanyFacade) clientFacade).getCompanyId(), Category.HOME, "Bead", "Not so bad...", LocalDate.now(), LocalDate.of(2021, 5, 12), 5, 50_000, "jpg "));
                ((CompanyFacade) clientFacade).addCoupon(new Coupon(((CompanyFacade) clientFacade).getCompanyId(), Category.CAR_ACCESSORIES, "auto-perfume", "Many choice !", LocalDate.now(), LocalDate.of(2021, 5, 12), 50, 50, "jpg "));


                //Show all coupons of the company
                System.out.println("Show all coupons for the company Ace");
                List<Coupon> coupons = ((CompanyFacade) clientFacade).getCompanyCoupons();
                System.out.println("Coupons list : ");
                coupons.forEach(System.out::println);
                System.out.println("*************************************************************************************************************************************\n");

                //Update coupon
                Coupon coupon = ((CompanyFacade) clientFacade).getCompanyCoupons().get(0);
                coupon.setPrice(3_500.00);
                coupon.setCategory(Category.ELECTRICITY);
                coupon.setAmount(20);
                coupon.setImage("jpg");
                coupon.setTitle("TV");
                coupon.setEndDate(LocalDate.of(2021, 5, 12));
                coupon.setStartDate(LocalDate.now());
                coupon.setDescription("Very good tv without remote...");
                if (((CompanyFacade) clientFacade).updateCoupon(coupon)) {
                    System.out.println("Coupon was updated ! \n");
                }
                System.out.println("*************************************************************************************************************************************\n");


                //Show one coupon - coupon 1
                System.out.println("Show one coupon - The coupon after updates ");
                System.out.println(((CompanyFacade) clientFacade).getCompanyCoupons().get(0));
                System.out.println("*************************************************************************************************************************************\n");


                //Show coupons under price - max price -> 1_000
                System.out.println("Show all coupon by price - max price is 40_000.00");
                List<Coupon> coupons1 = ((CompanyFacade) clientFacade).getCompanyCouponsUnderPrice(51_000);
                coupons1.forEach(System.out::println);
                System.out.println("*************************************************************************************************************************************\n");

                //Get company coupons by category
                System.out.println("Show all coupons by category - category : Home");
                coupons = ((CompanyFacade) clientFacade).getCompanyCouponsByCategory(6);
                coupons.forEach(System.out::println);
                System.out.println("*************************************************************************************************************************************\n");

                //Get company details
                System.out.println("Company details: ");
                System.out.println(((CompanyFacade) clientFacade).getCompany());
                System.out.println("*************************************************************************************************************************************\n");
            }

              /*              *************************************************************
                              2 - COMPANY TEST -- EMAIL: ddd@gmail.com -- PASSWORD: 4444
                            **************************************************************                      */

            clientFacade = LoginManager.getInstance().login("ddd@gmail.com", "4444", ClientType.COMPANY);
            if (clientFacade instanceof CompanyFacade){
                // Add 3 new coupons
                ((CompanyFacade) clientFacade).addCoupon(new Coupon(((CompanyFacade) clientFacade).getCompanyId(), Category.PETS, "Dog food", "Gucci gucci ay ay ay ", LocalDate.now(), LocalDate.of(2021, 5, 28), 10, 40_000, "jpg"));
                ((CompanyFacade) clientFacade).addCoupon(new Coupon(((CompanyFacade) clientFacade).getCompanyId(), Category.PETS, "Ninja Turtle", "Self security ", LocalDate.now(), LocalDate.of(2021, 5, 25), 5, 50_000, "jpg "));
                ((CompanyFacade) clientFacade).addCoupon(new Coupon(((CompanyFacade) clientFacade).getCompanyId(), Category.PETS, "Pig Pog ", "Comme with 10 bottles of perfume", LocalDate.now(), LocalDate.of(2021, 5, 20), 50, 50, "jpg "));


            }


            /*                    ***********************************************************************
                                   CUSTOMER TEST -- EMAIL: OmerAmss@gmail.com -- PASSWORD: I'mInTheHouse
                                  ***********************************************************************                                */


            clientFacade = LoginManager.getInstance().login("OmerAmss@gmail.com", "I'mInTheHouse", ClientType.CUSTOMER);
            if (clientFacade instanceof CustomerFacade) {
                System.out.println("****** Customer " + ((CustomerFacade) clientFacade).getCustomer().getFirstName() + " was login ******");

                //Purchase coupon - coupon 1, 2 , 3
                if (((CustomerFacade) clientFacade).purchaseCoupon(((CustomerFacade) clientFacade).getOneCoupon(1))
                        && ((CustomerFacade) clientFacade).purchaseCoupon(((CustomerFacade) clientFacade).getOneCoupon(4))
                        && ((CustomerFacade) clientFacade).purchaseCoupon(((CustomerFacade) clientFacade).getOneCoupon(6))){
                    System.out.println("Coupons were purchased successfully\n");
                }
                System.out.println("*************************************************************************************************************************************\n");


                //Get all purchased coupons
                List<Coupon> coupons = ((CustomerFacade) clientFacade).getCustomerCoupons();
                System.out.println(coupons);
                System.out.println("*************************************************************************************************************************************\n");


                // Get customer coupons by category - Category Electricity
                coupons = ((CustomerFacade) clientFacade).getCustomerCouponsByCategory(3);
                System.out.println(coupons);
                System.out.println("*************************************************************************************************************************************\n");

                // Get customer coupons by price - max price 10_000
                coupons = ((CustomerFacade) clientFacade).getCustomerCouponsUnderPrice(10_000);
                System.out.println(coupons);
                System.out.println("*************************************************************************************************************************************\n");

                //Check if we can by another time the same coupon
                if (((CustomerFacade) clientFacade).purchaseCoupon(((CustomerFacade) clientFacade).getOneCoupon(4))
                        && ((CustomerFacade) clientFacade).purchaseCoupon(((CustomerFacade) clientFacade).getOneCoupon(6))){
                    System.out.println("Coupons were purchased successfully");
                }

                //Show customer details
                System.out.println("Customer details: ");
                System.out.println(((CustomerFacade) clientFacade).getCustomer());

            }



            /*             ****************************************************************
                              DELETE COUPON CHECKS -- EMAIL: bbb@gmail.com -- PASSWORD: 2222
                            ******************************************************************/



            clientFacade = LoginManager.getInstance().login("bbb@gmail.com", "2222", ClientType.COMPANY);
            if (clientFacade instanceof CompanyFacade) {
                //Delete one coupon of the company for check
                ((CompanyFacade) clientFacade).deleteCoupon(2);
                //Show all company coupons after deleting one coupon
                System.out.println(((CompanyFacade) clientFacade).getCompanyCoupons());
                System.out.println("*************************************************************************************************************************************\n");

            }


            // check if the coupon was deleted from the customer purchased coupons after deleting one coupon from the company
            clientFacade = LoginManager.getInstance().login("OmerAmss@gmail.com", "I'mInTheHouse", ClientType.CUSTOMER);
            if (clientFacade instanceof CustomerFacade) {
                List<Coupon> coupons = ((CustomerFacade) clientFacade).getCustomerCoupons();
                coupons.forEach(System.out::println);
                System.out.println("*************************************************************************************************************************************\n");

            }






            /*                  **************************************************************************
                                   DELETE COMPANY CHECKS -- EMAIL: admin@admin.com -- PASSWORD: admin
                                **************************************************************************/


            clientFacade = LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
            if (clientFacade instanceof AdminFacade) {
                //Delete company to check if coupons are deleted
                ((AdminFacade) clientFacade).deleteCompany(2);
                List<Company> companies = ((AdminFacade) clientFacade).getAllCompanies();
                companies.forEach(System.out::println);

                //Check if coupons is deleted from customer purchased coupons
                List<Customer> customers = ((AdminFacade) clientFacade).getAllCustomers();
                customers.forEach(System.out::println);

            }



        } catch (Exception err) {
            System.err.println(err.getMessage());
        }
    }


    /**
     * Method to configure the config
     */
    private void setDbConfig() {
        Config config = new Config();
        config.setDBName("Groopon");
        config.setCreateIfNotExists(true);
        config.setUseTimeZone(true);
        config.setServerTimeZone("Asia/Jerusalem");
        config.setUserName("root");
        config.setUserPassword("");
        config.setSqlConnectionString("localhost:3306");

        config.saveConfig();
    }

    /**
     * Method to read from our file
     */
    private void getDbConfig() {
        Config config = Config.readConfig();
        System.out.println(config);
    }
}

