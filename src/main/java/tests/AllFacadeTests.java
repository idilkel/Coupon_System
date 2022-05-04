package tests;

import utils.StartEndUtil;

public class AllFacadeTests {
    public static void testAllFacades() {
        StartEndUtil.start();

        //Admin Facade tests
        AdminFacadeTests.adminFacadeTests();

        //Company Facade tests
        CompanyFacadeTests.companyFacadeTests();

        //Customers Facade tests
        CustomerFacadeTests.customerFacadeTests();

        //Admin Facade tests part2 - after existing coupons and purchases: Deleting company or customer with coupons
        AdminFacadeTestsPart2.adminFacadeTests();

        //Company Facade tests part2 - after existing coupons and purchases: Deleting coupons
        CompanyFacadeTestsPart2.companyFacadeTests();

        StartEndUtil.end();
    }
}
