package beans;

import dao.CompaniesDAO;
import dao.CompaniesDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    private List<Coupon> coupons = new ArrayList<>();

    public Company() {
    }

    public Company(String name, String email, String password) throws SQLException, InterruptedException {
        CompaniesDAO companiesDAO = new CompaniesDBDAO();
        boolean exists = companiesDAO.isCompanyExistsByName(name);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Company(int id, String name, String email, String password) throws SQLException, InterruptedException {
        this(name, email, password);
        this.id = id;
    }

    public Company(Company company) {
        this.id = company.getId();
        this.name = company.name;
        this.email = company.email;
        this.password = company.password;
        this.coupons = company.coupons;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }

//    public void addCoupon(Coupon coupon) {
//        this.coupons.add(coupon);
//    }


}
