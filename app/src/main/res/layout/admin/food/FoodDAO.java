package khanhnqph30151.fptpoly.duan1.admin.food;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import khanhnqph30151.fptpoly.duan1.data.DbHelper;

public class FoodDAO {
    DbHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    public FoodDAO(Context contex){
        dbHelper = new DbHelper(contex);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }
    @SuppressLint("Range")
    public ArrayList<Food> getData(String sql, String... SelectAvg){
        ArrayList<Food> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbl_food", SelectAvg);
        while (cursor.moveToNext()){
            Food food = new Food();
            food.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("food_id"))));
            food.setImg(cursor.getString(cursor.getColumnIndex("food_img")));
            food.setName(cursor.getString(cursor.getColumnIndex("food_name")));
            food.setDes(cursor.getString(cursor.getColumnIndex("food_description")));
            food.setPrice(cursor.getInt(cursor.getColumnIndex("food_price")));
            list.add(food);
        }
        return list;
    }
    public long insert(Food food){
        ContentValues values = new ContentValues();
        values.put("food_img", food.getImg());
        values.put("food_name", food.getName());
        values.put("food_description", food.getDes());
        values.put("food_price", food.getPrice());
        return sqLiteDatabase.insert("tbl_food", null, values);
    }
    public ArrayList<Food> getAllData(){
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
    public ArrayList<Food> Search(String ten) {
        SQLiteDatabase sqLite = dbHelper.getWritableDatabase();
        ArrayList<Food> list = new ArrayList<>();
        Cursor cursor = sqLite.rawQuery("SELECT * FROM tbl_food WHERE food_name LIKE '%"+ ten +"%' ", null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                Food food = new Food();
                food.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("food_id"))));
                food.setImg(cursor.getString(cursor.getColumnIndex("food_img")));
                food.setName(cursor.getString(cursor.getColumnIndex("food_name")));
                food.setDes(cursor.getString(cursor.getColumnIndex("food_description")));
                food.setPrice(Integer.parseInt(cursor.getString(cursor.getColumnIndex("food_price"))));
                list.add(food);

            }
            while (cursor.moveToNext());
        }
        return list;
    }
    public long update(Food food){
        ContentValues values = new ContentValues();

        values.put("food_img", food.getImg());
        values.put("food_name", food.getName());
        values.put("food_description", food.getDes());
        values.put("food_price", food.getPrice());
        return sqLiteDatabase.update("tbl_food", values, "food_id = ?", new String[]{String.valueOf(food.getId())});
    }
    public int delete(int ID) {
        return sqLiteDatabase.delete("tbl_food", "food_id = ?", new String[]{String.valueOf(ID)});
    }
    public Food getById(int id) {
        Cursor cursor = sqLiteDatabase.query("tbl_food", null,"food_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToNext()){
            return new Food(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getInt(4));
        }else {
            return null;
        }
    }

}
