package Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


import model.NhaCungCap;
import model.QuanAo;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quanlyQuanAo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME1 = "nhacungcap";
    private static final String COLUMN_idNcc = "id";
    private static final String COLUMN_tenNcc = "ten";
    private static final String TABLE_NAME2 = "quanao";
    private static final String COLUMN_idQuanAo = "id";
    private static final String COLUMN_tenQA = "ten";
    private static final String COLUMN_idNhaCungCap = "id_nhacungcap";
    private static final String COLUMN_image = "hinh";
    private static final String COLUMN_price = "gia";
    private static final String COLUMN_mota = "mota";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<QuanAo> getAllQuanAo(){
        List<QuanAo> quanAos = new ArrayList<>();
        String[] projection ={
                DBHelper.COLUMN_idQuanAo,
                DBHelper.COLUMN_tenQA,
                DBHelper.COLUMN_idNhaCungCap,
                DBHelper.COLUMN_image,
                DBHelper.COLUMN_price,
                DBHelper.COLUMN_mota
        };
        Cursor cursor = getReadableDatabase().query(
                DBHelper.TABLE_NAME2,projection,null,null,null,null,null
        );
        while (cursor.moveToNext()){
            QuanAo quanAo=new QuanAo(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_idQuanAo)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_tenQA)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_idNhaCungCap)),
                    cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_image)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_price)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_mota))
            );
            quanAos.add(quanAo);
        }
        cursor.close();
        return quanAos;
    }

    public List<NhaCungCap> getAllNhaCungCap(){
        List<NhaCungCap> nhaCungCaps = new ArrayList<>();
        String[] projection ={
                DBHelper.COLUMN_idNcc,
                DBHelper.COLUMN_tenNcc
        };
        Cursor cursor = getReadableDatabase().query(
                DBHelper.TABLE_NAME1,projection,null,null,null,null,null
        );
        while (cursor.moveToNext()){

            NhaCungCap nhaCungCap=new NhaCungCap(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_idNcc)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_tenNcc))
            );
            nhaCungCaps.add(nhaCungCap);
        }
        cursor.close();
        return nhaCungCaps;
    }
    public void addNcc(NhaCungCap nhaCungCap) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_tenNcc, nhaCungCap.getTen());
        long them= getWritableDatabase().insert(DBHelper.TABLE_NAME1, null, values);
    }
    public void updateNcc(NhaCungCap nhaCungCap){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_tenNcc, nhaCungCap.getTen());
        String selection = DBHelper.COLUMN_idNcc+" = ?";
        String[] selectionArgs={nhaCungCap.getId()+""};
        int updatedRows= getWritableDatabase().update(
                DBHelper.TABLE_NAME1,values,selection,selectionArgs
        );
    }
    public void deleteNcc(int ma){
        String selection = DBHelper.COLUMN_idNcc+" = ?";
        String[] selectionArgs={String.valueOf(ma)};
        int deletedRows= getWritableDatabase().delete(
                DBHelper.TABLE_NAME1,selection,selectionArgs
        );
    }
    public void addQuanAo(QuanAo quanAo) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_tenQA, quanAo.getTen());
        values.put(DBHelper.COLUMN_idNhaCungCap, quanAo.getIdncc());
        values.put(DBHelper.COLUMN_image, quanAo.getImage());
        values.put(DBHelper.COLUMN_price, quanAo.getPrice());
        values.put(DBHelper.COLUMN_mota, quanAo.getMota());
        long them= getWritableDatabase().insert(DBHelper.TABLE_NAME2, null, values);
    }
    public void deleteQuanAo(int ma){
        String selection = DBHelper.COLUMN_idQuanAo+" = ?";
        String[] selectionArgs={String.valueOf(ma)};
        int deletedRows= getWritableDatabase().delete(
                DBHelper.TABLE_NAME2,selection,selectionArgs
        );
    }
    public void updateQuanAo(QuanAo quanAo){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_tenQA, quanAo.getTen());
        values.put(DBHelper.COLUMN_idNhaCungCap, quanAo.getIdncc());
        values.put(DBHelper.COLUMN_image, quanAo.getImage());
        values.put(DBHelper.COLUMN_price, quanAo.getPrice());
        values.put(DBHelper.COLUMN_mota, quanAo.getMota());
        String selection = DBHelper.COLUMN_idQuanAo+" = ?";
        String[] selectionArgs={quanAo.getId()+""};
        int updatedRows= getWritableDatabase().update(
                DBHelper.TABLE_NAME2,values,selection,selectionArgs
        );
    }
    public boolean hasQuanAos(int idNcc) {
        String[] projection = {COLUMN_idQuanAo};
        String selection = COLUMN_idNhaCungCap + " = ?";
        String[] selectionArgs = {String.valueOf(idNcc)};

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME2, projection, selection, selectionArgs, null, null, null
        );
        boolean hasQuanAos = cursor.getCount() > 0;
        cursor.close();
        System.out.println( "Has QuanAos: " + hasQuanAos);
        return hasQuanAos;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
