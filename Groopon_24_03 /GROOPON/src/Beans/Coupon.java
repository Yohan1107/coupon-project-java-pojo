package Beans;

import java.time.LocalDate;

/**
 * Class of the coupon bean
 */
public class Coupon {

    private int id;
    private int companyId;
    private Category category;
    @SuppressWarnings("unused")
    private int categoryID;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;
    private double price;
    private String image;

    /**
     * Constructor full args
     *
     * @param id          coupon id
     * @param companyId   company id to which it corresponds
     * @param category    category of the coupon
     * @param title       title of the coupon
     * @param description description of the coupon
     * @param startDate   date when the coupon will start
     * @param endDate     date when the coupon will finish
     * @param amount      amount of coupons
     * @param price       price of one coupon
     * @param image       name of the image
     */


    public Coupon(int id, int companyId, Category category, String title, String description, LocalDate startDate, LocalDate endDate, int amount, double price, String image) {
        this.id = id;
        this.companyId = companyId;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * Constructor without id (id - Auto-Increment)
     * @param companyId   company id to which it corresponds
     * @param category    category of the coupon
     * @param title       title of the coupon
     * @param description description of the coupon
     * @param startDate   date when the coupon will start
     * @param endDate     date when the coupon will finish
     * @param amount      amount of coupons
     * @param price       price of one coupon
     * @param image       name of the image
     */
    public Coupon(int companyId, Category category, String title, String description, LocalDate startDate, LocalDate endDate, int amount, double price, String image) {
        this.companyId = companyId;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * Default constructor
     */
    public Coupon() {
    }

    /**
     * Method to get the coupon id
     * @return int - coupon id
     */
    public int getId() {
        return id;
    }

    /**
     * Method to get the id of the company that sell this coupon
     * @return int - company id
     */
    public int getCompanyId() {
        return companyId;
    }

    /**
     * Method to get the category id of the coupon
     * @return int - id of the category
     */
    public int getCategoryID() {
        return this.category.ordinal() +1;
    }

    /**
     * Method to set the category of the coupon
     * @param category object - the new category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Method to get the title of the coupon
     * @return string - title of the coupon
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method to set the title of the coupon
     * @param title string - new title for the coupon
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Method to get the description of the coupon
     * @return string - coupon description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to set the coupon description
     * @param description string - the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method to get the start date of the coupon
     * @return localDate - date of start
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Method to set the start date of the coupon
     * @param startDate local date - the new start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Method to get the end date for the coupon
     * @return local date - end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Method to set tge end date of the coupon
     * @param endDate local date - new end date
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Method to get the amount of the coupon
     * @return int - amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Method to set the amount of the coupon
     * @param amount int - new amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Method to get the price of the coupon
     * @return double - price of the coupon
     */
    public double getPrice() {
        return price;
    }

    /**
     * Method to set the price of the coupon
     * @param price double - the new price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Method to get the image of the coupon
     * @return string - image
     */
    public String getImage() {
        return image;
    }

    /**
     * Method to set the image of the coupon
     * @param image string - new image 
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return return to String of the class
     */
    @Override
    public String toString() {
        return " Coupon: " +
                "id=" + id +
                ", companyId=" + companyId +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '\n';
    }
}
