package hnu.fooma.yunlin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hnu.fooma.yunlin.R;

/**
 * Created by Fooma on 2016/5/2.
 */
public class FindResetPassword extends Activity{
    EditText find_reset;
    Button find_reset_next;
    CheckBox cb_reset_open;
    ImageView iv_top_return;
    TextView tv_second_top_title;
    private boolean isHidden = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_reset);
        //实现文本框密码显示或隐藏
        init();
        initview();

        tv_second_top_title.setText("重置密码");

        iv_top_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        find_reset_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = find_reset.getText().toString();
                if (a.length() >= 6 && a.length() <= 20) {
                    Intent intent = new Intent(FindResetPassword.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(FindResetPassword.this, "修改密码成功", Toast.LENGTH_LONG).show();
                    FindResetPassword.this.finish();
                } else {
                    Toast.makeText(FindResetPassword.this, "密码格式错误", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initview() {
        find_reset = (EditText)this.findViewById(R.id.et_find_reset);
        find_reset_next = (Button)this.findViewById(R.id.bt_find_reset_next);
        iv_top_return= (ImageView)this.findViewById(R.id.iv_top_return);
        tv_second_top_title= (TextView) this.findViewById(R.id.tv_second_top_title);
    }

    //实现文本框密码显示或隐藏
    private void init() {
        cb_reset_open = (CheckBox) this.findViewById(R.id.cb_reset_open);

        cb_reset_open.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isHidden) {
                    //设置EditText文本为可见的
                    find_reset.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    find_reset.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                isHidden = !isHidden;
                find_reset.postInvalidate();
                //切换后将editText光标置于末尾
                CharSequence charSequence = find_reset.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });
    }

}
