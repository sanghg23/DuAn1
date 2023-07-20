package khanhnqph30151.fptpoly.duan1.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import khanhnqph30151.fptpoly.duan1.MainActivity;
import khanhnqph30151.fptpoly.duan1.R;
import khanhnqph30151.fptpoly.duan1.setting.UserDAO;

public class LoginActivity extends AppCompatActivity {
    TextView tv_Register;
    EditText edUsername, edPassword;
    Button btnLogin;
    UserDAO dao;
    String strUser, strPass;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUsername=findViewById(R.id.edUsername);
        edPassword=findViewById(R.id.edPassword);
        btnLogin=findViewById(R.id.btn_login);
        dao=new UserDAO(this);
        tv_Register=findViewById(R.id.tv_Register);

        tv_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

    }
    public void checkLogin() {
        strUser = edUsername.getText().toString();
        strPass = edPassword.getText().toString();
        if (strUser.length() == 0) {
            edUsername.requestFocus();
            edUsername.setError("Vui lòng nhập tên đăng nhập");
        } else if (strPass.length() == 0) {
            edPassword.requestFocus();
            edPassword.setError("Vui lòng nhập mật khẩu");
        }else {
            if (dao.checkLogin(strUser, strPass) > 0) {
                Toast.makeText(this, "Login thanh cong", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                rememberUser(strUser, strPass,dao.getRole(strUser));
                finish();
            } else {
                Toast.makeText(this, "Login k thanh cong", Toast.LENGTH_SHORT).show();
            }

        }


    }
    public void rememberUser(String u, String p,String r) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("USERNAME", u);
        edit.putString("PASSWORD", p);
        edit.putString("ROLE",r);
        edit.commit();
    }
}