package com.cll.sample.frame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cll.sample.frame.retrofit.Demo_1;

public class MainActivity extends Activity {


    private Button demo_1;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initWidgets();
    }

    private void initWidgets(){
        demo_1 = findViewById(R.id.demo_1_button);

        initClickListener();
    }

    private void initClickListener(){
        demo_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Demo_1.class));
            }
        });
    }
}
