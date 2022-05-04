package tests;

import dao.CategoriesDAO;
import dao.CategoriesDBDAO;
import utils.ArtUtils;

import java.sql.SQLException;

public class CategoryTests {
    public static void categoryTests() {
        System.out.println(ArtUtils.categoriesTitle);

        CategoriesDAO categoriesDAO = new CategoriesDBDAO();

        System.out.println("---------------- Categories --------------------- ");
        try {
            categoriesDAO.getAllCategories().forEach(System.out::println);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------------------------------------------------- ");
    }
}
