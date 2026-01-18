package com.asyrafil.pengembalian.vo;

import com.asyrafil.pengembalian.model.Pengembalian;

public class ResponseTemplate {
    Pengembalian pengembalian;
    Peminjaman peminjaman;

    public ResponseTemplate(){

    }

    public ResponseTemplate(Pengembalian pengembalian, Peminjaman peminjaman){
        this.peminjaman = peminjaman;
        this.pengembalian = pengembalian;
    }

    public Pengembalian getPengembalian() {
        return pengembalian;
    }

    public void setPengembalian(Pengembalian pengembalian) {
        this.pengembalian = pengembalian;
    }

    public Peminjaman getPeminjaman() {
        return peminjaman;
    }

    public void setPeminjaman(Peminjaman peminjaman) {
        this.peminjaman = peminjaman;
    }

}
