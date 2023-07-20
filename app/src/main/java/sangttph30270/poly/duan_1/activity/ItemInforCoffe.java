package sangttph30270.poly.duan_1.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import sangttph30270.poly.duan_1.R;
import sangttph30270.poly.duan_1.user.home.Home;


public class ItemInforCoffe extends AppCompatActivity {
    ArrayList<Home> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffe_infor_coffee);

        ImageButton btn_back = findViewById(R.id.btn_infor_food_back);

        ImageView iv_image = findViewById(R.id.iv_infor_food_img);
        TextView tv_name = findViewById(R.id.tv_infor_food_name);
        TextView tv_content = findViewById(R.id.tv_infor_food_content);
        TextView tv_price = findViewById(R.id.tv_infor_food_price);

        String dataImage = getIntent().getStringExtra("foodImg");
        String dataName = getIntent().getStringExtra("foodName");
        String dataContent = getIntent().getStringExtra("foodDes");
        int dataPrice = getIntent().getIntExtra("foodPrice", 0);

        Picasso.get().load(dataImage).into(iv_image);
        tv_name.setText(dataName);
        tv_content.setText(dataContent);
        tv_price.setText(String.valueOf(dataPrice));

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}