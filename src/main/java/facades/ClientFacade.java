package facades;

import dao.*;
import exceptions.CustomCouponSystemException;

import java.sql.SQLException;

public abstract class ClientFacade {
    protected CompaniesDAO companiesDAO = new CompaniesDBDAO();
    protected CustomersDAO customersDAO = new CustomersDBDAO();
    protected CouponsDAO couponsDAO = new CouponsDBDAO();

    public ClientFacade() {
    }


    public abstract boolean login(String email, String password) throws SQLException, InterruptedException, CustomCouponSystemException;
}
