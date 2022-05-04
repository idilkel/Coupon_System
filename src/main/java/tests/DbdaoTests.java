package tests;

import utils.StartEndUtil;

public class DbdaoTests {

    public static void dbdaoTests() {
        StartEndUtil.start();

        //Company tests
        CompanyTests.companyTests();

        //Customer tests
        CustomerTests.customerTests();

        //Category tests
        CategoryTests.categoryTests();

        //Coupons tests
        CouponTests.couponTests();

        StartEndUtil.end();
    }
}
