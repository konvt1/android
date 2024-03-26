package vn.edu.stu.quanlyquanao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Menuquanao extends AppCompatActivity {
    Button btnQuanAo;
    Button btnDsNhaCungCap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuquanao);
        addControls();
        addEvents();
    }
    private void addControls() {
        btnQuanAo =findViewById(R.id.btnQuanAo);
        btnDsNhaCungCap=findViewById(R.id.btnNhaCungCap);
    }

    private void addEvents() {
        btnDsNhaCungCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menuquanao.this, listnhacungcap.class);
                startActivity(intent);
                finish();
            }
        });
        btnQuanAo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menuquanao.this, listquanao.class);
                startActivity(intent);
                finish();
            }
        });
    }

}