package com.zeyaly.notes.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zeyaly.notes.R;

import com.zeyaly.notes.bean.CreateBean;
import com.zeyaly.notes.databinding.CheckboxRowBinding;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    public ArrayList<CreateBean> mDataset;
    private Activity mContext;
    int row_index = 0;
    CheckboxRowBinding binding = null;


    public NotesAdapter(Activity mContext, ArrayList<CreateBean> myDataset) {
        this.mDataset = myDataset;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.checkbox_row, parent, false);

        return new ViewHolder(binding);

     /*   View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_row_layout, parent, false);
        //   itemView.getLayoutParams().width = (int) (getScreenWidth() / 3);*/
        // return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        binding.closeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);
                /*if (mContext instanceof CreateNotes) {
                    ((CreateNotes)mContext).Remove(position);
                }*/
            }
        });
        binding.closeLayout.setVisibility(View.GONE);
        if((mDataset.size()-1)==position){
            binding.closeLayout.setVisibility(View.VISIBLE);

        }



    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(CheckboxRowBinding binding) {
            super(binding.getRoot());


        }
    }
  /* public class ViewHolder extends RecyclerView.ViewHolder {
       CatagoryLayoutBinding binding;

       public ViewHolder(CatagoryLayoutBinding binding) {
           super(binding.getRoot());
           this.binding = binding;
       }


   }*/
  public void addNewRow() {
      this.mDataset.add(new CreateBean());
      notifyDataSetChanged();
  }
    public void removeAt(int position) {

        mDataset.remove(position);
        notifyDataSetChanged();
       /* notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataset.size());*/
    }
}


