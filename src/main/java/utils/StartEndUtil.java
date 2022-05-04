package utils;

import dao.CouponsDAO;
import dao.CouponsDBDAO;
import db.ConnectionPool;
import db.JDBCUtils;
import exceptions.CustomCouponSystemException;
import jobs.CouponExpirationDailyJob;

import java.sql.SQLException;
import java.util.Scanner;

public class StartEndUtil {

    static CouponExpirationDailyJob job1 = null;
    static Thread t1 = null;

    public static void start() {
        System.out.println("Start");

        //Init Database
        try {
            JDBCUtils.databaseStrategy();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

        CouponsDAO couponsDAO = new CouponsDBDAO();

        try {
            job1 = new CouponExpirationDailyJob(couponsDAO);
            t1 = new Thread(job1);
            t1.start();
        } catch (SQLException | InterruptedException | CustomCouponSystemException e) {
            e.printStackTrace();
        }
    }

    public static void end() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To exit the program enter 0");
        int exit = scanner.nextInt();
        if (exit == 0) {
            job1.stopRun();
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            ConnectionPool.getInstance().closeAllConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("End");
    }

}
