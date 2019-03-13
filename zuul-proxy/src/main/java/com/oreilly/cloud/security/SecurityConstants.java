package com.oreilly.cloud.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/signUp";
    public static final String PUBLIC_FLIGHT_CATALOG_URL = "/flight-catalog/**";
    public static final String PUBLIC_HOTEL_CATALOG_URL = "/hotel-catalog/**";
    public static final String PRIVATE_FLIGHT_CATALOG_URL = "/flight-catalog/reservations/**";
    public static final String PRIVATE_HOTEL_CATALOG_URL = "/hotel-catalog/reservations/**";
}
