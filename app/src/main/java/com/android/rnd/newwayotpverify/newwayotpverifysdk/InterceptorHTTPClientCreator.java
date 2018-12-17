package com.android.rnd.newwayotpverify.newwayotpverifysdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import com.android.rnd.newwayotpverify.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by Admin on 08-11-2017.
 */

public class InterceptorHTTPClientCreator {

    private static OkHttpClient defaultHttpClient;
    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "EVE_PREF_UNIQUE_ID";
    private static String companyId;
    private static String auth;
    private String authorization;
    private static HashMap<String, String> userDetails;

    private synchronized static String getUniqueId(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }

    public static void createInterceptorHTTPClient(final Context context){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            defaultHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {

//                                    String currentRequestTimeString = String.valueOf(System.currentTimeMillis());
//                                    currentRequestTimeString = currentRequestTimeString.substring(5, currentRequestTimeString.length());
//                                    long currentRequestTime = Long.parseLong(currentRequestTimeString);
//
//                                    String deviceIdPhysical = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//                                    String pKey = SecurityUtil.generatePKey(deviceIdPhysical, currentRequestTime);
//                                    userDetails = UserSessionManager.getInstance(context).getSessionDetails();
//                                    auth = (userDetails.get(UserSessionManager.KEY_JWT) != null) ? userDetails.get(UserSessionManager.KEY_JWT) : "";
//                                    String decodedAuth = SecurityUtil.base64Decode(auth);
//                                    try {
//                                        JSONObject jsonObject = new JSONObject(decodedAuth);
//                                        companyId = jsonObject.getString("companyId") == null ? "" : jsonObject.getString("companyId");
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                        companyId = "";
//                                    }
//                                    String deviceId = SecurityUtil.base64Encode("EVE") + ":" +
//                                            deviceIdPhysical + ":" +
//                                            getUniqueId(context) + ":" +
//                                            companyId;
//                                    String token = SecurityUtil.generateRandomString() + SecurityUtil.md5Encode(BuildConfig.APPLICATION_ID) + SecurityUtil.generateRandomString();
                                    Request request = chain.request().newBuilder()
//                                            .addHeader("Device-Id", deviceId)
//                                            .addHeader("Token", token)
//                                            .addHeader("App-Time", String.valueOf(currentRequestTime))
//                                            .addHeader("P-Key", pKey)
//                                            .addHeader("Fcm-Id", FirebaseInstanceId.getInstance().getToken() != null ? FirebaseInstanceId.getInstance().getToken() : "")
//                                            .addHeader("Device-Type","android")

//                                            .addHeader("Auth",auth)
//                                            .addHeader("User-Credential",SecurityUtil.base64Encode("suman.samanta@fingertipmail"+SecurityUtil.md5Encode("1234"))+"ABC")
                                            .build();
//                                    Log.i("RETROFIT_OREO", "Request: method ==> " + request.method());
//                                    Log.i("RETROFIT_OREO", "Request: url ==> " + request.url().toString());
//                                    Log.i("RETROFIT_OREO", "Request: headers ==> " + request.headers().toString());
//                                    if (request.body() != null) {
//                                        Log.i("RETROFIT_OREO", "Request: body length ==> " + request.body().contentLength());
//                                        Log.i("RETROFIT_OREO", "Request: body ==> " + request.body().toString());
//                                    }
//                                    Log.i("RETROFIT_OREO", "Request: ==> end");
                                    return chain.proceed(request);
                                }
                            })
                    .readTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(interceptor)
                    .build();
    }

    public static void createInterceptorHTTPClient(final Context context, boolean disableInterceptor){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        defaultHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {

                                String currentRequestTimeString = String.valueOf(System.currentTimeMillis());
                                currentRequestTimeString = currentRequestTimeString.substring(5, currentRequestTimeString.length());
                                long currentRequestTime = Long.parseLong(currentRequestTimeString);

                                String deviceIdPhysical = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//                                String pKey = SecurityUtil.generatePKey(deviceIdPhysical, currentRequestTime);
//                                userDetails = UserSessionManager.getInstance(context).getSessionDetails();
//                                companyId = (userDetails.get(UserSessionManager.KEY_COMPANYID) != null) ? userDetails.get(UserSessionManager.KEY_COMPANYID) : "";
//
//                                String deviceId = SecurityUtil.base64Encode("EVE") + ":" +
//                                        deviceIdPhysical + ":" +
//                                        getUniqueId(context) + ":" +
//                                        companyId;
//                                String token = SecurityUtil.generateRandomString() + SecurityUtil.md5Encode(BuildConfig.APPLICATION_ID) + SecurityUtil.generateRandomString();
                                Request request = chain.request().newBuilder()
//                                        .addHeader("Device-Id", deviceId)
//                                        .addHeader("Token", token)
//                                        .addHeader("App-Time", String.valueOf(currentRequestTime))
//                                        .addHeader("P-Key", pKey)
//                                        .addHeader("Fcm-Id", FirebaseInstanceId.getInstance().getToken() != null ? FirebaseInstanceId.getInstance().getToken() : "")
                                        .addHeader("Device-Type","android")
//                                            .addHeader("User-Credential",SecurityUtil.base64Encode("suman.samanta@fingertipmail"+SecurityUtil.md5Encode("1234"))+"ABC")
                                        .build();
//                                    Log.i("RETROFIT_OREO", "Request: method ==> " + request.method());
//                                    Log.i("RETROFIT_OREO", "Request: url ==> " + request.url().toString());
//                                    Log.i("RETROFIT_OREO", "Request: headers ==> " + request.headers().toString());
//                                    if (request.body() != null) {
//                                        Log.i("RETROFIT_OREO", "Request: body length ==> " + request.body().contentLength());
//                                        Log.i("RETROFIT_OREO", "Request: body ==> " + request.body().toString());
//                                    }
//                                    Log.i("RETROFIT_OREO", "Request: ==> end");
                                return chain.proceed(request);
                            }
                        })
                .readTimeout(5, TimeUnit.MINUTES)
//                .addInterceptor(interceptor)
                .build();


    }





    public static boolean isHttpClientNull(){
        return defaultHttpClient == null;
    }

    static OkHttpClient getOkHttpClient(){
        if (defaultHttpClient != null){
            return defaultHttpClient;
        }
        return null;
    }
    public static void clearOkHttpClient(){
        defaultHttpClient = null;
    }

}
