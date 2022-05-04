package tests;

import beans.Company;
import beans.Customer;
import dao.CompaniesDAO;
import dao.CompaniesDBDAO;
import dao.CustomersDAO;
import dao.CustomersDBDAO;
import exceptions.CustomCouponSystemException;
import facades.AdminFacade;
import facades.ClientType;
import security.LoginManager;
import utils.ArtUtils;
import utils.TableUtils;

import java.sql.SQLException;

public class AdminFacadeTests {

    public static void adminFacadeTests() {
        //Admin Facade tests tests
        CompaniesDAO companiesDAO = new CompaniesDBDAO();
        CustomersDAO customersDAO = new CustomersDBDAO();

        System.out.println(ArtUtils.adminFacadeTitle);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~Admin Facade Tests~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("----------------------------AdminFacade Login tests-------------------------");

        AdminFacade adminFacade = new AdminFacade();
        System.out.println("AdminFacade login: (should be true)");
        try {
            System.out.println(adminFacade.login("admin@admin.com", "admin"));
        } catch (CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
        System.out.println("AdminFacade login: (should be false - wrong email)");
        try {
            System.out.println(adminFacade.login("sheker@gmail.com", "admin"));
        } catch (CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("AdminFacade login: (should be false - wrong password)");
        try {
            System.out.println(adminFacade.login("admin@admin.com", "wrong"));
        } catch (CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-------------Login manager tests to Administrator-------------");
        LoginManager loginManager = LoginManager.getInstance();
        System.out.println("1) All parameters are right:");
        try {
            loginManager.login("admin@admin.com", "admin", ClientType.Administrator);
            System.out.println("Successfull login manager");
        } catch (SQLException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("2) Wrong Client type test:");
        try {
            loginManager.login("admin@admin.com", "admin", ClientType.Customer);
        } catch (SQLException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("3) Wrong email test:");
        try {
            loginManager.login("wrong@admin.com", "admin", ClientType.Administrator);
        } catch (SQLException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("4) Wrong password test:");
        try {
            loginManager.login("admin@admin.com", "wrong", ClientType.Administrator);
        } catch (SQLException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------------------");

        //Company related tests
        System.out.println(ArtUtils.companiesTitle);
        System.out.println("-------------Adding Companies by Admin Facade-------------");
        try {
            adminFacade.addCompany(new Company("FOX", "fox@gmail.com", "12345"));
            adminFacade.addCompany(new Company("All Meat", "allmeat@gmail.com", "9876"));
            adminFacade.addCompany(new Company("Easy Jet", "easyjet@gmail.com", "6474"));
            adminFacade.addCompany(new Company("Apple", "apple@gmail.com", "3456"));
            adminFacade.addCompany(new Company("Cinema City", "cinemacity@gmail.com", "ty56"));
            adminFacade.addCompany(new Company("Casa del Pepe", "casa@gmail.com", "7834f"));
            adminFacade.addCompany(new Company("Elit", "elit@gmail.com", "1234"));
            TableUtils.drawCompanies(companiesDAO.getAllCompanies());
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }

        System.out.println("---Trying to add a company with an existing name----");
        try {
            adminFacade.addCompany(new Company("FOX", "mail@gmail.com", "ghjse"));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("---Trying to add a company with an existing email----");
        try {
            adminFacade.addCompany(new Company("Z-MART", "fox@gmail.com", "769se"));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-------------Updating Company #5 details--------------");
        try {
            Company company5 = adminFacade.getOneCompany(5);
            company5.setEmail("cc@gmail.com");
            company5.setPassword("cc123");
            //Company company5 = new Company(5, "Cinema City", "cc@gmail.com", "cc123");
            adminFacade.updateCompany(5, company5);
            TableUtils.drawCompanies(companiesDAO.getAllCompanies());
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }

        System.out.println("-------------Trying to update the company id--------------");
        try {
            Company company5 = new Company(4, "Cinema City", "cinemacity@gmail.com", "ty56");
            adminFacade.updateCompany(5, company5);
            TableUtils.drawCompanies(companiesDAO.getAllCompanies());
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }

        System.out.println("-------------Trying to update the company name--------------");
        try {
            Company company5 = new Company(5, "Cinema World", "cinemacity@gmail.com", "ty56");
            adminFacade.updateCompany(5, company5);
            TableUtils.drawCompanies(companiesDAO.getAllCompanies());
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }

        System.out.println("-------------Getting all companies-------------");
        try {
            TableUtils.drawCompanies(adminFacade.getAllCompanies());
            System.out.println("-------------Getting one company: 4-------------");
            TableUtils.drawOneCompany(adminFacade.getOneCompany(4));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }

        System.out.println("Getting unexisting company (#20)");
        try {
            TableUtils.drawOneCompany(adminFacade.getOneCompany(20));
        } catch (SQLException | CustomCouponSystemException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }

        //Customer related tests
        System.out.println(ArtUtils.customersTitle);
        System.out.println("-------------Adding Customers by Admin Facade-------------");
        try {
            adminFacade.addCustomer(new Customer("Moshe", "Cohen", "moshe@gmail.com", "12345"));
            adminFacade.addCustomer(new Customer("Yoni", "Katz", "yoni@gmail.com", "gh4567"));
            adminFacade.addCustomer(new Customer("Ella", "Levi", "ella@gmail.com", "#123H"));
            adminFacade.addCustomer(new Customer("Shoshi", "Goldblum", "shoshi@gmail.com", "6745"));
            adminFacade.addCustomer(new Customer("Riki", "Shulman", "riki@gmail.com", "vgy147"));
            adminFacade.addCustomer(new Customer("David", "Herz", "david@gmail.com", "wi34!@"));
            adminFacade.addCustomer(new Customer("David", "Herz", "other@gmail.com", "wi34!@"));//same details excep email
            TableUtils.drawCustomers(customersDAO.getAllCustomers());
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-------------Trying to add a customer with an existing email-------------");
        try {
            adminFacade.addCustomer(new Customer("Shula", "Chen", "yoni@gmail.com", "hiafjb4"));
        } catch (SQLException | CustomCouponSystemException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-------------Trying to add a customer with an existing name and password but a different mail-------------");
        try {
            adminFacade.addCustomer(new Customer("Moshe", "Cohen", "mc@gmail.com", "12345"));
            TableUtils.drawCustomers(customersDAO.getAllCustomers());
        } catch (SQLException | CustomCouponSystemException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-------------Updating customer details-------------");
        try {
            Customer updatedCustomer = customersDAO.getOneCustomer(7);
            System.out.println("Before update:");
            TableUtils.drawOneCustomer(updatedCustomer);
            updatedCustomer.setFirstName("Dudi");
            updatedCustomer.setLastName("Hertzy");
            updatedCustomer.setEmail("newmail@gmail.com");
            updatedCustomer.setPassword("1234!@#");
            adminFacade.updateCustomer(7, updatedCustomer);
            System.out.println("After update:");
            TableUtils.drawOneCustomer(updatedCustomer);
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-------------Trying to update customer id-------------");
        //creating new customer has new customer id. Trying to update customer#5 there
        //No customerId setter in the system
        Customer updatedCustomer = new Customer("Riki", "Shulman", "riki@gmail.com", "vgy147");
        try {
            adminFacade.updateCustomer(5, updatedCustomer);
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("-------------Getting all customers-------------");
        try {
            TableUtils.drawCustomers(adminFacade.getAllCustomers());
            System.out.println("-------------Getting one customer: 4-------------");
            TableUtils.drawOneCustomer(adminFacade.getOneCustomer(4));
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }

        System.out.println("Getting unexisting customer (#20)");
        try {
            TableUtils.drawOneCustomer(adminFacade.getOneCustomer(20));
        } catch (SQLException | CustomCouponSystemException | InterruptedException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }

    }
}
