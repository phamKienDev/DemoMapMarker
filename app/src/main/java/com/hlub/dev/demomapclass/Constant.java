package com.hlub.dev.demomapclass;

public interface Constant {
    String TABLE = "BanDo";

    String COLUMN_ID = "Id";
    String COLUMN_TITLE = "Title";
    String COLUMN_KINHDO = "KinhDo";
    String COLUMN_VIDO = "ViDo";

    String CREATE_TABLE = " CREATE TABLE " + TABLE + " ( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " +
            COLUMN_TITLE + " VARCHAR(50) NOT NULL , " +
            COLUMN_KINHDO + " LONG NOT NULL, " +
            COLUMN_VIDO + " LONG NOT NULL " +
            ")";
}
