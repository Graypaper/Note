package com.example.pepper.note;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Pepper on 2015/7/29.
 */
public class MyDBHelper extends  SQLiteOpenHelper{
    public static final String database_name = "notedata.db";
    public static final int version = 2;
    private static SQLiteDatabase database;

    public MyDBHelper(Context context,String name,CursorFactory cursorFactory,int ver){
        super(context,name,cursorFactory,ver);
    }

    public MyDBHelper(Context context){
        super(context, database_name, null, version);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if(database !=null){
            System.out.println("found a db");
            //database.execSQL("DROP TABLE IF EXISTS " + ItemDAO.TABLE_NAME);
        }
        if (database == null || !database.isOpen()) {
            database = new MyDBHelper(context,database_name,null,version).getWritableDatabase();
            //database.execSQL(ItemDAO.CREATE_TABLE);
            System.out.println("after new a db");
        }
        /*
        Cursor dbCursor = database.query(ItemDAO.TABLE_NAME, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        for(int i =0;i<columnNames.length;i++){
            System.out.println(columnNames[i]);
        }
        */
        return database;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ItemDAO.CREATE_TABLE);
        System.out.println("created db");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemDAO.TABLE_NAME);
        onCreate(db);
    }
}
