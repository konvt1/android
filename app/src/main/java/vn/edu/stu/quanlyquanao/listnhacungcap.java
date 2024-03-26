package vn.edu.stu.quanlyquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import Dao.DBHelper;
import model.NhaCungCap;
import model.QuanAo;
import util.DBConfigUtil;

public class listnhacungcap extends AppCompatActivity {
    DBHelper dbHelper;
    ArrayList<NhaCungCap> dsNhaCungCap=new ArrayList<>();
    ArrayList<QuanAo> dsQuanAo=new ArrayList<>();
    ListView lvNcc;
    ArrayAdapter<NhaCungCap> adapter;
    Button btnThem;
    Button btnTroVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listnhacungcap);
        DBConfigUtil.copyDatabaseFromAssets(listnhacungcap.this);
        dbHelper=new DBHelper(listnhacungcap.this);
        addControls();
        getDataNcc();
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
            Intent intent = new Intent(listnhacungcap.this, aboutH.class);
            startActivity(intent);
            finish();
        }
        else if (item.getItemId()==R.id.mnuExit){
            Intent intent = new Intent(listnhacungcap.this, Menuquanao.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void addEvents() {
        lvNcc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(listnhacungcap.this, nhacungcap_form.class);
                NhaCungCap ncc=dsNhaCungCap.get(position);
                intent.putExtra("nhacungcap", ncc);
                startActivity(intent);
            }
        });
        lvNcc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                xuLyAlertDialog(position);
                return false;
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listnhacungcap.this, Menuquanao.class);
                startActivity(intent);
                finish();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listnhacungcap.this, nhacungcap_form.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void getDataNcc(){
        dsNhaCungCap=(ArrayList<NhaCungCap>)dbHelper.getAllNhaCungCap();
        adapter=new ArrayAdapter<>(listnhacungcap.this,
                android.R.layout.simple_list_item_1,
                dsNhaCungCap);
        lvNcc.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void addControls() {

        lvNcc=findViewById(R.id.lvDsNcc);
        btnTroVe=findViewById(R.id.btnTroVe);
        btnThem=findViewById(R.id.btnThem);
    }
    private void xulyXoa(int position) {
        NhaCungCap nhaCungCap = adapter.getItem(position);
        if (dbHelper.hasQuanAos(nhaCungCap.getId())) {
            showDeleteConfirmationDialog(position);
        } else {
            dbHelper.deleteNcc(nhaCungCap.getId());
            getDataNcc();
        }

    }
    private void xuLyAlertDialog(int index) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Ban co muon xoa");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                xulyXoa(index);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    private void showDeleteConfirmationDialog(int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Không xóa được vì nhà cung cấp này có QuanAo");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataNcc();
    
    }
}