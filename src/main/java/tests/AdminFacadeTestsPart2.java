package tests;

import dao.*;
import exceptions.CustomCouponSystemException;
import facades.AdminFacade;
import utils.ArtUtils;
import utils.TableUtils;

import java.sql.SQLException;

public class AdminFacadeTestsPart2 {
    //Admin Facade tests part 2 after existing coupon purchases

    public static void adminFacadeTests() {
        AdminFacade adminFacade = new AdminFacade();
        CompaniesDAO companiesDAO = new CompaniesDBDAO();
        CustomersDAO customersDAO = new CustomersDBDAO();
        CouponsDAO couponsDAO = new CouponsDBDAO();

        System.out.println(ArtUtils.adminFacadeTitle);
        System.out.println(ArtUtils.companiesTitle);

        System.out.println("-----------------------Deleting company#2------------------");
        System.out.println("Before delete:");
        try {
            TableUtils.drawCompanies(companiesDAO.getAllCompanies());
            TableUtils.drawCoupons(couponsDAO.getAllCoupons());
            adminFacade.deleteCompany(2);
            System.out.println("After delete:");
            TableUtils.drawCompanies(companiesDAO.getAllCompanies());
            TableUtils.drawCoupons(couponsDAO.getAllCoupons());
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }

        System.out.println("---------Trying to delete an unexisting company Id");
        try {
            adminFacade.deleteCompany(20);
        } catch (SQLException | CustomCouponSystemException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }

        System.out.println(ArtUtils.customersTitle);

        System.out.println("-----------------------Deleting customer#4-----------------");
        try {
            System.out.println("Before delete:");
            TableUtils.drawCustomers(customersDAO.getAllCustomers());
            TableUtils.drawCoupons(couponsDAO.getAllCouponsByCustomerId(4));
            System.out.println("After delete:");
            adminFacade.deleteCustomer(4);
            TableUtils.drawCustomers(customersDAO.getAllCustomers());
            TableUtils.drawCoupons(couponsDAO.getAllCouponsByCustomerId(4));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-----------------------Deleting unexisting customer (#20)-----------------");
        try {
            adminFacade.deleteCustomer(20);
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}
