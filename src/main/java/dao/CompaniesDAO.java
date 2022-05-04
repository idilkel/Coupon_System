package dao;

import beans.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompaniesDAO {

    boolean isCompanyExists(String email, String password) throws SQLException, InterruptedException;

    //For exception to adding a coupon to a non-existing company
    boolean isCompanyExistsById(int companyId) throws SQLException, InterruptedException;

    //For exception to adding an existing company - not used
    boolean isCompanyExistsByName(String name) throws SQLException, InterruptedException;

    //For Admin Facade: can't add a company with existing name or mail
    boolean isCompanyExistsByNameOrByEmail(String name, String email) throws SQLException, InterruptedException;

    //For Company Facade login
    boolean isCompanyExistsByEmailAndByPassword(String email, String password) throws SQLException, InterruptedException;

    //For Company Facade login
    //For initialization of a Company Facade with an existing customer id
    int companyIdByEmail(String email) throws SQLException, InterruptedException;

    void addCompany(Company company) throws SQLException, InterruptedException;

    void updateCompany(int companyId, Company company) throws SQLException, InterruptedException;

    void deleteCompany(int companyId) throws SQLException, InterruptedException;

    List<Company> getAllCompanies() throws SQLException, InterruptedException;

    Company getOneCompany(int companyId) throws SQLException, InterruptedException;

}
