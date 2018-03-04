package com.cll.sample.frame.retrofit;

import android.util.Log;

import com.cll.sample.frame.async.RequestExecutor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by cll on 2018/3/2.
 */

public class RetrofitUtils extends StarsApi {

    /**
     *
     * Retrofit requires at minimum Java 7 or Android 2.3.
     * Retrofit是一款类型安全的网络框架，基于HTTP协议，服务于Android和java语言
     */


    public interface Api{

        //api declaration

        /**
         * request method
         *
         * http annotation
         *         :GET  POST  PUT  DELETE  HEAD
         * if you can also specify query parameters in the URL
         *         :@GET("users/list?sort=desc")
         * relative url
         *          dynamically
         *          @path  corresponding parameter  @Path("a") Stirng a
         *          @query  query parameter   @Query("b") String b
         *          @querymap  complex query parameter    @QueryMap Map(String, String) map
         *          @Body  http request body @Body User user
         *
         * form-encoded
         *          @FormUrlEncoded
         *          @Post()
         *          @Field
         *           Call<String> call(@Field("first_name") String first_name);
         * multipart
         *          @Multipart
         *          @PUT()
         *          @Part()
         *           Call<String> call(@Part("first_name") RequestBody first_name);
         * you can set static headers for a method using the @Headers annotation.
         *          @Headers()
         *          @GET()
         *          @GET("user")
         *          Call<User> getUser(@Header("Authorization") String authorization)
         *
         *
         */
        @GET
        Call<Response> call(@Url String url, @Header("Authorization") String authorization);


    }

    public interface DownloadApi{
        @Streaming  //big file
        @GET
        Call<ResponseBody> call(@Url String url);
    }

    private static String getUrl(){
        return "";
    }


    public class Response{
        private int code;
        private String message;
        private String status;
        private List<Result_1> results_1;
        private Result_2 result_2;


        public boolean isSuccessfully(){
            return status == "ok";
        }
        public class Result_1{

        }
        public class Result_2{

        }
    }


    /**
     * converters
     *
     * addConverterFactory(GsonConverterFactory.create())
     *
     * •Gson: com.squareup.retrofit2:converter-gson
     * •Jackson: com.squareup.retrofit2:converter-jackson
     * •Moshi: com.squareup.retrofit2:converter-moshi
     * •Protobuf: com.squareup.retrofit2:converter-protobuf
     * •Wire: com.squareup.retrofit2:converter-wire
     * •Simple XML: com.squareup.retrofit2:converter-simplexml
     * •Scalars (primitives, boxed, and String):com.squareup.retrofit2:converter-scalars
     *
     *
     */
    public static void call(){
        try {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getUrl())
                    .addConverterFactory(getScalarsConverterFactory())
                    .client(newClient())
                    .build();

            final Call<Response> call = retrofit.create(Api.class).call(getUrl(), "");
            final retrofit2.Response<Response> result = call.execute();
            if (!result.isSuccessful()){
                return;
            }
            final Response response = result.body();
            if (response.isSuccessfully()){
//                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadFileCall(final String url, final String savePath){
        try {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getBaseUrl(url))
                    .addConverterFactory(getScalarsConverterFactory())
                    .client(newClient())
                    .build();

            final Call<ResponseBody> call = retrofit.create(DownloadApi.class).call(url);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    Log.w("tag","download request successful" + " contentType = " + response.body().contentType());
                    response.body().contentType();
                    download(response, savePath);

                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.w("tag","download request failure");
                }
            });
        } catch (Throwable t) {
            Log.w("tag","download request error = " + t.getMessage());
    }
    }

    private static void download(final retrofit2.Response<ResponseBody> response, final String savePath){
        RequestExecutor.SINGLE.execute(new RequestExecutor.Callback() {
            @Override
            public void execute() {
                try {
                    if (createFile(savePath)) {
                        BufferedInputStream bis = null;
                        FileOutputStream fos = null;
                        byte[] buffer = new byte[1024];
                        File saveFile = new File(savePath);
                        bis = new BufferedInputStream(response.body().byteStream());

                        fos = new FileOutputStream(saveFile);

                        int len;
                        while ((len = bis.read(buffer)) != -1){
                            fos.write(buffer, 0, len);
                        }
                        Log.w("tag","download successful");
                    } else {

                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });
    }

    private static boolean createFile(final String savePath){
        File file = new File(savePath);
        if (file.exists()){
            if (file.isFile()){
                createFile(savePath + "_1");
            }
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }
}
