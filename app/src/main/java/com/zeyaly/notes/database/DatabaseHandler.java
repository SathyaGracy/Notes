package com.zeyaly.notes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zeyaly.notes.model.NoteModel;

import java.util.ArrayList;
import java.util.Date;


public class DatabaseHandler extends SQLiteOpenHelper  {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;




    private static final String TABLE_NAME = "NotesDB";
    private static final String COLUMN_ID = "id";

    private static final String COLUMN_HEAD = "_title";
    private static final String COLUMN_TEXTVALUE = "_textvalue";
    private static final String COLUMN_REMAINDAERONOFF = "_remainderOnOff";
    private static final String COLUMN_RemainderDate = "_RemainderDate";
    private static final String COLUMN_RemainderTime = "_RemainderTime";
    private static final String COLUMN_SecurityONOFF = "_SecurityONOFF";
    private static final String COLUMN_SecurityLOCKPIN = "_SecurityLOCKPIN";
    private static final String COLUMN_CREATED_AT = "created_at";


    public DatabaseHandler(Context context) {
        super(context, "Data", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MODEL_TABLE_ocrdata = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_HEAD + " TEXT, "
                + COLUMN_TEXTVALUE + " TEXT, "
                + COLUMN_REMAINDAERONOFF + " TEXT, "
                + COLUMN_RemainderDate + " TEXT, "
                + COLUMN_RemainderTime + " TEXT, "
                + COLUMN_SecurityONOFF + " TEXT, "
                + COLUMN_SecurityLOCKPIN + " TEXT, "
                + COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP);";
        db.execSQL(CREATE_MODEL_TABLE_ocrdata);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    public long addValue(NoteModel noteModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HEAD, noteModel.getTitle());
        values.put(COLUMN_TEXTVALUE, noteModel.getDes());
        values.put(COLUMN_REMAINDAERONOFF, noteModel.getRemainderONOFF());
        values.put(COLUMN_RemainderDate, noteModel.getRemainderDate());
        values.put(COLUMN_RemainderTime, noteModel.getRemainderTime());
        values.put(COLUMN_SecurityONOFF, noteModel.getSecurityLockONOFF());
        values.put(COLUMN_SecurityLOCKPIN, noteModel.getSecurityPIN());
        values.put(COLUMN_CREATED_AT, System.currentTimeMillis());

        // Inserting Row
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }


    public NoteModel get(long index) {

        String query = "SELECT " + COLUMN_ID + ","
                + COLUMN_HEAD + ","
                + COLUMN_TEXTVALUE + ","
                + COLUMN_CREATED_AT +
                " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + index;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        NoteModel ocrdata = null;

        if (cursor.moveToFirst()) {
            ocrdata = new NoteModel();
            ocrdata.setId(Integer.parseInt(cursor.getString(0)));
//            ocrdata.setByteBuffer(cursor.getBlob(1));
            ocrdata.setCreated_at(new Date(cursor.getLong(2)));


        }

        return ocrdata;
    }


    public ArrayList<NoteModel> all() {

        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<NoteModel> ocrdatas = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_CREATED_AT + " DESC";


        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                NoteModel ocrdata = new NoteModel();
                ocrdata.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                ocrdata.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_HEAD)));
                ocrdata.setTextValue(cursor.getString(cursor.getColumnIndex(COLUMN_TEXTVALUE)));
                ocrdata.setRemainderDate(cursor.getString(cursor.getColumnIndex(COLUMN_RemainderDate)));
                ocrdata.setRemainderTime(cursor.getString(cursor.getColumnIndex(COLUMN_RemainderTime)));
                ocrdata.setRemainderONOFF(cursor.getString(cursor.getColumnIndex(COLUMN_REMAINDAERONOFF)));
                ocrdata.setSecurityLockONOFF(cursor.getString(cursor.getColumnIndex(COLUMN_SecurityONOFF)));
                ocrdata.setSecurityPIN(cursor.getString(cursor.getColumnIndex(COLUMN_SecurityLOCKPIN)));
                ocrdata.setCreated_at(new Date(cursor.getColumnIndex(COLUMN_CREATED_AT)));
                ocrdatas.add(ocrdata);
            } while (cursor.moveToNext());
        }
        return ocrdatas;
    }


    public void remove(int index) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + index;

        db.execSQL(query);
        db.close();

    }


    public void update(NoteModel Model, long index) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HEAD, Model.getTitle());
        values.put(COLUMN_TEXTVALUE, Model.getDes());
        //  String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_HEAD + "=" + photoModel.getTitle() + COLUMN_TEXTVALUE + "=" + photoModel.getTextValue() + " WHERE " + COLUMN_ID + " = " + index;
        db.update(TABLE_NAME,values, COLUMN_ID + " = " + index,null);
        db.close();
    }


    public void updateRemainderONOFF(NoteModel Model, long index) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HEAD, Model.getTitle());
        values.put(COLUMN_REMAINDAERONOFF, Model.getRemainderONOFF());
        values.put(COLUMN_RemainderDate, Model.getRemainderDate());
        values.put(COLUMN_RemainderTime, Model.getRemainderTime());
        //  String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_HEAD + "=" + photoModel.getTitle() + COLUMN_TEXTVALUE + "=" + photoModel.getTextValue() + " WHERE " + COLUMN_ID + " = " + index;
        db.update(TABLE_NAME,values, COLUMN_ID + " = " + index,null);
        db.close();
    }


    public void updateSecurityONOFF(NoteModel Model, long index) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HEAD, Model.getTitle());
        values.put(COLUMN_SecurityLOCKPIN, Model.getSecurityPIN());
        values.put(COLUMN_REMAINDAERONOFF, Model.getSecurityLockONOFF());
        values.put(COLUMN_RemainderTime, Model.getRemainderTime());
        //  String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_HEAD + "=" + photoModel.getTitle() + COLUMN_TEXTVALUE + "=" + photoModel.getTextValue() + " WHERE " + COLUMN_ID + " = " + index;
        db.update(TABLE_NAME,values, COLUMN_ID + " = " + index,null);
        db.close();
    }
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.delete(TABLE_NAME,null,null);
        db.execSQL("delete from " + TABLE_NAME);
        //  db.execSQL("TRUNCATE table" + TranscationHistoryInfo.TABLE_NAME);
        db.close();
    }

}
