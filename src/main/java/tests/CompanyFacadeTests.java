package tests;

import beans.Category;
import beans.Coupon;
import dao.CouponsDAO;
import dao.CouponsDBDAO;
import exceptions.CustomCouponSystemException;
import facades.ClientType;
import facades.CompanyFacade;
import security.LoginManager;
import utils.ArtUtils;
import utils.TableUtils;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class CompanyFacadeTests {

    public static void companyFacadeTests() {
        //Company Facade tests
        CouponsDAO couponsDAO = new CouponsDBDAO();

        System.out.println(ArtUtils.companyFacadeTitle);
        System.out.println("~~~~~~~~~~~~~~~~~Company Facade Tests~~~~~~~~~~~~~~~~~~~~");
        CompanyFacade companyFacade1 = new CompanyFacade(1);
        CompanyFacade companyFacade2 = new CompanyFacade(2);
        CompanyFacade companyFacade3 = new CompanyFacade(3);
        CompanyFacade companyFacade4 = new CompanyFacade(4);

        System.out.println("-------------Login manager tests to Company-------------");
        LoginManager loginManager = LoginManager.getInstance();
        System.out.println("1) All parameters are right:");
        try {
            loginManager.login("fox@gmail.com", "12345", ClientType.Company);
            System.out.println("Successfull login manager");
        } catch (SQLException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("2) Wrong Client type test:");
        try {
            loginManager.login("fox@gmail.com", "12345", ClientType.Customer);
        } catch (SQLException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("3) Wrong email test:");
        try {
            loginManager.login("wrong@admin.com", "12345", ClientType.Company);
        } catch (SQLException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("4) Wrong password test:");
        try {
            loginManager.login("fox@gmail.com", "wrong", ClientType.Company);
        } catch (SQLException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------------------");

        System.out.println("---------------CompanyFacade Login tests------------------");
        System.out.println("CompanyFacade login: (should be true)");
        try {
            System.out.println(companyFacade1.login("fox@gmail.com", "12345"));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("CompanyFacade login: (should be false - wrong email)");
        try {
            System.out.println(companyFacade1.login("sheker@gmail.com", "12345"));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("CompanyFacade login: (should be false - wrong password)");
        try {
            System.out.println(companyFacade1.login("fox@gmail.com", "stam"));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println(ArtUtils.couponsTitle);
        System.out.println("-------------Adding Coupons by Company Facade-------------");
        Date startDate = Date.valueOf(LocalDate.now().plusDays(1));
        Date datePlus1 = Date.valueOf(LocalDate.now().plusWeeks(1));
        Date datePlus2 = Date.valueOf(LocalDate.now().plusWeeks(2));
        Date datePlus3 = Date.valueOf(LocalDate.now().plusWeeks(3));
        Date datePlus4 = Date.valueOf(LocalDate.now().plusWeeks(4));

        try {
            companyFacade1.addCoupon(new Coupon(1, Category.RESTAURANTS, "50% OFF from all shoes", "Pay 150NIS for 300NIS value", startDate, datePlus1, 100, 150, "XXXX"));
            companyFacade1.addCoupon(new Coupon(1, Category.ELECTRONICS, "20% OFF from all phones", "Pay 150NIS for 300NIS value", startDate, datePlus2, 50, 100, "YYYY"));
            companyFacade2.addCoupon(new Coupon(2, Category.ENTERTAINMENT, "15% OFF from all movies", "Pay 150NIS for 300NIS value", startDate, datePlus4, 70, 70, "ZZZZ"));
            companyFacade3.addCoupon(new Coupon(3, Category.RESTAURANTS, "50% OFF from all deserts", "Pay 150NIS for 300NIS value", startDate, datePlus2, 120, 200, "BBBB"));
            companyFacade4.addCoupon(new Coupon(4, Category.TRAVEL, "50% OFF from all", "Pay 150NIS for 300NIS value", startDate, datePlus3, 100, 80, "AAAA"));
            TableUtils.drawCoupons(couponsDAO.getAllCoupons());
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        try {//To check the delete job an expired coupon was used:
            companyFacade1.addCoupon(new Coupon(1, Category.FASHION, "30% OFF from all shirts", "Pay 150NIS for 300NIS value", Date.valueOf(LocalDate.of(2022, 01, 01)), Date.valueOf(LocalDate.of(2022, 01, 21)), 100, 110, "EEEE"));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        //To check start time end time exception:
        System.out.println("Checking startDate after endDate exception");
        try {
            companyFacade1.addCoupon(new Coupon(1, Category.RESTAURANTS, "50% OFF from all", "Pay 150NIS for 300NIS value", datePlus2, datePlus1, 100, 150, "SHOES"));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-------Trying to add coupon with the same name to the same company------");
        try {
            companyFacade1.addCoupon(new Coupon(1, Category.FASHION, "20% OFF from all phones", "Pay 80NIS for 100NIS value", datePlus2, datePlus4, 20, 80, "NNNN"));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-------Trying to add coupon with the same name to the other company------");
        try {
            System.out.println("Before adding");
            TableUtils.drawCoupons(couponsDAO.getAllCoupons());
            System.out.println("After adding");
            companyFacade2.addCoupon(new Coupon(2, Category.FASHION, "20% OFF from all phones", "Pay 80NIS for 100NIS value", datePlus2, datePlus4, 20, 80, "NNNN"));
            TableUtils.drawCoupons(couponsDAO.getAllCoupons());
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("----Test get one coupon------:");
        try {
            TableUtils.drawOneCoupons(couponsDAO.getOneCoupon(4));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("----------Getting Company details-----------");
        try {
            companyFacade1.getCompanyDetails();
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-------------Updating Coupon#7 (without changing coupon ID or company ID---------------");
        System.out.println("Before update:");
        try {
            Coupon updatedCoupon = couponsDAO.getOneCoupon(7);
            TableUtils.drawOneCoupons(updatedCoupon);
            updatedCoupon.setCategory(Category.ELECTRONICS);
            updatedCoupon.setDescription("Pay 90NIS for 100NIS value");
            updatedCoupon.setTitle("10% OFF from all phones");
            updatedCoupon.setStartDate(startDate);
            updatedCoupon.setEndDate(datePlus1);
            updatedCoupon.setAmount(10);
            updatedCoupon.setPrice(90);
            updatedCoupon.setImage("SSSS");
            companyFacade2.updateCoupon(7, updatedCoupon);
            System.out.println("After update:");
            TableUtils.drawOneCoupons(updatedCoupon);
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-------------Trying to update Company ID on  Coupon#7---------------");
        try {
            Coupon updatedCoupon = couponsDAO.getOneCoupon(7);
            updatedCoupon.setCompanyId(1);
            companyFacade2.updateCoupon(7, updatedCoupon);
            System.out.println("After update:");
            TableUtils.drawOneCoupons(updatedCoupon);
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-------------Trying to update coupon ID on  Coupon#4---------------");
        try {
            //No coupon ID setter. Hence making the same coupon#4, which will get a different coupon ID
            Coupon coupon3WithNewId = new Coupon(3, Category.RESTAURANTS, "50% OFF from all deserts", "Pay 150NIS for 300NIS value", startDate, datePlus2, 120, 200, "BBBB");
            companyFacade3.updateCoupon(4, coupon3WithNewId);
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("--------Login company#1------------");
        System.out.println("CompanyFacade login: (should be true)");
        try {
            System.out.println(companyFacade1.login("fox@gmail.com", "12345"));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-----Getting all the coupons of company#1------");
        try {
            TableUtils.drawCoupons(companyFacade1.getCompanyCoupons());
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-----Getting all the coupons of company#1 from Fashion category------");
        try {
            TableUtils.drawCoupons(companyFacade1.getCompanyCoupons(Category.FASHION));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-----Getting all the coupons of company#1 with maximum 120NIS------");
        try {
            TableUtils.drawCoupons(companyFacade1.getCompanyCoupons(120));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("--------Getting company#1 details----------------");
        try {
            companyFacade1.getCompanyDetails();
            TableUtils.drawCoupons(companyFacade1.getCompanyCoupons());
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        try {//Adding one coupon to check purchase after getting zero coupons
            companyFacade1.addCoupon(new Coupon(2, Category.TRAVEL, "50% OFF from all", "Pay 1000NIS for 2000NIS value", startDate, datePlus1, 1, 1000, "FFFF"));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("------------------Login company#3 and using the Facade obtained in the login obtained by email and password----------------------");
        try {
            System.out.println("Login manager: (should be true)");
            CompanyFacade companyFacade = (CompanyFacade) loginManager.login("easyjet@gmail.com", "6474", ClientType.Company);
            System.out.println("Successfull login manager");
            //Adding additional coupon to test with variety
            companyFacade.addCoupon(new Coupon(3, Category.TRAVEL, "500NIS discount for fligths", "Pay 500NIS for 1000NIS value", startDate, datePlus2, 10, 500, "GGGG"));
            System.out.println("company#3 details (and not other or new company)");
            companyFacade.getCompanyDetails();
            System.out.println("Getting all the coupons of company#3");
            TableUtils.drawCoupons(companyFacade.getCompanyCoupons());
            System.out.println("All coupons of Company#3 from category 1 - Restaurants:");
            TableUtils.drawCoupons(companyFacade.getCompanyCoupons(Category.RESTAURANTS));
            System.out.println("All coupons of Company#3 up to 300NIS:");
            TableUtils.drawCoupons(companyFacade.getCompanyCoupons(300));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }
}
