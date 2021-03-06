package com.example.rmontoya.firebasetest.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rmontoya.firebasetest.EditActivity;
import com.example.rmontoya.firebasetest.R;
import com.example.rmontoya.firebasetest.model.Note;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public static String ROW_EXTRA = "ROW";
    private List<Note> elements;

    public NoteAdapter(List<Note> notes) {
        this.elements = notes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.rowText.setText(elements.get(position).getNoteValue());
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_row)
        TextView rowText;

        NoteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rowText.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Note note = elements.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), EditActivity.class);
            intent.putExtra(ROW_EXTRA, note);
            view.getContext().startActivity(intent);
        }
    }
}
