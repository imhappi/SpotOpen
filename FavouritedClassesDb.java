package naomi.me.spotopen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import naomi.me.spotopen.Model.UWClass;

/**
 * Created by naomikoo on 2016-08-09.
 */
public class FavouritedClassesDb extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    private static final int COLUMN_SUBJECT = 0;
    private static final int COLUMN_NUMBER = 1;
    private static final int COLUMN_TOTAL_CAPACITY = 2;
    private static final int COLUMN_TOTAL_ENROLLED = 3;
    private static final int COLUMN_TIME = 4;
    private static final int COLUMN_TERM = 5;
    private static final int COLUMN_LOCATION = 6;
    private static final int COLUMN_NAME = 7;
    private static final int COLUMN_SECTION = 8;


    // Database Name
    private static final String DATABASE_NAME = "favouritedClasses";

    // Contacts table name
    private static final String TABLE_CLASSES = "classes";

    // Contacts Table Columns names
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_TOTAL_CAPACITY = "totalCapacity";
    private static final String KEY_TOTAL_ENROLLED = "totalEnrolled";
    private static final String KEY_TIME = "time";
    private static final String KEY_TERM = "term";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_NAME = "name";
    private static final String KEY_SECTION = "section";

    public FavouritedClassesDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // creating db
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CLASSES_TABLE = "CREATE TABLE " + TABLE_CLASSES + "("
                + KEY_SUBJECT + " TEXT,"
                + KEY_NUMBER + " TEXT,"
                + KEY_TOTAL_CAPACITY + " INTEGER DEFAULT 0,"
                + KEY_TOTAL_ENROLLED + " INTEGER DEFAULT 0,"
                + KEY_TIME + " TEXT,"
                + KEY_TERM + " TEXT,"
                + KEY_LOCATION + " TEXT,"
                + KEY_NAME + " TEXT, "
                + KEY_SECTION + " TEXT"
                + ")";
        db.execSQL(CREATE_CLASSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);

        // Create tables again
        onCreate(db);
    }

    public void addClass(UWClass uwClass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUBJECT, uwClass.getSubject());
        values.put(KEY_NUMBER, uwClass.getNumber());
        values.put(KEY_TOTAL_CAPACITY, uwClass.getTotalCapacity());
        values.put(KEY_TOTAL_ENROLLED, uwClass.getTotalEnrolled());
        values.put(KEY_TIME, uwClass.getTime());
        values.put(KEY_TERM, uwClass.getTerm());
        values.put(KEY_LOCATION, uwClass.getLocation());
        values.put(KEY_NAME, uwClass.getName());
        values.put(KEY_SECTION, uwClass.getSection());

        // Inserting Row
        db.insert(TABLE_CLASSES, null, values);
        db.close(); // Closing database connection
    }

    public UWClass getClass(String subject, String number, String term, String section) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CLASSES, new String[] { KEY_SUBJECT,
                        KEY_NUMBER, KEY_TOTAL_CAPACITY, KEY_TOTAL_ENROLLED, KEY_TIME, KEY_TERM, KEY_LOCATION, KEY_NAME, KEY_SECTION },
                KEY_SUBJECT + "=?" + " AND " + KEY_NUMBER + "=?" + " AND " + KEY_TERM + "=?" + " AND " + KEY_SECTION + "=?",
                new String[] { subject, number, term, section }, null, null, null, null);
        UWClass uwClass = new UWClass();
        if (cursor != null) {
            cursor.moveToFirst();

            uwClass.setSubject(cursor.getString(COLUMN_SUBJECT));
            uwClass.setNumber(cursor.getString(COLUMN_NUMBER));
            //todo
//            uwClass = new UWClass(cursor.getString(COLUMN_SUBJECT), cursor.getString(COLUMN_NUMBER), cursor.getInt(COLUMN_TOTAL_CAPACITY),
//                    cursor.getInt(COLUMN_TOTAL_ENROLLED),
//                    cursor.getString(COLUMN_TIME), cursor.getString(COLUMN_TERM), cursor.getString(COLUMN_LOCATION), cursor.getString(COLUMN_NAME), cursor.getString(COLUMN_SECTION));

            cursor.close();
        }
        // return contact
        return uwClass;
    }

//    public List<UWClass> getAllClasses() {
//        List<UWClass> classList = new ArrayList<>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_CLASSES;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                UWClass uwClass = new UWClass(cursor.getString(COLUMN_SUBJECT), cursor.getString(COLUMN_NUMBER), cursor.getInt(COLUMN_TOTAL_CAPACITY),
//                        cursor.getInt(COLUMN_TOTAL_ENROLLED),
//                        cursor.getString(COLUMN_TIME), cursor.getString(COLUMN_TERM), cursor.getString(COLUMN_LOCATION), cursor.getString(COLUMN_NAME), cursor.getString(COLUMN_SECTION));
//                classList.add(uwClass);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//
//        // return contact list
//        return classList;
//    }

    public int updateClassCapacity(UWClass uwClass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TOTAL_CAPACITY, uwClass.getTotalCapacity());
        values.put(KEY_TOTAL_ENROLLED, uwClass.getTotalEnrolled());

        // updating row
        return db.update(TABLE_CLASSES, values, KEY_SUBJECT + "=?" + " AND " + KEY_NUMBER + "=?" + " AND " + KEY_TERM + "=?" + " AND " + KEY_SECTION + "=?",
                new String[] { uwClass.getSubject() , uwClass.getNumber(), uwClass.getTerm(), uwClass.getSection() });
    }

    public void deleteClass(UWClass uwClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLASSES, KEY_SUBJECT + "=?" + " AND " + KEY_NUMBER + "=?" + " AND " + KEY_TERM + "=?" + KEY_SECTION + "=?",
                new String[] { uwClass.getSubject() , uwClass.getNumber(), uwClass.getTerm(), uwClass.getSection() });
        db.close();
    }
}
