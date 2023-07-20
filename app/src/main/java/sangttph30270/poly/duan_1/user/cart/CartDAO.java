package sangttph30270.poly.duan_1.user.cart;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


import sangttph30270.poly.duan_1.data.DbHelper;

public class CartDAO {
    DbHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    public CartDAO(Context contex){
        dbHelper = new DbHelper(contex);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }
    @SuppressLint("Range")
    public ArrayList<Cart> getData(String sql, String... SelectAvg) {
        ArrayList<Cart> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, SelectAvg);

        while (cursor.moveToNext()) {
            Cart cart = new Cart();
            cart.setIdCart(cursor.getInt(cursor.getColumnIndex("cart_id")));
            cart.setIdFood(cursor.getInt(cursor.getColumnIndex("food_id")));
            cart.setQuanti(cursor.getInt(cursor.getColumnIndex("cart_quantity")));
            cart.setSum(cursor.getDouble(cursor.getColumnIndex("cart_sum")));
            cart.setUsername(cursor.getString(cursor.getColumnIndex("user_name")));
            list.add(cart);
        }

        cursor.close();
        return list;
    }
    public long insert(Cart cart){
        ContentValues values = new ContentValues();
        values.put("food_id", cart.getIdFood());
        values.put("cart_quantity", cart.getQuanti());
        values.put("cart_sum", cart.getSum());
        values.put("user_name",cart.getUsername());
        return sqLiteDatabase.insert("tbl_cart", null, values);
    }
    public ArrayList<Cart> getAllData() {
        String sql = "SELECT * FROM tbl_cart";
        return getData(sql);
    }

    public ArrayList<String> getName(String sql, String... SelectAvgs) {
        ArrayList<String> lst = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, SelectAvgs);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("food_name"));
            lst.add(name);
        }
        return lst;
    }
    public int delete(int ID) {
        return sqLiteDatabase.delete("tbl_cart", "cart_id = ?", new String[]{String.valueOf(ID)});
    }
    public long updateSum(Cart cart){
        ContentValues values = new ContentValues();
        values.put("food_id", cart.getIdFood());
        values.put("cart_quantity", cart.getQuanti());
        values.put("cart_sum", cart.getSum());
        values.put("user_name",cart.getUsername());
        return sqLiteDatabase.update("tbl_cart", values, "cart_id = ?", new String[]{String.valueOf(cart.getIdCart())});
    }
    public boolean isFoodExists(int foodId, String username) {
        String query = "SELECT * FROM tbl_cart WHERE food_id = ? AND user_name = ?";
        String[] selectionArgs = {String.valueOf(foodId), username};
        Cursor cursor = sqLiteDatabase.rawQuery(query, selectionArgs);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public ArrayList<Cart> getByUser(String user) {
        String sql = "SELECT * FROM tbl_cart  where user_name = ?";
        return getData(sql, user);
    }
    public void clearData(String username){
        String query = "DELETE FROM tbl_cart WHERE user_name = ?";
        String[] selectionArgs = {username};
        Cursor cursor = sqLiteDatabase.rawQuery(query, selectionArgs);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
    }

}
