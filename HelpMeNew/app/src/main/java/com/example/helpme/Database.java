package com.example.helpme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ReminderManager";

    // table name
    private static final String TABLE_REMINDERS = "Reminders";



    // Table Columns names

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REMINDERS_TABLE = "CREATE TABLE " + TABLE_REMINDERS + "( counter INTEGER PRIMARY KEY , medname TEXT , medinfo  TEXT,"+
                "interval  INTEGER ,TimeUnit TEXT,Freq INTEGER,weekdays TEXT,Times TEXT, StartTime TEXT,StartDate DATE,EndDate DATE)";
        db.execSQL(CREATE_REMINDERS_TABLE);





        Log.d("Database","Database Created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);

        // Create tables again
        onCreate(db);
    }

    int deleteReminder(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_REMINDERS,"medname" + "='" + name + "'",null);
    }

    void addReminder(ArrayList alist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("counter", (int) alist.get(0));
        values.put("medname", ""+alist.get(1));
        values.put("medinfo", ""+alist.get(2));
        values.put("interval", (int) alist.get(3));
        values.put("TimeUnit", ""+alist.get(4));
        values.put("Freq", ""+alist.get(5));
        values.put("weekdays", ""+alist.get(6));
        values.put("Times",""+alist.get(7));
        values.put("StartTime", ""+alist.get(8));
        values.put("StartDate", ""+alist.get(9));
        values.put("EndDate", ""+alist.get(10));

        // Inserting Row
        db.insert(TABLE_REMINDERS, null, values);
        Log.d("Inserted:","Row Inserted");
        db.close(); // Closing database connection

        ArrayList arraylist=new ArrayList();
        arraylist.add(alist.get(0));
        arraylist.add(alist.get(1));
        final boolean hi = arraylist.add("hi");
        MedicineReminder medrem=new MedicineReminder();
        medrem.receiveData(arraylist);
    }



    // Getting single contact
    ArrayList getReminder(int count) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REMINDERS, new String[] { "counter","medname",
                        "medinfo", "interval","TimeUnit","Freq","weekdays","Times","StartTime","StartDate","EndDate"}, "counter" + "=?",
                new String[] { ""+count },null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        ArrayList alist=new ArrayList();

        alist.add(cursor.getString(0));
        alist.add(cursor.getString(1));
        alist.add(cursor.getString(2));
        alist.add(cursor.getString(3));
        alist.add(cursor.getString(4));
        alist.add(cursor.getString(5));
        alist.add(cursor.getString(6));
        alist.add(cursor.getString(7));
        alist.add(cursor.getString(8));
        alist.add(cursor.getString(9));
        alist.add(cursor.getString(10));


        return alist;

    }

    // Getting All Contacts
    public ArrayList getAllReminders() {
        ArrayList ReminderList = new ArrayList();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REMINDERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                ReminderList.add(cursor.getString(1));
            }while(cursor.moveToNext());
            db.close();
        }

        return ReminderList;
    }


    // Getting Reminder Count
    public int getRemindersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_REMINDERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.getCount()==0)
            return 0;
        // looping through all rows and adding to list
        cursor.moveToLast();
        int count=Integer.parseInt(cursor.getString(0));

        db.close();
        return count;
    }

    public ArrayList getReminder(String s) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REMINDERS, new String[] { "counter","medname",
                        "medinfo", "interval","TimeUnit","Freq","weekdays","Times","StartTime","StartDate","EndDate"}, "medname" + "=?",
                new String[] {s },null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        ArrayList alist=new ArrayList();

        alist.add(cursor.getString(0));
        alist.add(cursor.getString(1));
        alist.add(cursor.getString(2));
        alist.add(cursor.getString(3));
        alist.add(cursor.getString(4));
        alist.add(cursor.getString(5));
        alist.add(cursor.getString(6));
        alist.add(cursor.getString(7));
        alist.add(cursor.getString(8));
        alist.add(cursor.getString(9));
        alist.add(cursor.getString(10));

        return alist;
    }


}

