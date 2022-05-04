package db;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CustomCouponSystemException;

import java.sql.SQLException;
import java.util.HashMap;

public class ResultsUtils {
    public static Company fromHashMapToCompany(HashMap<String, Object> row) throws SQLException, InterruptedException {
        int id = (int) row.get("ID");
        String name = (String) row.get("NAME");
        String email = (String) row.get("EMAIL");
        String password = (String) row.get("PASSWORD");
        return new Company(id, name, email, password);
    }

    public static Customer fromHashMapToCustomer(HashMap<String, Object> row) {
        int id = (int) row.get("ID");
        String firstName = (String) row.get("FIRST_NAME");
        String lastName = (String) row.get("LAST_NAME");
        String email = (String) row.get("EMAIL");
        String password = (String) row.get("PASSWORD");
        return new Customer(id, firstName, lastName, email, password);
    }

    public static Coupon fromHashMapToCoupon(HashMap<String, Object> row) throws CustomCouponSystemException {
        int id = (int) row.get("ID");
        int companyId = (int) row.get("COMPANY_ID");
        int categoryId = (int) row.get("CATEGORY_ID");
        Category category = Category.getCategoryFromId(categoryId);
        String title = (String) row.get("TITLE");
        String description = (String) row.get("DESCRIPTION");
        java.sql.Date startDate = (java.sql.Date) row.get("START_DATE");
        java.sql.Date endDate = (java.sql.Date) row.get("END_DATE");
        int amount = (int) row.get("AMOUNT");
        double price = (double) row.get("PRICE");
        String image = (String) row.get("IMAGE");
        return new Coupon(id, companyId, category, title, description, startDate, endDate, amount, price, image);
    }

    public static boolean fromHashMapToBool(HashMap<String, Object> row) {
        long result = (long) row.get("res");
        return result == 1;
    }

    public static Category fromHashMapToCategory(HashMap<String, Object> row) {
        String name = (String) row.get("NAME");
        return Category.valueOf(name);
    }

    public static int fromHashMapToInt(HashMap<String, Object> row) {
        int result = (int) row.get("ID");
        return result;
    }


}
