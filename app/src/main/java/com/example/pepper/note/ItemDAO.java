package com.example.pepper.note;

/**
 * Created by Pepper on 2015/7/29.
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ItemDAO {
    // table name
    public static final String TABLE_NAME = "item";
    // final column name
    public static final String KEY_ID = "_id";
    // other column name
    public static final String TITLE_COLUMN = "title";
    public static final String DATETIME_COLUMN = "date";
    public static final String CONTENT_COLUMN = "content";
    // create table
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TITLE_COLUMN + " TEXT NOT NULL," +
                    CONTENT_COLUMN + " TEXT NOT NULL, " +
                    DATETIME_COLUMN + " TEXT NOT NULL)";
    // db instance
    private SQLiteDatabase db;

    // constructor
    public ItemDAO(Context context) {
        //db = MyDBHelper.getDatabase(context);
        db = MyDBHelper.getDatabase(context);
    }

    // close db
    public void close() {
        db.close();
    }

    //
    public Memo insert(Memo memo) {

        ContentValues cv = new ContentValues();
        System.out.println("memo " + memo.getContent() + " " + memo.getDate());
        cv.put(TITLE_COLUMN, memo.getTitle());
        cv.put(CONTENT_COLUMN, memo.getContent());
        cv.put(DATETIME_COLUMN, memo.getDate());
        long id = db.insert(TABLE_NAME, null, cv);
        memo.setId(id);
        System.out.println("after insert id: " + id);
        return memo;
    }


    public boolean update(Memo memo) {

        ContentValues cv = new ContentValues();
        cv.put(DATETIME_COLUMN, memo.getDate());
        cv.put(CONTENT_COLUMN, memo.getContent());
        String where = KEY_ID + "=" + memo.getId();
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }


    public boolean delete(long id){

        String where = KEY_ID + "=" + id;

        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<Memo> getAll() {
        List<Memo> result = new ArrayList<Memo>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            System.out.println("cursor got next");
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }


    public Memo get(long id) {

        Memo memo = null;

        String where = KEY_ID + "=" + id;

        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);


        if (result.moveToFirst()) {

            memo = getRecord(result);
        }


        result.close();

        return memo;
    }


    public Memo getRecord(Cursor cursor) {

        Memo result = new Memo();

        result.setId(cursor.getLong(0));
        result.setContent(cursor.getString(1));
        result.setDate(cursor.getString(2));

        return result;
    }


    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }


}
