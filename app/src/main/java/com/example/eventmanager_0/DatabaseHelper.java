package com.example.eventmanager_0;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{
    private String TAG = "SQLiteHelperLog";

    private static int DB_VERSION = 1;
    public static final String DB_NAME = "eventsinfo.db";
    public static final String ToDo_Events_TABLE_NAME = "todo";
    public static final String InProgress_Events_TABLE_NAME = "inprogress";

    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_INDEX = "num";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_SUBJECT = "subject";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d(TAG, "onCreate");
        StringBuilder createToDoTable = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        createToDoTable.append(ToDo_Events_TABLE_NAME).append('(')
                .append(COLUMN_ID).append(" INTEGER PRIMARY KEY,")
                .append(COLUMN_INDEX).append(" INTEGER,")
                .append(COLUMN_TITLE).append(" VARCHAR(20),")
                .append(COLUMN_TYPE).append(" VARCHAR(20),")
                .append(COLUMN_SUBJECT).append(" VARCHAR(20)")
                .append(')');
        StringBuilder createInProgressTable = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        createInProgressTable.append(InProgress_Events_TABLE_NAME).append('(')
                .append(COLUMN_ID).append(" INTEGER PRIMARY KEY,")
                .append(COLUMN_INDEX).append(" INTEGER,")
                .append(COLUMN_TITLE).append(" VARCHAR(20),")
                .append(COLUMN_TYPE).append(" VARCHAR(20),")
                .append(COLUMN_SUBJECT).append(" VARCHAR(20)")
                .append(')');
        db.execSQL(createToDoTable.toString());
        db.execSQL(createInProgressTable.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.d(TAG, "onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + ToDo_Events_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + InProgress_Events_TABLE_NAME);
        onCreate(db);
    }

    private void setValues(ContentValues contentValues, int id, int num, String title, String type, String subject){
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_INDEX, num);
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_TYPE, type);
        contentValues.put(COLUMN_SUBJECT, subject);
    }

    private String setOperateTableName(int fragmentCode){
        String TABLE_NAME = "";
        if(fragmentCode == MainActivity.toDoFragmentCode){
            TABLE_NAME = ToDo_Events_TABLE_NAME;
        } else if(fragmentCode == MainActivity.inProgressFragmentCode){
            TABLE_NAME = InProgress_Events_TABLE_NAME;
        }
        return TABLE_NAME;
    }

    public void insertEvent(int fragmentCode, int id, int num, String title, String type, String subject){
        String TABLE_NAME = this.setOperateTableName(fragmentCode);

        SQLiteDatabase db = null;
        try{
            db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            this.setValues(contentValues, id, num, title, type, subject);
            db.insertOrThrow(TABLE_NAME, null, contentValues);
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            if(db != null){

            }
        }
    }

    public void deleteEvent(int fragmentCode, int num){
        String TABLE_NAME = this.setOperateTableName(fragmentCode);

        int count = 0;
        SQLiteDatabase db = null;
        try{
            db = this.getWritableDatabase();
            String whereCluse = this.COLUMN_INDEX + " = ?";
            String[] whereArgs = new String[]{Integer.toString(num)};
            count = db.delete(DatabaseHelper.ToDo_Events_TABLE_NAME, whereCluse, whereArgs);
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            if(db != null){
            }
        }
    }

    public List<EventContent> queryAllEvent(int fragmentCode){
        String TABLE_NAME = this.setOperateTableName(fragmentCode);

        SQLiteDatabase db = null;
        db = this.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.ToDo_Events_TABLE_NAME, null, null, null, null, null, null);
        if(cursor== null || cursor.getCount() <= 0){
            Log.d(TAG, "empty query result");
        }

        List<EventContent> result = new ArrayList<>();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            int num = cursor.getInt(1);
            String title = cursor.getString(2);
            String type = cursor.getString(3);
            String subject = cursor.getString(4);
            EventContent eventContent = new EventContent(title, type, subject);
            eventContent.setId(id);
            eventContent.setIndex(num);
            result.add(eventContent);
            Log.d("Info", id + " " + title + " " + type + " " + subject);
            //Toast.makeText(MainActivity.this, uid + " " + type + " " + subject, Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public void updateEvent(int fragmentCode, int id, int num, String title, String type, String subject){
        String TABLE_NAME = this.setOperateTableName(fragmentCode);

        SQLiteDatabase db = null;
        try{
            db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            this.setValues(contentValues, id, num, title, type, subject);
            String whereClause = COLUMN_INDEX + " = ?";
            String[] whereArgs = new String[]{Integer.toString(num)};
            db.update(TABLE_NAME, contentValues, whereClause, whereArgs);
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if(db != null){
            }
        }
    }

}
