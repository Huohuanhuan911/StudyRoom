package com.studyroom.common;

public class Constants {

    public static final String STATUS_ACTIVE = "1";
    public static final String STATUS_INACTIVE = "0";

    public static final int RESERVATION_STATUS_PENDING = 1;
    public static final int RESERVATION_STATUS_CONFIRMED = 2;
    public static final int RESERVATION_STATUS_CANCELLED = 3;
    public static final int RESERVATION_STATUS_NO_SHOW = 4;
    public static final int RESERVATION_STATUS_COMPLETED = 5;

    public static final String ROLE_STUDENT = "ROLE_STUDENT";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static final int MAX_DAILY_RESERVATION_COUNT = 3;
    public static final int SIGN_IN_TIMEOUT_MINUTES = 30;
    public static final int MAX_NO_SHOW_COUNT = 5;
    public static final int BLACKLIST_DAYS = 7;

    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";

}