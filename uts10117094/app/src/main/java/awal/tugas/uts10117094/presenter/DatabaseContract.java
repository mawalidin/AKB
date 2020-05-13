package awal.tugas.uts10117094.presenter;

import android.provider.BaseColumns;

//tanggal pengerjaan:5/12/2020
//nim: 10117094
//nama: Muhammad Walidin
//kelas: IF-3
public class DatabaseContract {

    static String TABLE_NAME = "teman";

    public static final class NoteColumns implements BaseColumns {
        public static String NIM = "nim";
        public static String NAMA = "nama";
        public static String KELAS = "kelas";
        public static String TELPON = "telpon";
        public static String EMAIL = "email";
        public static String IG = "ig";
        public static String DATE = "date";
    }
}
