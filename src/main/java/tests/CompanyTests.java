package tests;

import beans.Company;
import dao.CompaniesDAO;
import dao.CompaniesDBDAO;
import utils.ArtUtils;
import utils.TableUtils;

import java.sql.SQLException;

public class CompanyTests {

    public static void companyTests() {
        //Company tests
        System.out.println(ArtUtils.companiesTitle);

        CompaniesDAO companiesDAO = new CompaniesDBDAO();

        System.out.println("---------------- Add Companies ----------------- ");

        try {
            Company c1 = new Company("FOX", "fox@gmail.com", "12345");
            Company c2 = new Company("All Meat", "allmeat@gmail.com", "9876");
            Company c3 = new Company("Easy Jet", "easyjet@gmail.com", "6474");
            Company c4 = new Company("Apple", "apple@gmail.com", "3456");
            Company c5 = new Company("Cinema City", "cinemacity@gmail.com", "ty56");
            Company c6 = new Company("Casa del Pepe", "casa@gmail.com", "7834f");
            companiesDAO.addCompany(c1);
            companiesDAO.addCompany(c2);
            companiesDAO.addCompany(c3);
            companiesDAO.addCompany(c4);
            companiesDAO.addCompany(c5);
            companiesDAO.addCompany(c6);

            TableUtils.drawCompanies(companiesDAO.getAllCompanies());
            System.out.println("--------------- Is Company Exists --------------- ");
            System.out.println("email: easyjet@gmail.com, PW: 6474");
            System.out.println(companiesDAO.isCompanyExists("easyjet@gmail.com", "6474")); //true
            System.out.println("email: hello@gmail.com, PW: 6474");
            System.out.println(companiesDAO.isCompanyExists("hello@gmail.com", "6474")); //false
            System.out.println("----------- Company Updated ---------------- ");
            c1.setName("FOX HOME");
            c1.setEmail("newmail@gmail.com");
            c1.setPassword("vgy789");
            companiesDAO.updateCompany(1, c1);
            TableUtils.drawCompanies(companiesDAO.getAllCompanies());
            System.out.println("----------------- Delete Company --------------- ");
            companiesDAO.deleteCompany(2);
            TableUtils.drawCompanies(companiesDAO.getAllCompanies());
            System.out.println("--------------- Find Single Company --------------- ");
            TableUtils.drawOneCompany(companiesDAO.getOneCompany(3));
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }
    }
}
