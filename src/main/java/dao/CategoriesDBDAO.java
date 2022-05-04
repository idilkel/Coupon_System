package dao;

import beans.Category;
import db.JDBCUtils;
import db.ResultsUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesDBDAO implements CategoriesDAO {
    private static final String QUERY_INSERT = "INSERT INTO `coupon_system`.`categories` (`NAME`) VALUES (?);";

    private static final String QUERY_FIND_ALL = "SELECT * FROM coupon_system.categories;";

    @Override
    public void addCategories(String name) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, name);

        JDBCUtils.execute(QUERY_INSERT, params);
    }

    @Override
    public List<Category> getAllCategories() throws SQLException, InterruptedException {
        List<Category> results = new ArrayList<>();
        List<?> rows = JDBCUtils.executeResults(QUERY_FIND_ALL);
        for (Object row : rows) {
            results.add(ResultsUtils.fromHashMapToCategory(((HashMap<String, Object>) row)));
        }
        return results;
    }
}
