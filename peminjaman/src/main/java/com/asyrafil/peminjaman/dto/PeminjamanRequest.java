package com.asyrafil.peminjaman.dto;

public class PeminjamanRequest {
    private long anggota_id;
    private long buku_id;
    private String tanggal_pinjam;
    private String tanggal_kembali;
    private String email;
    private String namaBuku;

    // Constructors
    public PeminjamanRequest() {
    }

    public PeminjamanRequest(long anggota_id, long buku_id, String tanggal_pinjam, String tanggal_kembali, String email,
            String namaBuku) {
        this.anggota_id = anggota_id;
        this.buku_id = buku_id;
        this.tanggal_pinjam = tanggal_pinjam;
        this.tanggal_kembali = tanggal_kembali;
        this.email = email;
        this.namaBuku = namaBuku;
    }

    // Getters and Setters
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

    public String getTanggal_pinjam() {
        return tanggal_pinjam;
    }

    public void setTanggal_pinjam(String tanggal_pinjam) {
        this.tanggal_pinjam = tanggal_pinjam;
    }

    public String getTanggal_kembali() {
        return tanggal_kembali;
    }

    public void setTanggal_kembali(String tanggal_kembali) {
        this.tanggal_kembali = tanggal_kembali;
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
