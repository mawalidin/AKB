package awal.tugas.uts10117094.presenter;

import android.database.Cursor;

import java.util.ArrayList;

import awal.tugas.uts10117094.model.Teman;

//tanggal pengerjaan:5/12/2020
//nim: 10117094
//nama: Muhammad Walidin
//kelas: IF-3
public class MapingHelper {
    public static ArrayList<Teman> mapCursorToArrayList(Cursor friendsCursor){
        ArrayList<Teman> temanList = new ArrayList<>();

        while (friendsCursor.moveToNext()){
            int id  = friendsCursor.getInt(friendsCursor.getColumnIndexOrThrow(awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns._ID));
            String nim = friendsCursor.getString(friendsCursor.getColumnIndexOrThrow(awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.NIM));
            String nama = friendsCursor.getString(friendsCursor.getColumnIndexOrThrow(awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.NAMA));
            String kelas = friendsCursor.getString(friendsCursor.getColumnIndexOrThrow(awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.KELAS));
            String telp = friendsCursor.getString(friendsCursor.getColumnIndexOrThrow(awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.TELPON));
            String email = friendsCursor.getString(friendsCursor.getColumnIndexOrThrow(awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.EMAIL));
            String ig = friendsCursor.getString(friendsCursor.getColumnIndexOrThrow(awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.IG));
            String date = friendsCursor.getString(friendsCursor.getColumnIndexOrThrow(awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.DATE));
            temanList.add(new Teman(id, nim, nama, kelas, telp, email, ig, date));

        }
        return temanList;
    }
}
