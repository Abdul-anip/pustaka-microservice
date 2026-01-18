package com.asyrafil.pengembalian.vo;

import java.sql.Date;

public class Peminjaman {
    private long id;
    private long anggota_id;
    private long buku_id;
    private Date tanggal_pinjam;
    private Date tanggal_kembali;

    public Peminjaman(){

    }

    public Peminjaman(Long id, Long anggota_id, Long buku_id, Date tanggal_pinjam, Date tanggal_kembali){

        this.id = id;
        this.anggota_id = anggota_id;
        this.buku_id = buku_id;
        this.tanggal_pinjam = tanggal_pinjam;
        this.tanggal_kembali = tanggal_kembali;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAnggota_id() {
        return anggota_id;
    }

    public void setAnggota_id(long anggota_id) {
        this.anggota_id = anggota_id;
    }

    public long getBuku_id() {
        return buku_id;
    }

    public void setBuku_id(long buku_id) {
        this.buku_id = buku_id;
    }

    public Date getTanggal_pinjam() {
        return tanggal_pinjam;
    }

    public void setTanggal_pinjam(Date tanggal_pinjam) {
        this.tanggal_pinjam = tanggal_pinjam;
    }

    public Date getTanggal_kembali() {
        return tanggal_kembali;
    }

    public void setTanggal_kembali(Date tanggal_kembali) {
        this.tanggal_kembali = tanggal_kembali;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}