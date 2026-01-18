package com.asyrafil.peminjaman.vo;

public class Buku {
    private long id;
    private String judul;
    private String pengarang;
    private String penerbit;
    private Integer tahun_terbit;

    public Buku(){

    }

    public Buku(Long id, String judul, String pengarang, String penerbit, Integer tahun_terbit){
        this.id = id;
        this.judul = judul;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.tahun_terbit = tahun_terbit;
    }

    public long getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public Integer getTahun_terbit() {
        return tahun_terbit;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public void setTahun_terbit(Integer tahun_terbit) {
        this.tahun_terbit = tahun_terbit;
    }

    @Override
    public String toString(){
        return super.toString();
    }

}