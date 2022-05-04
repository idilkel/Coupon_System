package dao;

import beans.Company;
import db.JDBCUtils;
import db.ResultsUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompaniesDBDAO implements CompaniesDAO {

    private static final String QUERY_IS_EXIST = "Select exists ( SELECT * FROM coupon_system.companies where `EMAIL`=? and `PASSWORD`=?) as res;";
    private static final String QUERY_IS_EXIST_BY_ID = "Select exists ( SELECT * FROM coupon_system.companies where `ID`=?) as res;";
    private static final String QUERY_IS_EXIST_BY_NAME = "Select exists ( SELECT * FROM coupon_system.companies where `NAME`=?) as res;";
    private static final String QUERY_IS_EXIST_BY_NAME_OR_EMAIL = "SELECT EXISTS (SELECT * FROM coupon_system.companies WHERE `NAME`=? OR `EMAIL`=?) as res;";
    private static final String QUERY_IS_EXIST_BY_EMAIL_AND_PASSWORD = "SELECT EXISTS (SELECT * FROM coupon_system.companies WHERE `EMAIL`=? AND `PASSWORD`=?) as res;";
    private static final String QUERY_ID_BY_EMAIL = "SELECT `ID` FROM coupon_system.companies where `EMAIL`=?";
    private static final String QUERY_INSERT = "INSERT INTO `coupon_system`.`companies` (`NAME`, `EMAIL`, `PASSWORD`) VALUES (?,?,?);\n";
    private static final String QUERY_UPDATE = "UPDATE `coupon_system`.`companies` SET `NAME` = ?, `EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?);";
    private static final String QUERY_UPDATE_BY_ID = "UPDATE `coupon_system`.`companies` SET `NAME` = ?, `EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?);";
    private static final String QUERY_DELETE = "DELETE FROM `coupon_system`.`companies` WHERE (`ID` = ?);";
    private static final String QUERY_FIND_ALL = "SELECT * FROM coupon_system.companies;";
    private static final String QUERY_FIND_ONE = "SELECT * FROM coupon_system.companies WHERE (`ID` = ?);";
    private static final String QUERY_FIND_ALL_BY_CATEGORY = "SELECT * FROM coupon_system.coupons where (`CATEGORY_ID`=?);";
    private static final String QUERY_FIND_ALL_BY_MAX_PRICE = "SELECT * FROM coupon_system.coupons where (`PRICE`<=?);";


    @Override
    public boolean isCompanyExists(String email, String password) throws SQLException, InterruptedException {
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

    //For exception to adding a coupon to a non-existing company
    public boolean isCompanyExistsById(int companyId) throws SQLException, InterruptedException {
        boolean results = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);

        List<?> rows = JDBCUtils.executeResults(QUERY_IS_EXIST_BY_ID, params);
        for (Object row : rows) {
            results = ResultsUtils.fromHashMapToBool((HashMap<String, Object>) row);
            break;
        }

        return results;
    }

    @Override
    public boolean isCompanyExistsByName(String name) throws SQLException, InterruptedException {
        boolean results = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, name);

        List<?> rows = JDBCUtils.executeResults(QUERY_IS_EXIST_BY_NAME, params);
        for (Object row : rows) {
            results = ResultsUtils.fromHashMapToBool((HashMap<String, Object>) row);
            break;
        }

        return results;
    }

    @Override
    public boolean isCompanyExistsByNameOrByEmail(String name, String email) throws SQLException, InterruptedException {

        boolean results = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, name);
        params.put(2, email);

        List<?> rows = JDBCUtils.executeResults(QUERY_IS_EXIST_BY_NAME_OR_EMAIL, params);
        for (Object row : rows) {
            results = ResultsUtils.fromHashMapToBool((HashMap<String, Object>) row);
            break;
        }
        return results;
    }

    @Override
    public boolean isCompanyExistsByEmailAndByPassword(String email, String password) throws SQLException, InterruptedException {
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
    public int companyIdByEmail(String email) throws SQLException, InterruptedException {
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
    public void addCompany(Company company) throws SQLException, InterruptedException {

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());

        JDBCUtils.execute(QUERY_INSERT, params);
    }

    @Override
    public void updateCompany(int companyId, Company company) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        params.put(4, companyId);

        JDBCUtils.execute(QUERY_UPDATE, params);
    }

    @Override
    public void deleteCompany(int companyId) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);

        JDBCUtils.execute(QUERY_DELETE, params);
    }

    @Override
    public List<Company> getAllCompanies() throws SQLException, InterruptedException {
        List<Company> results = new ArrayList<>();
        List<?> rows = JDBCUtils.executeResults(QUERY_FIND_ALL);
        for (Object row : rows) {
            results.add(ResultsUtils.fromHashMapToCompany(((HashMap<String, Object>) row)));
        }
        return results;
    }

    @Override
    public Company getOneCompany(int companyId) throws SQLException, InterruptedException {
        Company results = null;

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);

        List<?> rows = JDBCUtils.executeResults(QUERY_FIND_ONE, params);
        for (Object row : rows) {
            results = ResultsUtils.fromHashMapToCompany((HashMap<String, Object>) row);
            break;
        }
        return results;

//        if (rows.get(0) != null) {
//            return ResultsUtils.fromHashMapToCompany((HashMap<String, Object>) rows.get(0));
//        }
//        return null;
    }


}
