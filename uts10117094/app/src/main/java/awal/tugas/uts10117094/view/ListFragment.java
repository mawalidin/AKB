package awal.tugas.uts10117094.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import awal.tugas.uts10117094.model.Teman;
import awal.tugas.uts10117094.presenter.MapingHelper;
import awal.tugas.uts10117094.presenter.TemanAdapter;
import awal.tugas.uts10117094.presenter.TemanHelper;
import awal.tugas.uts10117094.R;

//tanggal pengerjaan: 5//9/2020
//nim: 10117094
//nama: Muhammad Walidin
//kelas: IF-3
public class ListFragment extends Fragment implements LoadTemanCallback {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private TemanAdapter adapter;
    private TemanHelper temanHelper;
    private FloatingActionButton fabAdd;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        progressBar = view.findViewById(R.id.progressbar);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter = new TemanAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), awal.tugas.uts10117094.view.TemanAddUpdateActivity.class);
                startActivityForResult(intent, awal.tugas.uts10117094.view.TemanAddUpdateActivity.REQUEST_ADD);
            }
        });

        temanHelper = TemanHelper.getInstance(getActivity());
        temanHelper.open();

        if (savedInstanceState == null) {
            new LoadNotesAsync(temanHelper, this).execute();
        } else {
            ArrayList<Teman> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListNotes(list);
            }
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListNotes());
    }

    @Override
    public void preExecute() {
        new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        };
    }

    @Override
    public void postExecute(ArrayList<Teman> notes) {
        progressBar.setVisibility(View.INVISIBLE);
        if (notes.size() > 0) {
            adapter.setListNotes(notes);
        } else {
            adapter.setListNotes(new ArrayList<Teman>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private static class LoadNotesAsync extends AsyncTask<Void, Void, ArrayList<Teman>> {
        private final WeakReference<TemanHelper> weakNoteHelper;
        private final WeakReference<LoadTemanCallback> weakCallback;

        private LoadNotesAsync(TemanHelper noteHelper, LoadTemanCallback callback) {
            weakNoteHelper = new WeakReference<>(noteHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Teman> doInBackground(Void... voids) {
            Cursor dataCursor = weakNoteHelper.get().queryAll();
            return MapingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Teman> notes) {
            super.onPostExecute(notes);
            weakCallback.get().postExecute(notes);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == awal.tugas.uts10117094.view.TemanAddUpdateActivity.REQUEST_ADD) {
                if (resultCode == awal.tugas.uts10117094.view.TemanAddUpdateActivity.RESULT_ADD) {
                    Teman note = data.getParcelableExtra(awal.tugas.uts10117094.view.TemanAddUpdateActivity.EXTRA_NOTE);

                    adapter.addItem(note);
                    recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);

                    showSnackbarMessage("Satu item berhasil ditambahkan");
                }
            } else if (requestCode == awal.tugas.uts10117094.view.TemanAddUpdateActivity.REQUEST_UPDATE) {
                if (resultCode == awal.tugas.uts10117094.view.TemanAddUpdateActivity.RESULT_UPDATE) {
                    Teman note = data.getParcelableExtra(awal.tugas.uts10117094.view.TemanAddUpdateActivity.EXTRA_NOTE);
                    int position = data.getIntExtra(awal.tugas.uts10117094.view.TemanAddUpdateActivity.EXTRA_POSITION, 0);

                    adapter.updateItem(position, note);
                    recyclerView.smoothScrollToPosition(position);

                    showSnackbarMessage("Satu item berhasil dirubah");
                } else if (resultCode == awal.tugas.uts10117094.view.TemanAddUpdateActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(awal.tugas.uts10117094.view.TemanAddUpdateActivity.EXTRA_POSITION, 0);

                    adapter.removeItem(position);

                    showSnackbarMessage("Satu item berhasil dihapus");
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        temanHelper.close();
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }
}

interface LoadTemanCallback {
    void preExecute();
    void postExecute(ArrayList<Teman> notes);
}
