package db;

import beans.Category;
import dao.CategoriesDAO;
import dao.CategoriesDBDAO;

import java.sql.*;
import java.util.Date;
import java.util.*;

public class JDBCUtils {
    public static final String URL = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=TRUE&useTimezone=TRUE&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD = "1234";

    private static final String QUERY_CREATE_SCHEMA = "create schema Coupon_System";
    private static final String QUERY_DROP_SCHEMA = "drop schema Coupon_System";
    private static final String QUERY_CREATE_TABLE_COMPANIES = "CREATE TABLE `coupon_system`.`companies` (\n" +
            "  `ID` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `NAME` VARCHAR(45) NOT NULL,\n" +
            "  `EMAIL` VARCHAR(30) NOT NULL,\n" +
            "  `PASSWORD` VARCHAR(30) NOT NULL,\n" +
            "  PRIMARY KEY (`ID`));\n";

    private static final String QUERY_CREATE_TABLE_CUSTOMERS = "CREATE TABLE `coupon_system`.`customers` (\n" +
            "  `ID` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `FIRST_NAME` VARCHAR(45) NOT NULL,\n" +
            "  `LAST_NAME` VARCHAR(45) NOT NULL,\n" +
            "  `EMAIL` VARCHAR(30) NOT NULL,\n" +
            "  `PASSWORD` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`ID`));";

    private static final String QUERY_CREATE_TABLE_CATEGORIES = "CREATE TABLE `coupon_system`.`categories` (  \n" +
            "  `ID` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `NAME` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`ID`));";

    private static final String QUERY_CREATE_TABLE_COUPONS = "CREATE TABLE `coupon_system`.`coupons` (\n" +
            "  `ID` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `COMPANY_ID` INT NOT NULL,\n" +
            "  `CATEGORY_ID` INT NOT NULL,\n" +
            "  `TITLE` VARCHAR(45) NOT NULL,\n" +
            "  `DESCRIPTION` VARCHAR(120) NOT NULL,\n" +
            "  `START_DATE` DATE NOT NULL,\n" +
            "  `END_DATE` DATE NOT NULL,\n" +
            "  `AMOUNT` INT NOT NULL,\n" +
            "  `PRICE` DOUBLE NOT NULL,\n" +
            "  `IMAGE` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`ID`),\n" +
            "  INDEX `COMPANY_ID_idx` (`COMPANY_ID` ASC) VISIBLE,\n" +
            "  INDEX `CATEGORY_ID_idx` (`CATEGORY_ID` ASC) VISIBLE,\n" +
            "  CONSTRAINT `COMPANY_ID`\n" +
            "    FOREIGN KEY (`COMPANY_ID`)\n" +
            "    REFERENCES `coupon_system`.`companies` (`ID`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `CATEGORY_ID`\n" +
            "    FOREIGN KEY (`CATEGORY_ID`)\n" +
            "    REFERENCES `coupon_system`.`categories` (`ID`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);\n";


