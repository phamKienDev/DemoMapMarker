package com.hlub.dev.demomapclass;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MapDAO implements Constant {
    DatabaseManager databaseManager;
    SQLiteDatabase sqLiteDatabase;

    public MapDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        sqLiteDatabase = databaseManager.getWritableDatabase();
    }

    public long insertMap(Vitri viTri) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, viTri.getTitle());
        values.put(COLUMN_KINHDO, viTri.getKinhdo());
        values.put(COLUMN_VIDO, viTri.getVido());
        Log.d("sql", CREATE_TABLE);
        long id = sqLiteDatabase.insert(TABLE, null, values);
        Log.d("id", id + "");
        return id;
    }

    public long updateMap(Vitri viTri) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, viTri.getTitle());
        values.put(COLUMN_KINHDO, viTri.getKinhdo());
        values.put(COLUMN_VIDO, viTri.getVido());

        long update = sqLiteDatabase.update(TABLE,
                values,
                COLUMN_ID + " =?",
                new String[]{String.valueOf(viTri.getId())});
        Log.d("update", update + "");
        return update;
    }

    public long delete(Vitri viTri) {
        return sqLiteDatabase.delete(TABLE, COLUMN_ID + "=?", new String[]{String.valueOf(viTri.getId())});
    }


    public Vitri getVitri(double kinh, double vi) {
        Vitri viTri = new Vitri();
        String sql = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_KINHDO + " = d" + kinh + " AND " + COLUMN_VIDO + " = " + vi;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        Log.d("sql", sql);
        if (cursor.moveToFirst() && cursor != null) {
            viTri.setId(cursor.getLong(0));
            viTri.setTitle(cursor.getString(1));
            viTri.setKinhdo(cursor.getLong(2));
            viTri.setVido(cursor.getLong(3));
        }
        return viTri;
    }

    public List<Vitri> getAllViTri() {
        List<Vitri> viTris = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Vitri viTri = new Vitri();
                viTri.setId(cursor.getLong(0));
                viTri.setTitle(cursor.getString(1));
                viTri.setKinhdo(cursor.getLong(2));
                viTri.setVido(cursor.getLong(3));

                viTris.add(viTri);
            } while (cursor.moveToNext());
        }
        return viTris;
    }


    public Vitri getViTriById(String id) {
        Vitri viTri = null;
        Cursor cursor = sqLiteDatabase.query(TABLE,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_KINHDO, COLUMN_VIDO},
                COLUMN_ID + "=?",
                new String[]{id},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            viTri = new Vitri();
            viTri.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
            viTri.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            viTri.setKinhdo(cursor.getLong(cursor.getColumnIndex(COLUMN_KINHDO)));
            viTri.setVido(cursor.getLong(cursor.getColumnIndex(COLUMN_VIDO)));

        }
        return viTri;


    }

}
