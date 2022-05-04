package jobs;

import beans.Coupon;
import dao.CouponsDAO;
import exceptions.CustomCouponSystemException;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class CouponExpirationDailyJob implements Runnable {
    private CouponsDAO couponsDAO;
    private boolean quit = false;

    public static final int SLEEP_TIME = 1000;


    public CouponExpirationDailyJob(CouponsDAO couponsDAO) throws SQLException, InterruptedException, CustomCouponSystemException {
        System.out.println("^^^^^^^^^^^CouponExpirationDailyJob Started^^^^^^^^^^^^^^");
        //couponsDAO.getAllCoupons().forEach(System.out::println);
        this.couponsDAO = couponsDAO;
    }

    @Override
    public void run() {
        while (!quit) {
            try {
                //couponsDAO.getAllCoupons().forEach(System.out::println);//test to see if has coupons and running
                if (LocalTime.now() == LocalTime.MIDNIGHT) { //to check put 1st // on this line and 2nd // on line 40

                    List<Coupon> expiredCoupons = couponsDAO.getAllCouponsBefore();
                    for (Coupon coupon : expiredCoupons) {
                        if (coupon != null) {
                            couponsDAO.deleteCouponPurchaseByCouponId(coupon.getId());
                            couponsDAO.deleteCoupon(coupon.getId());//if 1st delete coupon, foreign key problem
                            System.out.println("^^^^^CouponId " + coupon.getId() + " was expired and deleted^^^^^");
                        }
                    }
                } //to check put 2nd // on this line and 1st // on line 30
            } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
                e.printStackTrace();
            }
            try {

                Thread.sleep(SLEEP_TIME);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void stopRun() {
        this.quit = true;
    }


}
