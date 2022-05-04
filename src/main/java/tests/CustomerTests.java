package tests;

import beans.Customer;
import dao.CustomersDAO;
import dao.CustomersDBDAO;
import utils.ArtUtils;
import utils.TableUtils;

import java.sql.SQLException;

public class CustomerTests {
    public static void customerTests() {
        //Customer tests
        CustomersDAO customersDAO = new CustomersDBDAO();

        System.out.println(ArtUtils.customersTitle);

        System.out.println("--------------- Add Customers --------------- ");
        Customer cs1 = new Customer("Moshe", "Cohen", "moshe@gmail.com", "12345");
        Customer cs2 = new Customer("Yoni", "Katz", "yoni@gmail.com", "gh4567");
        Customer cs3 = new Customer("Ella", "Levi", "ella@gmail.com", "#123H");
        Customer cs4 = new Customer("Shoshi", "Goldblum", "shoshi@gmail.com", "6745");
        Customer cs5 = new Customer("Riki", "Shulman", "riki@gmail.com", "vgy147");
        Customer cs6 = new Customer("David", "Herz", "david@gmail.com", "wi34!@");
        try {
            customersDAO.addCustomer(cs1);
            customersDAO.addCustomer(cs2);
            customersDAO.addCustomer(cs3);
            customersDAO.addCustomer(cs4);
            customersDAO.addCustomer(cs5);
            customersDAO.addCustomer(cs6);

            TableUtils.drawCustomers(customersDAO.getAllCustomers());
            System.out.println("--------------- Is Customer Exists --------------- ");
            System.out.println("email: yoni@gmail.com, PW: gh4567");
            System.out.println(customersDAO.isCustomerExists("yoni@gmail.com", "gh4567")); //true
            System.out.println("email: hello@gmail.com, PW: gh4567");
            System.out.println(customersDAO.isCustomerExists("hello@gmail.com", "gh4567")); //false
            System.out.println("--------------- Customer Updated --------------- ");
            cs1.setFirstName("Shlomo");
            customersDAO.updateCustomer(1, cs1);
            TableUtils.drawCustomers(customersDAO.getAllCustomers());
            System.out.println("--------------- Delete Customer --------------- ");
            customersDAO.deleteCustomer(2);
            TableUtils.drawCustomers(customersDAO.getAllCustomers());
            System.out.println("--------------- Find Single Customer --------------- ");
            TableUtils.drawOneCustomer(customersDAO.getOneCustomer(3));
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
