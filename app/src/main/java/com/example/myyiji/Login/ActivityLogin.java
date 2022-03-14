package com.example.myyiji.Login;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myyiji.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class ActivityLogin extends Activity {
    private Context context;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginbutton = findViewById(R.id.button_login);
        context = this;
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userid = findViewById(R.id.input_email);
                EditText password = findViewById(R.id.input_password);
                if(userid.getText().toString().equals("2622504692@qq.com")){
                    if(password.getText().toString().equals("123456")){
                        Intent intent = new Intent();
                        intent.putExtra("WYJ ","已登录");
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else{
                        Toast t = Toast.makeText(context,"密码错误，请重新输入", Toast.LENGTH_LONG);
                        t.show();
                    }
                }
                else{
                    Toast t = Toast.makeText(context,"用户名不存在!", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }

}
