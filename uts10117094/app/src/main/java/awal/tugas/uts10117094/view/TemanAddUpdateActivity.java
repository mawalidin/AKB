package awal.tugas.uts10117094.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import awal.tugas.uts10117094.model.Teman;
import awal.tugas.uts10117094.presenter.TemanHelper;
import awal.tugas.uts10117094.R;

import static awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.DATE;
import static awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.IG;
import static awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.KELAS;
import static awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.EMAIL;
import static awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.NAMA;
import static awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.NIM;
import static awal.tugas.uts10117094.presenter.DatabaseContract.NoteColumns.TELPON;

//tanggal pengerjaan: 5//9/2020
//nim: 10117094
//nama: Muhammad Walidin
//kelas: IF-3
public class TemanAddUpdateActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edtNim, edtNama, edtKelas, edtNohp, edtEmail, edtIg;
    private Button btnSubmit;

    private boolean isEdit = false;
    private Teman teman;
    private int position;
    private TemanHelper temanHelper;

    public static final String EXTRA_NOTE = "extra_note";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teman_add_update);

        edtNim = findViewById(R.id.edt_nim);
        edtNama = findViewById(R.id.edt_nama);
        edtKelas = findViewById(R.id.edt_kelas);
        edtNohp = findViewById(R.id.edt_telpon);
        edtEmail = findViewById(R.id.edt_email);
        edtIg = findViewById(R.id.edt_ig);
        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        temanHelper = TemanHelper.getInstance(getApplicationContext());

        teman = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (teman != null){
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
        }else {
            teman = new Teman();
        }

        String actionBarTitle;
        String btnTitle;

        if (isEdit){
            actionBarTitle = "Update";
            btnTitle = "Update";
//            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6C63FF")));

            if (teman != null){
                edtNim.setText(teman.getNim());
                edtNama.setText(teman.getNama());
                edtKelas.setText(teman.getKelas());
                edtNohp.setText(teman.getTelpon());
                edtEmail.setText(teman.getEmail());
                edtIg.setText(teman.getIg());
            }
        }else {
            actionBarTitle = "Add";
            btnTitle = "Save";
//            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6C63FF")));
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        btnSubmit.setText(btnTitle);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit){
            String nim = edtNim.getText().toString().trim();
            String nama = edtNama.getText().toString().trim();
            String kelas = edtKelas.getText().toString().trim();
            String nohp = edtNohp.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String ig = edtIg.getText().toString().trim();

            if (TextUtils.isEmpty(nim)){
                edtNim.setError("Nim tidak boleh kosong");
                return;
            }else if (TextUtils.isEmpty(nama)){
                edtNama.setError("Nama tidak boleh kosong");
                return;
            }else if (TextUtils.isEmpty(kelas)){
                edtKelas.setError("Kelas tidak boleh kosong");
                return;
            }else if (TextUtils.isEmpty(nohp)){
                edtNohp.setError("No Hp tidak boleh kosong");
                return;
            }else if (TextUtils.isEmpty(email)){
                edtEmail.setError("E-Mail tidak boleh kosong");
                return;
            }else if (TextUtils.isEmpty(ig)){
                edtIg.setError("Instagram tidak boleh kosong");
                return;
            }

            teman.setNim(nim);;
            teman.setNama(nama);
            teman.setKelas(kelas);
            teman.setTelpon(nohp);
            teman.setEmail(email);
            teman.setIg(ig);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_NOTE, teman);
            intent.putExtra(EXTRA_POSITION, position);

            ContentValues values = new ContentValues();
            values.put(NIM, nim);
            values.put(NAMA, nama);
            values.put(KELAS, kelas);
            values.put(TELPON, nohp);
            values.put(EMAIL, email);
            values.put(IG, ig);

            if (isEdit){
                long result = temanHelper.update(String.valueOf(teman.getId()), values);
                if (result > 0){
                    setResult(RESULT_UPDATE, intent);
                    finish();
                }else {
                    Toast.makeText(TemanAddUpdateActivity.this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show();
                }
            }else {
                teman.setDate(getCurrentDate());
                values.put(DATE, getCurrentDate());
                long result = temanHelper.insert(values);

                if (result > 0){
                    teman.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    finish();
                }else {
                    Toast.makeText(TemanAddUpdateActivity.this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit){
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogtitle, dialogMessage;

        if (isDialogClose){
            dialogtitle = "Batal";
            dialogMessage ="Apakah anda ingin membatalkan perubahan pada form?";
        }else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
            dialogtitle = "Hapus Teman";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogtitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isDialogClose){
                            finish();
                        }else {
                            long result = temanHelper.deleteById(String.valueOf(teman.getId()));
                            if (result > 0){
                                Intent intent = new Intent();
                                intent.putExtra(EXTRA_POSITION, position);
                                setResult(RESULT_DELETE, intent);
                                finish();
                            }else {
                                Toast.makeText(TemanAddUpdateActivity.this, "Gagal mengahpus data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
