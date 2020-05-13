package awal.tugas.uts10117094.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//tanggal pengerjaan:5/12/2020
//nim: 10117094
//nama: Muhammad Walidin
//kelas: IF-3
public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbtemanapp";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            awal.tugas.uts10117094.presenter.DatabaseContract.TABLE_NAME,
            awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns._ID,
            awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.NIM,
            awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.NAMA,
            awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.KELAS,
            awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.TELPON,
            awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.EMAIL,
            awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.IG,
            awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.DATE

    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + awal.tugas.uts10117094.presenter.DatabaseContract.TABLE_NAME);
        onCreate(db);
    }
}
