package com.cll.sample.frame.retrofit;

import android.support.annotation.NonNull;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by cll on 2018/3/2.
 */

public abstract class StarsApi {


    private  enum ScalarsConverter{
        SINGLE;

        private final ScalarsConverterFactory getFactory(){
            return ScalarsConverterFactory.create();
        }
    }

    protected enum GsonConverter{
        SINGLE;

        private final GsonConverterFactory getFactory(){
            return GsonConverterFactory.create();
        }
    }

    protected static ScalarsConverterFactory getScalarsConverterFactory(){
        return ScalarsConverter.SINGLE.getFactory();
    }

    protected static GsonConverterFactory getGsonConverterFactory(){
        return GsonConverter.SINGLE.getFactory();
    }

    @NonNull
    protected static String getBaseUrl(@NonNull final String url) {
        final int startIndex = url.indexOf("//");
        if (startIndex < 0) {
            return url;
        }
        final int endIndex = url.indexOf("/", startIndex + "//".length());
        if (endIndex < 0) {
            return url;
        }
        return url.substring(0, endIndex);
    }

    protected static OkHttpClient newClient(){
        return new OkHttpClient.Builder()
                .hostnameVerifier(newHostnameVerifier())
                .sslSocketFactory(newSSLSocketFactory(), newTrustManager())
                .pingInterval(60, TimeUnit.SECONDS)
                .build();
    }

    private static HostnameVerifier newHostnameVerifier(){
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    private static SSLSocketFactory newSSLSocketFactory(){
        try {
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{newTrustManager()}, null);
            return sslContext.getSocketFactory();
        } catch (Throwable t) {
            return (SSLSocketFactory) SSLSocketFactory.getDefault();
        }
    };

    private static X509TrustManager newTrustManager(){
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }
}
