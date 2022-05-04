package facades;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CustomCouponSystemException;
import exceptions.ErrMsg;

import java.sql.SQLException;
import java.util.List;

public class AdminFacade extends ClientFacade {

    public AdminFacade() {
    }

    @Override
    public boolean login(String email, String password) throws CustomCouponSystemException {
        if (!(email.equals("admin@admin.com") && password.equals("admin"))) {
            throw new CustomCouponSystemException(ErrMsg.LOGIN_EXCEPTION.getMessage());
        }
        return true;
    }

    public void addCompany(Company company) throws SQLException, InterruptedException, CustomCouponSystemException {

        if (companiesDAO.isCompanyExistsByNameOrByEmail(company.getName(), company.getEmail())) {
            throw new CustomCouponSystemException(ErrMsg.COMPANY_DETAILS_ALREADY_EXIST_EXCEPTION.getMessage());
        }

        companiesDAO.addCompany(company);
    }

    public void updateCompany(int companyId, Company company) throws SQLException, InterruptedException, CustomCouponSystemException {
        if (company.getId() != companyId) {
            throw new CustomCouponSystemException(ErrMsg.CANT_UPDATE.getMessage());
        }
        if (!(company.getName().equals(companiesDAO.getOneCompany(companyId).getName()))) {
            throw new CustomCouponSystemException(ErrMsg.CANT_UPDATE.getMessage());
        }
        companiesDAO.updateCompany(companyId, company);
    }

    public void deleteCompany(int companyId) throws SQLException, InterruptedException, CustomCouponSystemException {
        if (!companiesDAO.isCompanyExistsById(companyId)) {
            throw new CustomCouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION.getMessage());
        }
        for (Coupon coupon : couponsDAO.getAllCouponsByCompanyId(companyId)) {
            couponsDAO.deleteCouponPurchaseByCouponId(coupon.getId());
            couponsDAO.deleteCoupon(coupon.getId());
        }

        companiesDAO.deleteCompany(companyId);
    }

    public List<Company> getAllCompanies() throws SQLException, InterruptedException {
        return companiesDAO.getAllCompanies();
    }

    public Company getOneCompany(int companyId) throws SQLException, InterruptedException, CustomCouponSystemException {
        if (!companiesDAO.isCompanyExistsById(companyId)) {
            throw new CustomCouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION.getMessage());
        }
        return companiesDAO.getOneCompany(companyId);
    }

    public void addCustomer(Customer customer) throws SQLException, InterruptedException, CustomCouponSystemException {
        if (customersDAO.isCustomerExists(customer.getEmail())) {
            throw new CustomCouponSystemException(ErrMsg.EMAIL_ALREADY_EXISTS_EXCEPTION.getMessage());
        }
        customersDAO.addCustomer(customer);
    }

    public void updateCustomer(int customerId, Customer customer) throws SQLException, InterruptedException, CustomCouponSystemException {
        if (customer.getId() != customerId) {
            throw new CustomCouponSystemException(ErrMsg.CANT_UPDATE_ID.getMessage());
        }
        customersDAO.updateCustomer(customerId, customer);
    }

    public void deleteCustomer(int customerId) throws SQLException, InterruptedException, CustomCouponSystemException {
        if (!customersDAO.isCustomerExistsById(customerId)) {
            throw new CustomCouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION.getMessage());
        }

        for (Coupon coupon : couponsDAO.getAllCouponsByCustomerId(customerId)) {
            couponsDAO.deleteCouponPurchase(customerId, coupon.getId());
        }

        customersDAO.deleteCustomer(customerId);
    }

    public List<Customer> getAllCustomers() throws SQLException, InterruptedException {
        return customersDAO.getAllCustomers();
    }

    public Customer getOneCustomer(int customerId) throws SQLException, InterruptedException, CustomCouponSystemException {
        if (!customersDAO.isCustomerExistsById(customerId)) {
            throw new CustomCouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION.getMessage());
        }
        return customersDAO.getOneCustomer(customerId);
    }
}
