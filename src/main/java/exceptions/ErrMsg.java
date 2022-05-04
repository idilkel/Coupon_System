package exceptions;

public enum ErrMsg {
    START_TIME_AFTER_END_TIME("Start time after end time exception"),
    COMPANY_DETAILS_ALREADY_EXIST_EXCEPTION("Company name or email already exists exception"),
    COUPON_ALREADY_EXISTS_EXCEPTION("Coupon already exists exception"),
    COUPON_ALREADY_PURCHASED_EXCEPTION("Coupon already purchased exception"),
    CUSTOMER_ALREADY_EXISTS_EXCEPTION("Customer already exists exception"),
    EMAIL_ALREADY_EXISTS_EXCEPTION("Email already exists exception"),
    NO_COUPONS_LEFT_EXCEPTION("No coupons left exception"),
    COUPON_EXPIRED_EXCEPTION("Coupon expired exception"),
    CANT_UPDATE("Can't update id or name of company or id of customer exception"),
    CANT_UPDATE_ID("Can't update id exception"),
    COUPON_PURCHASE_EXCEPTION("Coupon purchase exception"),
    ID_DOES_NOT_EXIST_EXCEPTION("ID doesn't exist exception"),
    UNSUPPORTED_CLIENT_TYPE_EXCEPTION("Unsupported client type exception"),
    LOGIN_EXCEPTION("Wrong email or password or client type exception");

    private final String message;

    private ErrMsg(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
