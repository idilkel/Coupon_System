import tests.AllFacadeTests;

public class TestsFacades {
    public static void main(String[] args) {
        //All Facades Tests
        
        AllFacadeTests.testAllFacades();

        //To test the expired coupons delete job, lines 30 & 40 on job class should be put as remarks
        //To test individual dbdao prior to the Facades stage (without Facade): use DbdaoTest

    }
}

