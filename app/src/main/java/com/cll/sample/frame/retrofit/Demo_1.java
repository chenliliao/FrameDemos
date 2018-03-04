package com.cll.sample.frame.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

import com.cll.sample.frame.R;
import com.cll.sample.frame.async.RequestExecutor;

import java.io.File;

/**
 * Created by cll on 2018/3/4.
 */

public class Demo_1 extends Activity {


//    private final static String download_url = "http://wap.sogou.com/app/redir.jsp?dt=1&appdown=1&u=0Gd8piB6091kOtbAhntD_tgwzTwbQmCtzD0uA8MR4LfZGb7Qywh5rAIvm8kCpFczl1AKy02qcV-Q9HdJulsCEiMYno67jjer_qi9ZoYPeU3c0LLlYVpP8NIoPti-_vcRCoFutw0b2ST9n2EW8SC3pIZ0qnVqJlkXkqb6adUqkRX4gaIhMrqjeUztMxpRm3JmWtFLUzNiIjUt11EdkKO1gMuqxplQrsbk&docid=9190009583366084496&sourceid=7780666504990664623&w=1906&stamp=20180120";
    private final static String download_url = "http://wap.sogou.com";
    private final static String download_save_path = Environment.getExternalStorageDirectory() + File.separator + "retrofit_down";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit_layout);
        findViewById(R.id.button_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute();
            }
        });
    }

    private void execute(){
        RequestExecutor.SINGLE.execute(new RequestExecutor.Callback() {
            @Override
            public void execute() {
                RetrofitUtils.downloadFileCall(download_url, download_save_path);
            }
        });
    }
}
