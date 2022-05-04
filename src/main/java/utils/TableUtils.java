package utils;

import beans.Company;
import beans.Coupon;
import beans.Customer;

import java.util.List;

public class TableUtils {
    public static void drawCoupons(List<Coupon> coupons) {
        System.out.println("|    ID  | CompanyId |  Category   |            Title             |          Description         | startDate  |   endDate  | Amount |  Price  | Image  |\n" +
                "------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Coupon coupon : coupons) {
            System.out.printf("|    %2d  |   %3d     |%-13s|%-30s|%-30s| %-10s | %-10s |  %-5d | %7.2f  |%-7s|\n",
                    coupon.getId(), coupon.getCompanyId(), coupon.getCategory(), coupon.getTitle(), coupon.getDescription(), DateUtils.beautifyDate(coupon.getStartDate()), DateUtils.beautifyDate(coupon.getEndDate()), coupon.getAmount(), coupon.getPrice(), coupon.getImage());
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void drawOneCoupons(Coupon coupon) {
        System.out.println("|    ID  | CompanyId |  Category   |            Title             |          Description         | startDate  |   endDate  | Amount |  Price  | Image  |\n" +
                "------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("|    %2d  |   %3d     |%-13s|%-30s|%-30s| %-10s | %-10s |  %-5d | %7.2f  |%-7s|\n",
                coupon.getId(), coupon.getCompanyId(), coupon.getCategory(), coupon.getTitle(), coupon.getDescription(), DateUtils.beautifyDate(coupon.getStartDate()), DateUtils.beautifyDate(coupon.getEndDate()), coupon.getAmount(), coupon.getPrice(), coupon.getImage());
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void drawCompanies(List<Company> companies) {
        System.out.println("|    ID  |           Name           |          Email         | Password | \n" +
                "-------------------------------------------------------------------------");
        for (Company company : companies) {
            System.out.printf("|    %2d  |   %-20s   |  %-20s  |  %-7s |\n",
                    company.getId(), company.getName(), company.getEmail(), company.getPassword());
        }

        System.out.println("-------------------------------------------------------------------------");
    }

    public static void drawOneCompany(Company company) {
        System.out.println("|    ID  |           Name           |          Email         | Password | \n" +
                "-------------------------------------------------------------------------");
        System.out.printf("|    %2d  |   %-20s   |  %-20s  |  %-7s |\n",
                company.getId(), company.getName(), company.getEmail(), company.getPassword());
        System.out.println("-------------------------------------------------------------------------");
    }

    public static void drawOneCompanyWithCoupons(Company company) {
        System.out.println("|    ID  |           Name           |          Email         | Password | Coupon Id List | \n" +
                "-----------------------------------------------------------------------------------------");
        System.out.printf("|    %2d  |   %-20s   |  %-20s  |  %-7s |",
                company.getId(), company.getName(), company.getEmail(), company.getPassword());
        for (Coupon coupon : company.getCoupons()) {
            System.out.printf(" %2d ", coupon.getId());
        }
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    public static void drawCustomers(List<Customer> customers) {
        System.out.println("|    ID  |      First Name      |         Last Name        |          Email         | Password | \n" +
                "------------------------------------------------------------------------------------------------");
        for (Customer customer : customers) {
            System.out.printf("|    %2d  |   %-18s |   %-20s   |  %-20s  |  %-7s |\n",
                    customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword());
        }

        System.out.println("------------------------------------------------------------------------------------------------");
    }

    public static void drawOneCustomer(Customer customer) {
        System.out.println("|    ID  |      First Name      |         Last Name        |          Email         | Password | \n" +
                "------------------------------------------------------------------------------------------------");
        System.out.printf("|    %2d  |   %-18s |   %-20s   |  %-20s  |  %-7s |\n",
                customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword());

        System.out.println("------------------------------------------------------------------------------------------------");
    }

    public static void drawOneCustomerWithCoupons(Customer customer) {
        System.out.println("|    ID  |      First Name      |         Last Name        |          Email         | Password | Coupon Id List | \n" +
                "----------------------------------------------------------------------------------------------------------------");
        System.out.printf("|    %2d  |   %-18s |   %-20s   |  %-20s  |  %-7s |",
                customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword());
        for (Coupon coupon : customer.getCoupons()) {
            System.out.printf(" %2d ", coupon.getId());
        }
        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------------------------------");
    }

    public static void drawCouponsForCustomer(List<Coupon> coupons) {
        System.out.println("|    ID  | CompanyId |  Category   |            Title             |          Description         | startDate  |   endDate  |  Price  | Image  |\n" +
                "------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Coupon coupon : coupons) {
            System.out.printf("|    %2d  |   %3d     |%-13s|%-30s|%-30s| %-10s | %-10s | %7.2f  |%-7s|\n",
                    coupon.getId(), coupon.getCompanyId(), coupon.getCategory(), coupon.getTitle(), coupon.getDescription(), DateUtils.beautifyDate(coupon.getStartDate()), DateUtils.beautifyDate(coupon.getEndDate()), coupon.getPrice(), coupon.getImage());
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}
