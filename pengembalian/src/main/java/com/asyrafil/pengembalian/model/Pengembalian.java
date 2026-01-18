package com.asyrafil.pengembalian.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Pengembalian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long peminjaman_id;
    private Date tanggal_dikembalikan;
    private Boolean terlambat;
    private Long denda;
}   
