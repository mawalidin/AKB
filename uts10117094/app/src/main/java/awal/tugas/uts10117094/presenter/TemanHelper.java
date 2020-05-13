package awal.tugas.uts10117094.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static awal.tugas.uts10117094.presenter.DatabaseContract.TABLE_NAME;

//tanggal pengerjaan:5/12/2020
//nim: 10117094
//nama: Muhammad Walidin
//kelas: IF-3
public class TemanHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static awal.tugas.uts10117094.presenter.DatabaseHelper databaseHelper;
    private static TemanHelper INSTANCE;
    private static SQLiteDatabase database;

    private TemanHelper(Context context){
        databaseHelper = new awal.tugas.uts10117094.presenter.DatabaseHelper(context);
    }

    public static TemanHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new TemanHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();

        if (database.isOpen()){
            database.close();
        }
    }

    public Cursor queryAll(){
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC");

    }

    public Cursor queryById(String id){
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public long insert(ContentValues values){
        return  database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values){
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteById(String id){
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
