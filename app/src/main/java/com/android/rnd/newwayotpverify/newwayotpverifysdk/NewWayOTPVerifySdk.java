package com.android.rnd.newwayotpverify.newwayotpverifysdk;

import android.content.Context;

import com.android.rnd.newwayotpverify.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewWayOTPVerifySdk {
    private final Retrofit retrofit;
    private Service service;

    private NewWayOTPVerifySdk(Retrofit retrofit) {
        this.retrofit = retrofit;
        createService();
    }

    /**
     * Builder for {@link NewWayOTPVerifySdk}
     */
    public static class Builder {
        public Builder() {
        }

        /**
         * Create the {@link NewWayOTPVerifySdk} to be used.
         *
         * @return {@link NewWayOTPVerifySdk}
         */
        public NewWayOTPVerifySdk build(Context context, boolean shouldUseMyJson) {
            Retrofit retrofit = null;
            String baseUrl = null;
            if (shouldUseMyJson){
                baseUrl = context.getResources().getString(R.string.base_url_myjson);
            }else {
                baseUrl = context.getResources().getString(R.string.base_url);
            }
            if (InterceptorHTTPClientCreator.getOkHttpClient() != null) {
                retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(InterceptorHTTPClientCreator.getOkHttpClient())
                        .baseUrl(baseUrl)
                        .build();

                return new NewWayOTPVerifySdk(retrofit);
            } else {
                retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
//                        .client(InterceptorHTTPClientCreator.getOkHttpClient())
                        .baseUrl(baseUrl)
                        .build();
            }
            return new NewWayOTPVerifySdk(retrofit);
        }
        public NewWayOTPVerifySdk build(Context context) {
            Retrofit retrofit = null;
            String baseUrl = null;
            baseUrl = context.getResources().getString(R.string.base_url);
            if (InterceptorHTTPClientCreator.getOkHttpClient() != null) {
                retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(InterceptorHTTPClientCreator.getOkHttpClient())
                        .baseUrl(baseUrl)
                        .build();

                return new NewWayOTPVerifySdk(retrofit);
            } else {
                retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
//                        .client(InterceptorHTTPClientCreator.getOkHttpClient())
                        .baseUrl(baseUrl)
                        .build();
            }
            return new NewWayOTPVerifySdk(retrofit);
        }
    }

    private void createService() {
        service = retrofit.create(Service.class);
    }

    public Service getService(){
        return service;
    }
}
