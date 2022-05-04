package dao;

import beans.Customer;
import db.JDBCUtils;
import db.ResultsUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersDBDAO implements CustomersDAO {
    private static final String QUERY_IS_EXIST = "Select exists ( SELECT * FROM coupon_system.customers where `EMAIL`=? and `PASSWORD`=?) as res;";
    private static final String QUERY_IS_EXIST_BY_ID = "Select exists ( SELECT * FROM coupon_system.customers WHERE (`ID` = ?)) as res;";
    private static final String QUERY_IS_EXIST_BY_EMAIL = "Select exists ( SELECT * FROM coupon_system.customers WHERE (`EMAIL` = ?)) as res;";
    private static final String QUERY_IS_EXIST_BY_EMAIL_AND_PASSWORD = "SELECT EXISTS (SELECT * FROM coupon_system.customers WHERE `EMAIL`=? AND `PASSWORD`=?) as res;";
    private static final String QUERY_ID_BY_EMAIL = "SELECT `ID` FROM coupon_system.customers where `EMAIL`=?";
    private static final String QUERY_INSERT = "INSERT INTO `coupon_system`.`customers` (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES (?, ? ,? ,?);";
    private static final String QUERY_UPDATE = "UPDATE `coupon_system`.`customers` SET `FIRST_NAME` = ?, `LAST_NAME` = ?, `EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?);";
    private static final String QUERY_DELETE = "DELETE FROM `coupon_system`.`customers` WHERE (`ID` = ?);";
    private static final String QUERY_FIND_ALL = "SELECT * FROM coupon_system.customers;";
    private static final String QUERY_FIND_ONE = "SELECT * FROM coupon_system.customers WHERE (`ID` = ?);";


    @Override
    public boolean isCustomerExists(String email, String password) throws SQLException, InterruptedException {
        boolean results = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        List<?> rows = JDBCUtils.executeResults(QUERY_IS_EXIST, params);
        for (Object row : rows) {
            results = ResultsUtils.fromHashMapToBool((HashMap<String, Object>) row);
            break;
        }

        return results;
    }

    @Override
    public boolean isCustomerExists(String email) throws SQLException, InterruptedException {
        boolean results = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        List<?> rows = JDBCUtils.executeResults(QUERY_IS_EXIST_BY_EMAIL, params);
        for (Object row : rows) {
            results = ResultsUtils.fromHashMapToBool((HashMap<String, Object>) row);
            break;
        }

        return results;
    }

    @Override
    public boolean isCustomerExistsById(int customerId) throws SQLException, InterruptedException {
        boolean results = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);

        List<?> rows = JDBCUtils.executeResults(QUERY_IS_EXIST_BY_ID, params);
        for (Object row : rows) {
            results = ResultsUtils.fromHashMapToBool((HashMap<String, Object>) row);
            break;
        }

        return results;
    }


    @Override
    public boolean isCustomerExistsByEmailAndByPassword(String email, String password) throws SQLException, InterruptedException {
        boolean results = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);

        List<?> rows = JDBCUtils.executeResults(QUERY_IS_EXIST_BY_EMAIL_AND_PASSWORD, params);
        for (Object row : rows) {
            results = ResultsUtils.fromHashMapToBool((HashMap<String, Object>) row);
            break;
        }

        return results;
    }

    @Override
    public int customerIdByEmail(String email) throws SQLException, InterruptedException {
        int id = 0;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);

        List<?> rows = JDBCUtils.executeResults(QUERY_ID_BY_EMAIL, params);
        for (Object row : rows) {
            id = ResultsUtils.fromHashMapToInt((HashMap<String, Object>) row);
            break;
        }
        return id;
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());

        JDBCUtils.execute(QUERY_INSERT, params);
    }

    @Override
    public void updateCustomer(int customerId, Customer customer) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        params.put(5, customerId);

        JDBCUtils.execute(QUERY_UPDATE, params);
    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);

        JDBCUtils.execute(QUERY_DELETE, params);
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException, InterruptedException {
        List<Customer> results = new ArrayList<>();
        List<?> rows = JDBCUtils.executeResults(QUERY_FIND_ALL);
        for (Object row : rows) {
            results.add(ResultsUtils.fromHashMapToCustomer(((HashMap<String, Object>) row)));
        }
        return results;
    }

    @Override
    public Customer getOneCustomer(int customerId) throws SQLException, InterruptedException {
        Customer results = null;

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);

        List<?> rows = JDBCUtils.executeResults(QUERY_FIND_ONE, params);
        for (Object row : rows) {
            results = ResultsUtils.fromHashMapToCustomer((HashMap<String, Object>) row);
            break;
        }
        return results;
    }
}
