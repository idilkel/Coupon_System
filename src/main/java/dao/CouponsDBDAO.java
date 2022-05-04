package dao;

import beans.Category;
import beans.Coupon;
import db.JDBCUtils;
import db.ResultsUtils;
import exceptions.CustomCouponSystemException;
import exceptions.ErrMsg;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponsDBDAO implements CouponsDAO {
    private static final String QUERY_INSERT = "INSERT INTO `coupon_system`.`coupons` (`COMPANY_ID`, `CATEGORY_ID`, `TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);\n";
    private static final String QUERY_UPDATE = "UPDATE `coupon_system`.`coupons` SET `COMPANY_ID` = ?, `CATEGORY_ID` = ?, `TITLE` = ?, `DESCRIPTION` = ?, `START_DATE` = ?, `END_DATE` = ?, `AMOUNT` = ?, `PRICE` = ?, `IMAGE` = ? WHERE (`ID` = ?);";
    private static final String QUERY_DELETE = "DELETE FROM `coupon_system`.`coupons` WHERE (`ID` = ?);";
    private static final String QUERY_FIND_ALL = "SELECT * FROM coupon_system.coupons;";
    private static final String QUERY_FIND_ONE = "SELECT * FROM coupon_system.coupons WHERE (`ID` = ?);";
    private static final String QUERY_INSERT_PURCHASE = "INSERT INTO `coupon_system`.`customers_vs_coupons` (`CUSTOMER_ID`, `COUPON_ID`) VALUES (?, ?);";
    private static final String QUERY_DELETE_PURCHASE = "DELETE FROM `coupon_system`.`customers_vs_coupons` WHERE (`CUSTOMER_ID` = ?) and (`COUPON_ID` = ?);";
    private static final String QUERY_DELETE_COUPON_VS_CUSTOMER = "DELETE FROM `coupon_system`.`customers_vs_coupons` WHERE `COUPON_ID` = ?;";
    private static final String QUERY_FIND_ALL_BEFORE_DATE = "SELECT * FROM coupon_system.coupons where END_DATE<?;";
    private static final String QUERY_IS_EXIST_BY_ID = "SELECT exists ( SELECT * FROM coupon_system.coupons WHERE (`ID` = ?)) as res;";
    private static final String QUERY_FIND_ALL_BY_COMPANY_AND_CATEGORY = "SELECT * FROM coupon_system.coupons WHERE (`COMPANY_ID` = ?) and (`CATEGORY_ID`=?);";
    private static final String QUERY_FIND_ALL_BY_COMPANY_AND_MAX_PRICE = "SELECT * FROM coupon_system.coupons WHERE (`COMPANY_ID` = ?) and (`PRICE`<=?);";
    private static final String QUERY_IS_EXIST_BY_CUSTOMER_AND_COUPON_ID = "SELECT exists (SELECT * FROM coupon_system.customers_vs_coupons WHERE (`CUSTOMER_ID` = ?) and (`COUPON_ID` = ?)) as res;";
    private static final String QUERY_FIND_ALL_BY_COMPANY_ID = "SELECT * FROM coupon_system.coupons WHERE (`COMPANY_ID` = ?);";
    private static final String QUERY_FIND_ALL_BY_CUSTOMER_ID = "SELECT * FROM coupon_system.coupons c\n" +
            "join coupon_system.customers_vs_coupons  cvc\n" +
            "            on c.ID=cvc.COUPON_ID\n" +
            "            WHERE (`CUSTOMER_ID` = ?)";
    private static final String QUERY_COUPON_SET_AMOUNT = "UPDATE `coupon_system`.`coupons` SET `AMOUNT` = ? WHERE (`ID` = ?);";
    private static final String QUERY_FIND_ALL_BY_CUSTOMER_AND_MAX_PRICE = "SELECT *\n" +
            "            FROM coupon_system.coupons\n" +
            "            join coupon_system.customers_vs_coupons \n" +
            "            on coupon_system.coupons.ID=coupon_system.customers_vs_coupons.COUPON_ID\n" +
            "            WHERE (`coupon_system`.`customers_vs_coupons`.`CUSTOMER_ID`=?) and (`coupon_system`.`coupons`.`PRICE`<=?)";
    private static final String QUERY_FIND_ALL_BY_CUSTOMER_AND_CATEGORY = "SELECT *\n" +
            "            FROM coupon_system.coupons\n" +
            "            join coupon_system.customers_vs_coupons \n" +
            "            on coupon_system.coupons.ID=coupon_system.customers_vs_coupons.COUPON_ID\n" +
            "            WHERE (`coupon_system`.`customers_vs_coupons`.`CUSTOMER_ID`=?) and (`coupon_system`.`coupons`.`CATEGORY_ID`=?)";


    @Override
    public void addCoupon(Coupon coupon) throws SQLException, InterruptedException, CustomCouponSystemException {
        CompaniesDAO companiesDAO = new CompaniesDBDAO();
        boolean exists = companiesDAO.isCompanyExistsById(coupon.getCompanyId());
        if (!exists) {
            throw new CustomCouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION.getMessage());
        }

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, coupon.getCategory().ordinal() + 1);
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());

        JDBCUtils.execute(QUERY_INSERT, params);

    }

    @Override
    public void updateCoupon(int couponId, Coupon coupon) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, coupon.getCategory().ordinal() + 1);
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
        params.put(10, couponId);

        JDBCUtils.execute(QUERY_UPDATE, params);
    }

    @Override
    public void deleteCoupon(int couponId) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);

        JDBCUtils.execute(QUERY_DELETE, params);
    }

    @Override
    public List<Coupon> getAllCoupons() throws SQLException, InterruptedException, CustomCouponSystemException {
        List<Coupon> results = new ArrayList<>();
        List<?> rows = JDBCUtils.executeResults(QUERY_FIND_ALL);
        for (Object row : rows) {
            results.add(ResultsUtils.fromHashMapToCoupon(((HashMap<String, Object>) row)));
        }

        return results;
    }

    @Override
    public Coupon getOneCoupon(int couponId) throws SQLException, InterruptedException, CustomCouponSystemException {
        Coupon results = null;

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);

        List<?> rows = JDBCUtils.executeResults(QUERY_FIND_ONE, params);
        for (Object row : rows) {
            results = ResultsUtils.fromHashMapToCoupon((HashMap<String, Object>) row);
            break;
        }
        return results;
    }

    @Override
    public void addCouponPurchase(int customerID, int couponID) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, couponID);

        JDBCUtils.execute(QUERY_INSERT_PURCHASE, params);
    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, couponID);

        JDBCUtils.execute(QUERY_DELETE_PURCHASE, params);
    }

    //To delete coupon from customer vs coupon table too
    @Override
    public void deleteCouponPurchaseByCouponId(int couponId) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);

        JDBCUtils.execute(QUERY_DELETE_COUPON_VS_CUSTOMER, params);
    }

    public List<Coupon> getAllCouponsBefore() throws SQLException, InterruptedException, CustomCouponSystemException {
        java.sql.Date date = Date.valueOf(LocalDate.now());

        List<Coupon> results = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, date);

        List<?> rows = JDBCUtils.executeResults(QUERY_FIND_ALL_BEFORE_DATE, params);
        for (Object row : rows) {
            results.add(ResultsUtils.fromHashMapToCoupon(((HashMap<String, Object>) row)));
        }
        return results;
    }

    @Override
    public boolean isCouponExistsById(int couponId) throws SQLException, InterruptedException {
        boolean results = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);

        List<?> rows = JDBCUtils.executeResults(QUERY_IS_EXIST_BY_ID, params);
        for (Object row : rows) {
            results = ResultsUtils.fromHashMapToBool((HashMap<String, Object>) row);
            break;
        }

        return results;
    }

    @Override
    public List<Coupon> getAllCouponsByCompanyAndCategory(int companyId, Category category) throws SQLException, InterruptedException, CustomCouponSystemException {
        List<Coupon> results = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, category.ordinal() + 1);

        List<?> rows = JDBCUtils.executeResults(QUERY_FIND_ALL_BY_COMPANY_AND_CATEGORY, params);
        for (Object row : rows) {
            results.add(ResultsUtils.fromHashMapToCoupon(((HashMap<String, Object>) row)));
        }
        return results;
    }

    @Override
    public List<Coupon> getAllCouponsByCompanyAndMaxPrice(int companyId, double maxPrice) throws SQLException, InterruptedException, CustomCouponSystemException {
        List<Coupon> results = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, maxPrice);

        List<?> rows = JDBCUtils.executeResults(QUERY_FIND_ALL_BY_COMPANY_AND_MAX_PRICE, params);
        for (Object row : rows) {
            results.add(ResultsUtils.fromHashMapToCoupon(((HashMap<String, Object>) row)));
        }
        return results;
    }

    @Override
    public List<Coupon> getAllCouponsByCompanyId(int companyId) throws SQLException, InterruptedException, CustomCouponSystemException {
        List<Coupon> results = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);

        List<?> rows = JDBCUtils.executeResults(QUERY_FIND_ALL_BY_COMPANY_ID, params);
        for (Object row : rows) {
            results.add(ResultsUtils.fromHashMapToCoupon(((HashMap<String, Object>) row)));
        }
        return results;
    }

    @Override
    public boolean isCouponExistsByCustomerIdAndCouponId(int customerId, int couponId) throws SQLException, InterruptedException {
        boolean results = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, couponId);

        List<?> rows = JDBCUtils.executeResults(QUERY_IS_EXIST_BY_CUSTOMER_AND_COUPON_ID, params);
        for (Object row : rows) {
            results = ResultsUtils.fromHashMapToBool((HashMap<String, Object>) row);
            break;
        }

        return results;
    }

    @Override
    public List<Coupon> getAllCouponsByCustomerAndCategory(int customerId, Category category) throws SQLException, InterruptedException, CustomCouponSystemException {
        List<Coupon> results = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, category.ordinal() + 1);

        List<?> rows = JDBCUtils.executeResults(QUERY_FIND_ALL_BY_CUSTOMER_AND_CATEGORY, params);
        for (Object row : rows) {
            results.add(ResultsUtils.fromHashMapToCoupon(((HashMap<String, Object>) row)));
        }
        return results;
    }

    @Override
    public List<Coupon> getAllCouponsByCustomerAndMaxPrice(int customerId, double maxPrice) throws SQLException, InterruptedException, CustomCouponSystemException {
        List<Coupon> results = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, maxPrice);

        List<?> rows = JDBCUtils.executeResults(QUERY_FIND_ALL_BY_CUSTOMER_AND_MAX_PRICE, params);
        for (Object row : rows) {
            results.add(ResultsUtils.fromHashMapToCoupon(((HashMap<String, Object>) row)));
        }
        return results;
    }

    @Override
    public List<Coupon> getAllCouponsByCustomerId(int customerId) throws SQLException, InterruptedException, CustomCouponSystemException {
        List<Coupon> results = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);

        List<?> rows = JDBCUtils.executeResults(QUERY_FIND_ALL_BY_CUSTOMER_ID, params);
        for (Object row : rows) {
            results.add(ResultsUtils.fromHashMapToCoupon(((HashMap<String, Object>) row)));
        }
        return results;
    }

    @Override
    public void reduceCouponAmountByOne(int couponAmount, int couponID) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponAmount);
        params.put(2, couponID);

        JDBCUtils.execute(QUERY_COUPON_SET_AMOUNT, params);
    }
}
