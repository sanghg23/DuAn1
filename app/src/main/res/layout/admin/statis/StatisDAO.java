package khanhnqph30151.fptpoly.duan1.admin.statis;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import khanhnqph30151.fptpoly.duan1.data.DbHelper;

public class StatisDAO {
    private final SQLiteDatabase sqLiteDatabase;
    private Context context;
    ;

    public StatisDAO(Context context) {
        DbHelper helper = new DbHelper(context);
        sqLiteDatabase = helper.getWritableDatabase();
    }
    public int getDoanhThu(String tuNgay, String denNgay) {


        String sql1 ="   SELECT substr(invoice_time,7,10) AS date, SUM(invoice_sum) AS doanhThu" +
             "        FROM tbl_invoice" +
             "        where date BETWEEN ? AND ?";

//        String sql = "SELECT SUM(invoice_sum) as doanhThu FROM tbl_invoice WHERE (invoice_time) BETWEEN ? AND ? ";
        ArrayList<Integer> list = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery(sql1, new String[]{tuNgay,denNgay});
        while (c.moveToNext()) {
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }


}
