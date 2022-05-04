package dao;

import beans.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoriesDAO {
    void addCategories(String name) throws SQLException, InterruptedException;

    List<Category> getAllCategories() throws SQLException, InterruptedException;
}
