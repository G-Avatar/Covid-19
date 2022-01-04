package com.example.covid_19;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliDatabase extends SQLiteOpenHelper {
    private final String create_table = "CREATE TABLE ACTIVE_CASES (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,Reg_Name TEXT NOT NULL,Pro_Name TEXT NOT NULL,Mun_Name TEXT NOT NULL,date TEXT NOT NULL,active_muni INTEGER NOT NULL)";
    private final String create_table2 = "CREATE TABLE DASHBOARD (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, confirmed INTEGER NOT NULL, total_active INTEGER NOT NULL, recovered INTEGER NOT NULL, deceased INTEGER NOT NULL)";
    private int id;
    private final String table = "ACTIVE_CASES";
    private final String table2 = "DASHBOARD";
    private final String Reg_Name = "Reg_Name";
    private final String Pro_Name = "Pro_Name";
    private final String Mun_Name = "Mun_Name";
    private final String date = "date";
    private final String active_cases = "active_muni";
    private final String confirmed = "confirmed";
    private final String total_active = "total_active";
    private final String recovered = "recovered";
    private final String deceased = "deceased";
    private String[][] search;
    private boolean res;
    private static final int version = 3;
    private static final String database_name = "cases.db";
    private DataHandler setting = new DataHandler();

    public String getConfirmed() {
        return confirmed;
    }

    public String getTotal_active() {
        return total_active;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getDeceased() {
        return deceased;
    }

    public String getTable() {
        return table;
    }

    public String getTable2() {
        return table2;
    }

    public String getActive_cases() {
        return active_cases;
    }

    public String getReg_Name() {
        return Reg_Name;
    }

    public String getPro_Name() {
        return Pro_Name;
    }

    public String getMun_Name() {
        return Mun_Name;
    }

    public String getDate() {
        return date;
    }

    public boolean getRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String[][] getSearch(){
        return search;
    }

    public SqliDatabase(@Nullable Context context) {
        super(context, database_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_table);
        sqLiteDatabase.execSQL(create_table2);
        ContentValues cv = new ContentValues();
        cv.put(getConfirmed(),0);
        cv.put(getTotal_active(),0);
        cv.put(getRecovered(),0);
        cv.put(getDeceased(),0);
        sqLiteDatabase.insert(getTable2(),null,cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertData(String[][] data){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(getTable(),null,null);
        db.execSQL("VACUUM");
        db.beginTransaction();
        try {
            for (int i = 0; i < data.length; i++) {
                ContentValues cv = new ContentValues();
                cv.put(getReg_Name(),data[i][0]);
                cv.put(getPro_Name(),data[i][1]);
                cv.put(getMun_Name(),data[i][2]);
                cv.put(getDate(),data[i][3]);
                cv.put(getActive_cases(),data[i][4]);
                db.insert(getTable(),null,cv);
            }
            db.setTransactionSuccessful();
            setRes(true);
        }catch (Exception e){
            setRes(false);
            return getRes();
        }finally {
            db.endTransaction();
            return getRes();
        }
    }

    public boolean updateDash(String[][] data){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (int i = 0; i < data.length; i++) {
                ContentValues cv = new ContentValues();
                cv.put(getConfirmed(),data[i][0]);
                cv.put(getTotal_active(),data[i][1]);
                cv.put(getRecovered(),data[i][3]);
                cv.put(getDeceased(),data[i][2]);
                db.update(getTable2(), cv, null, null);
            }
            db.setTransactionSuccessful();
            setRes(true);
        }catch (Exception e){
            setRes(false);
            return getRes();
        }finally {
            db.endTransaction();
            return getRes();
        }
    }

    public DataHandler dashboard(){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM "+getTable2();
            Cursor cursor = db.rawQuery(sql,null);
            if (cursor.moveToFirst()){
                do {
                    setting.setConfirmed(cursor.getInt(1));
                    setting.setRecovered(cursor.getInt(3));
                    setting.setTotal_active(cursor.getInt(2));
                    setting.setDeceased(cursor.getInt(4));
                }while (cursor.moveToNext());
            }else{
                System.out.println("No data");
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            System.out.println("error: "+e.getMessage());
        }
        return setting;
    }

    public String[][] search(String municipal){
        String[] mun = {"%"+municipal+"%"};
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM "+getTable()+" WHERE "+getMun_Name()+" LIKE ?";
            Cursor cursor = db.rawQuery(sql, mun);
            int x = 0;
            search = new String[cursor.getCount()][6];
            if (cursor.moveToFirst()){
                do {
                    search[x][0] = cursor.getInt(0)+"";
                    search[x][1] = cursor.getString(1);
                    search[x][2] = cursor.getString(2);
                    search[x][3] = cursor.getString(3);
                    search[x][4] = cursor.getString(4);
                    search[x][5] = cursor.getInt(5)+"";
                    x++;
                }while (cursor.moveToNext());
            }else{
                System.out.println("No data");
            }
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }finally {
            return getSearch();
        }
    }

}
