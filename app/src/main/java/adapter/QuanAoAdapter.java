package adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Dao.DBHelper;
import model.QuanAo;
import model.NhaCungCap;

import vn.edu.stu.quanlyquanao.R;
import vn.edu.stu.quanlyquanao.quanaoform;

public class QuanAoAdapter extends ArrayAdapter<QuanAo> {
    Activity context;
    int resource;
    List<QuanAo> quanAos;
    DBHelper dbHelper;

    ArrayList<NhaCungCap> dsNhaCungCap;


    public QuanAoAdapter(Activity context, int resource, List<QuanAo> QuanAos, DBHelper dbHelper, ArrayList<NhaCungCap> dsNhaCungCap) {
        super(context, resource, QuanAos);
        this.context = context;
        this.resource =  resource;
        this.quanAos = QuanAos;
        this.dbHelper = dbHelper;
        this.dsNhaCungCap = dsNhaCungCap;
    }

    public String getTenNhaCungCap(int ma, ArrayList<NhaCungCap> ds){
        for (NhaCungCap ncc : ds) {
            if(ncc.getId()==ma) {
                return ncc.getTen();
            }
        }
        return null;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);
        ImageView imageQuanAo=(ImageView)item.findViewById(R.id.imageQuanAos);
        TextView txtIdQuanAo = item.findViewById(R.id.tvIdQuanAo);
        TextView txtTenQuanAo = item.findViewById(R.id.tvTenQuanAo);
        TextView txtNhaCungCap = item.findViewById(R.id.tvNhaCungCap);
        TextView txtPrice = item.findViewById(R.id.tvPrice);
        final Button btnSua = item.findViewById(R.id.btnSua);
        final Button btnXoa = item.findViewById(R.id.btnXoa);
        final QuanAo quanAo = quanAos.get(position);
        txtIdQuanAo.setText(quanAo.getId()+"");
        txtTenQuanAo.setText(quanAo.getTen());
        txtNhaCungCap.setText(getTenNhaCungCap(quanAo.getIdncc(), dsNhaCungCap));
        txtPrice.setText(quanAo.getFormattedPrice());
        Bitmap bmImageQuanAo= BitmapFactory.decodeByteArray(quanAo.getImage(),0,quanAo.getImage().length);
        imageQuanAo.setImageBitmap(bmImageQuanAo);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(context, quanaoform.class);
                QuanAo lt=quanAos.get(position);
                Intent.putExtra("dsNhaCungCap",dsNhaCungCap);
                Intent.putExtra("QuanAo", lt);
                context.startActivityForResult(Intent,789);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc muốn xóa lap top này?");

                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteQuanAo(quanAos.get(position).getId());
                        quanAos.remove(position);
                        notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return item;
    }
}
