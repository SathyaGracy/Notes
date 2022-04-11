package com.zeyaly.notes.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zeyaly.notes.R;
import com.zeyaly.notes.databinding.NotesRowBinding;
import com.zeyaly.notes.model.NoteModel;

import java.util.ArrayList;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {
    public ArrayList<NoteModel> mDataset;
    private Activity mContext;
    int row_index = 0;
    NotesRowBinding binding = null;


    public NotesListAdapter(Activity mContext, ArrayList<NoteModel> myDataset) {
        this.mDataset = myDataset;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.notes_row, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        NoteModel noteModel=mDataset.get(position);
        /*if(position%2 == 0){
           binding.cards.setCardBackgroundColor(mContext.getResources().getColor(R.color.blue));
        } else if(position%2 == 1) {
           binding.cards.setCardBackgroundColor(mContext.getResources().getColor(R.color.skyBlue));

        } else  {
            binding.cards.setCardBackgroundColor(mContext.getResources().getColor(R.color.dark_pink_color));

        }*/
        int[] androidColors = mContext.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        binding.cards.setCardBackgroundColor(randomAndroidColor);
        binding.text.setText(noteModel.getTitle());
        binding.textContent.setText(noteModel.getTextValue());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(NotesRowBinding binding) {
            super(binding.getRoot());


        }
    }


}
