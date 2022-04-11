/*
 * Copyright (c) 2017. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.zeyaly.notes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zeyaly.notes.R;
import com.zeyaly.notes.activity.EditNotes;
import com.zeyaly.notes.adapter.NotesListAdapter;
import com.zeyaly.notes.database.DatabaseHandler;
import com.zeyaly.notes.databinding.NoteListBinding;
import com.zeyaly.notes.model.NoteModel;
import com.zeyaly.notes.onItemClickListner.RecyclerTouchListener;

import java.util.ArrayList;

public class NoteListFragment extends Fragment implements View.OnClickListener {
    NoteListBinding binding;
    ArrayList<NoteModel> noteModelArrayList;
    NotesListAdapter notesListAdapter;
    DatabaseHandler databaseHandler;
    Boolean isFirst=false;
    Boolean isGridOn = false;


    public static NoteListFragment newInstance() {
        NoteListFragment fragment = new NoteListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.create_notes, container, false);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.note_list, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        databaseHandler = new DatabaseHandler(getActivity());
        dataAdd();
        binding.layoutMangager.setOnClickListener(this);
        binding.layoutgridMangager.setOnClickListener(this);
        isFirst=true;
        onClickListener();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutMangager:
                isGridOn = true;
                binding.layoutgridMangager.setVisibility(View.VISIBLE);
                binding.layoutMangager.setVisibility(View.GONE);
                binding.RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                notesListAdapter = new NotesListAdapter(getActivity(), noteModelArrayList);
                binding.RecyclerView.setAdapter(notesListAdapter);
                break;
            case R.id.layoutgridMangager:
                isGridOn = false;
                binding.layoutgridMangager.setVisibility(View.GONE);
                binding.layoutMangager.setVisibility(View.VISIBLE);
                binding.RecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                notesListAdapter = new NotesListAdapter(getActivity(), noteModelArrayList);
                binding.RecyclerView.setAdapter(notesListAdapter);
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirst) {
            dataAdd();
        }

    }
    private void onClickListener() {
        binding.RecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), binding.RecyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        //  dialogAmountEnter(position);
                        Intent intent=new Intent(getActivity(), EditNotes.class);
                        intent.putExtra("position", position+"");
                        startActivity(intent);

                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        //   dialogAlert(position);
                    }
                }));

    }
    private void dataAdd() {
        noteModelArrayList = new ArrayList<>();
        noteModelArrayList.addAll(databaseHandler.all());
        if (isGridOn) {
            binding.RecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            binding.RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        }
        notesListAdapter = new NotesListAdapter(getActivity(), noteModelArrayList);
        binding.RecyclerView.setAdapter(notesListAdapter);
        if(noteModelArrayList.size()>0){
            binding.RecyclerView.setVisibility(View.VISIBLE);
            binding.noData.setVisibility(View.GONE);
        }else {
            binding.RecyclerView.setVisibility(View.GONE);
            binding.noData.setVisibility(View.VISIBLE);
        }
    }
}
