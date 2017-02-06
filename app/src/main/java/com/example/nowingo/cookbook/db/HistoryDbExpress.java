package com.example.nowingo.cookbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nowingo.cookbook.entity.Collect;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/5.
 */
public class HistoryDbExpress {
    Context context;
    SQLiteDatabase db;

    public HistoryDbExpress(Context context) {
        this.context = context;
        HistoryDbHelper historyDbHelper = new HistoryDbHelper(context);//实例化数据库
        db = historyDbHelper.getReadableDatabase();//获取数据库操作对象
    }

    public void add(Collect collect){
        ContentValues cv = new ContentValues();
        cv.put("msg",collect.getMsg());
        db.insert("history",null,cv);
    }

    public ArrayList<Collect> findall(){
        ArrayList<Collect> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from history",null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String msg = cursor.getString(cursor.getColumnIndex("msg"));
                Collect collect = new Collect(msg);
                arrayList.add(collect);
            }
        }
        return arrayList;
    }

    public void delete(String name){
        db.delete("history","msg=?",new String[]{name});
    }

    public boolean ishave(String name){
        boolean flag = false;
        Cursor cursor = db.rawQuery("select * from history where msg=?",new String[]{name});
        if (cursor!=null){
            if (cursor.moveToNext()){
                flag = true;
            }
        }
        return flag;
    }
}
