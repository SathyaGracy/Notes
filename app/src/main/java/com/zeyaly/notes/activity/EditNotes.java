package com.zeyaly.notes.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zeyaly.notes.R;
import com.zeyaly.notes.adapter.NotesAdapter;
import com.zeyaly.notes.bean.CreateBean;
import com.zeyaly.notes.database.DatabaseHandler;
import com.zeyaly.notes.databinding.CreateNotesBinding;
import com.zeyaly.notes.model.NoteModel;
import com.zeyaly.notes.utils.TransistionAnimation;

import java.util.ArrayList;

public class EditNotes extends AppCompatActivity implements View.OnClickListener {
    CreateNotesBinding binding;
    NotesAdapter notesAdapter;
    ArrayList<CreateBean> createBeanArrayList;
    Dialog dialogBottom;
    Dialog dialogBottomSelect;
    DatabaseHandler databaseHandler;
    ArrayList<NoteModel> noteModelArrayList;
    NoteModel noteModel;
    long Index;
    String position;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.create_notes);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        TransistionAnimation transistionAnimation = new TransistionAnimation();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(transistionAnimation.enterTransition());
            getWindow().setSharedElementReturnTransition(transistionAnimation.returnTransition());
        }
        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            position = b.getString("position");
        }
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        databaseHandler = new DatabaseHandler(EditNotes.this);
        noteModelArrayList=new ArrayList<>();
        noteModelArrayList.addAll(databaseHandler.all());
        dialogBottom = new Dialog(this, R.style.CustomDialog);
        dialogBottom.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        dialogBottom.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        dialogBottom.getWindow().setStatusBarColor(Color.TRANSPARENT);

        dialogBottomSelect = new Dialog(this, R.style.CustomDialog);
        dialogBottomSelect.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialogBottomSelect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottomSelect.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        dialogBottomSelect.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        dialogBottomSelect.getWindow().setStatusBarColor(Color.TRANSPARENT);

        binding.more.setOnClickListener(this);
        binding.notesCheck.setOnClickListener(this);
        binding.backArrow.setOnClickListener(this);
        createBeanArrayList = new ArrayList<>();
        binding.checkList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        notesAdapter = new NotesAdapter(this, createBeanArrayList);
        binding.checkList.setAdapter(notesAdapter);
        addItem();
        Index=noteModelArrayList.get(Integer.parseInt(position)).getId();
        binding.title.setText(noteModelArrayList.get(Integer.parseInt(position)).getTitle());
        binding.textContent.setText(noteModelArrayList.get(Integer.parseInt(position)).getTextValue());
        binding.listItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();

            }
        });


        editTextChangeListener();

    }

    private void editTextChangeListener() {
        binding.textContent.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    noteModel = new NoteModel();
                    noteModel.setTitle(binding.title.getText().toString());
                    noteModel.setDes(binding.textContent.getText().toString());
                    databaseHandler.update(noteModel, Index);
                }
            }
        });
        binding.title.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    noteModel = new NoteModel();
                    noteModel.setTitle(binding.title.getText().toString());
                    noteModel.setDes(binding.textContent.getText().toString());
                    databaseHandler.update(noteModel, Index);
                }
            }
        });
    }

    public void addItem() {
        CreateBean newValue = new CreateBean();
        createBeanArrayList.add(newValue);
        // notesAdapter.notifyItemRangeChanged(0, notesAdapter.getItemCount());
        notesAdapter.notifyItemRangeInserted(notesAdapter.getItemCount(), createBeanArrayList.size() - 1);
    }

    public void Remove(int postion) {
        // notesAdapter.notifyItemRangeChanged(getAdapterPosition(),mDataSet.size());
        createBeanArrayList.remove(postion);
        notesAdapter.notifyItemRemoved(postion);
    }

    private void showBottomDialogMore() {
        dialogBottomSelect.dismiss();
        dialogBottom.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBottom.setContentView(R.layout.dialog_bottom_more);
        dialogBottom.setCanceledOnTouchOutside(true);

        Window window = dialogBottom.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialogBottom.show();
        // parent_layout.setAlpha(0.2f);

        dialogBottom.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // parent_layout.setAlpha(1.0f);

            }
        });
    }

    private void showBottomDialogSelect() {
        dialogBottom.dismiss();
        dialogBottomSelect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBottomSelect.setContentView(R.layout.dialog_check_note);
        dialogBottomSelect.setCanceledOnTouchOutside(true);

        RelativeLayout NoteLayout = dialogBottomSelect.findViewById(R.id.NoteLayout);
        RelativeLayout CheckListLayout = dialogBottomSelect.findViewById(R.id.CheckListLayout);

        Window window = dialogBottomSelect.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialogBottomSelect.show();
        // parent_layout.setAlpha(0.2f);

        dialogBottomSelect.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // parent_layout.setAlpha(1.0f);

            }
        });
        NoteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkListNex.setVisibility(View.GONE);
                binding.NotePageLayout.setVisibility(View.VISIBLE);
                dialogBottomSelect.dismiss();
            }
        });
        CheckListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkListNex.setVisibility(View.VISIBLE);
                binding.NotePageLayout.setVisibility(View.GONE);
                dialogBottomSelect.dismiss();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more:
                if (dialogBottom.isShowing()) {
                    dialogBottom.dismiss();
                } else {
                    showBottomDialogMore();
                }
                break;
            case R.id.notes_check:
                showBottomDialogSelect();
                break;
            case R.id.backArrow:
                finish();
                break;
        }
    }
}
