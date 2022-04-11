package com.zeyaly.notes.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jaimenejaim on 23/08/17.
 */

public class NoteModel implements Serializable {
    private int id;

    private Date created_at;
    private String Des;
    private String Title;
    private String RemainderONOFF;
    private String RemainderDate;
    private String RemainderTime;

    public String getRemainderONOFF() {
        return RemainderONOFF;
    }

    public void setRemainderONOFF(String remainderONOFF) {
        RemainderONOFF = remainderONOFF;
    }

    public String getRemainderDate() {
        return RemainderDate;
    }

    public void setRemainderDate(String remainderDate) {
        RemainderDate = remainderDate;
    }

    public String getRemainderTime() {
        return RemainderTime;
    }

    public void setRemainderTime(String remainderTime) {
        RemainderTime = remainderTime;
    }

    public String getSecurityPIN() {
        return SecurityPIN;
    }

    public void setSecurityPIN(String securityPIN) {
        SecurityPIN = securityPIN;
    }

    public String getSecurityLockONOFF() {
        return SecurityLockONOFF;
    }

    public void setSecurityLockONOFF(String securityLockONOFF) {
        SecurityLockONOFF = securityLockONOFF;
    }

    private String SecurityPIN;
    private String SecurityLockONOFF;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTextValue() {
        return TextValue;
    }

    public void setTextValue(String textValue) {
        TextValue = textValue;
    }

    private String TextValue;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
    public String getDes() {
        return Des;
    }
    public void setDes(String des) {
        this.Des = des;
    }
}
