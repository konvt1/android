package vn.edu.stu.quanlyquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import Dao.DBHelper;
import adapter.QuanAoAdapter;
import model.NhaCungCap;
import model.QuanAo;
import util.DBConfigUtil;



public class listquanao extends AppCompatActivity {
    DBHelper dbHelper;
    ListView lvQuanAo;
    QuanAoAdapter adapter;
    Button btnThem;
    Button btnTroVe;
    ArrayList<QuanAo> dsQuanAo=new ArrayList<>();
    ArrayList<NhaCungCap> dsNhaCungCap=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listquanao);
        DBConfigUtil.copyDatabaseFromAssets(listquanao.this);
        addControls();
        dbHelper=new DBHelper(listquanao.this);
        addEvents();
        hienthilistquanao();
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
            Intent intent = new Intent(listquanao.this, aboutH.class);
            startActivity(intent);
            finish();
        }
        else if (item.getItemId()==R.id.mnuExit){
            Intent intent = new Intent(listquanao.this, Menuquanao.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void hienthilistquanao() {
        dsQuanAo =(ArrayList<QuanAo>) dbHelper.getAllQuanAo();
        dsNhaCungCap=(ArrayList<NhaCungCap>)dbHelper.getAllNhaCungCap();
        adapter = new QuanAoAdapter(
                listquanao.this,
                R.layout.custom_listview,
                dsQuanAo,
                dbHelper,
                dsNhaCungCap
        );
        lvQuanAo.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listquanao.this, Menuquanao.class);
                startActivity(intent);
                finish();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listquanao.this, quanaoform.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addControls() {
        lvQuanAo=findViewById(R.id.lvDsQuanAo);
        btnTroVe=findViewById(R.id.btnTroVe);
        btnThem=findViewById(R.id.btnThem);
    }



}