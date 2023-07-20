package khanhnqph30151.fptpoly.duan1.user.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import khanhnqph30151.fptpoly.duan1.R;
import khanhnqph30151.fptpoly.duan1.activity.ItemInforFood;
import khanhnqph30151.fptpoly.duan1.user.cart.Cart;
import khanhnqph30151.fptpoly.duan1.user.cart.CartAdapter;
import khanhnqph30151.fptpoly.duan1.user.cart.CartDAO;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    private ArrayList<Home> list;
    private ArrayList<Cart> listCart;
    private HomeDAO homeDAO;
    private CartDAO cartDAO;
    private CartAdapter adapter;
    public HomeAdapter(Context context, ArrayList<Home> list, HomeDAO homeDAO) {
        this.context = context;
        this.list = list;
        this.homeDAO=homeDAO;
        cartDAO=new CartDAO(context);
    }
    public void setData(ArrayList<Home> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_home, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
       listCart=cartDAO.getAllData();
        Home home = list.get(position);
        holder.tv_name.setText(list.get(position).getName());
        String img = list.get(position).getImg();
        Picasso.get().load(img).into(holder.iv_img);

        holder.tv_des.setText(list.get(position).getDes());
        holder.tv_price.setText(String.valueOf(list.get(position).getPrice()));
        holder.btn_addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder dialogDL = new AlertDialog.Builder(context);
                dialogDL.setMessage("Bạn có muốn thêm không?");
                dialogDL.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CartDAO cartDAO = new CartDAO(context);
                        Cart cart = new Cart();
                        cart.setIdFood(home.getId());
                        cart.setQuanti(1);
                        cart.setSum(home.getPrice());
                        SharedPreferences sharedPreferences = context.getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                        String loggedInUserName = sharedPreferences.getString("USERNAME", "");
                        cart.setUsername(loggedInUserName);
                        if (!cartDAO.isFoodExists(cart.getIdFood(), cart.getUsername())) {
                            if (cartDAO.insert(cart) > 0) {
                                Toast.makeText(context, "Đã Thêm Vào Giỏ Hàng", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, "Đéo Thêm Vào Giỏ Hàng", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Món ăn đã được chọn", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialogDL.setPositiveButton("KHÔNG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                dialogDL.show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ItemInforFood.class);
                i.putExtra("foodImg", list.get(position).getImg());
                i.putExtra("foodName", list.get(position).getName());
                i.putExtra("foodDes", list.get(position).getDes());
                i.putExtra("foodPrice", list.get(position).getPrice());
                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_img;
        TextView tv_name, tv_des, tv_price;
        ImageButton btn_addCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_item_food_foodImg);
            tv_name = itemView.findViewById(R.id.tv_item_food_foodName);
            tv_des = itemView.findViewById(R.id.tv_item_food_foodContent);
            tv_price = itemView.findViewById(R.id.tv_item_food_foodPrice);
            btn_addCart = itemView.findViewById(R.id.btn_item_food_addCart);
        }
    }

}
