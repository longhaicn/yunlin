package hnu.fooma.yunlin.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import hnu.fooma.yunlin.MainActivity;
import hnu.fooma.yunlin.R;
import hnu.fooma.yunlin.dao.AdsDao;
import hnu.fooma.yunlin.dao.BigTypeDao;
import hnu.fooma.yunlin.dao.GoodsDao;
import hnu.fooma.yunlin.dao.MemberDao;
import hnu.fooma.yunlin.dao.SmallTypeDao;
import hnu.fooma.yunlin.entity.Member;
import hnu.fooma.yunlin.utils.Tools;

/**
 * Created by Fooma on 2016/4/21.
 */
public class LoginActivity extends Activity {
    private EditText et_account,et_password;
    private TextView tv_register,tv_find_password;
    private Button btn_login_submit;
    private CheckBox cb_remember_password;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListener();
        getMemory();
    }

    private void getMemory() {
        sp = getSharedPreferences("config", this.MODE_PRIVATE);
        //一启动就要判断
        boolean isChecked = sp.getBoolean("isChecked", false);
//        Toast.makeText(this, "ok" + isChecked, Toast.LENGTH_LONG).show();
        if (isChecked) {
            //用户做了记住密码的操作
            String username = sp.getString("username", null);
            String password = sp.getString("password", null);
            et_account.setText(username);
            et_password.setText(password);
            cb_remember_password.setChecked(true);
        }
    }

    private void setListener() {
        //登录按钮监听
        btn_login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login()){
                    Toast.makeText(LoginActivity.this,R.string.login_success,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,R.string.login_fail,Toast.LENGTH_LONG).show();
                }
            }
        });
        //注册按钮监听
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //找回密码监听
        tv_find_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean login() {
        String account = et_account.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if (Tools.isEnpty(account)||Tools.isEnpty(password)){
            return false;
        }else{
            Member member = new Member(account,password);

            MemberDao memberDao = new MemberDao(this);
            Member user = memberDao.readMember(member);
            if (account.equals(user.account) && password.equals(user.password)){
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("memberId",user.memberId);
//                editor.putString("phone",user.phone);
                editor.putString("username", user.account);
                editor.putString("password", user.password);
                editor.putString("mlongitude",user.mlongitude);
                editor.putString("mlatitude",user.mlatitude);
//                editor.apply();
                if (cb_remember_password.isChecked()) {
                    editor.putBoolean("isChecked", cb_remember_password.isChecked());
                    editor.apply();
//                    Toast.makeText(this, "ok" + cb_remember_password.isChecked(), Toast.LENGTH_LONG).show();
                } else {
                    //清除
                    editor.putBoolean("isChecked", cb_remember_password.isChecked());
                    editor.apply();
                }
                return true;
            }else{
                return false;
            }

        }
    }
    private void initView() {
        et_account= (EditText) findViewById(R.id.et_account);
        et_password= (EditText) findViewById(R.id.et_password);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_find_password = (TextView) findViewById(R.id.tv_find_password);
        cb_remember_password= (CheckBox) findViewById(R.id.cb_remember_password);
        btn_login_submit= (Button) findViewById(R.id.btn_login_submit);
    }
}