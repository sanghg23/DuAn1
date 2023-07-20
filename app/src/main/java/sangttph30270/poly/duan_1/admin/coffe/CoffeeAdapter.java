package sangttph30270.poly.duan_1.admin.coffe;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import sangttph30270.poly.duan_1.R;

public class CoffeeAdapter extends  RecyclerView.Adapter<CoffeeAdapter.ViewHolder>  {
    Context context;
    private ArrayList<Coffee> list;
    private CoffeeDAO dao;

    public CoffeeAdapter(Context context, ArrayList<Coffee> list, CoffeeDAO dao) {
        this.context = context;
        this.list = list;
        this.dao = dao;
    }

    public void setData(ArrayList<Coffee> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_coffee, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_name.setText(list.get(position).getName());

        String img = list.get(position).getImg();
        Picasso.get().load(img).into(holder.iv_img);

        holder.tv_des.setText(list.get(position).getDes());
        holder.tv_price.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public boolean onLongClick(View v) {
                @SuppressLint("RestrictedApi") MenuBuilder builder = new MenuBuilder(context);
                MenuInflater inflater = new MenuInflater(context);
                inflater.inflate(R.menu.menu_popup_edit_delete, builder);
                @SuppressLint("RestrictedApi") MenuPopupHelper optionmenu = new MenuPopupHelper(context, builder, v);
                builder.setCallback(new MenuBuilder.Callback() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        if (item.getItemId() == R.id.option_edit) {
                            updateDia(list.get(position), position);
                            return true;
                        } else if (item.getItemId() == R.id.option_delete) {
                            showDele(list.get(position).getId());
                            return true;
                        } else {
                            return false;
                        }
                    }

                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) {

                    }
                });
                optionmenu.show();
                return true;
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_item_listFood_foodImg);
            tv_name = itemView.findViewById(R.id.tv_item_listFood_foodName);
            tv_des = itemView.findViewById(R.id.tv_item_listFood_foodContent);
            tv_price = itemView.findViewById(R.id.tv_item_listFood_foodPrice);
        }
    }
    public void showDele(int id){
        AlertDialog.Builder dialogDL = new AlertDialog.Builder(context);
        dialogDL.setMessage("Bạn có muốn xóa không?");
        dialogDL.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDL.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CoffeeDAO dao = new CoffeeDAO(context);
                if (dao.delete(id) > 0) {
                    Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                    list = dao.getAllData();
                    setData(list);
                } else {
                    Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();

            }
        });
        dialogDL.show();
    }
    private void updateDia(Coffee food, int id) {
        Dialog dialog = new Dialog(context);
        CoffeeDAO foodDAO = new CoffeeDAO(context);
        dialog.setContentView(R.layout.dialog_listfcoffee_update);
        EditText ed1, ed2;

        EditText ed_listfood_img,ed_listfood_name,ed_listfood_price,ed_listfood_des;
        Button btnDialogAddCancel, btnDialogAddSubmit;
        ed_listfood_img = dialog.findViewById(R.id.edt_dialog_listfood_update_img);
        ed_listfood_name = dialog.findViewById(R.id.edt_dialog_listfood_update_name);
        ed_listfood_price = dialog.findViewById(R.id.edt_dialog_listfood_update_price);
        ed_listfood_des = dialog.findViewById(R.id.edt_dialog_listfood_update_des);


        ed_listfood_img.setText(list.get(id).getImg());
        ed_listfood_name.setText(list.get(id).getName());
        ed_listfood_price.setText(String.valueOf(list.get(id).getPrice()));
        ed_listfood_des.setText(list.get(id).getDes());


        btnDialogAddCancel = dialog.findViewById(R.id.btn_dialog_listfood_update_cancel);
        btnDialogAddSubmit = dialog.findViewById(R.id.btn_dialog_listfood_update_add);

        btnDialogAddCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDialogAddSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoffeeDAO foodDAO1 = new CoffeeDAO(context);
                String img = ed_listfood_img.getText().toString();
                String name = ed_listfood_name.getText().toString();
                String priceString = ed_listfood_price.getText().toString();
                String des = ed_listfood_des.getText().toString();

                if (img.trim().isEmpty() || name.trim().isEmpty() || priceString.trim().isEmpty() || des.trim().isEmpty()) {
                    Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!priceString.matches("\\d+")) {
                    Toast.makeText(context, "Giá tiền phải là một số", Toast.LENGTH_SHORT).show();
                } else {
                    int price = Integer.parseInt(priceString);
                    food.setImg(img);
                    food.setName(name);
                    food.setPrice(price);
                    food.setDes(des);

                    if (foodDAO1.update(food) > 0) {
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                        list = foodDAO1.getAllData();
                        setData(list);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        dialog.show();
    }

}
