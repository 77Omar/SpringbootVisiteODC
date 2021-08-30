package org.sid.secservice.sec;

public class JWTUtilConst {
    public static final String SECRET= "mySecret1234";
    public static final String AUTH_HEADER= "Authorization";
    public static final String PREFIX= "Bearer ";
    public static final long EXPIRE_ACCESS_TOKEN= 24*3600*1000;
    public static final long EXPIRE_REFRESH_TOKEN= 15*60*1000;
}
