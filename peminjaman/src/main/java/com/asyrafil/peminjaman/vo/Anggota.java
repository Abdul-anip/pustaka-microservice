package com.asyrafil.peminjaman.vo;

public class Anggota {
    private long id;
    private long nim;
    private String nama;
    private String alamat;
    private String jenis_kelamin;

    public Anggota(){
    }

    public Anggota(Long id, Long nim, String nama, String alamat, String jenis_kelamin){
        this.id = id;
        this.nim = nim;
        this.nama = nama;
        this.alamat = alamat;
        this.jenis_kelamin = jenis_kelamin;
    }

    public long getId() {
        return id;
    }

    public long getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNim(long nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
