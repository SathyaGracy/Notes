package com.zeyaly.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zeyaly.notes.activity.CreateNotes;
import com.zeyaly.notes.database.DatabaseHandler;
import com.zeyaly.notes.databinding.ActivityMaBinding;
import com.zeyaly.notes.fragment.NoteListFragment;
import com.zeyaly.notes.utils.CurvedBottomNavigation;


public class Main extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    Fragment selectedFragment = null;
    FragmentTransaction transaction;
    ActivityMaBinding binding;
    DatabaseHandler databaseHandler;
    private int ResultSuccess=101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ma);
        CurvedBottomNavigation mView = findViewById(R.id.customBottomBar);
        binding.customBottomBar.inflateMenu(R.menu.bottom_menu);
        binding.customBottomBar.setSelectedItemId(R.id.add);
        //getting bottom navigation view and attaching the listener
        binding.customBottomBar.setOnNavigationItemSelectedListener(Main.this);

        selectedFragment = NoteListFragment.newInstance();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();
        initView();
    }

    private void initView() {
        databaseHandler = new DatabaseHandler(this);
        binding.linId.setOnClickListener(this);
        //databaseHandler.deleteAll();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notes:
                selectedFragment = NoteListFragment.newInstance();
                break;
            case R.id.add:
                /*Intent intent = new Intent(Main.this, CreateNotes.class);
                startActivity(intent);*/
               /* selectedFragment = NoteListFragment.newInstance();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();*/
                break;
            case R.id.checklist:
                selectedFragment = NoteListFragment.newInstance();
                break;
            case R.id.remainder:
                selectedFragment = NoteListFragment.newInstance();
                break;
            case R.id.lock:
                selectedFragment = NoteListFragment.newInstance();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_id:
                Intent intent = new Intent(Main.this, CreateNotes.class);
                startActivityForResult(intent,ResultSuccess);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(ResultSuccess==requestCode){
            selectedFragment = NoteListFragment.newInstance();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
        }
    }
}
