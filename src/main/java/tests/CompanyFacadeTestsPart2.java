package tests;

import dao.CouponsDAO;
import dao.CouponsDBDAO;
import exceptions.CustomCouponSystemException;
import facades.CompanyFacade;
import facades.CustomerFacade;
import utils.ArtUtils;
import utils.TableUtils;

import java.sql.SQLException;

public class CompanyFacadeTestsPart2 {
    //Company Facade tests part 2 after existing coupon purchases

    public static void companyFacadeTests() {

        CouponsDAO couponsDAO = new CouponsDBDAO();
        CompanyFacade companyFacade4 = new CompanyFacade(4);
        CustomerFacade customerFacade1 = new CustomerFacade(1);
        CustomerFacade customerFacade2 = new CustomerFacade(2);

        System.out.println(ArtUtils.companyFacadeTitle);
        System.out.println(ArtUtils.couponsTitle);

        System.out.println("----------------------------------Deleting existing coupon#5-------------------------------------------");
        try {
            System.out.println("~~~~~Before deleting:~~~~~");
            System.out.println("Coupons list:");
            TableUtils.drawCoupons(couponsDAO.getAllCoupons());
            System.out.println("Coupons of Company#4");
            TableUtils.drawCoupons(companyFacade4.getCompanyCoupons());
            System.out.println("Purchased coupons by customers by customers details:");
            customerFacade1.getCustomerDetails();
            customerFacade2.getCustomerDetails();

            System.out.println("~~~~~After deleting:~~~~~");

            companyFacade4.deleteCoupon(5, couponsDAO.getOneCoupon(5));
            System.out.println("Coupons list:");
            TableUtils.drawCoupons(couponsDAO.getAllCoupons());
            System.out.println("Coupons of Company#4");
            TableUtils.drawCoupons(companyFacade4.getCompanyCoupons());
            System.out.println("Purchased coupons by customers by customers details:");
            customerFacade1.getCustomerDetails();
            customerFacade2.getCustomerDetails();

        } catch (SQLException | InterruptedException |
                CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }

        System.out.println("-------------------------Trying to delete non-existing coupon#20----------------------------");
        try {
            companyFacade4.deleteCoupon(20, couponsDAO.getOneCoupon(5));
        } catch (SQLException | InterruptedException |
                CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }
    }
}
