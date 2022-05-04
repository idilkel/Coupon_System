package beans;

import exceptions.CustomCouponSystemException;
import exceptions.ErrMsg;
import utils.DateUtils;

import java.sql.Date;

public class Coupon {
    private int id;
    private int companyId;
    private Category category;
    private String title;
    private String description;
    private java.sql.Date startDate;
    private java.sql.Date endDate;
    private int amount;
    private double price;
    private String image;

    public Coupon() {
    }

    public Coupon(int companyId, Category category, String title, String description, java.sql.Date startDate, java.sql.Date endDate, int amount, double price, String image) throws CustomCouponSystemException {
        this.companyId = companyId;
        this.category = category;
        this.title = title;
        this.description = description;
        if (startDate.after(endDate)) {
            throw new CustomCouponSystemException(ErrMsg.START_TIME_AFTER_END_TIME.getMessage());
        }
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    public Coupon(int id, int companyId, Category category, String title, String description, java.sql.Date startDate, java.sql.Date endDate, int amount, double price, String image) throws CustomCouponSystemException {
        this(companyId, category, title, description, startDate, endDate, amount, price, image);
        this.id = id;
    }

    public Coupon(Coupon coupon) {
        this.id = coupon.id;
        this.companyId = coupon.companyId;
        this.category = coupon.category;
        this.title = coupon.title;
        this.description = coupon.description;
        this.startDate = coupon.startDate;
        this.endDate = coupon.endDate;
        this.amount = coupon.amount;
        this.price = coupon.price;
        this.image = coupon.image;
    }

    public int getId() {
        return id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + DateUtils.beautifyDate(startDate) +
                ", endDate=" + DateUtils.beautifyDate(endDate) +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
