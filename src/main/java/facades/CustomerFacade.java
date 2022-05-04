package facades;

import beans.Category;
import beans.Coupon;
import beans.Customer;
import exceptions.CustomCouponSystemException;
import exceptions.ErrMsg;
import utils.TableUtils;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CustomerFacade extends ClientFacade {

    private int customerId;

    public CustomerFacade() {
    }

    public CustomerFacade(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean login(String email, String password) throws SQLException, InterruptedException, CustomCouponSystemException {
        if (!customersDAO.isCustomerExistsByEmailAndByPassword(email, password)) {
            throw new CustomCouponSystemException(ErrMsg.LOGIN_EXCEPTION.getMessage());
        }
        return true;
    }

    public void loginIdCustomerFacade(String email, String password) throws SQLException, InterruptedException, CustomCouponSystemException {
        if (!customersDAO.isCustomerExistsByEmailAndByPassword(email, password)) {
            throw new CustomCouponSystemException(ErrMsg.LOGIN_EXCEPTION.getMessage());
        }
        this.customerId = customersDAO.customerIdByEmail(email);//For initialization of a CustomerFacade with an existing customer id
    }

    public void purchaseCoupon(Coupon coupon) throws SQLException, InterruptedException, CustomCouponSystemException {
        if (coupon.getAmount() == 0) {
            throw new CustomCouponSystemException(ErrMsg.NO_COUPONS_LEFT_EXCEPTION.getMessage());
        }
//        if (coupon.getEndDate().after(Date.valueOf(LocalDate.now()))) {
//            throw new CustomCouponSystemException(ErrMsg.COUPON_EXPIRED_EXCEPTION.getMessage());
//        }
        if ((coupon.getEndDate().compareTo(Date.valueOf(LocalDate.now()))) < 0) {
            throw new CustomCouponSystemException(ErrMsg.COUPON_EXPIRED_EXCEPTION.getMessage());
        }
        if (couponsDAO.isCouponExistsByCustomerIdAndCouponId(this.customerId, coupon.getId())) {
            throw new CustomCouponSystemException(ErrMsg.COUPON_ALREADY_PURCHASED_EXCEPTION.getMessage());
        }
        couponsDAO.addCouponPurchase(this.customerId, coupon.getId());
        couponsDAO.reduceCouponAmountByOne(coupon.getAmount() - 1, coupon.getId());
    }

    public List<Coupon> getCustomerCoupons() throws SQLException, InterruptedException, CustomCouponSystemException {
        return couponsDAO.getAllCouponsByCustomerId(this.customerId);
    }

    public List<Coupon> getCustomerCoupons(Category category) throws SQLException, InterruptedException, CustomCouponSystemException {
        return couponsDAO.getAllCouponsByCustomerAndCategory(this.customerId, category);
    }

    public List<Coupon> getCustomerCoupons(double maxPrice) throws SQLException, InterruptedException, CustomCouponSystemException {
        return couponsDAO.getAllCouponsByCustomerAndMaxPrice(this.customerId, maxPrice);
    }

    public Customer getCustomerDetails() throws SQLException, InterruptedException, CustomCouponSystemException {
        Customer customer = customersDAO.getOneCustomer(this.customerId);
        List<Coupon> coupons = couponsDAO.getAllCouponsByCustomerId(this.customerId);
        customer.setCoupons(coupons);
        TableUtils.drawOneCustomerWithCoupons(customer);
        System.out.println(customer);
        return customer;
    }

}
