package model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class QuanAo implements Serializable {
    private int id;
    private String ten;
    private int idncc;
    private byte[] image;
    private double price;
    private String mota;

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public QuanAo() {
    }




    public QuanAo(int id, String ten, int idncc, byte[] image, double price, String mota) {
        this.id = id;
        this.ten = ten;
        this.idncc = idncc;
        this.image = image;
        this.price = price;

        this.mota = mota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdncc() {
        return idncc;
    }

    public void setIdncc(int idncc) {
        this.idncc = idncc;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }



    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public String getFormattedPrice() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormat.format(this.price);
    }
}




