import exceptions.CustomCouponSystemException;
import tests.DbdaoTests;

import java.sql.SQLException;

public class DbdaoTest {
    public static void main(String[] args) throws SQLException, InterruptedException, CustomCouponSystemException {

        DbdaoTests.dbdaoTests();

    }
}
