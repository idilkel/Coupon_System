package dao;

import beans.Category;
import beans.Coupon;
import exceptions.CustomCouponSystemException;

import java.sql.SQLException;
import java.util.List;

public interface CouponsDAO {

    void addCoupon(Coupon coupon) throws SQLException, InterruptedException, CustomCouponSystemException;

    void updateCoupon(int couponId, Coupon coupon) throws SQLException, InterruptedException;

    void deleteCoupon(int couponId) throws SQLException, InterruptedException;

    List<Coupon> getAllCoupons() throws SQLException, InterruptedException, CustomCouponSystemException;

    Coupon getOneCoupon(int couponId) throws SQLException, InterruptedException, CustomCouponSystemException;

    void addCouponPurchase(int customerID, int couponID) throws SQLException, InterruptedException;

    void deleteCouponPurchase(int customerID, int couponID) throws SQLException, InterruptedException;

    //To delete coupon from customer vs coupon table too
    void deleteCouponPurchaseByCouponId(int couponID) throws SQLException, InterruptedException;

    //For daily job
    List<Coupon> getAllCouponsBefore() throws SQLException, InterruptedException, CustomCouponSystemException;

    //For exception to deleting a non-existing coupon
    boolean isCouponExistsById(int couponId) throws SQLException, InterruptedException;

    //For company Facade
    List<Coupon> getAllCouponsByCompanyAndCategory(int companyId, Category category) throws SQLException, InterruptedException, CustomCouponSystemException;

    //For company Facade
    List<Coupon> getAllCouponsByCompanyAndMaxPrice(int companyId, double maxPrice) throws SQLException, InterruptedException, CustomCouponSystemException;

    //For company Facade - to get all coupons of a company for company details
    List<Coupon> getAllCouponsByCompanyId(int companyId) throws SQLException, InterruptedException, CustomCouponSystemException;

    //For customer Facade - for coupon purchase
    boolean isCouponExistsByCustomerIdAndCouponId(int customerId, int couponId) throws SQLException, InterruptedException;

    //For customer Facade
    List<Coupon> getAllCouponsByCustomerAndCategory(int customerId, Category category) throws SQLException, InterruptedException, CustomCouponSystemException;

    //For customer Facade
    List<Coupon> getAllCouponsByCustomerAndMaxPrice(int customerId, double maxPrice) throws SQLException, InterruptedException, CustomCouponSystemException;

    //For customer Facade - to get all coupons of a customer for customer details
    List<Coupon> getAllCouponsByCustomerId(int customerId) throws SQLException, InterruptedException, CustomCouponSystemException;

    //For customer Facade - to reduce coupon amount after coupon purchase
    void reduceCouponAmountByOne(int couponAmount, int couponID) throws SQLException, InterruptedException;
}
