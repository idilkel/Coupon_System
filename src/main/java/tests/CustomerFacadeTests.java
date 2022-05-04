package tests;

import beans.Category;
import dao.*;
import exceptions.CustomCouponSystemException;
import facades.ClientType;
import facades.CustomerFacade;
import security.LoginManager;
import utils.ArtUtils;
import utils.TableUtils;

import java.sql.SQLException;

public class CustomerFacadeTests {
    public static void customerFacadeTests() {
        //Customer Facade tests tests
        CompaniesDAO companiesDAO = new CompaniesDBDAO();
        CustomersDAO customersDAO = new CustomersDBDAO();
        CategoriesDAO categoriesDAO = new CategoriesDBDAO();
        CouponsDAO couponsDAO = new CouponsDBDAO();

        System.out.println(ArtUtils.customersFacadeTitle);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~Customers Facade Tests~~~~~~~~~~~~~~~~~~~~~~");
        CustomerFacade customerFacade1 = new CustomerFacade(1);
        CustomerFacade customerFacade2 = new CustomerFacade(2);
        CustomerFacade customerFacade4 = new CustomerFacade(4);

        System.out.println("-------------Login manager tests to Customer-------------");
        LoginManager loginManager = LoginManager.getInstance();
        System.out.println("1) All parameters are right:");
        try {
            loginManager.login("yoni@gmail.com", "gh4567", ClientType.Customer);
            System.out.println("Successfull login manager");
        } catch (SQLException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("2) Wrong Client type test:");
        try {
            loginManager.login("yoni@gmail.com", "gh4567", ClientType.Administrator);
        } catch (SQLException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("3) Wrong email test:");
        try {
            loginManager.login("wrong@gmail.com", "gh4567", ClientType.Customer);
        } catch (SQLException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("4) Wrong password test:");
        try {
            loginManager.login("yoni@gmail.com", "wrong", ClientType.Customer);
        } catch (SQLException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------------------");

        System.out.println("-----------------CustomerFacade Login tests--------------------");
        System.out.println("CustomerFacade login: (should be true)");
        try {
            System.out.println(customerFacade1.login("moshe@gmail.com", "12345"));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("CustomerFacade login: (should be false - wrong mail)");
        try {
            System.out.println(customerFacade1.login("sheker@gmail.com", "12345"));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("CustomerFacade login: (should be false - wrong password)");
        try {
            System.out.println(customerFacade1.login("moshe@gmail.com", "stam"));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }


        System.out.println("----------Coupon purchase test---------");
        System.out.println("Coupons before purchase:");
        try {
            TableUtils.drawCoupons(couponsDAO.getAllCoupons());
        } catch (SQLException | CustomCouponSystemException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        try {
            customerFacade1.purchaseCoupon(couponsDAO.getOneCoupon(4));
            customerFacade1.purchaseCoupon(couponsDAO.getOneCoupon(5));
            customerFacade2.purchaseCoupon(couponsDAO.getOneCoupon(1));
            customerFacade2.purchaseCoupon(couponsDAO.getOneCoupon(2));
            customerFacade2.purchaseCoupon(couponsDAO.getOneCoupon(3));
            customerFacade2.purchaseCoupon(couponsDAO.getOneCoupon(4));
            customerFacade2.purchaseCoupon(couponsDAO.getOneCoupon(5));
            customerFacade2.purchaseCoupon(couponsDAO.getOneCoupon(7));
            customerFacade2.purchaseCoupon(couponsDAO.getOneCoupon(8));
            customerFacade4.purchaseCoupon(couponsDAO.getOneCoupon(3));
            customerFacade4.purchaseCoupon(couponsDAO.getOneCoupon(5));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("Coupons after purchase:");
        try {
            TableUtils.drawCoupons(couponsDAO.getAllCoupons());
        } catch (SQLException | CustomCouponSystemException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-----------Trying to purchase an expired coupon----------");
        try {
            customerFacade2.purchaseCoupon(couponsDAO.getOneCoupon(6));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-----------Trying to purchase the same coupon again--------------");
        try {
            customerFacade2.purchaseCoupon(couponsDAO.getOneCoupon(5));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-----------Trying to purchase a coupon with zero stock--------------");
        try {
            customerFacade1.purchaseCoupon(couponsDAO.getOneCoupon(8));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("------------------Login customer#2----------------------");
        System.out.println("Login manager: (should be true)");
        try {
            loginManager.login("yoni@gmail.com", "gh4567", ClientType.Customer);
            System.out.println("Successfull login manager");
        } catch (SQLException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("CustomerFacade login: (should be true)");
        try {
            System.out.println(customerFacade1.login("yoni@gmail.com", "gh4567"));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-----Getting all the coupons of customer#2------");
        try {
            TableUtils.drawCoupons(customerFacade2.getCustomerCoupons());
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-----Getting all the coupons of customer#2 from Restaurants category------");
        try {
            TableUtils.drawCoupons(customerFacade2.getCustomerCoupons(Category.RESTAURANTS));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-----Getting all the coupons of customer#2 with maximum 100NIS------");
        try {
            TableUtils.drawCoupons(customerFacade2.getCustomerCoupons(100));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }


        System.out.println("----------Getting Customer#2 details-----------");
        try {
            customerFacade2.getCustomerDetails();
            TableUtils.drawCouponsForCustomer(customerFacade2.getCustomerCoupons());
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("------------------Login customer#3 and using the Facade obtained in the login obtained by email and password----------------------");
        try {
            System.out.println("Login manager: (should be true)");
            CustomerFacade customerFacade = (CustomerFacade) loginManager.login("ella@gmail.com", "#123H", ClientType.Customer);
            System.out.println("Successfull login manager");
            System.out.println("Customer 3 details");
            customerFacade.getCustomerDetails();
            System.out.println("Customer3 is logged in and purchasing coupon 1 and 2");
            customerFacade.purchaseCoupon(couponsDAO.getOneCoupon(1));
            customerFacade.purchaseCoupon(couponsDAO.getOneCoupon(2));
            System.out.println("Details of customer 3 after purchasing coupon 1 and 2");
            customerFacade.getCustomerDetails();
            System.out.println("All purchased coupons by customer 3:");
            TableUtils.drawCoupons(customerFacade.getCustomerCoupons());
            System.out.println("All purchased coupons by customer 3 from category 1 - Restaurants:");
            TableUtils.drawCoupons(customerFacade.getCustomerCoupons(Category.RESTAURANTS));
            System.out.println("All purchased coupons by customer 3 fup to 110NIS:");
            TableUtils.drawCoupons(customerFacade.getCustomerCoupons(110));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("------------------Checking if re-Logging-in gets the same customer(#2) or a new customer----------------------");
        try {
            System.out.println("Login manager: (should be true)");
            CustomerFacade customerFacade = (CustomerFacade) loginManager.login("yoni@gmail.com", "gh4567", ClientType.Customer);
            System.out.println("Successfull login manager");
            System.out.println("Customer 2 details: should be Yoni Katz details");
            customerFacade.getCustomerDetails();
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