    private static final String QUERY_CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE `coupon_system`.`customers_vs_coupons` (\n" +
            "  `CUSTOMER_ID` INT NOT NULL,\n" +
            "  `COUPON_ID` INT NOT NULL,\n" +
            "  PRIMARY KEY (`CUSTOMER_ID`, `COUPON_ID`),\n" +
            "  INDEX `COUPON_ID_idx` (`COUPON_ID` ASC) VISIBLE,\n" +
            "  CONSTRAINT `CUSTOMER_ID`\n" +
            "    FOREIGN KEY (`CUSTOMER_ID`)\n" +
            "    REFERENCES `coupon_system`.`customers` (`ID`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `COUPON_ID`\n" +
            "    FOREIGN KEY (`COUPON_ID`)\n" +
            "    REFERENCES `coupon_system`.`coupons` (`ID`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);\n";

    // not relevant for us, since we're using MySQL Driver Type 4
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void databaseStrategy() throws SQLException, InterruptedException {
        CategoriesDAO categoriesDAO = new CategoriesDBDAO();
        try {
            execute(QUERY_DROP_SCHEMA);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        execute(QUERY_CREATE_SCHEMA);
        execute(QUERY_CREATE_TABLE_COMPANIES);
        execute(QUERY_CREATE_TABLE_CUSTOMERS);
        execute(QUERY_CREATE_TABLE_CATEGORIES);
        execute(QUERY_CREATE_TABLE_COUPONS);
        execute(QUERY_CREATE_TABLE_CUSTOMERS_VS_COUPONS);

        for (Category category : Category.values()) {
            String name = category.name();
            categoriesDAO.addCategories(name);
        }

    }


    public static Connection getConnection() throws SQLException, InterruptedException {
        return ConnectionPool.getInstance().getConnection();
    }

    public static void closeConnection(Connection conn) throws SQLException {
        ConnectionPool.getInstance().returnConnection(conn);
    }

    public static void closePreparedStatement(PreparedStatement statement) throws SQLException {
        statement.close();
    }

    public static void closeResultSet(ResultSet resultSet) throws SQLException {
        resultSet.close();
    }

    public static void closeResources(Connection conn, PreparedStatement statement) throws SQLException {
        closePreparedStatement(statement);
        closeConnection(conn);
    }

    public static void closeResources(Connection conn, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        closeResultSet(resultSet);
        closeResources(conn, statement);
    }


    public static void execute(String sql) throws SQLException, InterruptedException {

        //Step 2 - Open Connection
        Connection conn = getConnection();

        //Step 3 - Prepared Statement
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();

        //Step 5 - Close Resources
        closeResources(conn, statement);
    }


    public static List<?> executeResults(String sql) throws SQLException, InterruptedException {

        //Step 2 - Open Connection
        Connection conn = getConnection();

        //Step 3 - Prepared Statement
        PreparedStatement statement = conn.prepareStatement(sql);

        //Step 4 - ResultSet
        ResultSet resultSet = statement.executeQuery();
        List<?> rows = resultSetToArrayList(resultSet);

        //Step 5 - Close Resources
        closeResources(conn, statement, resultSet);

        return rows;
    }

    public static List<?> executeResults(String sql, Map<Integer, Object> map) throws SQLException, InterruptedException {

        //Step 2 - Open Connection
        Connection conn = getConnection();

        //Step 3 - Prepared Statement
        PreparedStatement statement = conn.prepareStatement(sql);
        addParams(statement, map);

        //Step 4 - ResultSet
        ResultSet resultSet = statement.executeQuery();
        List<?> rows = resultSetToArrayList(resultSet);

        //Step 5 - Close Resources
        closeResources(conn, statement, resultSet);

        return rows;
    }

    public static void execute(String sql, Map<Integer, Object> map) throws SQLException, InterruptedException {

        //Step 2 - Open Connection
        Connection conn = getConnection();

        //Step 3 - Prepared Statement
        PreparedStatement statement = conn.prepareStatement(sql);
        addParams(statement, map);

        statement.execute();

        //Step 5 - Close Resources
        closeResources(conn, statement);
    }


    public static void addParams(PreparedStatement statement, Map<Integer, Object> map) throws SQLException {
        for (Map.Entry<Integer, Object> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Object obj = entry.getValue();
            if (obj instanceof Integer) {
                statement.setInt(key, (int) obj);
            } else if (obj instanceof String) {
                statement.setString(key, (String) obj);
            } else if (obj instanceof Double) {
                statement.setDouble(key, (double) obj);
            } else if (obj instanceof Float) {
                statement.setDouble(key, (Float) obj);
            } else if (obj instanceof Date) {
                statement.setDate(key, (java.sql.Date) obj);
            }
        }
    }


    public static List<?> resultSetToArrayList(ResultSet rs) throws SQLException {

        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();

        List<HashMap<String, Object>> list = new ArrayList<>();

        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }


}
