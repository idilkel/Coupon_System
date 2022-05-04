package security;

import dao.CustomersDAO;
import dao.CustomersDBDAO;
import exceptions.CustomCouponSystemException;
import exceptions.ErrMsg;
import facades.*;

import java.sql.SQLException;

public class LoginManager {
    private static LoginManager instance = null;

    CustomersDAO customersDAO = new CustomersDBDAO();

    private LoginManager() {
    }

    //Lazily Loaded:
    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws SQLException, InterruptedException {
        try { //In-order to throw exception and return null if wrong mail or password
            switch (clientType.name()) {
                case "Administrator":
                    AdminFacade adminFacade = new AdminFacade();

                    if (adminFacade.login(email, password)) {//If wrong, exception thrown
                        return adminFacade;
                    }
                    break;
                case "Company":
                    CompanyFacade companyFacade = new CompanyFacade();
                    System.out.println(companyFacade.login(email, password));//To print true for the login tests & Exception thrown if login fails
                    companyFacade.loginIdCompanyFacade(email, password);//The customer facade with the id belonging to the mail is returned
                    return companyFacade;
                case "Customer":
                    CustomerFacade customerFacade = new CustomerFacade();
                    System.out.println(customerFacade.login(email, password));//To print true for the login tests & Exception thrown if login fails
                    customerFacade.loginIdCustomerFacade(email, password);//The customer facade with the id belonging to the mail is returned
                    return customerFacade;
                default:
                    throw new CustomCouponSystemException(ErrMsg.UNSUPPORTED_CLIENT_TYPE_EXCEPTION.getMessage());
            }
        } catch (CustomCouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }


}
