package com.example.myshop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myshop.R;

public class LoginActivity extends Activity implements View.OnClickListener {

    private ImageView login_back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_back_img = (ImageView) findViewById(R.id.login_back_img);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_back_img:
                this.finish();
                break;
        }
    }
}
