package dao;

import beans.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomersDAO {

    boolean isCustomerExists(String email, String password) throws SQLException, InterruptedException;

    //For Admin Facade - can't add a customer with the same email
    boolean isCustomerExists(String email) throws SQLException, InterruptedException;

    //For exception to removing a coupon from a non-existing company
    boolean isCustomerExistsById(int customerId) throws SQLException, InterruptedException;

    //For Customer Facade login
    boolean isCustomerExistsByEmailAndByPassword(String email, String password) throws SQLException, InterruptedException;

    //For Customer Facade login
    //For initialization of a CustomerFacade with an existing customer id
    int customerIdByEmail(String email) throws SQLException, InterruptedException;

    void addCustomer(Customer customer) throws SQLException, InterruptedException;

    void updateCustomer(int customerId, Customer customer) throws SQLException, InterruptedException;

    void deleteCustomer(int customerId) throws SQLException, InterruptedException;

    List<Customer> getAllCustomers() throws SQLException, InterruptedException;

    Customer getOneCustomer(int customerId) throws SQLException, InterruptedException;

}
