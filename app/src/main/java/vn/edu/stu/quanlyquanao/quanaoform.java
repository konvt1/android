package vn.edu.stu.quanlyquanao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import Dao.DBHelper;
import model.NhaCungCap;
import model.QuanAo;
import util.DBConfigUtil;

public class quanaoform extends AppCompatActivity {
    static final int REQUEST_CHOOSE_PHOTO=321;
    ImageView imageQuanAo;
    Spinner spinner;
    EditText etTenQuanAo,etPrice,etSoLuong,etMota;
    DBHelper dbHelper;

    ArrayList<NhaCungCap> dsNhaCungCap = new ArrayList<>();
    ArrayAdapter<String> spinnerTen;
    Button btnLuu,btnTroVe,btnChon;
    QuanAo QuanAo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanaoform);
        Intent intent = getIntent();
        QuanAo = (QuanAo) intent.getSerializableExtra("QuanAo");
        dbHelper=new DBHelper(quanaoform.this);
        DBConfigUtil.copyDatabaseFromAssets(quanaoform.this);
        addControls();
        addEvents();

    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mnuAbout){
            Intent intent = new Intent(quanaoform.this, aboutH.class);
            startActivity(intent);
            finish();
        }
        else if (item.getItemId()==R.id.mnuExit){
            Intent intent = new Intent(quanaoform.this, listquanao.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void addEvents() {
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(quanaoform.this, listquanao.class);
                startActivity(intent);
                finish();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(QuanAo!=null){
                    QuanAo.setTen(etTenQuanAo.getText().toString());
                    QuanAo.setPrice(Double.parseDouble(etPrice.getText().toString()));

                    QuanAo.setMota(etMota.getText().toString());
                    QuanAo.setImage(getBytesFromImageView(imageQuanAo));
                    dbHelper.updateQuanAo(QuanAo);
                }
                else {
                    QuanAo=new QuanAo();
                    QuanAo.setTen(etTenQuanAo.getText().toString());
                    QuanAo.setPrice(Double.parseDouble(etPrice.getText().toString()));

                    QuanAo.setMota(etMota.getText().toString());
                    QuanAo.setImage(getBytesFromImageView(imageQuanAo));
                    dbHelper.addQuanAo(QuanAo);
                }
                Intent intent = new Intent(quanaoform.this, listquanao.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addControls() {
        this.spinner = (Spinner) findViewById(R.id.spinner);
        etTenQuanAo = findViewById(R.id.etTenQuanAo);
        etPrice=findViewById(R.id.etPrice);
        etSoLuong=findViewById(R.id.etSoLuong);
        etMota=findViewById(R.id.etMota);
        btnLuu= findViewById(R.id.btnLuu);
        btnTroVe= findViewById(R.id.btnTroVe);
        btnChon=findViewById(R.id.btnChon);
        imageQuanAo=(ImageView) findViewById(R.id.imageQuanAo);
        if(QuanAo!=null){
            etTenQuanAo.setText(QuanAo.getTen());
            etPrice.setText(QuanAo.getPrice()+"");
            etMota.setText(QuanAo.getMota());
            Bitmap bmImageQuanAo= BitmapFactory.decodeByteArray(QuanAo.getImage(),0,QuanAo.getImage().length);
            imageQuanAo.setImageBitmap(bmImageQuanAo);
            selectedAdapterNcc();
        }
        else {
            hienthiAdapterNcc();
        }
    }
    private void selectedAdapterNcc(){
        dsNhaCungCap=(ArrayList<NhaCungCap>) dbHelper.getAllNhaCungCap();
        ArrayList<String> dsTenNCC = new ArrayList<>();
        int pos = -1;
        for(int i=0;i<dsNhaCungCap.size();i++) {
            dsTenNCC.add(dsNhaCungCap.get(i).getTen());
            if(dsNhaCungCap.get(i).getId()==QuanAo.getIdncc()){
                pos = i;
            }
        }
        spinnerTen = new ArrayAdapter(quanaoform.this, android.R.layout.simple_spinner_dropdown_item, dsTenNCC);
        spinnerTen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerTen);
        spinner.setSelection(pos);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                QuanAo.setIdncc(dsNhaCungCap.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void hienthiAdapterNcc(){
        dsNhaCungCap=(ArrayList<NhaCungCap>) dbHelper.getAllNhaCungCap();
        ArrayList<String> ds = new ArrayList<>();
        for(int i=0;i<dsNhaCungCap.size();i++)
            ds.add(dsNhaCungCap.get(i).getTen());
        spinnerTen = new ArrayAdapter(quanaoform.this, android.R.layout.simple_spinner_dropdown_item, ds);
        spinnerTen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerTen);
    }
    private void choosePhoto(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if (requestCode==REQUEST_CHOOSE_PHOTO){
                try {
                    Uri imageUri=data.getData();
                    InputStream inputStream=getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                    imageQuanAo.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private byte[] getBytesFromImageView(ImageView imageQuanAo) {
        BitmapDrawable drawable=(BitmapDrawable) imageQuanAo.getDrawable();
        Bitmap bitmap=drawable.getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray=stream.toByteArray();
        return byteArray;
    }
    
}