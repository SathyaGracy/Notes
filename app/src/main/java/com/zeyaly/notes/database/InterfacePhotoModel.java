package com.zeyaly.notes.database;


import com.zeyaly.notes.model.NoteModel;

import java.util.List;

public interface InterfacePhotoModel {

    long addValue(NoteModel Model);
    NoteModel get(int index);
    List<NoteModel> all();
    void remove(long index);
    void update(NoteModel Model, long index);
    void updateRemainderONOFF(NoteModel Model, long index);
    void updateSecurityONOFF(NoteModel Model, long index);

}
