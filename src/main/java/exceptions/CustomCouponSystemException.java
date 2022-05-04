package exceptions;

import utils.DateUtils;

import java.time.LocalDateTime;

public class CustomCouponSystemException extends Exception {

    public CustomCouponSystemException(String message) {
        super("{" + message + "} has occurred on " + DateUtils.format(LocalDateTime.now()));
    }


}
