package facades;

import beans.Category;
import beans.Company;
import beans.Coupon;
import exceptions.CustomCouponSystemException;
import exceptions.ErrMsg;
import utils.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class CompanyFacade extends ClientFacade {
    private int companyId;

    public CompanyFacade() {
    }

    public CompanyFacade(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean login(String email, String password) throws SQLException, InterruptedException, CustomCouponSystemException {
        if (!companiesDAO.isCompanyExistsByEmailAndByPassword(email, password)) {
            throw new CustomCouponSystemException(ErrMsg.LOGIN_EXCEPTION.getMessage());
        }
        return true;
    }

    public void loginIdCompanyFacade(String email, String password) throws SQLException, InterruptedException, CustomCouponSystemException {
        if (!companiesDAO.isCompanyExistsByEmailAndByPassword(email, password)) {
            throw new CustomCouponSystemException(ErrMsg.LOGIN_EXCEPTION.getMessage());
        }
        this.companyId = companiesDAO.companyIdByEmail(email);//For initialization of a CompanyFacade with an existing customer id
    }

    public void addCoupon(Coupon coupon) throws SQLException, InterruptedException, CustomCouponSystemException {
        for (Coupon cpn : couponsDAO.getAllCoupons()) {
            if (coupon.getTitle().equals(cpn.getTitle()) && coupon.getCompanyId() == cpn.getCompanyId()) {
                throw new CustomCouponSystemException(ErrMsg.COUPON_ALREADY_EXISTS_EXCEPTION.getMessage());
            }
        }
        couponsDAO.addCoupon(coupon);
    }

    public void updateCoupon(int couponId, Coupon coupon) throws SQLException, InterruptedException, CustomCouponSystemException {
        for (Coupon cpn : couponsDAO.getAllCoupons()) {
            if (coupon.getId() != couponId) {
                throw new CustomCouponSystemException(ErrMsg.CANT_UPDATE.getMessage());
            }
            if (couponsDAO.getOneCoupon(couponId).getCompanyId() != coupon.getCompanyId()) {
                throw new CustomCouponSystemException(ErrMsg.CANT_UPDATE.getMessage());
            }
        }
        couponsDAO.updateCoupon(couponId, coupon);
    }

    public void deleteCoupon(int couponId, Coupon coupon) throws SQLException, InterruptedException, CustomCouponSystemException {
        if (!couponsDAO.isCouponExistsById(couponId)) {
            throw new CustomCouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION.getMessage());
        }
        couponsDAO.deleteCouponPurchaseByCouponId(coupon.getId());
        couponsDAO.deleteCoupon(coupon.getId());

    }

    public List<Coupon> getCompanyCoupons() throws SQLException, InterruptedException, CustomCouponSystemException {
        return couponsDAO.getAllCouponsByCompanyId(this.companyId);
    }

    public List<Coupon> getCompanyCoupons(Category category) throws SQLException, InterruptedException, CustomCouponSystemException {
        return couponsDAO.getAllCouponsByCompanyAndCategory(this.companyId, category);
    }

    public List<Coupon> getCompanyCoupons(double maxPrice) throws SQLException, InterruptedException, CustomCouponSystemException {
        return couponsDAO.getAllCouponsByCompanyAndMaxPrice(this.companyId, maxPrice);
    }

    public Company getCompanyDetails() throws SQLException, InterruptedException, CustomCouponSystemException {
        Company company = companiesDAO.getOneCompany(this.companyId);
        List<Coupon> coupons = couponsDAO.getAllCouponsByCompanyId(this.companyId);
        company.setCoupons(coupons);
        TableUtils.drawOneCompanyWithCoupons(company);
        System.out.println(company);
        return company;
    }

}
