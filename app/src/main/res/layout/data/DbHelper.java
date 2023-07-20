package khanhnqph30151.fptpoly.duan1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "data";
    private static final int DB_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static final String TABLE_USER_CREATE = "CREATE TABLE IF NOT EXISTS " +
            "tbl_user (" +
            "user_name TEXT PRIMARY KEY," +
            "user_pass TEXT NOT NULL," +
            "user_role TEXT" +
            ")";
    public static final String insert_admin = "Insert into tbl_user(user_name,user_pass,user_role) values" +
            "('admin','123','admin'), ('khanh','123','hehe')";
    public static final String TABLE_REQUEST_CREATE = "CREATE TABLE IF NOT EXISTS " +
            "tbl_request (" +
            "request_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "request_name TEXT NOT NULL," +
            "request_email TEXT NOT NULL," +
            "request_phone TEXT NOT NULL," +
            "request_content TEXT NOT NULL" +
            ")";

    public static final String TABLE_FOOD_CREATE = "CREATE TABLE IF NOT EXISTS " +
            "tbl_food (" +
            "food_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "food_img TEXT NOT NULL, " +
            "food_name TEXT NOT NULL, " +
            "food_description TEXT NOT NULL, " +
            "food_price DOUBLE NOT NULL" +
            ")";

    public static final String TABLE_CART_CREATE = "CREATE TABLE IF NOT EXISTS " +
            "tbl_cart (" +
            "cart_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "food_id INTEGER REFERENCES tbl_food(food_id), " +
            "user_name TEXT REFERENCES tbl_user(user_name)," +
            "cart_quantity INTEGER NOT NULL, " +
            "cart_sum DOUBLE NOT NULL" +
            ")";

    public static final String TABLE_INVOICE_CREATE = "CREATE TABLE IF NOT EXISTS " +
            "tbl_invoice (" +
            "invoice_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user_name TEXT REFERENCES tbl_user(user_name)," +
            "cart_id INTEGER REFERENCES tbl_cart(cart_id), " +
            "cart_phone TEXT NOT NULL, " +
            "cart_address TEXT NOT NULL, " +
            "invoice_content TEXT NOT NULL, " +
            "invoice_sum DOUBLE NOT NULL, " +
            "invoice_status TEXT ," +
            "invoice_time TEXT NOT NULL" +
            ")";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USER_CREATE);

        db.execSQL(TABLE_FOOD_CREATE);

        db.execSQL(TABLE_CART_CREATE);

        db.execSQL(TABLE_REQUEST_CREATE);

        db.execSQL(TABLE_INVOICE_CREATE);



//        db.execSQL("INSERT INTO tbl_invoice VALUES (1, 1,'0985825597','nguyễn hoàng nhật', '25/5 đường ngô quyền - thanh xuân- hà nội','07:00 SA 13/07/2023',30000,'đã thanh toán','07:00 2023-07-22')," +
//                " (2,2 ,'0983927037','ngô quốc khánh','25/5 đường ngô quyền - thanh xuân- hà nội','07:00 2023-12-07',30000,'đã thanh toán','05:06 2023-07-13'), " +
//                "(3,3 ,'0965656281','lê ngọc khải','25/5 đường trần hưng đạo - thanh xuân- hà nội','07:00 SA 13/07/2023',30000,'đã thanh toán','06:06 2023-07-31')");



        db.execSQL("INSERT INTO tbl_food(food_img,food_name,food_description,food_price) VALUES ('https://image.vtc.vn/resize/th/upload/2020/03/17/cay-to-7-mon-08364272.jpg', 'Thit cho', 'mota1 fdsjfjkldsjkldfjkslfjkljklsdfjklsdjklfjklsdjklfjkls', 20000), ('link2', 'ten2', 'mota2', 30000), " +
                "('link3', 'ten3', 'mota3', 40000)");


        db.execSQL(insert_admin);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS tbl_usser");
            db.execSQL("DROP TABLE IF EXISTS tbl_request");
            db.execSQL("DROP TABLE IF EXISTS tbl_food");
            db.execSQL("DROP TABLE IF EXISTS tbl_cart");
            db.execSQL("DROP TABLE IF EXISTS tbl_invoice");
            onCreate(db);
        }

    }
}
