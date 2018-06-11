package com.example.admin.whereareyou.sqliteDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//DBHelper 생성자로 관리할 DB이름과 버전 정보를 받음
public abstract class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        //새로운 테이블 생성
        db.execSQL("CREATE TABLE USERINFO (_id INTEGER PRIMARY KEY AUTOINCREMENT, idqr BLOB)");
    }
}