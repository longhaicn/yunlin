package hnu.fooma.yunlin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import hnu.fooma.yunlin.R;

/**
 * Created by Fooma on 2016/4/22.
 */
public class FindPasswordActivity extends Activity {EditText find_phone, find_code;
    TextView tv_find_code,tv_second_top_title;
    Button find_next;
    ImageView iv_top_return;
    private String authCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        initview();
        tv_second_top_title.setText("重置密码");
        find_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = find_phone.getText().toString();
                String b = find_code.getText().toString();
                String c = tv_find_code.getText().toString();
                if (a.length() == 1 && c.equals(b)) {
                    Intent intent = new Intent(FindPasswordActivity.this, FindResetPassword.class);
                    startActivity(intent);
                    FindPasswordActivity.this.finish();
                } else {
                    Toast.makeText(FindPasswordActivity.this, "请输入正确的手机号码或密码", Toast.LENGTH_LONG).show();
                }
            }
        });
        tv_find_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_find_code.setText("");
                makeAuthCode();
                tv_find_code.setText(authCode+"");
            }
        });
        iv_top_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initview() {
        find_phone = (EditText) findViewById(R.id.et_find_phone);
        find_code = (EditText) findViewById(R.id.ed_find_code);
        find_next = (Button) findViewById(R.id.bt_find_next);
        tv_find_code= (TextView) findViewById(R.id.tv_find_code);
        iv_top_return= (ImageView) findViewById(R.id.iv_top_return);
        tv_second_top_title= (TextView) findViewById(R.id.tv_second_top_title);
    }

    public void makeAuthCode() {
        Random rd = new Random();
        authCode = "";
        for (int i = 0; i < 5; i++) {
            authCode = authCode + rd.nextInt(9);
        }
    }
}
