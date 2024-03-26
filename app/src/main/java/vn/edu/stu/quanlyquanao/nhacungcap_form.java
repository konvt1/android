package vn.edu.stu.quanlyquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Dao.DBHelper;
import model.NhaCungCap;
import util.DBConfigUtil;

public class nhacungcap_form extends AppCompatActivity {
    Button btnLuu,btnTroVe;
    EditText edTenNcc;
    NhaCungCap nhaCungCap;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhacungcap_form);
        Intent intent = getIntent();
        nhaCungCap = (NhaCungCap) intent.getSerializableExtra("nhacungcap");
        dbHelper=new DBHelper(nhacungcap_form.this);
        DBConfigUtil.copyDatabaseFromAssets(nhacungcap_form.this);
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
            Intent intent = new Intent(nhacungcap_form.this, aboutH.class);
            startActivity(intent);
            finish();
        }
        else if (item.getItemId()==R.id.mnuExit){
            Intent intent = new Intent(nhacungcap_form.this, listnhacungcap.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nhacungcap_form.this, listnhacungcap.class);
                startActivity(intent);
                finish();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nhaCungCap!=null){
                    nhaCungCap.setTen(edTenNcc.getText().toString());
                    dbHelper.updateNcc(nhaCungCap);
                }
                else {
                    nhaCungCap=new NhaCungCap();
                    nhaCungCap.setTen(edTenNcc.getText().toString());
                    dbHelper.addNcc(nhaCungCap);
                }
                Intent intent = new Intent(nhacungcap_form.this, listnhacungcap.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addControls() {
        btnLuu=findViewById(R.id.btnLuu);
        btnTroVe=findViewById(R.id.btnTroVe);
        edTenNcc=findViewById(R.id.etTenNcc);
        if(nhaCungCap!=null){
            edTenNcc.setText(nhaCungCap.getTen());

        }
    
    }
}