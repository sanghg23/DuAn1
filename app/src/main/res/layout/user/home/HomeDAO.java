package khanhnqph30151.fptpoly.duan1.user.home;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import khanhnqph30151.fptpoly.duan1.data.DbHelper;

public class HomeDAO {
    DbHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    public HomeDAO(Context contex){
        dbHelper = new DbHelper(contex);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }
    @SuppressLint("Range")
    public ArrayList<Home> getData(String sql, String... SelectAvg){
        ArrayList<Home> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbl_food", SelectAvg);
        while (cursor.moveToNext()){
            Home home = new Home();
            home.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("food_id"))));
            home.setImg(cursor.getString(cursor.getColumnIndex("food_img")));
            home.setName(cursor.getString(cursor.getColumnIndex("food_name")));
            home.setDes(cursor.getString(cursor.getColumnIndex("food_description")));
            home.setPrice(cursor.getInt(cursor.getColumnIndex("food_price")));
            list.add(home);
        }
        return list;
    }
    public long insert(Home home){
        ContentValues values = new ContentValues();
        values.put("food_img", home.getImg());
        values.put("food_name", home.getName());
        values.put("food_description", home.getDes());
        values.put("food_price", home.getPrice());
        return sqLiteDatabase.insert("tbl_food", null, values);
    }
    public ArrayList<Home> getAllData(){
        String sql = "SELECT * FROM tbl_food";
        return getData(sql);
    }
    public ArrayList<String> name() {
        String name = "SELECT food_name FROM tbl_food";
        return getName(name);
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
    @SuppressLint("Range")
    public ArrayList<Home> Search(String ten) {
        SQLiteDatabase sqLite = dbHelper.getWritableDatabase();
        ArrayList<Home> list = new ArrayList<>();
        Cursor cursor = sqLite.rawQuery("SELECT * FROM tbl_food WHERE food_name LIKE '%"+ ten +"%' ", null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                Home home = new Home();
                home.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("food_id"))));
                home.setImg(cursor.getString(cursor.getColumnIndex("food_img")));
                home.setName(cursor.getString(cursor.getColumnIndex("food_name")));
                home.setDes(cursor.getString(cursor.getColumnIndex("food_description")));
                home.setPrice(Integer.parseInt(cursor.getString(cursor.getColumnIndex("food_price"))));
                list.add(home);

            }
            while (cursor.moveToNext());
        }
        return list;
    }

}
