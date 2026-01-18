package com.asyrafil.peminjaman.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Peminjaman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long anggota_id;
    private long buku_id;
    private Date tanggal_pinjam;
    private Date tanggal_kembali;
}
