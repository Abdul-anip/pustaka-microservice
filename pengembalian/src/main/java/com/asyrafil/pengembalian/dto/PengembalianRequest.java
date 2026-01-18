package com.asyrafil.pengembalian.dto;

public class PengembalianRequest {
    private long peminjaman_id;
    private String tanggal_pengembalian;
    private double denda;
    private String email;
    private String namaBuku;

    // Constructors
    public PengembalianRequest() {
    }

    public PengembalianRequest(long peminjaman_id, String tanggal_pengembalian, double denda, String email,
            String namaBuku) {
        this.peminjaman_id = peminjaman_id;
        this.tanggal_pengembalian = tanggal_pengembalian;
        this.denda = denda;
        this.email = email;
        this.namaBuku = namaBuku;
    }

    // Getters and Setters
    public long getPeminjaman_id() {
        return peminjaman_id;
    }

    public void setPeminjaman_id(long peminjaman_id) {
        this.peminjaman_id = peminjaman_id;
    }

    public String getTanggal_pengembalian() {
        return tanggal_pengembalian;
    }

    public void setTanggal_pengembalian(String tanggal_pengembalian) {
        this.tanggal_pengembalian = tanggal_pengembalian;
    }

    public double getDenda() {
        return denda;
    }

    public void setDenda(double denda) {
        this.denda = denda;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamaBuku() {
        return namaBuku;
    }

    public void setNamaBuku(String namaBuku) {
        this.namaBuku = namaBuku;
    }
}
