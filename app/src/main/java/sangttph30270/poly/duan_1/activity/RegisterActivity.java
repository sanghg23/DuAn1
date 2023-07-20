package sangttph30270.poly.duan_1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import sangttph30270.poly.duan_1.R;
import sangttph30270.poly.duan_1.setting.User;
import sangttph30270.poly.duan_1.setting.UserDAO;

public class RegisterActivity extends AppCompatActivity {
    EditText edUsername_regis,edUserpass_regis,edUserRePass;
    TextView resetlogin;
    Button btn_register;
    UserDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edUsername_regis=findViewById(R.id.signup_email);
        edUserpass_regis=findViewById(R.id.signup_password);
        edUserRePass=findViewById(R.id.signup_password1);
        btn_register=findViewById(R.id.sigup_button);
        resetlogin = findViewById(R.id.loginRedirectText);
        dao=new UserDAO(this);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User();
                user.setUser_name(edUsername_regis.getText().toString());
                user.setUser_pass(edUserpass_regis.getText().toString());
                user.setUser_role("user");
                if(validate()>0){
                    if(dao.insert(user)>0){
                        Toast.makeText(getApplicationContext(), "thêm tai khoan thanh cong", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                        finish();
                        startActivity(i);
                    }else {
                        Toast.makeText(getApplicationContext(), "them that bai", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        resetlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            }
        });

    }
    public int validate(){
        int check=1;
        if(edUsername_regis.getText().length()==0||edUserpass_regis.getText().length()==0||edUserRePass.getText().length()==0
                ){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin ", Toast.LENGTH_SHORT).show();
            check=-1;
        }else{
            String pass=edUserpass_regis.getText().toString();
            String repass=edUserRePass.getText().toString();
            if(!pass.equals(repass)){
                Toast.makeText(getApplicationContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check=-1;
            }else{
                check=1;
            }

        }
        return check;
    }
}