package sangttph30270.poly.duan_1.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import sangttph30270.poly.duan_1.R;
import sangttph30270.poly.duan_1.setting.User;
import sangttph30270.poly.duan_1.setting.UserDAO;

public class ChangePassActivity extends AppCompatActivity {
    EditText edPassOld, edPass, edRePass;
    Button btn_update, btn_back;
    UserDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        edPassOld=findViewById(R.id.edOldPass);
        edPass=findViewById(R.id.edNewPass);
        edRePass=findViewById(R.id.edReNewPass);
        btn_update=findViewById(R.id.btn_changePass);
        btn_back =findViewById(R.id.btn_changePass_cancel);
        dao=new UserDAO(this);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref=getApplication().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user=pref.getString("USERNAME","");
                if(validate()>0){
                    User user1=dao.getByID(user);
                    user1.setUser_pass(edPass.getText().toString());
                    if(dao.update(user1)>0){
                        Toast.makeText(getApplicationContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edPass.setText("");
                        edPassOld.setText("");
                        edRePass.setText("");
//
                    }else{
                        Toast.makeText(getApplicationContext(), "Thay doi mat khau that bai", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Thay doi mat khau that bai", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    public int validate(){
        int check=1;
        if(edPassOld.getText().length()==0||edPass.getText().length()==0||edRePass.getText().length()==0){
            Toast.makeText(this, "không được để trông", Toast.LENGTH_SHORT).show();
            check=-1;
        }else{
            SharedPreferences pref=getApplication().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld=pref.getString("PASSWORD","");
            String pass=edPassOld.getText().toString();
            String passN=edPass.getText().toString();
            String repass=edRePass.getText().toString();
            if (!passOld.equals(pass)){
                Toast.makeText(this, "Mật khẩu sai", Toast.LENGTH_SHORT).show();
                check=-1;
            }
            if(!passN.equals(repass)){
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                check=-1;}
        }
        return check;
    }
}