package tests;

import beans.Category;
import beans.Coupon;
import dao.CompaniesDAO;
import dao.CompaniesDBDAO;
import dao.CouponsDAO;
import dao.CouponsDBDAO;
import exceptions.CustomCouponSystemException;
import utils.ArtUtils;
import utils.TableUtils;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class CouponTests {
    public static void couponTests() {
        System.out.println(ArtUtils.couponsTitle);

        CouponsDAO couponsDAO = new CouponsDBDAO();
        CompaniesDAO companiesDAO = new CompaniesDBDAO();

        System.out.println("--------------- Add Coupons --------------- ");
        Date startDate = Date.valueOf(LocalDate.now().plusDays(1));
        Date datePlus1 = Date.valueOf(LocalDate.now().plusWeeks(1));
        Date datePlus2 = Date.valueOf(LocalDate.now().plusWeeks(2));
        Date datePlus3 = Date.valueOf(LocalDate.now().plusWeeks(3));
        Date datePlus4 = Date.valueOf(LocalDate.now().plusWeeks(4));


        try {
            Coupon cp1 = new Coupon(1, Category.RESTAURANTS, "50% OFF from all shoes", "Pay 150NIS for 300NIS value", startDate, datePlus1, 100, 150, "XXXX");
            Coupon cp2 = new Coupon(3, Category.TRAVEL, "20% OFF from all phones", "Pay 150NIS for 300NIS value", startDate, datePlus2, 50, 100, "YYYY");
            Coupon cp3 = new Coupon(4, Category.RESTAURANTS, "15% OFF from all movies", "Pay 150NIS for 300NIS value", startDate, datePlus4, 70, 70, "ZZZZ");
            //To check the delete job an expired coupon was used:
            Coupon cp4 = new Coupon(5, Category.FASHION, "30% OFF from all shirts", "Pay 150NIS for 300NIS value", Date.valueOf(LocalDate.of(2022, 01, 01)), Date.valueOf(LocalDate.of(2022, 01, 21)), 100, 110, "EEEE");
            Coupon cp5 = new Coupon(3, Category.RESTAURANTS, "50% OFF from all deserts", "Pay 150NIS for 300NIS value", startDate, datePlus2, 120, 200, "BBBB");
            Coupon cp6 = new Coupon(1, Category.RESTAURANTS, "50% OFF from all", "Pay 150NIS for 300NIS value", startDate, datePlus3, 100, 80, "AAAA");
            //To check start time end time exception:
            //Coupon cp7 = new Coupon(1, Category.RESTAURANTS, "50% OFF from all", "Pay 150NIS for 300NIS value", datePlus2, datePlus1, 100, 150, "SHOES");

            couponsDAO.addCoupon(cp1);
            couponsDAO.addCoupon(cp2);
            couponsDAO.addCoupon(cp3);
            couponsDAO.addCoupon(cp4);
            couponsDAO.addCoupon(cp5);
            couponsDAO.addCoupon(cp6);
            //couponsDAO.addCoupon(cp7); ////To check start time end time exception:

            TableUtils.drawCoupons(couponsDAO.getAllCoupons());

            System.out.println("~~~~~~~~ Companies after addition of coupons ~~~~~~~~~~~~ ");
            TableUtils.drawCompanies(companiesDAO.getAllCompanies());

            System.out.println("--------------- Coupon Updated --------------- ");
            cp1.setCategory(Category.TRAVEL);
            cp1.setStartDate(Date.valueOf(LocalDate.of(2022, 07, 02)));
            cp1.setEndDate(Date.valueOf(LocalDate.of(2022, 8, 02)));
            couponsDAO.updateCoupon(1, cp1);
            TableUtils.drawCoupons(couponsDAO.getAllCoupons());
            System.out.println("--------------- Delete Coupon --------------- ");
            couponsDAO.deleteCoupon(2);
            TableUtils.drawCoupons(couponsDAO.getAllCoupons());
            System.out.println("--------------- Find Single Coupon --------------- ");
            TableUtils.drawOneCoupons(couponsDAO.getOneCoupon(3));
            System.out.println("--------------- Add Coupon Purchase --------------- ");
            couponsDAO.addCouponPurchase(1, 4);
            couponsDAO.addCouponPurchase(1, 5);
            couponsDAO.addCouponPurchase(4, 3);
            couponsDAO.addCouponPurchase(4, 5);
            TableUtils.drawCoupons(couponsDAO.getAllCoupons());
            System.out.println("--------------- Delete Coupon Purchase --------------- ");
            couponsDAO.deleteCouponPurchase(4, 5);
            TableUtils.drawCoupons(couponsDAO.getAllCoupons());
            System.out.println("--------------- Get all coupons before today --------------- ");
            TableUtils.drawCoupons(couponsDAO.getAllCoupons());
            System.out.println("*****************************************");
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            e.printStackTrace();
            e.getMessage().toString();
        }

    }
}
