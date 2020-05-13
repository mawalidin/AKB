package awal.tugas.uts10117094.presenter;

//tanggal pengerjaan:5/12/2020
//nim: 10117094
//nama: Muhammad Walidin
//kelas: IF-3

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import awal.tugas.uts10117094.model.Teman;
import awal.tugas.uts10117094.R;
import awal.tugas.uts10117094.view.TemanAddUpdateActivity;

//tanggal pengerjaan:5/12/2020
//nim: 10117094
//nama: Muhammad Walidin
//kelas: IF-3
public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {
    private ArrayList<Teman> listTeman = new ArrayList<>();
    private Activity activity;

    public TemanAdapter(Activity activity){
        this.activity = activity;
    }

    public ArrayList<Teman> getListNotes(){
        return listTeman;
    }

    public void setListNotes(ArrayList<Teman> listNotes){
        if (listNotes.size() > 0 ){
            this.listTeman.clear();
        }
        this.listTeman.addAll(listNotes);

        notifyDataSetChanged();
    }

    public void addItem(Teman note){
        this.listTeman.add(note);
        notifyItemInserted(listTeman.size() -1);
    }

    public void updateItem(int position, Teman note){
        this.listTeman.set(position, note);
        notifyItemChanged(position, note);
    }

    public void removeItem(int position){
        this.listTeman.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listTeman.size());
    }

    @NonNull
    @Override
    public TemanAdapter.TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemanAdapter.TemanViewHolder holder, int position) {
        holder.tvNim.setText(listTeman.get(position).getNim());
        holder.tvNama.setText(listTeman.get(position).getNama());
        holder.tvKelas.setText(listTeman.get(position).getKelas());
        holder.tvTelp.setText(listTeman.get(position).getTelpon());
        holder.tvEmail.setText(listTeman.get(position).getEmail());
        holder.tvIg.setText(listTeman.get(position).getIg());
        holder.tvDate.setText(listTeman.get(position).getDate());
        holder.cvNote.setOnClickListener(new awal.tugas.uts10117094.presenter.CustomOnItemClickListener(position, new awal.tugas.uts10117094.presenter.CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, TemanAddUpdateActivity.class);
                intent.putExtra(TemanAddUpdateActivity.EXTRA_POSITION, position);
                intent.putExtra(TemanAddUpdateActivity.EXTRA_NOTE, listTeman.get(position));
                activity.startActivityForResult(intent, TemanAddUpdateActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listTeman.size();
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder{
        final TextView tvNim, tvNama, tvKelas, tvTelp, tvEmail, tvIg, tvDate;
        final CardView cvNote;

        public TemanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNim = itemView.findViewById(R.id.tv_item_nim);
            tvNama = itemView.findViewById(R.id.tv_item_nama);
            tvKelas = itemView.findViewById(R.id.tv_item_kelas);
            tvTelp = itemView.findViewById(R.id.tv_item_telpon);
            tvEmail = itemView.findViewById(R.id.tv_item_email);
            tvIg = itemView.findViewById(R.id.tv_item_ig);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            cvNote = itemView.findViewById(R.id.cv_item_note);
        }
    }
}
