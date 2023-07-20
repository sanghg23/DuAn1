package sangttph30270.poly.duan_1.user.history;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


import sangttph30270.poly.duan_1.data.DbHelper;

public class History_DAO {
    DbHelper dbHelper;
    private SQLiteDatabase sqLite;
    public History_DAO(Context context){
        dbHelper = new DbHelper(context);
        sqLite = dbHelper.getWritableDatabase();
    }
    @SuppressLint("Range")
    public ArrayList<History_model> getDataHistory(String sql, String... SelectAvg){
        ArrayList<History_model> list = new ArrayList<>();
        Cursor cursor = sqLite.rawQuery("SELECT * FROM tbl_invoice", SelectAvg);
        while (cursor.moveToNext()){
            History_model history = new History_model();
            history.setId_history(Integer.parseInt(cursor.getString(cursor.getColumnIndex("invoice_id"))));
            history.setId_cart(Integer.parseInt(cursor.getString(cursor.getColumnIndex("cart_id"))));
            history.setPhone(Integer.parseInt(cursor.getString(cursor.getColumnIndex("cart_phone"))));
            history.setName(cursor.getString(cursor.getColumnIndex("user_name")));
            history.setAddress(cursor.getString(cursor.getColumnIndex("cart_address")));
            history.setTime(cursor.getString(cursor.getColumnIndex("invoice_time")));
            history.setContten(cursor.getString(cursor.getColumnIndex("invoice_content")));
            history.setStatus(cursor.getString(cursor.getColumnIndex("invoice_status")));
            history.setSum(Double.parseDouble(cursor.getString(cursor.getColumnIndex("invoice_sum"))));
            list.add(history);
        }
        return list;
    }
    public ArrayList<History_model> getAllData() {
        String sql = "SELECT * FROM tbl_invoice";
        return getDataHistory(sql);
    }
    public long insert(History_model history){
        ContentValues values = new ContentValues();
        values.put("cart_id", history.getId_cart());
        values.put("user_name", history.getName());
        values.put("cart_address", history.getAddress());
        values.put("cart_phone", history.getPhone());
        values.put("invoice_time", history.getTime());
        values.put("invoice_content", history.getContten());
        values.put("invoice_sum", history.getSum());
        values.put("invoice_status", history.getStatus());

        return sqLite.insert("tbl_invoice", null, values);
    }
//    public ArrayList<String> getName(String sql, String... SelectAvgs) {
//        ArrayList<String> lst = new ArrayList<>();
//        Cursor cursor = sqLite.rawQuery(sql, SelectAvgs);
//        while (cursor.moveToNext()) {
//            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("food_name"));
//            lst.add(name);
//        }
//        return lst;
//    }

//    public long updateSum(Cart cart){
//        ContentValues values = new ContentValues();
//        values.put("food_id", cart.getIdFood());
//        values.put("cart_quantity", cart.getQuanti());
//        values.put("cart_sum", cart.getSum());
//        return sqLiteDatabase.update("tbl_cart", values, "cart_id = ?", new String[]{String.valueOf(cart.getIdCart())});
//    }
    public boolean isInvoiceExists(History_model history) {
        String query = "SELECT * FROM tbl_invoice WHERE invoice_id = ?";
        String[] selectionArgs = {String.valueOf(history.getId_history())};
        Cursor cursor = sqLite.rawQuery(query, selectionArgs);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
//    public ArrayList<History_model> getByUser(String user) {
//        String sql = "SELECT * FROM tbl_cart where user_name = ?";
//        return getDataHistory(sql, user);
//    }
     @SuppressLint("Range")
     public ArrayList<History_model> getByUser(String username) {
        ArrayList<History_model> list = new ArrayList<>();
        String query = "SELECT * FROM tbl_invoice WHERE user_name = ?";
        Cursor cursor = sqLite.rawQuery(query, new String[]{username});
        while (cursor.moveToNext()) {
            History_model history = new History_model();
            history.setId_history(cursor.getInt(cursor.getColumnIndex("invoice_id")));
            history.setId_cart(Integer.parseInt(cursor.getString(cursor.getColumnIndex("cart_id"))));
            history.setPhone(cursor.getInt(cursor.getColumnIndex("cart_phone")));
            history.setName(cursor.getString(cursor.getColumnIndex("user_name")));
            history.setAddress(cursor.getString(cursor.getColumnIndex("cart_address")));
            history.setTime(cursor.getString(cursor.getColumnIndex("invoice_time")));
            history.setContten(cursor.getString(cursor.getColumnIndex("invoice_content")));
            history.setSum(cursor.getDouble(cursor.getColumnIndex("invoice_sum")));
            history.setStatus(cursor.getString(cursor.getColumnIndex("invoice_status")));
            list.add(history);
        }
        cursor.close();
        return list;
    }

}
